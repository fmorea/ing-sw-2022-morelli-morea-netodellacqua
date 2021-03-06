package it.polimi.ingsw.client.view.gui.windows;

import com.google.gson.Gson;
import it.polimi.ingsw.client.view.gui.Game;
import it.polimi.ingsw.client.view.gui.GuiView;
import it.polimi.ingsw.server.model.StudentSet;
import it.polimi.ingsw.server.model.enums.PeopleColor;
import it.polimi.ingsw.utils.network.Network;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

import static it.polimi.ingsw.client.view.gui.GuiView.*;

public class JesterAndMinstrell implements Initializable {
    private final GuiView guiView = new GuiView();

    @FXML
    private Label start = new Label();
    @FXML
    private Label name = new Label();
    @FXML
    private Label explanation = new Label();
    @FXML
    private Label notice = new Label();

    @FXML
    private ImageView student11a;
    @FXML
    private ImageView student21a;
    @FXML
    private ImageView student31a;
    @FXML
    private ImageView student41a;
    @FXML
    private ImageView student51a;

    @FXML
    private ImageView student12a;
    @FXML
    private ImageView student22a;
    @FXML
    private ImageView student32a;
    @FXML
    private ImageView student42a;
    @FXML
    private ImageView student52a;

    @FXML
    private ImageView student13a;
    @FXML
    private ImageView student23a;
    @FXML
    private ImageView student33a;
    @FXML
    private ImageView student43a;
    @FXML
    private ImageView student53a;


    @FXML
    private ImageView student11b;
    @FXML
    private ImageView student21b;
    @FXML
    private ImageView student31b;
    @FXML
    private ImageView student41b;
    @FXML
    private ImageView student51b;

    @FXML
    private ImageView student12b;
    @FXML
    private ImageView student22b;
    @FXML
    private ImageView student32b;
    @FXML
    private ImageView student42b;
    @FXML
    private ImageView student52b;

    @FXML
    private ImageView student13b;
    @FXML
    private ImageView student23b;
    @FXML
    private ImageView student33b;
    @FXML
    private ImageView student43b;
    @FXML
    private ImageView student53b;

    private PeopleColor tempColor;
    private ArrayList<PeopleColor> entranceJester;
    private ArrayList<PeopleColor> jester;
    private ArrayList<PeopleColor> entranceMinstrell;
    private ArrayList<PeopleColor> diningMinstrell;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        windowNode = student11a;
        notice.setText("");
        ArrayList<ImageView> students1a = new ArrayList<>(Arrays.asList(student11a, student21a, student31a, student41a, student51a));
        ArrayList<ImageView> students2a = new ArrayList<>(Arrays.asList(student12a, student22a, student32a, student42a, student52a));
        ArrayList<ImageView> students3a = new ArrayList<>(Arrays.asList(student13a, student23a, student33a, student43a, student53a));
        ArrayList<ImageView> students1b = new ArrayList<>(Arrays.asList(student11b, student21b, student31b, student41b, student51b));
        ArrayList<ImageView> students2b = new ArrayList<>(Arrays.asList(student12b, student22b, student32b, student42b, student52b));
        ArrayList<ImageView> students3b = new ArrayList<>(Arrays.asList(student13b, student23b, student33b, student43b, student53b));

        StudentSet entranceSet = this.guiView.getClientModel().getServerModel().getCurrentPlayer().getSchoolBoard().getEntranceSpace();
        StudentSet dinnerSet = this.guiView.getClientModel().getServerModel().getCurrentPlayer().getSchoolBoard().getDinnerTable();
        tempColor = null;
        initializeArrays();

