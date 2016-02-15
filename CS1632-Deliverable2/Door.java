//For doors. Doors only need a to location
public class Door 
{
	//To location
	private int leadsToLocation;
	
	public Door(int to)
	{
		leadsToLocation = to;
	}
	
	public int getNextLocation()
	{
		return leadsToLocation;
	}
}
