import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.BeforeClass;
import org.junit.Test;
import java.lang.reflect.*;

public class pinningTests {

	//MainPanel.convertToInt()
	//Test whether convert int works properly on zero (returns zero)
	@Test
	public void testConvertIntZero(){
		try{
			Method method = MainPanel.class.getDeclaredMethod("convertToInt", int.class);
			method.setAccessible(true);
			MainPanel panel = new MainPanel(15);
			Object returnValue = method.invoke(panel, 0);
			int result = ((Integer)returnValue).intValue();
			assertEquals(0, result);
		}
		catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
			fail();
		}
	}
	
	//Test whether convert int works properly on a negative number (throws an exception originally because it can't parse a padded negative, would otherwise return same number)
	@Test
	public void testConvertIntNegative(){
		try{
			Method method = MainPanel.class.getDeclaredMethod("convertToInt", int.class);
			method.setAccessible(true);
			MainPanel panel = new MainPanel(15);
			Object returnValue = method.invoke(panel, -4);
			int result = ((Integer)returnValue).intValue();
			assertEquals(-4, result);
		}
		catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
		}
	}
	
	//Test whether convert int works properly on a very large integer (returns that int)
	@Test
	public void testConvertIntMax(){
		try{
			Method method = MainPanel.class.getDeclaredMethod("convertToInt", int.class);
			method.setAccessible(true);
			MainPanel panel = new MainPanel(15);
			Object returnValue = method.invoke(panel, Integer.MAX_VALUE);
			int result = ((Integer)returnValue).intValue();
			assertEquals(Integer.MAX_VALUE, result);
		}
		catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
			fail();
		}
	}
	
	//Test whether convert int works properly on a normal integer (15 returns 15).
	@Test
	public void testConvertIntTypical(){
		try{
			Method method = MainPanel.class.getDeclaredMethod("convertToInt", int.class);
			method.setAccessible(true);
			MainPanel panel = new MainPanel(15);
			Object returnValue = method.invoke(panel, 15);
			int result = ((Integer)returnValue).intValue();
			assertEquals(15, result);
		}
		catch(NoSuchMethodException | IllegalAccessException | InvocationTargetException e){
			fail();
		}
	}
	
	
	//Cell.toString()
	//Test Cell.toString on an alive Cell. Should return "X"
	@Test
	public void testCellToStringX(){
		Cell testCell = new Cell(true);
		assertEquals(testCell.toString(), "X");
		
	}
	
	//Test Cell.toString on a dead Cell. Should return "."
	@Test
	public void testCellToStringNotAlive(){
		Cell testCell = new Cell(false);
		assertEquals(testCell.toString(), ".");
	}
	
	//Test Cell.toString on a living cell made dead. Should return "."
	@Test
	public void testCellToStringSetNotAlive(){
		Cell testCell = new Cell(true);
		testCell.setAlive(false);
		assertEquals(testCell.toString(), ".");
	}
	
	//MainPanel.backup()
	//Test that backup properly copies an empty grid
	@Test
	public void testBackupEmptyCell(){
		MainPanel m = new MainPanel(15);
		try{
			Field main = MainPanel.class.getDeclaredField("_cells");
			main.setAccessible(true);
			Cell[][] mainCells = (Cell[][])main.get(m);
			Field backup = MainPanel.class.getDeclaredField("_backupCells");
			backup.setAccessible(true);
			m.backup();
			Cell[][] backupCells = (Cell[][])main.get(m);
			assertEquals(backupCells.toString(), mainCells.toString());
		}
		catch(Exception e){
			fail();
		}
	}
	
	//Test that backup properly copies a full grid
	@Test
	public void testBackupFullCell(){
		MainPanel m = new MainPanel(15);
		try{
			Field main = MainPanel.class.getDeclaredField("_cells");
			main.setAccessible(true);
			Cell[][] mainCells = (Cell[][])main.get(m);
			for(int i = 0; i < mainCells.length; i++){
				for(int j = 0; j < mainCells[0].length; j++){
					mainCells[i][j].setAlive(true);
				}
			}
			main.set(m, mainCells); //Actually set field of class
			Field backup = MainPanel.class.getDeclaredField("_backupCells");
			backup.setAccessible(true);
			m.backup();
			Cell[][] backupCells = (Cell[][])main.get(m);
			assertEquals(backupCells.toString(), mainCells.toString());
		}
		catch(Exception e){
			fail();
		}
	}
	
	//Test that backup properly copies a partially-filled grid
	@Test
	public void testBackupPartialCell(){
		MainPanel m = new MainPanel(15);
		try{
			Field main = MainPanel.class.getDeclaredField("_cells");
			main.setAccessible(true);
			Cell[][] mainCells = (Cell[][])main.get(m);
			mainCells[1][2].setAlive(true);
			mainCells[3][6].setAlive(true);
			mainCells[9][7].setAlive(true);
			main.set(m, mainCells); //Actually set field of class
			Field backup = MainPanel.class.getDeclaredField("_backupCells");
			backup.setAccessible(true);
			m.backup();
			Cell[][] backupCells = (Cell[][])main.get(m);
			assertEquals(backupCells.toString(), mainCells.toString());
		}
		catch(Exception e){
			fail();
		}
	}
	
}
