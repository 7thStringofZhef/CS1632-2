//Furnishing for a room

public class Furnishing 
{
	private String adjective;
	private String furnishing;
	
	public Furnishing(String furnishing, String adjective)
	{
		this.adjective = adjective;
		this.furnishing = furnishing;
	}
	
	//Getters. No setters, furniture doesn't change
	public String getFurnishing()
	{
		return furnishing;
	}
	
	public String getAdjective()
	{
		return adjective;
	}
	
	//Convenient toString
	@Override
	public String toString()
	{
		return adjective + " " + furnishing;
	}
}
