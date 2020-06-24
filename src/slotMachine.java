import java.util.Random;


public class slotMachine

{

    private int credits;
    private int roundsLeft;

    // --Commented out by Inspection (24/06/2020 14:08):private boolean hasCredits;
    // --Commented out by Inspection (24/06/2020 14:08):ArrayList<Integer> resultsLog = new ArrayList<>();

    public slotMachine()
    {
        credits = 0;
    }

    public int genRandNum()
    {
        Random rand = new Random();
        return rand.nextInt(8);
    }







    public void setCredits(int credits)
    {
        this.credits = credits;
    }

    public int getCredits()
    {
        return credits;
    }

    public void addCredits(int creditsAdd)
    {
        credits += creditsAdd;
    }

    public int getRoundsLeft()
    {
        return roundsLeft;
    }

    public void setRoundsLeft(int roundsLeft)
    {
        this.roundsLeft = roundsLeft;
    }

    public void addCoins()
    {
        roundsLeft+=5;

    }

    public int cashOut()
    {
        int winnings = credits;
        setCredits(0);
        return winnings;

    }


    public boolean canPlay()
    {
        return roundsLeft > 0;
    }

    public String setImageRef(int imageRef)
    {

        String icon = "";
        switch (imageRef)
        {
            case 0: icon = fruitType.APPLE.toString();
            break;
            case 1: icon = fruitType.BANANA.toString();
            break;
            case 2: icon = fruitType.BLUEBERRIES.toString();
            break;
            case 3: icon = fruitType.CHERRY.toString();
            break;
            case 4: icon = fruitType.GRAPES.toString();
            break;
            case 5: icon = fruitType.WATERMELON.toString();
            break;
            case 6: icon = fruitType.PEACH.toString();
            break;
            case 7: icon = fruitType.STRAWBERRY.toString();
        }

        return icon;

    }



   public int transferWinningsToCreditRemainder()
   {
       return credits%20;
   }

    public void transferWinningsToCredit(int credits)
    {
        int tempRound = credits/20;

        setCredits(0);
        roundsLeft = roundsLeft + tempRound;

    }



}