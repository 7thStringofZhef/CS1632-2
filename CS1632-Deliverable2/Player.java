//Class to represent the player

public class Player 
{
	//Inventory
	private boolean coffee;
	private boolean cream;
	private boolean sugar;
	
	//Location
	private int location;
	
	public Player()
	{
		location = 0;
		coffee = false;
		cream = false;
		sugar = false;
	}
	
	//Getters
	public boolean hasCoffee()
	{
		return coffee;
	}
	
	public boolean hasCream()
	{
		return cream;
	}
	
	public boolean hasSugar()
	{
		return sugar;
	}
	
	public int getLocation()
	{
		return location;
	}
	
	
	//Setters and movers. Note that you can't lose items once you have them
	public void acquireCoffee()
	{
		coffee = true;
	}
	
	public void acquireCream()
	{
		cream = true;
	}
	
	public void acquireSugar()
	{
		sugar = true;
	}
	
	public void setLocation(int location)
	{
		this.location = location;
	}
	
	public void goNorth()
	{
		this.location++;
	}
	
	public void goSouth()
	{
		this.location--;
	}
}
