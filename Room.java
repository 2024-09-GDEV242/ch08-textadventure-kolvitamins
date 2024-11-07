import java.util.Set;
import java.util.HashMap;
import java.util.Iterator;
import java.util.HashSet;

/**
 * Class Room - a room in an adventure game.
 *
 * This class is part of the "World of Zuul" application. 
 * "World of Zuul" is a very simple, text based adventure game.  
 *
 * A "Room" represents one location in the scenery of the game.  It is 
 * connected to other rooms via exits.  For each existing exit, the room 
 * stores a reference to the neighboring room.
 * 
 * @author  Jeff Kolvites
 * @version 11/3/2024
 */

public class Room 
{
    private String description;
    private HashMap<String, Room> exits;        // stores exits of this room.
    private Items items;
    private HashSet itemList;

    /**
     * Create a room described "description". Initially, it has
     * no exits. "description" is something like "a kitchen" or
     * "an open court yard".
     * @param description The room's description.
     */
    public Room(String description) 
    {
        this.description = description;
        exits = new HashMap<>();
        itemList = new HashSet();
        items = new Items();
    }

    /**
     * Define an exit from this room.
     * @param direction The direction of the exit.
     * @param neighbor  The room to which the exit leads.
     */
    public void setExit(String direction, Room neighbor) 
    {
        exits.put(direction, neighbor);
    }

    /**
     * @return The short description of the room
     * (the one that was defined in the constructor).
     */
    public String getShortDescription()
    {
        return description;
    }
    
    /**
     * 
     */
    public String getItemDescription()
    {
        String itemString = "You see " + itemList.size() + " items:";
        for(Iterator i = itemList.iterator(); i.hasNext();)
            itemString += "(" + ((Item) i.next()).getName() + ")";
        return itemString;
    }
    
    /**
     * Return a description of the room in the form:
     *     You are in the kitchen.
     *     Exits: north west
     * @return A long description of this room
     */
    public String getLongDescription()
    {
        return "You are " + description + ".\n" + getExitString() + "\n" + getItemDescription();
    }

    /**
     * Return a string describing the room's exits, for example
     * "Exits: north west".
     * @return Details of the room's exits.
     */
    private String getExitString()
    {
        String returnString = "Exits:";
        Set<String> keys = exits.keySet();
        for(String exit : keys) {
            returnString += " (" + exit + ")";
        }
        return returnString;
    }

    /**
     * Return the room that is reached if we go from this room in direction
     * "direction". If there is no room in that direction, return null.
     * @param direction The exit's direction.
     * @return The room in the given direction.
     */
    public Room getExit(String direction) 
    {
        return exits.get(direction);
    }
    
    /**
     * Add item adds an item to the item list
     * @param the name of the item
     */
    public void addItem(Item item)
    {
        itemList.add(item);
        items.put(item.getName(), item);
    }
    
    /**
     * Get item gets the item name
     * @param the item name
     * @return the item
     */
    public Item getItem(String name)
    {
        return (Item) items.get(name);
    }
    
    /**
     * Remove item removes the item from the list
     * @param the item name
     * @return the item
     */
    public Item removeItem(String name)
    {
        return (Item) items.remove(name);
    }
}

