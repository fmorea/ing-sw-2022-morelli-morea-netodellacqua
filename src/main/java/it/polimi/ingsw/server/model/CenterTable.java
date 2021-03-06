package it.polimi.ingsw.server.model;

import it.polimi.ingsw.server.model.characters.*;
import it.polimi.ingsw.server.model.characters.Character;
import it.polimi.ingsw.server.model.enums.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * This class models the so-called "Center Table",
 * a physical object that contains the clouds, the islands,
 * the bag, and so on.
 *
 * @author Ignazio Neto Dell'Acqua
 */
public class CenterTable {
    private final ArrayList<Cloud> clouds;
    private final ArrayList<Island> islands;
    private int motherNaturePosition;
    private final StudentSet bag;
    private final ArrayList<Professor> professors;
    private final ArrayList<TowerColor> availableTowerColor;
    private final ArrayList<CharacterCard> characterCards;
    private boolean centaurEffect;
    private Player farmerEffect;
    private PeopleColor mushroomColor;
    private Player knightEffect;
    private StudentSet princessSet, jesterSet, monkSet;
    private int numDivieti;

    public static final String ANSI_CYAN = "\033[0;36m";
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * The constructor permit to Set up the Table as declared in the rules in function of numOfPlayers and game mode
     * @param debug is used for testing to unlock all the 12 cards (and not only 3)
     */
    public CenterTable(int numOfPlayers, GameMode gamemode, boolean debug) {

        islands = new ArrayList<>();
        StudentSet island_bag = new StudentSet(2, 2, 2, 2, 2);

        for (int i = 0; i < 12; i++) {
            if (i == 0 || i == 6) {
                islands.add(new Island(0, island_bag));
            } else {
                islands.add(new Island(1, island_bag));
            }
        }

        motherNaturePosition = 0;

        bag = new StudentSet(24, 24, 24, 24, 24); //24

        availableTowerColor = new ArrayList<>();
        if (numOfPlayers == 3) {
            Collections.addAll(availableTowerColor, TowerColor.values());
        } else {
            availableTowerColor.add(TowerColor.WHITE);
            availableTowerColor.add(TowerColor.BLACK);
        }

        professors = new ArrayList<>();
        for (PeopleColor Color : PeopleColor.values()) {
            professors.add(new Professor(Color));
        }

        clouds = new ArrayList<>();
        for (int i = 0; i < numOfPlayers; i++) {
            clouds.add(new Cloud(numOfPlayers));
            clouds.get(i).charge(bag);
        }

        if (gamemode.equals(GameMode.EXPERT)) {
            characterCards = new ArrayList<>();
            ArrayList<Integer> picks = new ArrayList<>();

            var ref = new Object() {
                int pick;
            };

            if (debug){
                            characterCards.add(new Monk(bag));
                            monkSet = ((Monk) characterCards.get(0)).getSet();

                            characterCards.add(new Thief());

                            characterCards.add(new Farmer());

                            characterCards.add(new Granny());
                            numDivieti = ((Granny) characterCards.get(3)).getNumDivieti();

                            characterCards.add(new Herald());

                            characterCards.add(new Jester(bag));
                            jesterSet = ((Jester) characterCards.get(5)).getSet();

                            characterCards.add(new Knight());

                            characterCards.add(new Centaur());

                            characterCards.add(new Postman());

                            characterCards.add(new Princess(bag));
                            princessSet = ((Princess) characterCards.get(9)).getSet();

                            characterCards.add(new Minstrel());

                            characterCards.add(new MushroomHunter());
            }
            else {
                for (int i = 0; i < 3; i++) {
                    do {
                        ref.pick = new Random().nextInt(Character.values().length);
                    } while (picks.stream().anyMatch(j -> j.equals(ref.pick)));
                    picks.add(ref.pick);
                    switch (Character.values()[ref.pick]) {
                        case MONK:
                            characterCards.add(new Monk(bag));
                            monkSet = ((Monk) characterCards.get(i)).getSet();
                            break;
                        case THIEF:
                            characterCards.add(new Thief());
                            break;
                        case FARMER:
                            characterCards.add(new Farmer());
                            break;
                        case GRANNY:
                            characterCards.add(new Granny());
                            numDivieti = ((Granny) characterCards.get(i)).getNumDivieti();
                            break;
                        case HERALD:
                            characterCards.add(new Herald());
                            break;
                        case JESTER:
                            characterCards.add(new Jester(bag));
                            jesterSet = ((Jester) characterCards.get(i)).getSet();
                            break;
                        case KNIGHT:
                            characterCards.add(new Knight());
                            break;
                        case CENTAUR:
                            characterCards.add(new Centaur());
                            break;
                        case POSTMAN:
                            characterCards.add(new Postman());
                            break;
                        case PRINCESS:
                            characterCards.add(new Princess(bag));
                            princessSet = ((Princess) characterCards.get(i)).getSet();
                            break;
                        case MINSTRELL:
                            characterCards.add(new Minstrel());
                            break;
                        case MUSHROOM_HUNTER:
                            characterCards.add(new MushroomHunter());
                            break;


                    }
                }
            }
        } else {
            characterCards = null;
        }
        centaurEffect = false;
        farmerEffect = null;
        mushroomColor = null;
        knightEffect = null;
    }

