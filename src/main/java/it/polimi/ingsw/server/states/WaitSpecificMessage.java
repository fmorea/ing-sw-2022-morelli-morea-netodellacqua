package it.polimi.ingsw.server.states;

import it.polimi.ingsw.utils.network.events.MessageReceived;
import it.polimi.ingsw.utils.stateMachine.IEvent;
import it.polimi.ingsw.utils.stateMachine.State;

import java.io.IOException;

public class WaitSpecificMessage extends State {

    private MessageReceived newMessage;
    public WaitSpecificMessage() {
        super("[Il server è in attesa di un messaggio specifico]");
        newMessage = new MessageReceived("CREATE");
    }

    @Override
    public IEvent entryAction(IEvent cause) throws IOException, InterruptedException {
        while(!newMessage.messageReceived()){
                // messaggio non ancora ricevuto
        }
        newMessage.fireStateEvent();
        return super.entryAction(cause);
    }
}
