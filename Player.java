
/**
 * The player class controls the player information, including name, room, items, 
 * and max weight.
 *
 * @author Jeffrey Kovlites
 * @version 11/3/24
 */
public class Player
{
    private String name;    
    private Room currentRoom;
    private Items items = new Items();

    /**
     * Constructor for objects of class Player
     */
    public Player(String name)
    {
        this.name = name;        
    }
    
    /**
     * Returns the players name
     * @return players name
     */
    public String getName()
    {
        return name;
    }
    
    /**
     * Allows player to enter a room
     */
    public void enterRoom(Room room)
    {
        currentRoom = room;
    }
    
    /**
     * Returns the current room the player is in
     * @return current room
     */
    public Room getCurrentRoom()
    {
        return currentRoom;
    }
    
    /**
     * Check inventory lists the current items the player is holding
     * @return list of items
     */
    public String checkInventory()
    {
        return "Inventory: " + items.getDescription();
    }
    
    /**
     * Picks up the item and adds it to the player inventory
     * @param name of the item
     * @return name of the item picked up
     */
    public Item pickUpItem(String itemName)
    {
        Item item = currentRoom.removeItem(itemName);
        items.put(item.getDescription(), item);
        return item;
    }
    
    /**
     * Drops the stated item in the current room
     * @param item name
     * @return item
     */
    public Item dropItem(String itemName)
    {
        Item item = items.remove(itemName);
        if(item != null)
        {
            currentRoom.addItem(item);
        }
        return item;
    }
    
}
