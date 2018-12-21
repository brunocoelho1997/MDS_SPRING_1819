package hello;

import java.util.ArrayList;
import java.util.List;

public class Team {

    List<String> listPlayers;
    int number;

    public Team() {
        this.listPlayers = new ArrayList<>();
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

    @Override
    public String toString() {
        return "Team{" +
                "listPlayers=" + listPlayers +
                '}';
    }
}
