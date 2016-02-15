import static org.junit.Assert.*;

import org.junit.Test;

public class FurnishingTest {

	//Make sure constructor does not return null
	@Test
	public void testConstructor() {
		Furnishing furniture = new Furnishing("test","test");
		assertNotNull(furniture);
	}
	
	//Make sure furnishing is "test"
	@Test
	public void testGetFurnishing() {
		Furnishing furniture = new Furnishing("test","test");
		assertEquals("test", furniture.getFurnishing());
	}
	
	//Make sure adjective is "test"
	@Test
	public void testGetAdjective() {
		Furnishing furniture = new Furnishing("test","test");
		assertEquals("test", furniture.getAdjective());
	}
	
	//Make sure the toString returns "test test"
	@Test
	public void testToString() {
		Furnishing furniture = new Furnishing("test","test");
		assertEquals("test test", furniture.toString());
	}

}
