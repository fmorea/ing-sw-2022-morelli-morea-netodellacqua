package it.polimi.ingsw.client.states;

import it.polimi.ingsw.client.Model;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.events.ParametersFromTerminal;
import it.polimi.ingsw.utils.stateMachine.IEvent;

import java.io.IOException;

public class Read_wheremove_mother extends Read_from_terminal {
    public Read_wheremove_mother(View view, Model model) throws IOException {
        super(view, model, 1, "WHEREMOVEMOTHER");
    }

    @Override
    public void exitAction(IEvent cause) throws IOException {
        super.exitAction(cause);
        if (cause instanceof ParametersFromTerminal) {
            //INDEX ISLAND
            model.setMother_movement_Choosed(Integer.parseInt(model.getFromTerminal().get(0)));
        }
    }
}

