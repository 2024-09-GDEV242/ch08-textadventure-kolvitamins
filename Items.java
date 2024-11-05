import java.util.HashMap;
import java.util.Iterator;
/**
 * Items class concerns the management of multiple items in the players inventory
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
     * get name
     */
    public Item get(String name)
    {
       return (Item) items.get(name);
    }
    
    /**
     * remove item
     */
    public Item remove(String name)
    {
        return (Item) items.remove(name);
    }
    
    /**
     * put item
     */
    public void put(String name, Item value)
    {
        items.put(name, value);
    }
    
    /**
     * get description
     */
    public String getDescription()
    {
        String returnString = "";
        for( Iterator i = items.values().iterator(); i.hasNext();)
            returnString += " " + ((Item) i.next()).getDescription();
        return returnString;
    }
}
