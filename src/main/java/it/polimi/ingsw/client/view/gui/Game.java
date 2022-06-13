package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.server.model.*;
import it.polimi.ingsw.server.model.characters.CharacterCard;
import it.polimi.ingsw.server.model.enums.GameMode;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import static it.polimi.ingsw.client.GUI.*;
import static it.polimi.ingsw.client.view.gui.Position.*;

public class Game implements Initializable {
    private final GUI gui = new GUI();
    @FXML
    private Label phaseLabel;
    @FXML
    private Label turnLabel;

    @FXML
    private Button assistantCardBtn;
    @FXML
    private Button setOnSchoolBtn;
    @FXML
    private Button setOnIslandBtn;
    @FXML
    private Button moveBtn;
    @FXML
    private Button cloudBtn;

    @FXML
    private GridPane islandGrid;

    @FXML
    private GridPane entrance1Grid;
    @FXML
    private GridPane entrance2Grid;
    @FXML
    private GridPane entrance3Grid;
    @FXML
    private GridPane entrance4Grid;

    @FXML
    private GridPane school1Grid;
    @FXML
    private GridPane school2Grid;
    @FXML
    private GridPane school3Grid;
    @FXML
    private GridPane school4Grid;

    @FXML
    private GridPane tower1Grid;
    @FXML
    private GridPane tower2Grid;
    @FXML
    private GridPane tower3Grid;
    @FXML
    private GridPane tower4Grid;

    @FXML
    private ImageView school1;
    @FXML
    private ImageView school2;
    @FXML
    private ImageView school3;
    @FXML
    private ImageView school4;

    @FXML
    private GridPane professor1Grid;
    @FXML
    private GridPane professor2Grid;
    @FXML
    private GridPane professor3Grid;
    @FXML
    private GridPane professor4Grid;

    @FXML
    private ImageView motherNature;

    @FXML
    private ImageView assistantCard1;
    @FXML
    private ImageView assistantCard2;
    @FXML
    private ImageView assistantCard3;
    @FXML
    private ImageView assistantCard4;

    @FXML
    private ImageView characterCard1;
    @FXML
    private ImageView characterCard2;
    @FXML
    private ImageView characterCard3;
    @FXML
    private ImageView characterCard4;
    @FXML
    private ImageView characterCard5;
    @FXML
    private ImageView characterCard6;
    @FXML
    private ImageView characterCard7;
    @FXML
    private ImageView characterCard8;
    @FXML
    private ImageView characterCard9;
    @FXML
    private ImageView characterCard10;
    @FXML
    private ImageView characterCard11;
    @FXML
    private ImageView characterCard12;

    @FXML
    private GridPane character1Grid;
    @FXML
    private GridPane character2Grid;
    @FXML
    private GridPane character3Grid;
    @FXML
    private GridPane character4Grid;

    @FXML
    private ImageView coin1;
    @FXML
    private ImageView coin2;
    @FXML
    private ImageView coin3;
    @FXML
    private ImageView coin4;

    @FXML
    private Label coin1Label;
    @FXML
    private Label coin2Label;
    @FXML
    private Label coin3Label;
    @FXML
    private Label coin4Label;

    @FXML
    private Label playerName1;
    @FXML
    private Label playerName2;
    @FXML
    private Label playerName3;
    @FXML
    private Label playerName4;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        currNode = phaseLabel;
        phaseLabel.setText(gameState);
        turnLabel.setText("è il turno di: " + this.gui.getClientModel().getServermodel().getcurrentPlayer().getNickname());
        if (myTurn) {
            switch (this.gui.getClientModel().getTypeOfRequest()) {
                case "CHOOSEASSISTANTCARD":
                    System.out.println("si sceglie la carta assistente");
                    assistantCardBtn.setVisible(true);
                    setOnSchoolBtn.setVisible(false);
                    setOnIslandBtn.setVisible(false);
                    moveBtn.setVisible(false);
                    cloudBtn.setVisible(false);
                    break;
                case "CHOOSEWHERETOMOVESTUDENTS":
                    assistantCardBtn.setVisible(false);
                    setOnSchoolBtn.setVisible(true);
                    setOnIslandBtn.setVisible(true);
                    moveBtn.setVisible(false);
                    cloudBtn.setVisible(false);
                    break;
                case "CHOOSEWHERETOMOVEMOTHER":
                    assistantCardBtn.setVisible(false);
                    setOnSchoolBtn.setVisible(false);
                    setOnIslandBtn.setVisible(false);
                    moveBtn.setVisible(true);
                    cloudBtn.setVisible(false);
                    break;
                case "CHOOSECLOUDS":
                    assistantCardBtn.setVisible(false);
                    setOnSchoolBtn.setVisible(false);
                    setOnIslandBtn.setVisible(false);
                    moveBtn.setVisible(false);
                    cloudBtn.setVisible(true);
                    break;
            }
        } else {
            assistantCardBtn.setVisible(false);
            setOnSchoolBtn.setVisible(false);
            setOnIslandBtn.setVisible(false);
            moveBtn.setVisible(false);
            cloudBtn.setVisible(false);
        }

