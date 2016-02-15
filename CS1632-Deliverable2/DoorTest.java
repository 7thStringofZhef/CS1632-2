import static org.junit.Assert.*;

import org.junit.Test;

public class DoorTest {

	//Make sure door constructor works successfully
	@Test
	public void testConstructor() {
		Door door = new Door(1);
		assertNotNull(door);
	}
	
	//Make sure door leads to appropriate next location
	@Test
	public void testNextLocation(){
		Door door = new Door(1);
		assertEquals(door.getNextLocation(), 1);
	}

}
