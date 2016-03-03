
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * As a user
 * I would like to be able to create and use an account
 * so that I can keep track of my scores
 */
public class OSUUserFunctionsTest {

	static WebDriver driver = new HtmlUnitDriver();
	static WebDriverWait wait = new WebDriverWait(driver, 10); //Wait up to 10 seconds
	
	//Start at the OSU home page for each test
	@Before
	public void setUp() throws Exception {
		driver.get("https://osu.ppy.sh");
	}
	
	/*
	 * Given that I am on the main page
	 * When I type a correct username and password into the dropdown box
	 * Then I am logged on as that user
	 */
	@Test
	public void testValidLogin()
	{
		driver.findElement(By.className("login-open-button")).click();
		driver.findElement(By.id("username-field")).sendKeys("7thStringofZhef");
		driver.findElement(By.id("password-field")).sendKeys("z1P^)+29X%d");
		driver.findElement(By.name("login")).click();
		
		assertTrue(driver.findElement(By.linkText("Logout")).isDisplayed());
		assertTrue(driver.findElement(By.linkText("7thStringofZhef")).isDisplayed());
	}
	
	/*
	 * Given that I am on the main page
	 * When I type an incorrect username or password into the dropdown box
	 * Then I am not logged on
	 */
	@Test
	public void testInvalidLogin()
	{
		driver.findElement(By.className("login-open-button")).click();
		driver.findElement(By.id("username-field")).sendKeys("derp");
		driver.findElement(By.id("password-field")).sendKeys("herp");
		driver.findElement(By.name("login")).click();
		
		assertFalse(driver.findElement(By.linkText("Logout")).isDisplayed());
	}
	
	/*
	 * Given that I am logged on and on the main page
	 * When I click the logout button
	 * Then I should be logged out
	 */
	@Test
	public void testLogout()
	{
		driver.findElement(By.id("username-field")).sendKeys("7thStringofZhef");
		driver.findElement(By.id("password-field")).sendKeys("z1P^)+29X%d");
		driver.findElement(By.name("login")).click();
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.linkText("Logout")));
		driver.findElement(By.linkText("Logout")).click();
		wait.until(ExpectedConditions.not(ExpectedConditions.visibilityOfElementLocated(By.linkText("Logout"))));
		assertFalse(driver.findElement(By.linkText("Logout")).isDisplayed());
	}
	
	/*
	 * Given that I am logged on and on the main page
	 * When I look at my header
	 * Then I should see links for messages, search, settings, and logout
	 */
	@Test
	public void testUserHeaders()
	{
		driver.findElement(By.id("username-field")).sendKeys("7thStringofZhef");
		driver.findElement(By.id("password-field")).sendKeys("z1P^)+29X%d");
		driver.findElement(By.name("login")).click();
		
		try{
			driver.findElement(By.linkText("Logout"));
			driver.findElement(By.linkText("Settings"));
			driver.findElement(By.linkText("Search"));
			driver.findElement(By.partialLinkText("message"));
		}
		catch(NoSuchElementException e)
		{
			fail();
		}
	}
	
	/*
	 * Given that I am on the home page
	 * When I click the "I'm new" button
	 * Then I should be taken to the user registration page
	 */
	@Test
	public void testNewUserButton()
	{
		driver.findElement(By.className("new-user-button")).click();
		String newPageTitle = driver.getTitle();
		assertTrue(newPageTitle.contains("Create Account"));
	}
	
	/*
	 * Given that I am on the user creation page
	 * When I enter an invalid set of user options
	 * The continue option should not be available
	 */
	
	@Test
	public void testInvalidNewUser()
	{
		driver.get("https://osu.ppy.sh/p/register");
		driver.findElement(By.id("reg-username")).sendKeys("__23");
		driver.findElement(By.id("reg-password")).sendKeys("asdf");
		driver.findElement(By.id("reg-email")).sendKeys("dsdf");
		assertFalse(driver.findElement(By.cssSelector("a.btn.register-button")).isDisplayed());
	}
	
	/*
	 * Given that I am on the user creation page
	 * When I enter a valid set of user options
	 * The continue option should be available
	 */
	
	@Test
	public void testValidNewUser()
	{
		driver.get("https://osu.ppy.sh/p/register");
		driver.findElement(By.id("reg-username")).sendKeys("TheForellinLord");
		driver.findElement(By.id("reg-password")).sendKeys("svarg385kfdT");
		driver.findElement(By.id("reg-email")).sendKeys("levenswrath@gmail.com");
		assertTrue(driver.findElement(By.cssSelector("a.btn.register-button")).isDisplayed());
	}
}
