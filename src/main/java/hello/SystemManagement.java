package hello;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SystemManagement {

    List<Team> teamList = new ArrayList<>();

    List<Answer> answerList = new ArrayList<>();

    Map corretAnswers= new HashMap();

    int atualQuestion;

    List<String> possibleAnswersList;

    public SystemManagement() {
        this.corretAnswers.put("1", "C");
        this.corretAnswers.put("2", "C");
        this.corretAnswers.put("3", "D");
        this.corretAnswers.put("4", "D");
        this.corretAnswers.put("5", "B");
        this.corretAnswers.put("6", "D");
        this.corretAnswers.put("7", "C");
        this.corretAnswers.put("8", "C");
        this.corretAnswers.put("9", "E");
        this.corretAnswers.put("10", "A");

        this.atualQuestion = 1;

        possibleAnswersList = new ArrayList<>();
        possibleAnswersList.add("A");
        possibleAnswersList.add("B");
        possibleAnswersList.add("C");
        possibleAnswersList.add("D");
        possibleAnswersList.add("E");
    }

    public List<Team> getTeamList() {
        return teamList;
    }

    public void setTeamList(List<Team> teamList) {
        this.teamList = teamList;
    }

    public List<Answer> getAnsweList() {
        return answerList;
    }

    public void setAnsweList(List<Answer> answerList) {
        this.answerList = answerList;
    }

    public int getAtualQuestion() {
        return atualQuestion;
    }

    public void setAtualQuestion(int atualQuestion) {
        this.atualQuestion = atualQuestion;
    }


    /*
    auxiliar methods
     */
    public void processAnswer(Answer answer) {

        try
        {
            Team teamTmp = null;
            for(Team team : teamList)
            {
                if(team.getNumber()==answer.getTeamNumber())
                {
                    teamTmp = team;
                    break;
                }
            }
            if(teamTmp == null || teamTmp.getTotalPoints()<=0)
                return;

            if(Integer.parseInt(answer.getQuestion()) != atualQuestion)
                return;

            if(!possibleAnswersList.contains(answer.getAnswer().toUpperCase()))
                return;

            if(answer.getBet()<=0)
                return;

            if(teamTmp.totalPoints < answer.bet)
                return;

            for(Answer answerTmp : answerList)
            {
                if(answer.teamNumber == answerTmp.teamNumber)
                {
                    answerTmp.setAnswer(answer.answer);
                    answerTmp.setBet(answer.bet);
                    return;
                }
            }
            answerList.add(answer);

        }catch (Exception e){
            return;
        }

    }

    public void refreshAllScores()
    {
        boolean teamAnswed;

        for(Team teamTmp : teamList)
        {
            teamAnswed = false;
            for(Answer answerTmp : answerList)
            {
                if(answerTmp.getTeamNumber() == teamTmp.getNumber())
                {
                    teamAnswed = true;

                    if(isCorrectAnswer(answerTmp))
                        teamTmp.setTotalPoints(teamTmp.getTotalPoints() + answerTmp.getBet());
                    else
                        teamTmp.setTotalPoints(teamTmp.getTotalPoints() - answerTmp.getBet());

                }
            }

            if(!teamAnswed && teamTmp.getTotalPoints()>0)
                teamTmp.setTotalPoints(teamTmp.getTotalPoints() - 1);
        }
        answerList.clear();

        atualQuestion++;
    }

    private boolean isCorrectAnswer(Answer answerTmp) {
        String correctAnswer = (String) corretAnswers.get(answerTmp.question);

        if(correctAnswer.equals(answerTmp.answer))
            return true;
        return false;
    }

    public void resetGame(){
        this.atualQuestion = 1;
        answerList.clear();
        for(Team team : teamList)
        {
            team.setTotalPoints(10);
        }
    }

    public String getResults(){
        String results = new String();

        refreshAllScores();


        for(Team team : teamList)
        {
            results += "Equipa " + team.getNumber() + " tem " + team.totalPoints + " pontos! \n";
        }

        if(results.isEmpty())
            results+="Sem resultados de equipas.";
        return results;
    }


}