        Character character = new Character();
        name.setText(currentCharacter.getName());
        if (currentCharacter.getName().equals("MINSTRELL")) {
            start.setText("Your dining room"); //DINING ROOM + ENTRANCE ROOM
            explanation.setText("You can change up to 2 students between your entrance and your dining room");
            character.setToBlackAndWhite(students1a, dinnerSet, 0);
            character.setToBlackAndWhite(students2a, dinnerSet, 1);
            character.setToBlackAndWhite(students1b, entranceSet, 0);
            character.setToBlackAndWhite(students2b, entranceSet, 1);
            students3a.forEach(student -> student.setVisible(false));
            students3b.forEach(student -> student.setVisible(false));
            this.populateList(students1a, dinnerSet, diningMinstrell, 0);
            this.populateList(students2a, dinnerSet, diningMinstrell, 1);
            this.populateList(students1b, entranceSet, entranceMinstrell, 0);
            this.populateList(students2b, entranceSet, entranceMinstrell, 1);
        } else {
            start.setText("Jester's set");
            explanation.setText("You may take up to 3 students from this card and replace them with the same number of students from your entrance");
            StudentSet jesterSet = this.guiView.getClientModel().getServerModel().getTable().getJesterSet();
            character.setToBlackAndWhite(students1a, jesterSet, 0); // JESTER SET + ENTRANCE
            character.setToBlackAndWhite(students2a, jesterSet, 1);
            character.setToBlackAndWhite(students3a, jesterSet, 2);
            character.setToBlackAndWhite(students1b, entranceSet, 0);
            character.setToBlackAndWhite(students2b, entranceSet, 1);
            character.setToBlackAndWhite(students3b, entranceSet, 2);
            this.populateList(students1a, jesterSet, jester, 0);
            this.populateList(students2a, jesterSet, jester, 1);
            this.populateList(students3a, jesterSet, jester, 2);
            this.populateList(students1b, entranceSet, entranceJester, 0);
            this.populateList(students2b, entranceSet, entranceJester, 1);
            this.populateList(students3b, entranceSet, entranceJester, 2);
        }
    }

    /**
     * This method is used to populate with the chose color
     *
     * @param students   the student images to get the click event from.
     * @param studentSet the student set with all the available students.
     * @param colors     the list to update.
     * @param index      the index of the list to update.
     */
    private void populateList(ArrayList<ImageView> students, StudentSet studentSet, ArrayList<PeopleColor> colors, int index) {
        students.forEach(Game::setShadow);
        students.forEach(student -> student.setCursor(Cursor.HAND));
        students.forEach(student -> student.setOnMouseClicked(event -> {
            switch (students.indexOf(student)) {
                case 0:
                    if (studentSet.getNumOfBlueStudents() > 0) {
                        tempColor = PeopleColor.BLUE;
                    }
                    break;
                case 1:
                    if (studentSet.getNumOfGreenStudents() > 0) {
                        tempColor = PeopleColor.GREEN;
                    }
                    break;
                case 2:
                    if (studentSet.getNumOfPinkStudents() > 0) {
                        tempColor = PeopleColor.PINK;
                    }
                    break;
                case 3:
                    if (studentSet.getNumOfRedStudents() > 0) {
                        tempColor = PeopleColor.RED;
                    }
                    break;
                case 4:
                    if (studentSet.getNumOfYellowStudents() > 0) {
                        tempColor = PeopleColor.YELLOW;
                    }
                    break;
            }
            if (tempColor == null) {
                notice.setText("ERROR: Color unavailable!");
            } else {
                colors.set(index, tempColor);
                notice.setText("");
            }
        }));

    }

    /**
     * This event is used to activate the effect of the card, after filling in the specifications.
     *
     * @param mouseEvent the event to close the window.
     */
    @FXML
    private void useEffect(MouseEvent mouseEvent) {
        entranceMinstrell.removeAll(Collections.singletonList(null));
        diningMinstrell.removeAll(Collections.singletonList(null));
        jester.removeAll(Collections.singletonList(null));
        entranceJester.removeAll(Collections.singletonList(null));
        if (!myTurn) {
            notice.setText("It's not your turn!");
        } else if (isCardUsed) {
            notice.setText("You can use only one card at a time!");
        } else if (this.guiView.getClientModel().getServerModel().getCurrentPlayer().getCoins() < currentCharacter.getCost()) {
            notice.setText("You don't have enough coins! :(");
        } else if (entranceMinstrell.size() != diningMinstrell.size() || jester.size() != entranceJester.size()) {
            notice.setText("You must select the same quantity of students! Please try again.");
            initializeArrays();
        } else {
            this.guiView.getClientModel().setTypeOfRequest(currentCharacter.getName());
            this.guiView.getClientModel().setResponse(true);
            this.guiView.getClientModel().setPingMessage(false);
            if (currentCharacter.getName().equals("MINSTRELL")) {
                this.guiView.getClientModel().setColors1(entranceMinstrell);
                this.guiView.getClientModel().setColors2(diningMinstrell);
            } else {
                this.guiView.getClientModel().setColors1(entranceJester);
                this.guiView.getClientModel().setColors2(jester);
            }
            this.guiView.response();
            Gson gson = new Gson();
            Network.send(gson.toJson(this.guiView.getClientModel()));
            isCardUsed = true;
            this.guiView.closeWindow(mouseEvent);
        }

    }

    /**
     * This method initializes the minstrel and jester's arrays.
     */
    private void initializeArrays() {
        entranceMinstrell = new ArrayList<>(Arrays.asList(null, null));
        diningMinstrell = new ArrayList<>(Arrays.asList(null, null));
        jester = new ArrayList<>(Arrays.asList(null, null, null));
        entranceJester = new ArrayList<>(Arrays.asList(null, null, null));
    }
}
