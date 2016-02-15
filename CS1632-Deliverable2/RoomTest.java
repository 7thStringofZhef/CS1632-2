import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import org.junit.Test;
import org.mockito.Mockito;

public class RoomTest {

	//Make sure my constructor generates a non-null object
	@Test
	public void testBasicConstructor() {
		Room room = new Room(0);
		assertNotNull(room);		
	}
	
	//Make sure the full constructor generates a non-null object
	@Test
	public void testAdvancedConstructor() {
		Furnishing furniture = Mockito.mock(Furnishing.class);
		Room room = new Room(0, false, false, false, furniture, "test", false, false);
		assertNotNull(room);		
	}
	
	//Make sure it returns proper location (0 with 0 constructor)
	@Test
	public void testGetLocation(){
		Room room = new Room(0);
		assertEquals(0, room.getLocation());
	}
	
	//Make sure the base room has no north door
	@Test
	public void testHasNorthDoor(){
		Room room = new Room(0);
		assertFalse(room.hasNorthDoor());
	}
	
	//Make sure the base room has no south door
	@Test
	public void testHasSouthDoor(){
		Room room = new Room(0);
		assertFalse(room.hasSouthDoor());		
	}
	
	//Make sure the base room has no coffee
	@Test
	public void testHasCoffee(){
		Room room = new Room(0);
		assertFalse(room.hasCoffee());		
	}
	
	//Make sure the base room has no cream
	@Test
	public void testHasCream(){
		Room room = new Room(0);
		assertFalse(room.hasCream());		
	}
	
	//Make sure the base room has no sugar
	@Test
	public void testHasSugar(){
		Room room = new Room(0);
		assertFalse(room.hasSugar());		
	}
	
	//Make sure an empty room returns that it is empty
	@Test
	public void testIsEmptyOfItems(){
		Room room = new Room(0);
		assertTrue(room.isEmptyOfItems());		
	}
	
	//Make sure a room with no adjective returns null, and a room with one returns it
	@Test
	public void testGetAdjective(){
		Room room = new Room(0);
		assertNull(room.getAdjective());	
		room.setAdjective("test");
		assertEquals(room.getAdjective(), "test");
	}
	
	//Make sure a room with no furnishing returns null, and a room with one returns it
	@Test
	public void testGetFurnishing(){
		Room room = new Room(0);
		assertNull(room.getFurnishing());	
		Furnishing furniture = Mockito.mock(Furnishing.class);
		room.setFurniture(furniture);
		assertEquals(room.getFurnishing(), furniture);
	}
	
	//Make sure items can be removed from the room, and that the correct items are returned
	@Test
	public void testRemoveItems(){
		Furnishing furniture = Mockito.mock(Furnishing.class);
		Room room = new Room(0, true, false, true, furniture, "test", false, false);
		boolean[] items = room.removeItems();
		assertTrue(items[0]);
		assertFalse(items[1]);
		assertTrue(items[2]);
		assertFalse(room.hasCoffee());
		assertFalse(room.hasCream());
		assertFalse(room.hasSugar());
	}
	
	//Make sure items can be checked without removal from the room, and that the correct items are returned
	@Test
	public void testCheckItems(){
		Furnishing furniture = Mockito.mock(Furnishing.class);
		Room room = new Room(0, true, false, true, furniture, "test", false, false);
		boolean[] items = room.checkItems();
		assertTrue(items[0]);
		assertFalse(items[1]);
		assertTrue(items[2]);
		assertTrue(room.hasCoffee());
		assertFalse(room.hasCream());
		assertTrue(room.hasSugar());
	}
	
	//Make sure I can add coffee
	@Test
	public void testAddCoffee(){
		Room room = new Room(0);
		assertFalse(room.hasCoffee());
		room.addCoffee();
		assertTrue(room.hasCoffee());
		room.addCoffee();
		assertTrue(room.hasCoffee());
	}
	
	//Make sure I can add cream
	@Test
	public void testAddCream(){
		Room room = new Room(0);
		assertFalse(room.hasCream());
		room.addCream();
		assertTrue(room.hasCream());
		room.addCream();
		assertTrue(room.hasCream());
	}
		
	//Make sure I can add sugar
	@Test
	public void testAddSugar(){
		Room room = new Room(0);
		assertFalse(room.hasSugar());
		room.addSugar();
		assertTrue(room.hasSugar());
		room.addSugar();
		assertTrue(room.hasSugar());
	}
	
	//Make sure I can set the adjective
	@Test
	public void testSetAdjective(){
		Room room = new Room(0);
		assertNull(room.getAdjective());
		room.setAdjective("test");
		assertEquals("test", room.getAdjective());
		room.setAdjective("test2");
		assertEquals("test2",room.getAdjective());
	}
	
	//Make sure I can change the furniture
	@Test
	public void testSetFurniture(){
		Furnishing furniture = Mockito.mock(Furnishing.class);
		when(furniture.getAdjective()).thenReturn("hello");
		Furnishing furniture2 = Mockito.mock(Furnishing.class);
		when(furniture2.getAdjective()).thenReturn("goodbye");
		Room room = new Room(0);
		assertNull(room.getFurnishing());
		room.setFurniture(furniture);
		assertEquals(furniture.getAdjective(), room.getFurnishing().getAdjective());
		room.setFurniture(furniture2);
		assertNotEquals(furniture.getAdjective(), room.getFurnishing().getAdjective());
	}
	
	//Make sure I can add a north door
	@Test
	public void testAddNorthDoor(){
		Room room = new Room(0);
		room.addNorthDoor();
		assertTrue(room.hasNorthDoor());
	}
	
	//Make sure I can add a south door
	@Test
	public void testAddSouthDoor(){
		Room room = new Room(0);
		room.addSouthDoor();
		assertTrue(room.hasSouthDoor());
	}

	
}