        // OK! valori partita
        GameMode gameMode = this.gui.getClientModel().getServermodel().getGameMode();
        int motherNaturePos = this.gui.getClientModel().getServermodel().getTable().getMotherNaturePosition();
        ArrayList<Island> islands = this.gui.getClientModel().getServermodel().getTable().getIslands();
        ArrayList<Player> players = this.gui.getClientModel().getServermodel().getPlayers();
        ArrayList<Integer> assistantCardsValues = new ArrayList<>();
        players.forEach(player -> assistantCardsValues.add(0));
        players.forEach(player -> {
            if (player.getChoosedCard() != null) {
                assistantCardsValues.set(players.indexOf(player), (int) player.getChoosedCard().getValues());
            }
        });
        ArrayList<Professor> professors = this.gui.getClientModel().getServermodel().getTable().getProfessors();
        ArrayList<Cloud> clouds = this.gui.getClientModel().getServermodel().getTable().getClouds();
        Player currentPlayer = this.gui.getClientModel().getServermodel().getcurrentPlayer();

        ArrayList<Label> playerNames = new ArrayList<>(Arrays.asList(playerName1, playerName2, playerName3, playerName4));
        ArrayList<ImageView> assistantCards = new ArrayList<>(Arrays.asList(assistantCard1, assistantCard2, assistantCard3, assistantCard4));
        ArrayList<ImageView> characterCardsImages = new ArrayList<>(Arrays.asList(characterCard1, characterCard2, characterCard3, characterCard4, characterCard5, characterCard6, characterCard7, characterCard8, characterCard9, characterCard10, characterCard11, characterCard12));
        ArrayList<GridPane> professorGrids = new ArrayList<>(Arrays.asList(professor1Grid, professor2Grid, professor3Grid, professor4Grid));
        ArrayList<GridPane> entranceGrids = new ArrayList<>(Arrays.asList(entrance1Grid, entrance2Grid, entrance3Grid, entrance4Grid));
        ArrayList<GridPane> schoolGrids = new ArrayList<>(Arrays.asList(school1Grid, school2Grid, school3Grid, school4Grid));
        ArrayList<GridPane> towerGrids = new ArrayList<>(Arrays.asList(tower1Grid, tower2Grid, tower3Grid, tower4Grid));
        ArrayList<ImageView> schools = new ArrayList<>(Arrays.asList(school1, school2, school3, school4));
        ArrayList<ImageView> coins = new ArrayList<>(Arrays.asList(coin1, coin2, coin3, coin4));
        ArrayList<Label> coinLabels = new ArrayList<>(Arrays.asList(coin1Label, coin2Label, coin3Label, coin4Label));

        // OK! ISOLE
        islandGrid.setAlignment(Pos.CENTER);
        islands.forEach(island -> {
            StackPane tile = new StackPane();
            ImageView islandImage = new ImageView();
            switch (islands.indexOf(island) % 3) {
                case 0:
                    islandImage = new ImageView("/graphics/pieces/islands/island1.png");
                    break;
                case 1:
                    islandImage = new ImageView("/graphics/pieces/islands/island2.png");
                    break;
                case 2:
                    islandImage = new ImageView("/graphics/pieces/islands/island3.png");
                    break;
            }
            islandImage.setFitHeight(180);
            islandImage.setFitWidth(180);
            tile.getChildren().add(islandImage);

            //INIZIALIZZO GLI INHABITANTS
            GridPane students = new GridPane();
            students.setAlignment(Pos.CENTER);
            StudentSet islandSet = island.getInhabitants();
            populateGrid(students, 0, 3, islandSet);
            tile.getChildren().add(students);

            //INIZIALIZZO LE TORRI NELLE ISOLE
            GridPane towers = new GridPane();
            towers.setAlignment(Pos.BOTTOM_CENTER);
            towers.setHgap(-15);
            for (int j = 0; j < island.getNumberOfTowers(); j++) {
                ImageView towerImage = null;
                switch (island.getTowerColor()) {
                    case BLACK:
                        towerImage = new ImageView("graphics/pieces/towers/black_tower.png");
                        break;
                    case GREY:
                        towerImage = new ImageView("graphics/pieces/towers/grey_tower.png");
                        break;
                    case WHITE:
                        towerImage = new ImageView("graphics/pieces/towers/white_tower.png");
                        break;
                }
                towerImage.setFitWidth(40);
                towerImage.setFitHeight(40);
                towers.addRow(1, towerImage);
            }
            tile.getChildren().add(towers);

            //INIZIALIZZO MADRE NATURA
            if (islands.indexOf(island) == motherNaturePos) {
                tile.getChildren().add(motherNature);
                StackPane.setAlignment(motherNature, Pos.TOP_CENTER);
            }
            islandGrid.add(tile, islandX(islands.indexOf(island)), islandY(islands.indexOf(island)));

        });

