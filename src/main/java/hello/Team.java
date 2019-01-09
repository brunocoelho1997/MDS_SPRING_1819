package hello;

import java.util.ArrayList;
import java.util.List;

public class Team {

    List<String> listPlayers;
    int number;
    int totalPoints;

    public Team() {
        this.listPlayers = new ArrayList<>();
        this.totalPoints = 10;
    }

    public List<String> getListPlayers() {
        return listPlayers;
    }

    public void setListPlayers(List<String> listPlayers) {
        this.listPlayers = listPlayers;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(int totalPoints) {
        this.totalPoints = totalPoints;
    }

    @Override
    public String toString() {
        return "Team{" +
                "listPlayers=" + listPlayers +
                ", number=" + number +
                ", totalPoints=" + totalPoints +
                '}';
    }
}
