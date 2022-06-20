package it.polimi.ingsw.client.view.gui.windows;

import com.google.gson.Gson;
import it.polimi.ingsw.client.GUI;
import it.polimi.ingsw.server.model.StudentSet;
import it.polimi.ingsw.server.model.enums.PeopleColor;
import it.polimi.ingsw.utils.network.Network;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.ResourceBundle;

import static it.polimi.ingsw.client.GUI.currentCharacter;

public class JesterAndMinstrell implements Initializable {
    private final GUI gui = new GUI();

    public Label start = new Label();
    public Label finish = new Label();
    public Label name = new Label();
    public Label explaination = new Label();
    public Label notice = new Label();

    public ImageView student11a;
    public ImageView student21a;
    public ImageView student31a;
    public ImageView student41a;
    public ImageView student51a;

    public ImageView student12a;
    public ImageView student22a;
    public ImageView student32a;
    public ImageView student42a;
    public ImageView student52a;

    public ImageView student13a;
    public ImageView student23a;
    public ImageView student33a;
    public ImageView student43a;
    public ImageView student53a;


    public ImageView student11b;
    public ImageView student21b;
    public ImageView student31b;
    public ImageView student41b;
    public ImageView student51b;

    public ImageView student12b;
    public ImageView student22b;
    public ImageView student32b;
    public ImageView student42b;
    public ImageView student52b;

    public ImageView student13b;
    public ImageView student23b;
    public ImageView student33b;
    public ImageView student43b;
    public ImageView student53b;

    private PeopleColor tempColor;
    ArrayList<PeopleColor> entranceJester = new ArrayList<>();
    ArrayList<PeopleColor> jester = new ArrayList<>();
    ArrayList<PeopleColor> entranceMinstrell = new ArrayList<>();
    ArrayList<PeopleColor> diningMinstrell = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        notice.setText("");
        ArrayList<ImageView> students1a = new ArrayList<>(Arrays.asList(student11a, student21a, student31a, student41a, student51a));
        ArrayList<ImageView> students2a = new ArrayList<>(Arrays.asList(student12a, student22a, student32a, student42a, student52a));
        ArrayList<ImageView> students3a = new ArrayList<>(Arrays.asList(student13a, student23a, student33a, student43a, student53a));
        ArrayList<ImageView> students1b = new ArrayList<>(Arrays.asList(student11b, student21b, student31b, student41b, student51b));
        ArrayList<ImageView> students2b = new ArrayList<>(Arrays.asList(student12b, student22b, student32b, student42b, student52b));
        ArrayList<ImageView> students3b = new ArrayList<>(Arrays.asList(student13b, student23b, student33b, student43b, student53b));

        StudentSet entranceSet = this.gui.getClientModel().getServermodel().getcurrentPlayer().getSchoolBoard().getEntranceSpace();
        StudentSet dinnerSet = this.gui.getClientModel().getServermodel().getcurrentPlayer().getSchoolBoard().getDinnerTable();
        tempColor = null;

        entranceMinstrell = new ArrayList<>(Arrays.asList(null, null));
        diningMinstrell = new ArrayList<>(Arrays.asList(null, null));
        jester = new ArrayList<>(Arrays.asList(null, null, null));
        entranceJester = new ArrayList<>(Arrays.asList(null, null, null));

        Character character = new Character();
        name.setText(currentCharacter.getName());
        if (currentCharacter.getName().equals("MINSTRELL")) {
            start.setText("Your dining room"); //DINING ROOM + ENTRANCE ROOM
            explaination.setText("You can change up to 2 students between your entrance and your dining room");
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
            explaination.setText("You may take up to 3 students from this card and replace them with the same number of students from your entrance");
            StudentSet jesterSet = this.gui.getClientModel().getServermodel().getTable().getJesterSet();
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

    public void populateList(ArrayList<ImageView> students, StudentSet studentSet, ArrayList<PeopleColor> colors, int index) {
        students.forEach(student -> System.out.println(student.getImage().toString()));
        students.forEach(student -> student.setOnMouseClicked(event -> {
            System.out.println("cliccateddd");
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
                System.out.println("jester set:");
                jester.forEach(el -> System.out.println(el != null ? el.name() : "null"));
                System.out.println("jester entrance:");
                entranceJester.forEach(el -> System.out.println(el != null ? el.name() : "null"));
            }
        }));

    }

    public void okay(MouseEvent mouseEvent) throws InterruptedException {
        this.gui.getClientModel().setTypeOfRequest(currentCharacter.getName());
        this.gui.getClientModel().setResponse(true); //lo flaggo come messaggio di risposta
        this.gui.getClientModel().setPingMessage(false);
        if (currentCharacter.getName().equals("MINSTRELL")) {
            entranceMinstrell.removeAll(Collections.singletonList(null));
            diningMinstrell.removeAll(Collections.singletonList(null));
            this.gui.getClientModel().setColors1(entranceMinstrell);
            this.gui.getClientModel().setColors2(diningMinstrell);
        } else {
            jester.removeAll(Collections.singletonList(null));
            entranceJester.removeAll(Collections.singletonList(null));
            this.gui.getClientModel().setColors1(entranceJester);
            this.gui.getClientModel().setColors2(jester);
        }
        Gson gson = new Gson();
        Network.send(gson.toJson(this.gui.getClientModel()));
        this.gui.closeWindow(mouseEvent);
    }
}