        //NUVOLE
        clouds.forEach(cloud -> {
            StackPane tile = new StackPane();
            ImageView cloudImage = new ImageView("/graphics/pieces/clouds/cloud_card.png");
            imageResize(cloudImage, 120);
            tile.getChildren().add(cloudImage);
            GridPane studentsCloudGrid = new GridPane();
            studentsCloudGrid.setAlignment(Pos.CENTER);
            studentsCloudGrid.setHgap(10);
            StudentSet cloudSet = cloud.getStudentsAccumulator();

            populateGrid(studentsCloudGrid, 0, 2, cloudSet);
            tile.getChildren().add(studentsCloudGrid);
            islandGrid.add(tile, cloudX(clouds.indexOf(cloud)), cloudY(clouds.indexOf(cloud)));
        });

        // OK! STUDENT IN ENTRANCE
        entranceGrids.forEach(entrance -> entrance.setAlignment(Pos.CENTER));
        players.forEach(player -> {
            StudentSet entranceSet = player.getSchoolBoard().getEntranceSpace();
            populateGrid(entranceGrids.get(players.indexOf(player)), 1, 2, entranceSet);
        });

        // STUDENT IN SCHOOL
        players.forEach(player -> {
            GridPane school = schoolGrids.get(players.indexOf(player));
            if (school != null) {
                school.setAlignment(Pos.CENTER);
                for (int i = 0; i < player.getSchoolBoard().getDinnerTable().getNumOfBlueStudents(); i++) {
                    ImageView studentBlue = new ImageView("/graphics/pieces/students/student_blue.png");
                    imageResize(studentBlue, 30);
                    school.add(studentBlue, i, this.getColorPlace("blue"));
                }
                for (int i = 0; i < player.getSchoolBoard().getDinnerTable().getNumOfRedStudents(); i++) {
                    ImageView studentRed = new ImageView("/graphics/pieces/students/student_red.png");
                    imageResize(studentRed, 30);
                    school.add(studentRed, i, this.getColorPlace("red"));
                }
                for (int i = 0; i < player.getSchoolBoard().getDinnerTable().getNumOfGreenStudents(); i++) {
                    ImageView studentGreen = new ImageView("/graphics/pieces/students/student_green.png");
                    imageResize(studentGreen, 30);
                    school.add(studentGreen, i, this.getColorPlace("green"));
                }
                for (int i = 0; i < player.getSchoolBoard().getDinnerTable().getNumOfPinkStudents(); i++) {
                    ImageView studentPink = new ImageView("/graphics/pieces/students/student_pink.png");
                    imageResize(studentPink, 30);
                    school.add(studentPink, i, this.getColorPlace("pink"));
                }
                for (int i = 0; i < player.getSchoolBoard().getDinnerTable().getNumOfYellowStudents(); i++) {
                    ImageView studentYellow = new ImageView("/graphics/pieces/students/student_yellow.png");
                    imageResize(studentYellow, 30);
                    school.add(studentYellow, i, this.getColorPlace("yellow"));
                }
            }
        });

