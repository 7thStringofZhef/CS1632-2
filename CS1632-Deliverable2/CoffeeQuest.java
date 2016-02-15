import java.io.InputStream;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

//The main game class
public class CoffeeQuest 
{
	//8 rooms, lists of adjectives
	private static final int numberOfRooms = 8;
	private static final String[] listOfAdjectives = {"forboding", "insouciant", "ridiculous", "highfalutin", "burlesque", "riparian", "plain", "lecherous", "milquetoast", "annoyed", "imbalanced", "malodorous", "deteriorating", "rambunctious", "messy", "secluded"};
	private static final String[] listOfNouns = {"sofa", "ottoman", "table", "desk", "puppy", "television", "archway", "bed"};
	
	
	public static Room[] layout;
	public static Player player;
	public static Scanner scn;
	public static boolean gameEnd;
	
	public static void main(String[] args)
	{
		int currentCommand = -1;
		initialize();
		displayRoomInformation();
		while(!gameEnd)
		{
			displayMainPrompt();
			currentCommand = promptAndParseInput(System.in);
			respondToCommand(currentCommand);
			if(!gameEnd)
				displayRoomInformation();
		}
	}
	
	//Set up rooms, player, and scanner
	public static void initialize()
	{
		gameEnd = false;
		scn = new Scanner(System.in);
		player = new Player();
		layout = generateRooms();
	}
	
	//Refine room layout so that unique requirements are fulfilled
	public static Room[] generateRooms()
	{
		Room[] newLayout = new Room[numberOfRooms];
		Random rng = new Random();
		
		//Shuffle lists
		List<String> firstList = Arrays.asList(listOfAdjectives);
		List<String> secondList = Arrays.asList(listOfNouns);
		Collections.shuffle(firstList);
		Collections.shuffle(secondList);
		
		//Find where to put coffee, cream, and sugar
		int coffeePos = rng.nextInt(numberOfRooms);
		int creamPos = rng.nextInt(numberOfRooms);
		int sugarPos = rng.nextInt(numberOfRooms);
		
		for(int i = 0; i < numberOfRooms; i++)
		{
			newLayout[i] = new Room(i);
			Furnishing furniture = new Furnishing(secondList.get(i), firstList.get(i+numberOfRooms));
			if(i == coffeePos)
				newLayout[i].addCoffee();
			if(i == creamPos)
				newLayout[i].addCream();
			if(i == sugarPos)
				newLayout[i].addSugar();
			
			newLayout[i].setAdjective(firstList.get(i));
			newLayout[i].setFurniture(furniture);
			if(i < 7)
				newLayout[i].addNorthDoor();
			if(i > 0)
				newLayout[i].addSouthDoor();
		}
		
		return newLayout;
	}
	
	//Display the main command prompt
	public static void displayMainPrompt()
	{
		System.out.println();
		System.out.println("Enter a command (N,S,L,I,H (for help),D): ");
	}
	
	//Display the help menu
	public static void displayHelpMenu()
	{
		System.out.println();
		System.out.println("Commands:");
		System.out.println("N: Go through the door to the north. If there is no door, nothing happens.");
		System.out.println("S: Go through the door to the south. If there is no door, nothing happens.");
		System.out.println("L: Look around the current room. Will tell if any items are in the room and collect them.");
		System.out.println("I: Tells you what items you have collected.");
		System.out.println("H: Displays a listing of commands and what they do. But you're here, so you clearly know that!");
		System.out.println("D: Drink the coffee. If you have the items you need, then you will win. To drink otherwise is to dance with death!");
	}
	
