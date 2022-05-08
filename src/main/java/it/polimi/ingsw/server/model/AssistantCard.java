package it.polimi.ingsw.server.model;

public class AssistantCard {
    private float values;
    private final int moves;


    public AssistantCard(int values,int moves){
        this.values = values;
        this.moves = moves;
    }
    public int getMoves() {
        return moves;
    }

    public float getValues() {
        return values;
    }

    /* nessun bisogno di cambiare il contenuto delle carte
    una volta che sono state costruite (vedere classe Deck)
     */
    public void lowPriority(){
        values+=0.5;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof AssistantCard) {
            if (this.getMoves() != ((AssistantCard) obj).getMoves()) return false;
            return this.getValues() == ((AssistantCard) obj).getValues();
        }
        return false;
    }
}
