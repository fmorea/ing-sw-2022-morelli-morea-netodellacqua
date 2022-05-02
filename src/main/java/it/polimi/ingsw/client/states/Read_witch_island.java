package it.polimi.ingsw.client.states;

import it.polimi.ingsw.client.Model;
import it.polimi.ingsw.client.view.View;
import it.polimi.ingsw.client.events.ParametersFromTerminal;
import it.polimi.ingsw.utils.stateMachine.IEvent;

import java.io.IOException;

public class Read_witch_island extends Read_from_terminal {
    public Read_witch_island(View view, Model model) throws IOException {
        super(view, model, 1, "WICHISLAND");
    }
    @Override
    public void exitAction(IEvent cause) throws IOException {
        super.exitAction(cause);
        if (cause instanceof ParametersFromTerminal) {
            //ISLAND INDEX
            model.setIsland_Choosed(Integer.parseInt(model.getFromTerminal().get(0)));
        }
    }
}