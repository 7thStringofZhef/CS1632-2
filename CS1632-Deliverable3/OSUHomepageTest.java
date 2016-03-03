import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * As a user
 * I would like to be able to access important osu links from the main page
 * So that I do not have to google search to get to the important parts
 */
public class OSUHomepageTest {
	static WebDriver driver = new HtmlUnitDriver();
	static WebDriverWait wait = new WebDriverWait(driver, 10); //Wait up to 10 seconds
	
	//Start at the OSU home page for each test
	@Before
	public void setUp() throws Exception {
		driver.get("https://osu.ppy.sh");
	}
	
	/*
	 * Given that I am on the main page
	 * When I view the title
	 * then I see "osu!"
	 */
	@Test
	public void testShowsCorrectTitle()
	{
		String title = driver.getTitle();
		assertTrue(title.contains("osu!"));
	}
	
	/*
	 * Given that I am on the main page
	 * When I view the top
	 * Then I see social network buttons for osu's facebook and twitter
	 */
	@Test
	public void testShowsSocialLinks()
	{
		try
		{
			driver.findElement(By.id("top_facebook"));
			driver.findElement(By.id("top_follow"));
		}
		catch(NoSuchElementException e)
		{
			fail();
		}
	}
	
	/*
	 * Given that I am on the main page
	 * When I view the headers
	 * Then I see drop-down lists for "info", "download", "wiki/help", "beatmaps", "rankings", and "community"
	 */
	@Test
	public void testShowsHeaderLinks()
	{
		try
		{
			driver.findElement(By.partialLinkText("Info"));
			driver.findElement(By.partialLinkText("Download"));
			driver.findElement(By.partialLinkText("Wiki"));
			driver.findElement(By.partialLinkText("Beatmaps"));
			driver.findElement(By.partialLinkText("Rankings"));
			driver.findElement(By.partialLinkText("Community"));
		}
		catch(NoSuchElementException e)
		{
			fail();
		}
	}
	
	/*
	 * Given that I am on the main page
	 * When I hover over the drop-down list for "Info"
	 * Then I can click on link for "Changelog" to get to the changelog page
	 */
	@Test
	public void testDropDownLinks()
	{
		driver.findElement(By.linkText("Changelog")).click();
		//wait.until(ExpectedConditions.visibilityOfElementLocated(By.className("public_release")));
		String newPageTitle = driver.getTitle();
		assertTrue(newPageTitle.contains("Changelog"));
	}
	
	/*
	 * Given that I am on the main page
	 * When I click the drop-down list for "Community"
	 * Then I am sent to the forums page
	 */
	@Test
	public void testDefaultClickDropDownLinks()
	{
		driver.findElement(By.partialLinkText("Community")).click();
		//wait.until(ExpectedConditions.titleIs("Forum Listing"));
		String newPageTitle = driver.getTitle();
		assertTrue(newPageTitle.contains("Forum Listing"));
	}
	
	/*
	 * Given that I am on the main page
	 * When I click the link to login
	 * Then a dropdown menu appears for me to login with
	 */
	@Test
	public void testLoginLink()
	{
		WebElement dropdown = driver.findElement(By.className("login-dropdown"));
		assertFalse(dropdown.isDisplayed());
		WebElement usrField = driver.findElement(By.id("username-field"));
		driver.findElement(By.cssSelector("a.login-open-button")).click();
		//wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.id("username-field"))));
		assertTrue(usrField.isDisplayed());
	}
	
	/*
	 * Given that I am on the main page
	 * When I view the top of the page
	 * There are search elements for "google search" "User" and "beatmaps"
	 */
	@Test
	public void testHasSearchBoxes()
	{
		try{
			driver.findElement(By.id("cse-search-box"));
			driver.findElement(By.id("user-search"));
			driver.findElement(By.id("beatmap-search"));
		}
		catch(NoSuchElementException e)
		{
			fail();
		}
	}
}

