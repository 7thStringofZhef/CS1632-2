import static org.junit.Assert.*;

import org.junit.Test;

public class PlayerTest {

	//Constructor successfully creates object
	@Test
	public void testConstructor() {
		Player player = new Player();
		assertNotNull(player);
	}
	
	//Starts with no coffee
	@Test
	public void testHasCoffee() {
		Player player = new Player();
		assertEquals(false, player.hasCoffee());
	}
	
	//Starts with no cream
	@Test
	public void testHasCream() {
		Player player = new Player();
		assertEquals(false, player.hasCream());
	}
	
	//Starts with no sugar
	@Test
	public void testHasSugar() {
		Player player = new Player();
		assertEquals(false, player.hasSugar());
	}
	
	//Starts at location 0
	@Test
	public void testGetLocation() {
		Player player = new Player();
		assertEquals(0, player.getLocation());
	}
	
	//Can acquire coffee successfully
	@Test
	public void testAcquireCoffee() {
		Player player = new Player();
		player.acquireCoffee();
		assertEquals(true, player.hasCoffee());
	}
	
	//Can acquire cream successfully
	@Test
	public void testAcquireCream() {
		Player player = new Player();
		player.acquireCream();
		assertEquals(true, player.hasCream());
	}
	
	//Can acquire sugar successfully
	@Test
	public void testAcquireSugar() {
		Player player = new Player();
		player.acquireSugar();
		assertEquals(true, player.hasSugar());
	}
	
	//Can change location directly
	@Test
	public void testSetLocation() {
		Player player = new Player();
		player.setLocation(3);
		assertEquals(3, player.getLocation());
	}
	
	//Can increment location (go north)
	@Test
	public void testGoNorth() {
		Player player = new Player();
		player.goNorth();
		assertEquals(1, player.getLocation());
	}
	
	//Can decrement location (go south)
	@Test
	public void testGoSouth() {
		Player player = new Player();
		player.setLocation(3);
		player.goSouth();
		assertEquals(2, player.getLocation());
	}

}
