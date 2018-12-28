package hello;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    SystemManagement systemManagement;

    int teamNumber = 1;

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

        System.out.println("\n\n\n team: " + team);
        team.setNumber(teamNumber);
        systemManagement.getTeamList().add(team);
        teamNumber++;

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

        return "startGame";
    }

}