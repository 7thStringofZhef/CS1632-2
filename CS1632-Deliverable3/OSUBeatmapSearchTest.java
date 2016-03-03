import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
/*
 * As a user
 * I would like to be able to sort through the beatmaps
 * So I can find a good one to play
 */
public class OSUBeatmapSearchTest {

	static WebDriver driver = new HtmlUnitDriver();
	static WebDriverWait wait = new WebDriverWait(driver, 10); //Wait up to 10 seconds
	
	//Start at the OSU home page for each test
	@Before
	public void setUp() throws Exception {
		driver.get("https://osu.ppy.sh");
	}
	
	/*
	 * Given that I'm on the mainpage and not logged in
	 * When I search beatmaps for any string
	 * Then I will get all beatmap results and the login menu will drop down
	 */
	@Test
	public void testNotLoggedInBeatmapSearch()
	{
		driver.findElement(By.id("beatmap-search")).sendKeys("asdf");
		driver.findElement(By.id("beatmap-search")).submit();
		WebElement dropdown = driver.findElement(By.className("login-dropdown"));
		assertTrue(dropdown.isDisplayed());
	}
	
	/*
	 * Given that I'm on the mainpage and logged in
	 * When I search beatmaps for any string
	 * Then I will get beatmap results AND more search options
	 */
	@Test
	public void testLoggedInBeatmapSearch()
	{
		driver.findElement(By.id("username-field")).sendKeys("7thStringofZhef");
		driver.findElement(By.id("password-field")).sendKeys("z1P^)+29X%d");
		driver.findElement(By.name("login")).click();
		driver.findElement(By.id("beatmap-search")).sendKeys("asdf");
		driver.findElement(By.id("beatmap-search")).submit();
		try{
			driver.findElement(By.linkText("osu!mania"));
			driver.findElement(By.linkText("Ranked & Approved"));
			driver.findElement(By.linkText("Recommended Difficulty"));
			driver.findElement(By.xpath("//div[@class='beatmapListing']"));
		}
		catch(NoSuchElementException e)
		{
			fail();
		}	
	}
	
	/*
	 * Given that I'm on the mainpage and not logged in
	 * When I search users for 7thStringofZhef
	 * Then I will get a profile page for that user
	 */
	@Test
	public void testValidUserSearch()
	{
		driver.findElement(By.id("user-search")).sendKeys("rafis");
		driver.findElement(By.id("user-search")).submit();
		try{
			driver.findElement(By.id("userpage"));
		}
		catch(NoSuchElementException e)
		{
			fail();
		}
	}
	
	/*
	 * Given that I'm on the mainpage and not logged in
	 * When I search users for an invalid user like angklasdfknsdf
	 * Then I will get a message saying the user was not found
	 */
	@Test
	public void testInvalidUserSearch()
	{
		driver.findElement(By.id("user-search")).sendKeys("angklasdfknsdf");
		driver.findElement(By.id("user-search")).submit();
		String text = driver.findElement(By.xpath("//div[@class='paddingboth'][1]/h2")).getText();
		assertEquals(text, "The user you are looking for was not found!");
	}
}
