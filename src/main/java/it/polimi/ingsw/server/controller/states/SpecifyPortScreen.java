package it.polimi.ingsw.server.controller.states;

import it.polimi.ingsw.client.view.cli.CommandPrompt;
import it.polimi.ingsw.utils.network.Network;
import it.polimi.ingsw.utils.stateMachine.Event;
import it.polimi.ingsw.utils.stateMachine.IEvent;
import it.polimi.ingsw.utils.stateMachine.State;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This state handles the specification of the port by the server administrator. A default port is provided,
 * carefully chosen so as not to conflict with existing services in unix and windows operating systems
 * @author Ignazio Neto Dell'Acqua
 * @author Fernando Morea
 */
public class SpecifyPortScreen extends State {
    private final Event portSpecified;

    private static final String PORT_REGEX = "^([1-9]\\d{0,3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$";


    private static final Pattern PORT_PATTERN = Pattern.compile(PORT_REGEX);

    public SpecifyPortScreen(){
        super("[Choose the port for the server Socket connection]");
        portSpecified = new Event("Port chose");

    }


    /**
     * This method control if the port chose is valid
     * @param port : port entered
     * @return true if is valid, false if not
     */
    public static boolean isValidPort(String port) {
        if (port == null) {
            return false;
        }

        Matcher matcher = PORT_PATTERN.matcher(port);

        return matcher.matches();
    }

    /**
     * Method is used to set up the port of the server for the Client Connection. Empty string --> default port
     * @param cause the event that caused the controller transition in this state
     * @return null event
     * @throws Exception input output or network related exceptions
     */
    @Override
    public IEvent entryAction(IEvent cause) throws Exception {
        if (!Network.isServerListening()) {
            CommandPrompt.ask(
                    "Enter the port for Clients Connection [ENTER with empty string: default port 1234]",
                    "Port :");
        }
        if (CommandPrompt.gotFromTerminal().equals("")){
            CommandPrompt.forceInput("1234");
        }

        while (!isValidPort(CommandPrompt.gotFromTerminal())) {
            CommandPrompt.ask(
                    "Port chose is not in the correct format (number between 0 and 65536)",
                    "Port :");
        }
        portSpecified.fireStateEvent();

        return super.entryAction(cause);
    }

    /**
     * Events caller
     * @return portSpecified event in order to trigger the fsm machine
     */
    public Event portSpecified() {
        return portSpecified;
    }

}
