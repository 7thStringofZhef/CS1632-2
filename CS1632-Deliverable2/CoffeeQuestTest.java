import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito.*;
import org.mockito.Mockito;

public class CoffeeQuestTest {

	private ByteArrayOutputStream out = new ByteArrayOutputStream();
	
	@Before
	public void setUp() throws Exception {
		System.setOut(new PrintStream(out));
	}

	@After
	public void tearDown() throws Exception {
		System.setOut(null);
	}

	//Make sure initialization gets the right variables setup
	@Test
	public void testInitialize() {
		CoffeeQuest.initialize();
		assertFalse(CoffeeQuest.gameEnd);
		assertNotNull(CoffeeQuest.scn);
		assertNotNull(CoffeeQuest.player);
		assertEquals(CoffeeQuest.player.getLocation(), 0);
		assertNotNull(CoffeeQuest.layout);
	}
	
	//Make sure the room layout generated has all unique adjectives and furnishings
	@Test
	public void testGenerateRooms(){
		Room[] rooms = CoffeeQuest.generateRooms();
		ArrayList<String> roomAdjSeen = new ArrayList<String>();
		ArrayList<String> furnSeen = new ArrayList<String>();
		for(int i = 0; i < rooms.length; i++)
		{
			assertFalse(roomAdjSeen.contains(rooms[i].getAdjective()));
			assertFalse(furnSeen.contains(rooms[i].getFurnishing().toString()));
			roomAdjSeen.add(rooms[i].getAdjective());
			furnSeen.add(rooms[i].getFurnishing().toString());
		}
		assertFalse(rooms[0].hasSouthDoor());
		assertFalse(rooms[rooms.length-1].hasNorthDoor());
	}
	
	//Make sure program parses a valid help command to 4
	@Test
	public void testParseValidUppercaseCommand()
	{
		CoffeeQuest.initialize();
		ByteArrayInputStream in = new ByteArrayInputStream("H".getBytes());
		assertEquals(4, CoffeeQuest.promptAndParseInput(in));
	}
	
	//Make sure program parses a valid lowercase inventory command to 3
	@Test
	public void testParseValidLowercaseCommand()
	{
		CoffeeQuest.initialize();
		ByteArrayInputStream in2 = new ByteArrayInputStream("i".getBytes());
		assertEquals(3, CoffeeQuest.promptAndParseInput(in2));
	}
	
	//Make sure program parses an invalid command to -1
	@Test
	public void testParseInvalidCommand()
	{
		CoffeeQuest.initialize();
		ByteArrayInputStream in3 = new ByteArrayInputStream("y".getBytes());
		assertEquals(-1, CoffeeQuest.promptAndParseInput(in3));
	}
	
	
	//Make sure program outputs "what?" in response to invalid commands
	@Test
	public void testRespondToInvalid(){
		CoffeeQuest.initialize();
		//Invalid commands out what?
		CoffeeQuest.respondToCommand(-1);
		assertEquals("What?\r\n", out.toString());
	}
	
	//Make sure player moves north from start in response to north command
	@Test
	public void testRespondToValidMove(){
		CoffeeQuest.initialize();
		//North should move player up from 0 to 1
		CoffeeQuest.respondToCommand(0);
		assertEquals(1, CoffeeQuest.player.getLocation());
		
		//South should move player from 1 to 0
		CoffeeQuest.respondToCommand(1);
		assertEquals(0, CoffeeQuest.player.getLocation());
	}
	
	//Make sure player cannot move where there is no door and it outputs properly
	@Test
	public void testRespondToInvalidMove(){
		CoffeeQuest.initialize();
		Room doorlessRoom = Mockito.mock(Room.class);
		when(doorlessRoom.hasNorthDoor()).thenReturn(false);
		when(doorlessRoom.hasSouthDoor()).thenReturn(false);
		CoffeeQuest.layout[0] = doorlessRoom;
		CoffeeQuest.respondToCommand(0);
		assertEquals(0, CoffeeQuest.player.getLocation());
		assertEquals("There is no door there\r\n", out.toString());
		CoffeeQuest.respondToCommand(1);
		assertEquals(0, CoffeeQuest.player.getLocation());
		assertEquals("There is no door there\r\nThere is no door there\r\n", out.toString());
	}
	
	//Ensure proper response to full player inventory
	@Test
	public void testRespondToEmptyInv(){
		CoffeeQuest.initialize();
		CoffeeQuest.respondToCommand(3);
		assertEquals("\r\nYou have no coffee\r\nYou have no cream\r\nYou have no sugar\r\n", out.toString());
	}
	
	//Ensure proper response to empty player inventory
	@Test
	public void testRespondToFullInv(){
		CoffeeQuest.initialize();
		Player mockFilledPlayer = Mockito.mock(Player.class);
		when(mockFilledPlayer.hasCoffee()).thenReturn(true);
		when(mockFilledPlayer.hasCream()).thenReturn(true);
		when(mockFilledPlayer.hasSugar()).thenReturn(true);
		CoffeeQuest.player = mockFilledPlayer;
		CoffeeQuest.respondToCommand(3);
		assertEquals("\r\nYou have some coffee\r\nYou have some cream\r\nYou have some sugar\r\n", out.toString());
	}
	//Make sure that the program responds to look commands
	//Upon item collection, it should have items, and the room should not
	@Test
	public void testRespondToLook(){
		CoffeeQuest.initialize();
		//Check the player's empty inventory
		CoffeeQuest.respondToCommand(3);
		assertEquals("\r\nYou have no coffee\r\nYou have no cream\r\nYou have no sugar\r\n", out.toString());
		
		//Mock a room with coffee
		Room mockFilledRoom = Mockito.mock(Room.class);
		when(mockFilledRoom.isEmptyOfItems()).thenReturn(false);
		boolean[] items = new boolean[3];
		items[0] = true;
		items[1] = false;
		items[2] = false;
		when(mockFilledRoom.removeItems()).thenReturn(items);
		
		CoffeeQuest.layout[0] = mockFilledRoom;
		
		//Pick up coffee. Ensure player has coffee and nothing else
		CoffeeQuest.respondToCommand(2);
		assertTrue(CoffeeQuest.player.hasCoffee());
		assertFalse(CoffeeQuest.player.hasCream() || CoffeeQuest.player.hasSugar());
	}

	//Test victory condition
	@Test
	public void testVictory(){
		CoffeeQuest.initialize();
		CoffeeQuest.player.acquireCoffee();
		CoffeeQuest.player.acquireCream();
		CoffeeQuest.player.acquireSugar();
		CoffeeQuest.respondToCommand(5);
		assertEquals("With all ingredients in hand, you deftly stave off the sleep demons with your enhanced coffee. You win!\r\n", out.toString());
		assertTrue(CoffeeQuest.gameEnd);
	}
	
	//Test a defeat condition
	@Test
	public void testDefeat(){
		CoffeeQuest.initialize();
		CoffeeQuest.player.acquireCoffee();
		CoffeeQuest.player.acquireSugar();
		CoffeeQuest.respondToCommand(5);
		assertEquals("Without cream, the coffee is too bitter. Before you realize it, you've dumped it in the sink. You lose!\r\n", out.toString());
		assertTrue(CoffeeQuest.gameEnd);
	}
}