    public ArrayList<Professor> getProfessors() {
        return professors;
    }

    public StudentSet getBag() {
        return bag;
    }

    public ArrayList<TowerColor> getAvailableTowerColor() {
        return availableTowerColor;
    }

    /**
     * Method permit to change the professor of the player
     * @param player the player
     * @param color color to update the professor
     */
    public void changeProfessor(Player player, PeopleColor color) {
        for (Professor professor : professors) {
            if (professor.getColor().equals(color)) professor.setHeldBy(player);
        }
    }

    public ArrayList<Island> getIslands() {
        return islands;
    }

    /**
     * Method permits to move mother nature of n position.
     * Modular arithmetic is used, considering the islands are "cyclical"
     * (mother nature can return to the same island several times going forward)
     * @param moves number of moves that mother nature has to do
     */
    public void mother(int moves) {
        motherNaturePosition = (motherNaturePosition + moves) % islands.size();
    }

    public int getMotherNaturePosition() {
        return motherNaturePosition;
    }

    /**
     * Add at the specified island inhabitants the color passed as argument and remove the color from the entrance of the player
     * @param player the player
     * @param color the color
     * @param index_island the index of the island
     */

    public void loadIsland(Player player, PeopleColor color, int index_island) {
        player.getSchoolBoard().getEntranceSpace().removeStudent(1, color);
        islands.get(index_island).getInhabitants().addStudents(1, color);
    }

    /**
     * Check who has the professor and change the holding of it if it's needed (i.e. if someone hase more color in dinner or by specific cases for example by using cards . See the rules to better understand)
     * @param color the color to check
     * @param players list of players
     */

    public void checkProfessor(PeopleColor color, ArrayList<Player> players) {
        int max = 0;
        Player moreInfluenced = null;
        for (Player player : players) {
            if (player.getSchoolBoard().getDinnerTable().numStudentsByColor(color) > max) {
                max = player.getSchoolBoard().getDinnerTable().numStudentsByColor(color);
                moreInfluenced = player;
            } else if (player.getSchoolBoard().getDinnerTable().numStudentsByColor(color) == max) {
                moreInfluenced = null;
            }
        }
        if (farmerEffect != null && farmerEffect.getSchoolBoard().getDinnerTable().numStudentsByColor(color) == max)
            moreInfluenced = farmerEffect;
        if (moreInfluenced != null) changeProfessor(moreInfluenced, color);
    }

    /**
     * Method to conquest an island. The method permit to change all the tower on the island (and consequently re-add those in the School of the player) with
     * the tower of the influence_player (who conquest the island) and remove latter from the School.
     * @param index_island index of the island to conquest
     * @param players players list
     * @param influence_player player with the influence
     */

    public void conquestIsland(int index_island, ArrayList<Player> players, Player influence_player) {
        for (Player player : players) {
            if (player.getSchoolBoard().getTowerColor().equals(islands.get(index_island).getTowerColor())) {
                for (int i = 0; i < islands.get(index_island).getNumberOfTowers(); i++) {
                    player.getSchoolBoard().removeTower();
                }
            }
        }
        islands.get(index_island).controlIsland(influence_player);
    }

    /**
     * Similar to the conquestIsland method, but with 4 player rules
     * @param index_island index of the island
     * @param teams teams list
     * @param influence_team team with the influence
     */
    public void conquestIsland(int index_island, ArrayList<Team> teams, Team influence_team) {
        for (Team team : teams) {
            if (team.getPlayer1().getSchoolBoard().getTowerColor().equals(islands.get(index_island).getTowerColor())) {
                for (int i = 0; i < islands.get(index_island).getNumberOfTowers(); i++) {
                    team.getPlayer1().getSchoolBoard().removeTower();
                    team.getPlayer2().getSchoolBoard().removeTower();
                }
            }
        }
        islands.get(index_island).controlIsland(influence_team);
    }

