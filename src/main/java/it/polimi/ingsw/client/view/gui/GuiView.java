package it.polimi.ingsw.client.view.gui;

import it.polimi.ingsw.client.states.*;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.utils.gui.Gui;
import it.polimi.ingsw.utils.gui.ImagePanel;
import it.polimi.ingsw.utils.stateMachine.State;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class GuiView implements View, ActionListener {

    // La vista matiene una reference allo stato chiamante (schermata video/command line) ed al precedente.
    private State callingState;
    private State precedentCallingState;

    // Uno stato che vuole chiamare un metodo della vista si registra prima chiamando questo metodo
    // ad esempio sono nello stato WelcomeScreen e faccio "view.setCallingState(this)"
    // Non è altro che il pattern Observer riadattato per il pattern State
    @Override
    public void setCallingState(State callingState) {
        this.precedentCallingState = this.callingState;
        this.callingState = callingState;
    }

    // Elementi grafici (finestre, bottoni, fuochi d'artificio...) (libreria SWING)
    JFrame window;
    JPanel titleNamePanel, startButtonPanel, userInfoPanel;
    JLabel titleNameLabel, nicknameLabel, ipLabel, portLabel;
    JButton startButton, sendButton;

    JTextField nickname,ip, port;

    Image background = (new ImageIcon(getClass().getResource("/GuiResources/background.jpg"))).getImage();


    public GuiView(){
        window = new JFrame("Eriantys");
        window.setSize(800, 600);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setBackground(Color.WHITE);
        window.setLayout(null);
        window.setVisible(true);
        Gui.setContainer(window.getContentPane());


        titleNamePanel = new ImagePanel(background);
        window.repaint();
        titleNamePanel.setBounds(0, 0, 800, 600);
        titleNameLabel = new JLabel("Eriantys");
        titleNameLabel.setForeground(Color.BLUE);
        titleNameLabel.setFont(Gui.getTitleFont());


        startButtonPanel = new JPanel();
        startButtonPanel.setBounds(350, 500, 100, 50);
        startButtonPanel.setBackground(Color.WHITE);

        startButton = new JButton("START");
        startButton.setBackground(Color.WHITE);
        startButton.setForeground(Color.BLACK);
        startButton.setOpaque(true);
        startButton.setBorderPainted(false);
        startButton.setFont(Gui.getNormalFont());
        startButton.addActionListener(this);

        titleNamePanel.add(titleNameLabel);
        startButtonPanel.add(startButton);

        Gui.getContainer().add(titleNamePanel);
        Gui.getContainer().add(startButtonPanel);

    }

    @Override
    public void askToStart() {
        window.setVisible(true);
        window.setResizable(false);
    }

    @Override
    public void askConnectionInfo() {
        userInfoPanel = new JPanel();
        userInfoPanel.setLayout(null);
        userInfoPanel.setBounds(0,0,800,600);
        Gui.getContainer().add(userInfoPanel);

        nicknameLabel = new JLabel("Nickname");
        nicknameLabel.setHorizontalAlignment(SwingConstants.CENTER);
        nicknameLabel.setFont(Gui.getButtonFont());
        nicknameLabel.setSize(600, 25);
        nicknameLabel.setLocation(100, 25);
        userInfoPanel.add(nicknameLabel);
        nickname = new JTextField();
        nickname.setSize(300, 25);
        nickname.setLocation(250, 50);
        nickname.addActionListener(this);
        userInfoPanel.add(nickname);

        ipLabel = new JLabel("Ip");
        ipLabel.setHorizontalAlignment(SwingConstants.CENTER);
        ipLabel.setFont(Gui.getButtonFont());
        ipLabel.setSize(600, 25);
        ipLabel.setLocation(100, 125);
        userInfoPanel.add(ipLabel);
        ip = new JTextField();
        ip.setSize(300, 25);
        ip.setLocation(250, 150);
        ip.addActionListener(this);
        userInfoPanel.add(ip);

        portLabel = new JLabel("Port");
        portLabel.setHorizontalAlignment(SwingConstants.CENTER);
        portLabel.setFont(Gui.getButtonFont());
        portLabel.setSize(600, 25);
        portLabel.setLocation(100, 225);
        userInfoPanel.add(portLabel);
        port = new JTextField();
        port.setSize(300, 25);
        port.setLocation(250, 250);
        port.addActionListener(this);
        userInfoPanel.add(port);

        sendButton = new JButton("Confirm");
        sendButton.setSize(300, 25);
        sendButton.setLocation(250, 400);
        sendButton.addActionListener(this);
        userInfoPanel.add(sendButton);



    }

    @Override
    public void askConnectOrCreate() {
        //todo
    }

    @Override
    public void showTryToConnect() {
        //todo
    }

    @Override
    public void showConnectingGame() {

    }

    @Override
    public void showWaitingForOtherPlayer() {

    }

    @Override
    public void showGameStarted() {

    }

    @Override
    public void ComunicationError() {

    }

    @Override
    public void ask_carta_assistente() {

    }

    @Override
    public void askGameCode() {

    }

    @Override
    public void askGameInfo() {

    }

    @Override
    public void actionPerformed(ActionEvent event) {
        try {
            if (event.getSource() == startButton) {
                ((WelcomeScreen) callingState).start().fireStateEvent();
                titleNamePanel.setVisible(false);
                startButtonPanel.setVisible(false);
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