        //PROFESSORI
        professors.forEach(prof -> {
            if (prof.getHeldBy() != null) {
                Player choosenPlayer = null;
                for (Player player : players) {
                    if (player.getNickname().equals(prof.getHeldBy().getNickname())) {
                        choosenPlayer = player;
                    }
                }
                if (choosenPlayer != null) {
                    System.out.println("il player " + choosenPlayer.getNickname() + " si piglia il prof " + prof.getColor().name());
                    ImageView profImage = null;
                    String color = "";
                    switch (prof.getColor()) {
                        case BLUE:
                            profImage = new ImageView("graphics/pieces/professors/teacher_blue.png");
                            color = "blue";
                            break;
                        case RED:
                            profImage = new ImageView("graphics/pieces/professors/teacher_red.png");
                            color = "red";
                            break;
                        case PINK:
                            profImage = new ImageView("graphics/pieces/professors/teacher_pink.png");
                            color = "pink";
                            break;
                        case GREEN:
                            profImage = new ImageView("graphics/pieces/professors/teacher_green.png");
                            color = "green";
                            break;
                        case YELLOW:
                            profImage = new ImageView("graphics/pieces/professors/teacher_yellow.png");
                            color = "yellow";
                            break;
                    }
                    imageResize(profImage, 30);
                    professorGrids.get(players.indexOf(choosenPlayer)).add(profImage, 0, getColorPlace(color));
                }

            }
        });

        // OK! TORRI
        players.forEach(player -> {
            GridPane tower = towerGrids.get(players.indexOf(player));
            tower.setAlignment(Pos.CENTER);
            for (int i = 0; i < player.getSchoolBoard().getNumOfTowers(); i++) {
                ImageView towerImage = null;
                switch (player.getSchoolBoard().getTowerColor()) {
                    case BLACK:
                        towerImage = new ImageView("graphics/pieces/towers/black_tower.png");
                        break;
                    case WHITE:
                        towerImage = new ImageView("graphics/pieces/towers/white_tower.png");
                        break;
                    case GREY:
                        towerImage = new ImageView("graphics/pieces/towers/grey_tower.png");
                        break;
                }
                imageResize(towerImage, 50);
                tower.setHgap(-15);
                tower.add(towerImage, i % 2, i / 2);
            }
        });

        // OK! GAMEMODE
        if (gameMode.equals(GameMode.PRINCIPIANT)) {
            characterCardsImages.forEach(card -> card.setVisible(false));
            coins.forEach(coin -> coin.setVisible(false));
            coinLabels.forEach(coinLabel -> coinLabel.setVisible(false));
        } else {
            ArrayList<CharacterCard> characterCards = this.gui.getClientModel().getServermodel().getTable().getCharacters();
            toImageCharacters(characterCards, characterCardsImages);
            players.forEach(player -> {
                Label coinLabel = coinLabels.get(players.indexOf(player));
                coinLabel.setText("" + player.getCoins());
            });
            ArrayList<GridPane> characterGrids = new ArrayList<>(Arrays.asList(character1Grid, character2Grid, character3Grid, character4Grid));
            characterGrids.forEach(grid -> {
                grid.setAlignment(Pos.CENTER);
                StudentSet studentSet = null;
                switch (characterGrids.indexOf(grid)) {
                    case 0:
                        studentSet = gui.getClientModel().getServermodel().getTable().getMonkSet();
                        break;
                    case 1:
                        studentSet = gui.getClientModel().getServermodel().getTable().getPrincessSet();
                        break;
                    case 2:
                        studentSet = gui.getClientModel().getServermodel().getTable().getJesterSet();
                        break;
                    case 3:
                        studentSet = new StudentSet();
                        break;
                }
                assert studentSet != null;
                populateGrid(grid, 0, 2, studentSet);
            });

            characterCards.forEach(card -> characterCardsImages.get(characterCards.indexOf(card)).setOnMouseClicked(event -> {
                System.out.println("ho premuto " + card.getName());
                int cost = card.getCost();
                if (currentPlayer.getCoins() >= cost) {
                    currentCharacter = card;
                    try {
                        gui.openNewWindow("Character");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    System.out.println("non puoi!");
                }
            }));
        }

        // OK! NOMI E NUMERO GIOCATORI
        players.forEach(player -> playerNames.get(players.indexOf(player)).setText(player.getNickname()));
        if (players.size() < 4) {
            playerNames.get(3).setVisible(false);
            assistantCards.get(3).setVisible(false);
            schools.get(3).setVisible(false);
            coins.get(3).setVisible(false);
            coinLabels.get(3).setVisible(false);
        }
        if (players.size() < 3) {
            playerNames.get(2).setVisible(false);
            assistantCards.get(2).setVisible(false);
            schools.get(2).setVisible(false);
            coins.get(2).setVisible(false);
            coinLabels.get(2).setVisible(false);
        }

        // OK! ASSISTANT CARDS
        assistantCardsValues.forEach(value -> {
            if (value == 0) {
                assistantCards.get(assistantCardsValues.indexOf(value)).setVisible(false);
            } else {
                Image assistantImage = new Image("graphics/assistants/assistantCard" + assistantCardsValues.get(assistantCardsValues.indexOf(value)) + ".png");
                assistantCards.get(assistantCardsValues.indexOf(value)).setImage(assistantImage);
            }
        });

    }