	//Take the user input and parse into a command (or invalid)
	public static int promptAndParseInput(InputStream in)
	{
		int currentCommand;
		String currentInput;
		scn = new Scanner(in);
		currentInput = scn.nextLine();
		if(currentInput.length() > 1 || !currentInput.matches("[a-zA-Z]+"))
			currentCommand = -1;
		
		currentInput = currentInput.toUpperCase();
		if(currentInput.equals("N"))
			currentCommand = 0;
		else if(currentInput.equals("S"))
			currentCommand = 1;
		else if(currentInput.equals("L"))
			currentCommand = 2;
		else if(currentInput.equals("I"))
			currentCommand = 3;
		else if(currentInput.equals("H"))
			currentCommand = 4;
		else if(currentInput.equals("D"))
			currentCommand = 5;
		else
			currentCommand = -1;
		
		return currentCommand;
		
	}
	
	//Respond to the parsed command
	public static void respondToCommand(int currentCommand)
	{
		switch(currentCommand)
		{
			//Invalid
			case -1:
				System.out.println("What?");
				break;
			//North
			case 0:
				if(layout[player.getLocation()].hasNorthDoor())
				{
					player.goNorth();
				}
				else
					System.out.println("There is no door there");
				break;
			//South
			case 1:
				if(layout[player.getLocation()].hasSouthDoor())
				{
					player.goSouth();
				}
				else
					System.out.println("There is no door there");
				break;
			//Look
			case 2:
				boolean[] items = layout[player.getLocation()].removeItems();
				if(!items[0] && !items[1] && !items[2])
				{
					System.out.println("There are no items of interest in the room");
					break;
				}
				if(items[0])
				{
					System.out.println("You found a bad cup of coffee. Better than nothing.");
					player.acquireCoffee();
				}
				if(items[1])
				{
					System.out.println("You found some creamy cream!");
					player.acquireCream();
				}
				if(items[2])
				{
					System.out.println("You found some unsweetened sugar!");
					player.acquireSugar();
				}
				break;
			//Inventory
			case 3:
				System.out.println();
				if(player.hasCoffee())
					System.out.println("You have some coffee");
				else
					System.out.println("You have no coffee");
				if(player.hasCream())
					System.out.println("You have some cream");
				else
					System.out.println("You have no cream");
				if(player.hasSugar())
					System.out.println("You have some sugar");
				else
					System.out.println("You have no sugar");
				
				break;
			//Help
			case 4:
				displayHelpMenu();
				break;
			//Drink. Victory conditions.
			case 5:
				if(player.hasCoffee() && player.hasCream() && player.hasSugar())
					System.out.println("With all ingredients in hand, you deftly stave off the sleep demons with your enhanced coffee. You win!");
				else if(player.hasCoffee() && player.hasCream())
					System.out.println("Without sugar, the deceptive spike of caffeine quickly fades from memory, and you drift off to sleep. You lose!");
				else if(player.hasCoffee() && player.hasSugar())
					System.out.println("Without cream, the coffee is too bitter. Before you realize it, you've dumped it in the sink. You lose!");
				else if(player.hasCream() && player.hasSugar())
					System.out.println("Wait, seriously?! You drink the sweetened cream, and immediately your eyes droop and you fall asleep. You lose!");
				else if(player.hasCoffee())
					System.out.println("With nothing to cover up the acrid flavors, you spit out the coffee the moment it touches your lips. You lose!");
				else if(player.hasCream())
					System.out.println("You drink the cream. It wasn't very effective. You lose!");
				else if(player.hasSugar())
					System.out.println("You pour the sugar in your mouth. Without caffeine, the sugar rush cannot save you from the nightmares. You lose!");
				else {
					System.out.println("What were you thinking?! You suck in nothing but air and collapse before you can resume your search for the coffee. You lose!");
				}
				gameEnd = true;
				break;
		}
	}
	
	//Displays the basic information about the room the player is in
	public static void displayRoomInformation()
	{
		int location = player.getLocation();
		System.out.println();
		System.out.println("You are in a " + layout[location].getAdjective() + " room");
		System.out.println("You see a " + layout[location].getFurnishing());
		if(layout[location].hasNorthDoor())
			System.out.println("A nondescript door stands to the north");
		if(layout[location].hasSouthDoor())
			System.out.println("A familiar door stands to the south");
		
	}
}