    /**
     * Method to merge two adjacent islands into a single one. The island resulted is the fist one plus the second island inhabitants plus the second island number of towers.
     * Then the method remove the second one from the array list of island
     * @param index_1 index of the first island
     * @param index_2 index of the second island
     */
    public void mergeIsland(int index_1, int index_2) {
        islands.get(index_1).setNumberOfTowers(islands.get(index_1).getNumberOfTowers() + islands.get(index_2).getNumberOfTowers());
        for (PeopleColor Color : PeopleColor.values()) {
            islands.get(index_1).getInhabitants().addStudents(islands.get(index_2).getInhabitants().numStudentsByColor(Color), Color);
        }
        islands.get(index_1).setBlocked(islands.get(index_1).isBlocked());
        islands.remove(index_2);
        if (index_2 < index_1) motherNaturePosition--;
        if (index_2 == 0 && index_1 == islands.size()) motherNaturePosition = islands.size() - 1;
    }

    public ArrayList<Cloud> getClouds() {
        return clouds;
    }

    /**
     * This method permit to print all the Table graphics for the CLI View
     * @param isLastTurn = this flag set as true hide the view of the bag and of the clouds
     * @return the Table View
     */
    public String toString(boolean isLastTurn) {
        return "-----------------------------------------TABLE----------------------------------------------------------------------------------------------------------------------------------------\n" +
                "\n----------------ISLANDS---------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n" + printIslands() + (!isLastTurn ? "------------------BAG-----------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n" +
                "    SIZE : " + bag.size() + "    " + bag +
                "\n----------------CLOUDS----------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n" + printClouds() : "") +
                (characterCards == null ? "--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n" :
                        "-----------CHARACTER-CARD---------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n" + printCharacter()) +
                "---------------PROFESSORS-------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n" +
                printProfessors() + "\n\n--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------\n\n";
    }

    /**
     * Util method for the CLI View graphics to represent the clouds
     * @return Clouds View
     */

    public String printClouds() {
        StringBuilder result = new StringBuilder();
        int i = 1;
        for (Cloud cloud : clouds) {
            result.append(ANSI_CYAN).append("    CLOUD : ").append(i).append(ANSI_RESET).append("\n").append(cloud.toString());
            i++;
        }
        return result.toString();
    }

    /**
     * Util method for the CLI View graphics to represent the character cards
     * @return Characters Card View
     */

    public String printCharacter() {
        StringBuilder result = new StringBuilder();
        for (CharacterCard c : characterCards) {
            result.append(c.toString());
            switch (c.getName()) {
                case "PRINCESS":
                    result.append("    STUDENTS:   ").append(princessSet.toString()).append("\n\n");
                    break;
                case "JESTER":
                    result.append("    STUDENTS:   ").append(jesterSet.toString()).append("\n\n");
                    break;
                case "MONK":
                    result.append("    STUDENTS:   ").append(monkSet.toString()).append("\n\n");
                    break;
                case "GRANNY":
                    result.append("    BANS:   ").append(numDivieti).append("\n\n");
                    break;
                default:
                    result.append("\n\n");
                    break;
            }
        }
        return result.toString();
    }

    /**
     * Util method for the CLI View graphics to represent the islands
     * @return Islands View
     */

    public String printIslands() {
        StringBuilder result = new StringBuilder();
        int i = 1;
        for (Island island : islands) {
            result.append(ANSI_CYAN).append("    ISLAND : ").append(i);
            if (i == (motherNaturePosition + 1)) {
                result.append(" - MOTHER NATURE IS HERE ").append(ANSI_RESET).append("\n").append(island);
            } else {
                result.append(ANSI_RESET).append("\n").append(island);
            }
            i++;
        }
        return result.toString();
    }

    /**
     * Util method for the CLI View to represent the professors
     * @return ProfessorsView
     */

    public String printProfessors() {
        StringBuilder result = new StringBuilder();
        int i = 0;
        for (Professor prof : professors) {
            result.append(i != 0 ? " |  " + prof.toString() : prof.toString());
            i++;
        }
        return result.toString();
    }

    public ArrayList<CharacterCard> getCharacters() {
        return characterCards;
    }

    public boolean isCentaurEffect() {
        return centaurEffect;
    }

    public void setCentaurEffect(boolean centaurEffect) {
        this.centaurEffect = centaurEffect;
    }

    public PeopleColor getMushroomColor() {
        return mushroomColor;
    }

    public void setMushroomColor(PeopleColor mushroomColor) {
        this.mushroomColor = mushroomColor;
    }

    public Player getKnightEffect() {
        return knightEffect;
    }

    public void setKnightEffect(Player knightEffect) {
        this.knightEffect = knightEffect;
    }

    public void setFarmerEffect(Player farmerEffect) {
        this.farmerEffect = farmerEffect;
    }

    public StudentSet getJesterSet() {
        return jesterSet;
    }

    public StudentSet getMonkSet() {
        return monkSet;
    }

    public StudentSet getPrincessSet() {
        return princessSet;
    }

    public int getNumDivieti() {
        return numDivieti;
    }

}