
/**
 * Item class controls the description and weight of Items.
 *
 * @author Jeffrey Kolvites
 * @version 11/3/24
 */
public class Item
{
    // instance variables - replace the example below with your own
    private String name;

    /**
     * Constructor for Item class
     * @return Item
     */
    public Item (String name)
    {
        this.name = name;
    }

    /**
     * Gets description of item
     * @return string of item name
     */
    public String getName()
    {
        return name;
    }
    
}
