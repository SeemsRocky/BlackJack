import java.util.*;
public class BlackJack
{
    private static boolean checkForAce(Card c)
    {
        return c.getValue()==1;
    }
    private static int[] possibleTotals(ArrayList<Card> cards)
    {
        int[] possible = new int[3];
        boolean hasAce = false;
        int total = 0;
        for(Card c : cards)
        {
            if(checkForAce(c))
            {
                hasAce=true;
            }
            total += c.getTrueValue();
        }
        if(!hasAce)
        {
            return possible;
        }
        else
        {
            possible[0] = total;
            possible[1] = total+10;
            possible[2] = 1; // 0 for no ace, 1 for ace
            return possible;
        }
    }
    private static int house(ArrayList<Card> dealerHand, Deck d,int playerTotal)
    {
        int dealerTotal = dealerHand.get(0).getTrueValue() + dealerHand.get(1).getTrueValue();
        int[] dealer = possibleTotals(dealerHand);
        boolean hasAces = dealer[2]==1;
        dealerTotal = hasAces ? dealer[1] : dealerTotal;
        if(dealerTotal >= 21)
        {
            return dealerTotal;
        }
        else
        {

            while(playerTotal>dealerTotal)
            {
                Card nextCard = d.deal();
                dealerTotal += nextCard.getTrueValue();
                dealer[0] += nextCard.getTrueValue();
                if(dealerTotal>21 && hasAces)
                {
                    dealerTotal = dealer[0];
                    hasAces=false;
                }
                if(dealerTotal==playerTotal)
                {
                    break;
                }
            }
            return dealerTotal;
        }
    }
    private static int hitOrStand(ArrayList<Card> cards,Deck d,int[] aces, Scanner scan)
    {
        int total = cards.size()==1 ? cards.get(0).getTrueValue() : cards.get(0).getTrueValue() + cards.get(1).getTrueValue();

        boolean hasAce = aces[2]==1;
        total = hasAce ?  aces[1]: total;

        while(total<21)
        {
            System.out.println("Your hand is currently " + total + "\nDo you want to hit or stand?");
            String action = scan.nextLine();

            if(action.equalsIgnoreCase("hit"))
            {
                Card nextCard = d.deal();
                total += nextCard.getTrueValue();
                aces[0] += nextCard.getTrueValue();

                if (hasAce && total > 21)
                {
                    total = aces[0];
                    hasAce=false;
                }

                System.out.println("You've been dealt "+ nextCard.toString() + "\n");

            }
            else if(action.equalsIgnoreCase("stand"))
            {
                return total;
            }
            else
            {
                System.out.println("Please enter a valid action. Hit/Stand");
            }
        }
        return total;
    }
    private static void declareWinner(int dealerTotal,int playerTotal)
    {
        if(playerTotal == -1 )
        {
            // do nothing
        }
        else if(playerTotal == 21 && dealerTotal!=21)
        {
            System.out.println("You win! \nDo you want to play again? Y/N");
        }
        else if(playerTotal>21)
        {
            System.out.println("You busted with " + playerTotal + " \nDo you want to play again? Y/N");
        }
        else if(dealerTotal==playerTotal)
        {
            System.out.println("It's a tie! You both had " + playerTotal + " \nDo you want to play again? Y/N");
        }
        else if(dealerTotal>playerTotal && dealerTotal<=21)
        {
            System.out.println("You lost! The house won with " + dealerTotal + " and you only had " + playerTotal + "\nDo you want to play again? Y/N");
        }
        else
        {
            if(dealerTotal>21)
            {
                System.out.println("You win! House busted with " + dealerTotal + "\nDo you want to play again? Y/N");
            }
            else
            {
                System.out.println("You win! You won with " + playerTotal + " and house only had " + dealerTotal + "\nDo you want to play again? Y/N");
            }
        }
    }
    public static void play()
    {
        System.out.println("Do you want to play BlackJack? Y/N");
        Scanner scan = new Scanner(System.in);

        while(scan.hasNext())
        {
            String answer = scan.nextLine();

            if(answer.equalsIgnoreCase("Y"))
            {
                Deck deck = new Deck();
                deck.shuffle();
                deck.shuffle();

                ArrayList<Card> playerHand = new ArrayList<>();
                ArrayList<Card> dealerHand = new ArrayList<>();

                Card playerCard1 = deck.deal();
                Card dealerCard1 = deck.deal(); // this should be face up
                Card playerCard2 = deck.deal();
                Card dealerCard2 = deck.deal();

                playerHand.add(playerCard1);
                playerHand.add(playerCard2);
                dealerHand.add(dealerCard1);
                dealerHand.add(dealerCard2);

                System.out.println("You've drawn "+ playerCard1.toString() + " and a " + playerCard2.toString());
                System.out.println("Dealer has a " + dealerCard1.toString());

                int playerTotal = 0;

                int[] aces = possibleTotals(playerHand);
                boolean canSplit = playerCard1.getTrueValue()==playerCard2.getTrueValue();

                    if(canSplit)
                    {
                        System.out.println("Do you wish to split? Y/N");
                        String split = scan.nextLine();
                        if(split.equalsIgnoreCase("y"))
                        {
                            ArrayList<Card> playerHand1 = new ArrayList<>();
                            ArrayList<Card> playerHand2 = new ArrayList<>();

                            playerHand1.add(playerCard1);
                            playerHand2.add(playerCard2);

                            int hand1Total = hitOrStand(playerHand1,deck,aces,scan);
                            int hand2Total = hitOrStand(playerHand2,deck,aces,scan);

                            if(hand1Total > 21 && hand2Total > 21)
                            {
                                System.out.println("You lose. Both hands busted with "+ hand1Total + " and " + hand2Total + " Do you wish to play again? Y/N");
                            }
                            else if(hand1Total > 21 && hand2Total <= 21)
                            {
                                playerTotal = hand2Total;
                            }
                            else if(hand2Total > 21 && hand1Total <= 21)
                            {
                                playerTotal = hand1Total;
                            }
                            else
                            {
                                playerTotal = hand2Total > hand1Total ? hand2Total : hand1Total;
                            }

                            int dealerTotal = house(dealerHand,deck,playerTotal);

                            declareWinner(dealerTotal,playerTotal);
                        }
                        else if(split.equalsIgnoreCase("n"))
                        {
                            playerTotal = hitOrStand(playerHand,deck,aces,scan);

                            int dealerTotal = house(dealerHand,deck,playerTotal);

                            declareWinner(dealerTotal,playerTotal);
                        }
                        else
                        {
                            System.out.println("That is not a valid answer. Please enter Y or N");
                        }
                    }
                    else
                    {
                        playerTotal = hitOrStand(playerHand,deck,aces,scan);

                        int dealerTotal = house(dealerHand,deck,playerTotal);

                        declareWinner(dealerTotal,playerTotal);
                    }


            }
            else
            {
                System.out.println("Thanks for playing BlackJack today!");
                break;
            }
        } // end of hasnext while
    }//end of play function
} // end of class