    public void quit() throws IOException {
        this.gui.openNewWindow("Quit");
    }

    public void assistant() throws IOException {
        System.out.println("apro la finestra");
        this.gui.openNewWindow("ChooseAssistantCard");
    }

    public void setOnSchool() throws IOException {
        this.gui.openNewWindow("MoveToSchool");
    }

    public void setOnIsland() throws IOException {
        this.gui.openNewWindow("MoveToIsland");
    }

    public void cloud() throws IOException {
        this.gui.openNewWindow("ChooseCloud");
    }

    public void move() throws IOException {
        this.gui.openNewWindow("MoveMotherNature");
    }

    public void populateGrid(GridPane grid, int init, int cols, StudentSet studentSet) {
        int green = studentSet.getNumOfGreenStudents();
        int blue = studentSet.getNumOfBlueStudents();
        int red = studentSet.getNumOfRedStudents();
        int yellow = studentSet.getNumOfYellowStudents();
        int pink = studentSet.getNumOfPinkStudents();
        int position = init;
        for (int i = 0; i < green; i++) {
            ImageView student = new ImageView("/graphics/pieces/students/student_green.png");
            this.imageResize(student, 25);
            grid.add(student, position % cols, position / cols);
            position++;
        }
        for (int i = 0; i < red; i++) {
            ImageView student = new ImageView("/graphics/pieces/students/student_red.png");
            this.imageResize(student, 25);
            grid.add(student, position % cols, position / cols);
            position++;
        }
        for (int i = 0; i < yellow; i++) {
            ImageView student = new ImageView("/graphics/pieces/students/student_yellow.png");
            this.imageResize(student, 25);
            grid.add(student, position % cols, position / cols);
            position++;
        }
        for (int i = 0; i < pink; i++) {
            ImageView student = new ImageView("/graphics/pieces/students/student_pink.png");
            this.imageResize(student, 25);
            grid.add(student, position % cols, position / cols);
            position++;
        }
        for (int i = 0; i < blue; i++) {
            ImageView student = new ImageView("/graphics/pieces/students/student_blue.png");
            this.imageResize(student, 25);
            grid.add(student, position % cols, position / cols);
            position++;
        }
    }

    public void imageResize(ImageView image, int size) {
        image.setFitHeight(size);
        image.setFitWidth(size);
    }
    public int getColorPlace(String color) {
        int n = -1;
        switch (color) {
            case "green": n = 0; break;
            case "red": n = 1; break;
            case "yellow": n = 2; break;
            case "pink": n = 3; break;
            case "blue": n = 4; break;

        }
        return n;
    }

    public void toImageCharacters(ArrayList<CharacterCard> characterCard, List<ImageView> images) {
        for (int i = 0; i < 12; i++) { //todo debug
            Image character = null;
            switch (characterCard.get(i).getName()) {
                case "MONK":
                    character = new Image("graphics/characters/monk.jpg");
                    break;
                case "PRINCESS":
                    character = new Image("graphics/characters/princess.jpg");
                    break;
                case "MUSHROOMHUNTER":
                    character = new Image("graphics/characters/mushroom_hunter.jpg");
                    break;
                case "THIEF":
                    character = new Image("graphics/characters/thief.jpg");
                    break;
                case "FARMER":
                    character = new Image("graphics/characters/farmer.jpg");
                    break;
                case "CENTAUR":
                    character = new Image("graphics/characters/centaur.jpg");
                    break;
                case "KNIGHT":
                    character = new Image("graphics/characters/knight.jpg");
                    break;
                case "POSTMAN":
                    character = new Image("graphics/characters/postman.jpg");
                    break;
                case "GRANNY":
                    character = new Image("graphics/characters/granny.jpg");
                    break;
                case "JESTER":
                    character = new Image("graphics/characters/jester.jpg");
                    break;
                case "HERALD":
                    character = new Image("graphics/characters/herald.jpg");
                    break;
                case "MINSTRELL":
                    character = new Image("graphics/characters/minstrell.jpg");
                    break;
            }
            images.get(i).setImage(character);
        }
    }
}
