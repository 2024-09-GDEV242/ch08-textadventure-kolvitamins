import java.util.HashMap;
import java.util.Iterator;
/**
 * Items class concerns the management of multiple items in the players inventory.
 *
 * @author Jeffrey Kolvites
 * @version 11/3/24
 */
public class Items
{
    private HashMap items = new HashMap();

    /**
     * Constructor for inventory
     */
    public Items()
    {
        
    }

    /**
     * Get name gets the name of the item
     * @param the item name
     * @return the item name
     */
    public Item get(String name)
    {
       return (Item) items.get(name);
    }
    
    /**
     * Remove removes the item from the hashmap
     * @param the item name
     * @return the item name
     */
    public Item remove(String name)
    {
        return (Item) items.remove(name);
    }
    
    /**
     * Put puts the item in the item list
     * @param item name and the value on the hashmap
     */
    public void put(String name, Item value)
    {
        items.put(name, value);
    }
    
    /**
     * Get description returns the name of the item
     * @param string of the item name
     */
    public String getDescription()
    {
        String returnString = "";
        for( Iterator i = items.values().iterator(); i.hasNext();)
            returnString += " " + ((Item) i.next()).getName();
        return returnString;
    }
}
