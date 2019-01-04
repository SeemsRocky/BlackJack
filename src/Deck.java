import java.util.*;

public class Deck
{
    private ArrayList<Card> deck;
    public Deck()
    {
        deck = new ArrayList<Card>();
        for(int i=1;i<=4;i++)
        {
            String suit ="";
            switch(i){
                case 1:
                    suit = "diamonds";
                    break;
                case 2:
                    suit = "clubs";
                    break;
                case 3:
                    suit = "hearts";
                    break;
                case 4:
                    suit = "spades";
                    break;

            }
            for(int j=1;j<=13;j++)
            {
                deck.add(new Card(suit,j));
            }
        }

    }
    public ArrayList<Card> getDeck()
    {
        return this.deck;
    }
    public int getDeckSize()
    {
        return this.deck.size();
    }
    public void shuffle()
    {
        for ( int i = deck.size()-1; i > 0; i-- ) {
            int r = (int)(Math.random()*(i+1));
            Card temp = deck.get(i);
            deck.set(i,deck.get(r));
            deck.set(r,temp);
        }
    }
    public Card deal()
    {
        if(this.deck.size()==0)
        {
            throw new Error("There are no more cards in the deck!");
        }
        return this.deck.remove(0);
    }
    public void reset()
    {

    }
}
