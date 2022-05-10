package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.enums.PeopleColor;
import it.polimi.ingsw.server.model.enums.TowerColor;

import java.util.ArrayList;

public class Island {
    private final StudentSet inhabitants;
    private int numberOfTowers;
    private TowerColor towerColor;
    private boolean isBlocked;

    public Island(int initialstudent, StudentSet bag, ArrayList<PeopleColor> avaiablecolor) {
        this.inhabitants = new StudentSet();
        this.inhabitants.setStudentsRandomly(initialstudent, bag, avaiablecolor);
        this.numberOfTowers = 0;
        this.towerColor = null;
        this.isBlocked = false;
    }

    public TowerColor getTowerColor() {
        return towerColor;
    }

    public void setTowerColor(TowerColor towerColor) {
        this.towerColor = towerColor;
    }

    public StudentSet getInhabitants() {
        return inhabitants;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }

    public Player player_influence(ArrayList<Player> Players, ArrayList<Professor> Professors) { //ritorna nullo se nessuno ha influenza
        int max = 0, partialSum = 0;   //2 or 3 player
        Player maxInfluence = null;
        for (Player player : Players) {
            if (player.getSchoolBoard().getTowerColor().equals(towerColor)) partialSum++;
            for (Professor professor : Professors) {
                if (player.equals(professor.getHeldBy())) {
                    partialSum = partialSum + inhabitants.numStudentsbycolor(professor.getColor());
                }
            }
            if (partialSum > max) {
                max = partialSum;
                maxInfluence = player;
            } else if (partialSum == max) {
                maxInfluence = null;
            }
            partialSum = 0;
        }
        return maxInfluence; //ritorna player con piu influenza
    }

    public Team team_influence(ArrayList<Team> Teams, ArrayList<Professor> Professors) { //ritorna nullo se nessuno ha influenza
        int max = 0, partialSum = 0;   //4 player
        Team maxInfluence = null;

        for (Team team : Teams) {
            if (team.getPlayer1().getSchoolBoard().getTowerColor().equals(towerColor)) partialSum++;
            for (Professor professor : Professors) {
                if (team.getPlayer1().equals(professor.getHeldBy()) || team.getPlayer2().equals(professor.getHeldBy())) {
                    partialSum = partialSum + inhabitants.numStudentsbycolor(professor.getColor());
                }
            }
            if (partialSum > max) {
                max = partialSum;
                maxInfluence = team;
            } else if (partialSum == max) {
                maxInfluence = null;
            }
            partialSum = 0;
        }
        return maxInfluence; //ritorna team con piu influenza
    }

    public void placeTower() {
        this.numberOfTowers++;
    }

    public void controllIsland(Player influence_player) {
        setTowerColor(influence_player.getSchoolBoard().getTowerColor());
        influence_player.getSchoolBoard().placeTower();
    }

    public void controllIsland(Team influence_team) {
        setTowerColor(influence_team.getPlayer1().getSchoolBoard().getTowerColor());
        influence_team.getPlayer1().getSchoolBoard().placeTower();
        influence_team.getPlayer2().getSchoolBoard().placeTower();
    }


    public int getNumberOfTowers() {
        return numberOfTowers;
    }

    @Override
    public String toString() {
        return "Island{" +
                " inhabitants=" + inhabitants.toString() +
                ", numberOfTowers=" + numberOfTowers +
                ", towerColor=" + (towerColor == null ? "null" : towerColor.toString()) +
                ", isBlocked=" + isBlocked +
                '}';
    }
}
