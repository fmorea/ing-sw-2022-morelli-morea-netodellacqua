package it.polimi.ingsw.client.view.cli.cliController.states;

import it.polimi.ingsw.client.model.ClientModel;
import it.polimi.ingsw.client.view.cli.cliController.events.IncorrectNumberOfParameters;
import it.polimi.ingsw.client.view.cli.cliController.events.ParametersFromTerminal;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.utils.stateMachine.*;
import java.io.IOException;

/**
 * This state manages the generic reading of information from stdin by the controller
 * (therefore it should be understood as: the controller is reading what the user is writing).
 * The user is expected to type a specified number of parameters (strings) separated by a space
 * and then hit enter when he thinks he's done.
 */
public class ReadFromTerminal extends State {
    final ClientModel clientModel;
    final View view;
    final String type;
    @SuppressWarnings("unused")
    final
    ParametersFromTerminal fromTerminal;
    final IncorrectNumberOfParameters numberOfParametersIncorrect;
    final Event insertedParameters;

    public ReadFromTerminal(View view, ClientModel clientModel, Controller controller, int numOfParameters, String type) {
        super("[STATUS of reading of" + numOfParameters + " terminal parameters interpreted as :"+ type+ "]");
        this.view = view;
        this.clientModel = clientModel;
        this.type = type;

        insertedParameters = new Event("Terminal input of type" +type );
        numberOfParametersIncorrect = new IncorrectNumberOfParameters(numOfParameters);
        fromTerminal = new ParametersFromTerminal(clientModel, numOfParameters);
        insertedParameters.setStateEventListener(controller);
    }

    public Event insertedParameters() {
        return insertedParameters;
    }

    public IncorrectNumberOfParameters numberOfParametersIncorrect() {
        return numberOfParametersIncorrect;
    }

    public String getType() {
        return type;
    }

    public IEvent entryAction(IEvent cause) throws Exception {
        view.setCallingState(this);
        view.askParameters();

        insertedParameters.fireStateEvent();

        return null;
    }

    @Override
    public void exitAction(IEvent cause) throws IOException {
        super.exitAction(cause);
    }
}
