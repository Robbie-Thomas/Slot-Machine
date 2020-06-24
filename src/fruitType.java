
public enum fruitType
{
    CHERRY("Resources/fruitSmall/64x64/cherries.png"),APPLE("Resources/fruitSmall/64x64/apple.png"),
    BANANA("Resources/fruitSmall/64x64/banana.png"), BLUEBERRIES("Resources/fruitSmall/64x64/blueberries.png"),
    GRAPES("Resources/fruitSmall/64x64/grapes.png"),WATERMELON("Resources/fruitSmall/64x64/watermelon.png"),
    PEACH("Resources/fruitSmall/64x64/peach.png"),STRAWBERRY("Resources/fruitSmall/64x64/strawberry.png");

    final String type;

    fruitType(String type)
    {
        this.type = type;
    }

    public String toString()
    {
        return type;
    }
}
