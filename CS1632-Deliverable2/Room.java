//Class for individual rooms

public class Room 
{
	//Room inventory
	private boolean coffee;
	private boolean cream;
	private boolean sugar;
	
	//Room furnishing
	private Furnishing furniture;
	
	//Room adjective
	private String roomAdjective;

	//Room number. Starts at 0 in southernmost, goes up from there
	private int location;
	
	//Doors. By default are null
	private Door northDoor = null;
	private Door southDoor = null;
	
	public Room(int location)
	{
		this.location = location;
	}
	
	public Room(int location, boolean coffee, boolean cream, boolean sugar, Furnishing furniture, String adjective, boolean northDoor, boolean southDoor)
	{
		this.location = location;
		this.coffee = coffee;
		this.cream = cream;
		this.sugar = sugar;
		this.furniture = furniture;
		this.roomAdjective = adjective;
		if(northDoor)
		{
			this.northDoor = new Door(location+1);
		}
		if(southDoor && location > 0)
		{
			this.southDoor = new Door(location-1);
		}
	}
	//Get location
	public int getLocation()
	{
		return location;
	}
	
	//Has north door
	public boolean hasNorthDoor()
	{
		return northDoor != null;
	}
	
	//Has south door
	public boolean hasSouthDoor()
	{
		return southDoor != null;
	}
	
	//Convenience catch-all
	public boolean isEmptyOfItems()
	{
		return !coffee && !sugar && !cream;
	}
	
	//Return room's adjective
	public String getAdjective()
	{
		return roomAdjective;
	}
	
	//Return room's furnishing
	public Furnishing getFurnishing()
	{
		return furniture;
	}
	
	//Has coffee
	public boolean hasCoffee()
	{
		return coffee;
	}
	//Has cream
	public boolean hasCream()
	{
		return cream;
	}
	//Has coffee
	public boolean hasSugar()
	{
		return sugar;
	}
	
	//Remove all items and return an array to tell which ones were there
	public boolean[] removeItems()
	{
		boolean[] returnedItems = new boolean[3];
		if(coffee)
			returnedItems[0] = true;
		if(cream)
			returnedItems[1] = true;
		if(sugar)
			returnedItems[2] = true;
		
		coffee = cream = sugar = false;		
		return returnedItems;
	}
	
	//Check all items and return an array to tell which ones are there
	public boolean[] checkItems()
	{
		boolean[] returnedItems = new boolean[3];
		if(coffee)
			returnedItems[0] = true;
		if(cream)
			returnedItems[1] = true;
		if(sugar)
			returnedItems[2] = true;
				
		return returnedItems;
	}
	
	//Add coffee
	public void addCoffee()
	{
		coffee = true;
	}
	
	//Add cream
	public void addCream()
	{
		cream = true;
	}
	
	//Add sugar
	public void addSugar()
	{
		sugar = true;
	}
	
	//Add adjective
	public void setAdjective(String adjective)
	{
		this.roomAdjective = adjective;
	}
	
	//Add furniture
	public void setFurniture(Furnishing furniture)
	{
		this.furniture = furniture;
	}
	
	//Add north door
	public void addNorthDoor()
	{
		northDoor = new Door(location+1);
	}
	
	//Add south door
	public void addSouthDoor()
	{
		southDoor = new Door(location-1);
	}
}
