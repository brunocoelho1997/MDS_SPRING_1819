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



        /*
        <!DOCTYPE html>
        <html>
        <body>

        <h2>HTML Iframes</h2>
        <p>You can use the height and width attributes to specify the size of the iframe:</p>

        <iframe src="https://docs.google.com/spreadsheets/d/e/2PACX-1vSQ1QnzpFeFU8biZgnG6n_ff_X93LiLtojrfqSFG1P9-TUlfZqMybfXLsr0O_afyR3BmpLInDgtS1pC/pubhtml?gid=274706901&amp;single=true&amp;widget=true&amp;headers=false"

        style="height:500px;width:500px;"

        ></iframe>

        </body>
        </html>

         */


        return "dashboarddemo1";
    }

    @GetMapping("/dashboard")
    public String dashboard(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("teams", systemManagement.teamList);

        return "dashboard";
    }




    @GetMapping("/greeting")
    public String greeting(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        List<String> listPlayers = new ArrayList<>();
        model.addAttribute("listPlayers", listPlayers);

        return "greeting";
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
    public String clearAllAnswers(@RequestParam(name="teamNumber") String teamNumber, Model model) {

        systemManagement.getAnsweList().clear();
        return "Apagou todas as respostas para questão atual";
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
        return results;

    }

}