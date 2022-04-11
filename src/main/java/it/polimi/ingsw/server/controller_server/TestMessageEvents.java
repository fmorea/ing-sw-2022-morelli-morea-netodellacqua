package it.polimi.ingsw.server.controller_server;

import javax.swing.*;

/**
 * Questa classe demo e' una macchina a stati che riceve, come eventi, messaggi da
 * socket TCP, i quali triggherano un cambiamento di stato
 */
public class TestMessageEvents {
    // ////////////////////// DECLARE AND INSTANTIATE STATES
    // Each state can either inherit from the State base class when
    // it's instantiated (anynomous inner class)
    // OR they can be their own class (and have parent/child classes, etc).
    // Actually any java construct will work.

    // These overriden Action methods are where the implementation is done.
    private final State STATE_BEGIN = new State("LOBBY");
    private final State STATE_SETUP = new State("SETUP") {

        // Note the event is passed in--and it can contain info about whatever
        // caused it.
        public IEvent entryAction(IEvent cause) {
            System.out.println("Attesa invio di nickname da parte del giocatore");
            return null;
        }
    };
    // Just defining a few extra states, but states would generally be useless
    // without overriding an Action method
    private final State STATE_PLANNING = new State("PLANNING") {
        public IEvent entryAction(IEvent cause) {
            System.out.println("Attesa della scelta della carta assistente");
            return null;
        }
    };

    // /////////////////////// DECLARE EVENTS
    private final MessageEvent EVENT_connect, EVENT_plan;

    public TestMessageEvents(JTextArea jTextArea) {

        EVENT_connect = new MessageEvent("nickname", jTextArea);
        EVENT_plan = new MessageEvent("assistantcard", jTextArea);

        ControllerServer e = new ControllerServer("Controllore server principale", STATE_BEGIN);

        e.addTransition(STATE_BEGIN, EVENT_connect, STATE_SETUP);
        e.addTransition(STATE_SETUP, EVENT_plan, STATE_PLANNING);


    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException | UnsupportedLookAndFeelException e) {
            e.printStackTrace();
        }
        VirtualView tf3 = new VirtualView();
        VirtualView tf2 = new VirtualView();
        VirtualView tf = new VirtualView();
        new TestMessageEvents(tf.getTextArea());
    }
}

