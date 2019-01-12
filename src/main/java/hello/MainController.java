package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {


    /*

    CONTROLADORES:
    dasbboard - para consultar jogo no quadro
    setteams - para equipas registarem equipas
    setanswer - para jogadores chamarem enquanto apostam
    get_results - responde uma String com score

    pode vir dar jeito:
    removeteam?teamNumber=X
    clearallanswers - apaga todas as respostas dadas até ao momento (apenas apra a x pergunta atual)
    setactualquestion - caso a actual question necessite de ser muadada .... (vai de 0-10 visto q existem 10 questoes)
    resetgame - Realiza reset ao jogo. Apenas as equipas continuam definidas.
     */
    @Autowired
    SystemManagement systemManagement;

    int teamNumber = 1;


    @GetMapping("/dashboarddemo1")
    public String dashboarddemo1(Model model) {

        List<Team> teamList = new ArrayList<>();

        Team tmp = new Team();

        tmp.getListPlayers().add("jogador1");
        tmp.getListPlayers().add("jogador2");
        tmp.getListPlayers().add("jogador3");
        tmp.getListPlayers().add("jogador4");
        tmp.setNumber(4);

        teamList.add(tmp);
        teamList.add(tmp);
        teamList.add(tmp);
        teamList.add(tmp);

        model.addAttribute("teams", teamList);

        return "dashboarddemo1";
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("teams", systemManagement.teamList);

        return "dashboard";
    }

    @GetMapping("/setteams")
    public String setTeams(Model model) {
        Team team = new Team();
        team.getListPlayers().add("");
        model.addAttribute("team", team);
        return "setTeams";
    }

    @PostMapping("/setteams")
    public String addClient(Model model, @ModelAttribute("team") Team team, BindingResult bindingResult, RedirectAttributes attributes) {

        team.setNumber(teamNumber);
        systemManagement.getTeamList().add(team);
        teamNumber++;

        System.out.println("\n\n\n team: " + team);

        return "redirect:/startgame?teamNumber=" + (teamNumber-1);
    }

    @GetMapping("/removeteam")
    public String removeTeam(Model model, @ModelAttribute("teamNumber") int teamNumber) {
        systemManagement.getTeamList().remove(teamNumber-1);
        model.addAttribute("teams", systemManagement.teamList);
        return "redirect:/dashboard";
    }

    @GetMapping("/startgame")
    public String startGame(@RequestParam(name="teamNumber") String teamNumber, Model model) {
        model.addAttribute("teamNumber", teamNumber);

        Answer answer = new Answer();
        answer.setTeamNumber(Integer.parseInt(teamNumber));

        model.addAttribute("answer",answer);


        return "startGame";
    }

    @GetMapping("/clearallanswers")
    @ResponseBody
    public String clearAllAnswers(Model model) {

        systemManagement.getAnsweList().clear();

        System.out.println("\n\n\n\n\n asnwer list " + systemManagement.getAnsweList());

        return "Apagou todas as respostas para questão atual";
    }

    @GetMapping("/setactualquestion")
    @ResponseBody
    public String setActualQuestion(@RequestParam(name="questionNumber") int questionNumber, Model model) {

        systemManagement.setAtualQuestion(questionNumber);
        return "Definiu a question atual";
    }

    @GetMapping("/resetgame")
    @ResponseBody
    public String resetGame(Model model) {

        systemManagement.resetGame();

        return "Foi realizado reset ao jogo. Apenas as equipas ficaram definidas.";
    }

    @PostMapping("/setanswer")
    public String setAnswer(@ModelAttribute("answer") Answer answer, Model model) {

        System.out.println("\n\n\n\n\n " + answer);

        systemManagement.processAnswer(answer);

        System.out.println("\n\n\n\n\n asnwer list " + systemManagement.getAnsweList());


        return "redirect:/startgame?teamNumber=" + answer.getTeamNumber();

    }

    @GetMapping("/get_results")
    @ResponseBody
    public String get_statics(Model model) {

        String results = systemManagement.getResults();
        System.out.println("\n\n\n\n\n asnwer list " + systemManagement.getAnsweList());

        return results;

    }

}