package it.polimi.ingsw.client.view.cli.cliController.events;

import it.polimi.ingsw.client.view.cli.CommandPrompt;
import it.polimi.ingsw.utils.observerPattern.Observer;
import it.polimi.ingsw.utils.stateMachine.Event;

/**
 * Event that is triggered when the user enters an incorrect number of parameters in the terminal
 * @author Fernando
 */

public class IncorrectNumberOfParameters extends Event implements Observer {
    private final CommandPrompt commandPrompt;

    public IncorrectNumberOfParameters(int numberOfStrings) {
        super("[Incorrect number of parameters (should be " + numberOfStrings + ")]");
        this.commandPrompt = CommandPrompt.getInstance();
        this.subscribe();
    }

    @Override
    public void update(Object message) {
    }

    @Override
    public void subscribe() {
        this.commandPrompt.subscribeObserver(this);
    }

    @Override
    public void unSubscribe() {
        this.commandPrompt.unsubscribeObserver(this);
    }
}
