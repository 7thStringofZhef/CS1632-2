import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

import org.junit.BeforeClass;
import org.junit.Test;

public class testSort {
	private static ArrayList<int[]> intArrays = new ArrayList<int[]>();
	
	//Generate my arrays, only once. 
	@BeforeClass
	public static void oneTimeSetUp()
	{
		int numberOfArrays = 200; //How many arrays do I want?
		for(int i = 0; i < numberOfArrays; i++)
		{
			intArrays.add(generateRandomArray());
		}
	}
	
	//Test whether running a sort on an already-sorted array changes it (it should not)
	@Test
	public void testIdemopotency() {
		for(int i = 0; i < intArrays.size(); i++)
		{
			int[] array = intArrays.get(i).clone(); //Clone the array so I don't mess with it
			Arrays.sort(array);
			int[] sortedArray = array.clone();
			Arrays.sort(sortedArray);
			assertArrayEquals(array, sortedArray); //Ensure that the double-sorted array is the same as single-sorted
		}
	}
	
	//Test whether running a sort on an array results in the same output every time (it should)
	@Test
	public void testPurity()
	{
		for(int i = 0; i < intArrays.size(); i++)
		{
			int[] array = intArrays.get(i).clone(); //Clone the array so I don't mess with it
			Arrays.sort(array);
			int[] array2 = intArrays.get(i).clone();
			Arrays.sort(array2); //Sort the array a seperate time
			assertArrayEquals(array, array2); //Compare the two sorted arrays
		}
	}
	
	//Ensure that every element in the array is greater than or equal to its predecessor
	@Test
	public void testGreaterOrEqual()
	{
		for(int i = 0; i < intArrays.size(); i++)
		{
			int[] array = intArrays.get(i).clone(); //Clone the array so I don't mess with it
			Arrays.sort(array);
			for(int j = 1; j < array.length; j++)
			{
				assertTrue(array[j] >= array[j-1]); //Compare each pair of elements
			}
		}
	}
	
	//Ensure that the sorted array is the same size as the unsorted array
	@Test
	public void testSameSize()
	{
		for(int i = 0; i < intArrays.size(); i++)
		{
			int[] array = intArrays.get(i).clone(); //Clone the array so I don't mess with it
			Arrays.sort(array);
			assertTrue(array.length == intArrays.get(i).length);
		}
	}
	
	//Ensure that the sorted array contains every element from input array
	@Test
	public void testOutputContains()
	{
		for(int i = 0; i < intArrays.size(); i++)
		{
			int[] array = intArrays.get(i).clone(); //Clone the array so I don't mess with it
			Arrays.sort(array);
			for(int j = 0; j < intArrays.get(i).length; j++)
			{
				int value = intArrays.get(i)[j];
				assertTrue(IntStream.of(array).anyMatch(x->x==value)); //Check each value from input array
			}
		}
	}

	//Generate a random int (any valid int value) array of a random size (up to 10,000, I had out-of-memory errors otherwise)
	private static int[] generateRandomArray()
	{
		Random rng = new Random();
		int arraySize = rng.nextInt(10000);
		int returnArray[] = new int[arraySize];
		for(int i = 0; i < returnArray.length; i++)
		{
			returnArray[i] = rng.nextInt();
		}
		return returnArray;
	}
}
