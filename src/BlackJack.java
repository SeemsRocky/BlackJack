import java.util.*;
public class BlackJack
{

    public static boolean checkForAce(Card c)
    {
        return c.getValue()==1;
    }
    public static int[] possibleTotals(ArrayList<Card> cards)
    {
        int[] possible = new int[3];
        boolean hasAce = false;
        int total = 0;
        for(int i=0;i<cards.size();i++)
        {
            if(checkForAce(cards.get(i)))
            {
                hasAce=true;
            }
            total += cards.get(i).getTrueValue();
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
    public static int house(ArrayList<Card> dealerHand, Deck d,int playerTotal)
    {
        int dealerTotal = dealerHand.get(0).getTrueValue() + dealerHand.get(1).getTrueValue();
        int[] dealer = possibleTotals(dealerHand);
        boolean hasAces = false;
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
    public static void main(String[] args)
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

                ArrayList<Card> playerHand = new ArrayList<Card>();
                ArrayList<Card> dealerHand = new ArrayList<Card>();

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

                int playerTotal = playerCard1.getTrueValue()+playerCard2.getTrueValue();
                boolean gameEnded = false;
                int[] aces = possibleTotals(playerHand);
                boolean hasAce = aces[2]==1;

                playerTotal = hasAce ?  aces[1]: playerTotal;

                if(playerTotal == 21)
                {
                    System.out.println("You win! \nDo you want to play again? Y/N");
                }
                if(playerTotal>21)
                {
                    System.out.println("You got " + playerTotal + " You bust! \nDo you want to play again? Y/N");
                }
                while(playerTotal<21 && !gameEnded)
                {
                    System.out.println("Your hand is currently " + playerTotal + "\nDo you want to hit or stand?");
                    String action = scan.nextLine();

                    if(action.equalsIgnoreCase("hit"))
                    {
                        Card nextCard = deck.deal();
                        playerTotal += nextCard.getTrueValue();
                        aces[0] += nextCard.getTrueValue();

                        if (hasAce && playerTotal > 21)
                        {
                            playerTotal = aces[0];
                            hasAce=false;
                        }

                        System.out.println("You've been dealt a "+ nextCard.toString());

                        if(playerTotal == 21)
                        {
                            System.out.println("You win! \nDo you want to play again? Y/N");
                        }
                        if(playerTotal>21)
                        {
                            System.out.println("Bust! \nDo you want to play again? Y/N");
                        }
                    }
                    else if(action.equalsIgnoreCase("stand"))
                    {
                        int dealerTotal = house(dealerHand,deck,playerTotal);
                        gameEnded = true;

                        if(dealerTotal==playerTotal)
                        {
                            System.out.println("It's a tie! \nDo you want to play again? Y/N");
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
                    else
                    {
                        System.out.println("That is not a valid action! Please enter a valid action");
                    }

                } // end of while loop of player hand less than 21
            }
            else
            {
                System.out.println("Thanks for playing BlackJack today!");
                break;
            }
        } // end of hasnext while

    } // end of main method
} // end of class
