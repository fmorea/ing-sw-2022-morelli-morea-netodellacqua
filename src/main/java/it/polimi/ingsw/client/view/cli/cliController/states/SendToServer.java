package it.polimi.ingsw.client.view.cli.cliController.states;

import com.google.gson.Gson;
import it.polimi.ingsw.client.model.ClientModel;
import it.polimi.ingsw.utils.network.Network;
import it.polimi.ingsw.utils.network.events.ParametersFromNetwork;
import it.polimi.ingsw.utils.stateMachine.*;


/**
 * Generic status of data sending to the server
 * Waits for a generic ack to be received from the server
 * @author Ignazio Neto Dell'Acqua
 * @author Fernando Morea
 */
public class SendToServer extends State{
    private final Gson json;
    private final ClientModel clientModel;
    private final ParametersFromNetwork ack;
    public SendToServer(ClientModel clientModel, Controller controller) {
        super("[Invio dati al server ed attesa di un ack]");
        this.clientModel = clientModel;
        ack = new ParametersFromNetwork(1);
        ack.setStateEventListener(controller);
        json = new Gson();
    }

    @Override
    public IEvent entryAction(IEvent cause) throws Exception {
        Network.send(json.toJson(clientModel));
        checkAck();
        return super.entryAction(cause);
    }

    public ParametersFromNetwork getAck() {
        return ack;
    }

    public void checkAck() throws Exception {

        boolean responseReceived=false;
        long start = System.currentTimeMillis();
        long end = start + 15 * 1000L;
        while (!responseReceived) {
            // invio al server il mio modello
            //System.out.println("loop");

            ack.enable();
            boolean check =ack.waitParametersReceived(5);


            if(check || System.currentTimeMillis()>=end){
                System.out.println("\n\nServer non ha dato risposta");
                Network.disconnect();
                System.exit(0);
            }

            if (ack.getParameter(0)!=null && json.fromJson(ack.getParameter(0), ClientModel.class).getClientIdentity() == clientModel.getClientIdentity()) {
                responseReceived = true;
            }
        }
        System.out.println("Waiting for the other players to log in ...");
        ack.fireStateEvent();
    }
}
