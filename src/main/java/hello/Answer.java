package hello;

public class Answer {
    String question, answer;
    int bet, teamNumber;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getBet() {
        return bet;
    }

    public void setBet(int bet) {
        this.bet = bet;
    }

    public int getTeamNumber() {
        return teamNumber;
    }

    public void setTeamNumber(int teamNumber) {
        this.teamNumber = teamNumber;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "question='" + question + '\'' +
                ", answer='" + answer + '\'' +
                ", bet=" + bet +
                ", teamNumber=" + teamNumber +
                '}';
    }
}
