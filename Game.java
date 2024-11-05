import java.util.Stack;
/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes
 * @version 2016.02.29
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    private Stack lastRooms = new Stack();
    private Player player;
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        player = new Player("John Zuul");
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room outside, lobby, theater, pub, lab, office, dorms, street, home;
      
        // create the rooms
        outside = new Room("outside the main entrance of the university");
        lobby = new Room("inside the main entrance of the university");
        theater = new Room("in a lecture theater");
        pub = new Room("in the campus pub (what kind of school is this?)");
        lab = new Room("in a chemistry lab");
        office = new Room("in the computing admin office");
        dorms = new Room("in the university dorms");
        street = new Room("outside of campus by the road");
        home = new Room("back at home");
        
        outside.addItem(new Item("bike"));
        outside.addItem(new Item("branch"));
        theater.addItem(new Item("old popcorn"));
        theater.addItem(new Item("ticket stub"));
        pub.addItem(new Item("beer"));
        pub.addItem(new Item("bottle of whiskey"));
        pub.addItem(new Item("pool stick"));
        lab.addItem(new Item("notebook"));
        lab.addItem(new Item("mystery vial"));
        office.addItem(new Item("folder"));
        office.addItem(new Item("laptop"));
        
        
        // initialise room exits
        outside.setExit("east", theater);
        outside.setExit("south", lab);
        outside.setExit("west", pub);
        outside.setExit("north", lobby);
        
        lobby.setExit("south", outside);
        
        theater.setExit("west", outside);

        pub.setExit("east", outside);

        lab.setExit("north", outside);
        lab.setExit("east", office);

        office.setExit("west", lab);
        office.setExit("east", dorms);
        
        dorms.setExit("west", office);
        dorms.setExit("east", street);
        
        street.setExit("west", dorms);
        street.setExit("take the road", home);
        
        home.setExit("take the road", street);

        currentRoom = outside;  // start game outside
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
                
            case LOOK:
                look(command);
                break;
                
            case BACK:
                lastRoom(command);
                break;
                
            case TAKE:
                take(command);
                break;
                
            case DROP:
                drop(command);
                break;

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }
    
    /**
     * Prints the room description
     */
    private void look(Command command)
    {
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Takes item and places it in player inventory if there is an item. Prints error if
     * item is not stated. If no items in the room, will state that there are no items.
     */
    private void take(Command command)
    {
        if(!command.hasSecondWord()){
            System.out.println("Please name the item you wish to take");
            return;
        }
        
        String itemName = command.getSecondWord();
        Item item = player.pickUpItem(itemName);
        
        if(item == null)
        {
            System.out.println("You don't see that here.");
        }
        else{
            System.out.println("Picked up " + item.getDescription());
        }
    }
    
    /**
     * Drops an item in the current room, prints error if the item is not stated.
     * If item is not in inventory prints that the player does not have the item.
     */
    private void drop(Command command)
    {
        if(!command.hasSecondWord()){
            System.out.println("Please name the item you wish to drop.");
            return;
        }
        
        String itemName = command.getSecondWord();
        Item item = player.dropItem(itemName);
        
        if(item == null)
        {
            System.out.println("You don't have anything like that");
        }
        else{
            System.out.println("Dropped " + item.getDescription());
        }
    }
    
    /**
     * Prints the current items in the players inventory
     */
    private void printInventory()
    {
        System.out.println(player.checkInventory());
    }
    
    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            lastRooms.push(currentRoom);
            enterRoom(nextRoom);
        }
    }
    
    /**
     * enter room
     */
    private void enterRoom(Room nextRoom)
    {
        currentRoom = nextRoom;
        System.out.println(currentRoom.getLongDescription());
    }
    
    /**
     * Last room
     */
    private void lastRoom(Command command)
    {
        if(command.hasSecondWord()){
            System.out.println("Back where?");
            return;
        }
        
        if(lastRooms.isEmpty())
            System.out.println("You can't go home just yet.");
        else{
            Room lastRoom = (Room) lastRooms.pop();
            enterRoom(lastRoom);
        }
    }
    
    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
