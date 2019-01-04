public class Card
{
    private String suit;
    private int value;
    private int trueValue;
    public Card(String suit,int value)
    {
        if(value<1 || value >14)
        {
            throw new IllegalArgumentException("Value can only be between 1 and 13");
        }
        suit = suit.toLowerCase();
        if(!(suit.equals("hearts") || suit.equals("spades") || suit.equals("clubs") || suit.equals("diamonds")))
        {
            throw new IllegalArgumentException("Invalid suit!");
        }
        this.suit = suit.substring(0, 1).toUpperCase() + suit.substring(1);
        this.value = value;
        if(value>=10)
        {
            this.trueValue=10;
        }
        else
        {
            this.trueValue = value;
        }
    }
    public String getSuit()
    {
        return this.suit;
    }
    public int getValue()
    {
        return this.value;
    }
    public int getTrueValue()
    {
        return this.trueValue;
    }
    public String toString()
    {
        String num = "";
        switch (this.value){
            case 1 :
                num = "Ace";
                break;
            case 2 :
                num = "Two";
                break;
            case 3 :
                num ="Three";
                break;
            case 4 :
                num ="Four";
                break;
            case 5 :
                num = "Five";
                break;
            case 6 :
                num = "Six";
                break;
            case 7 :
                num = "Seven";
                break;
            case 8 :
                num = "Eight";
                break;
            case 9 :
                num = "Nine";
                break;
            case 10 :
                num = "Ten";
                break;
            case 11 :
                num = "Jack";
                break;
            case 12 :
                num = "Queen";
                break;
            case 13 :
                num = "King";
                break;
        }
        return num + " of "+ this.suit;
    }
}
