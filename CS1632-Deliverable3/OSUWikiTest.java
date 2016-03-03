import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

/*
 * As a user
 * I would like to be able to access guides
 * so that I can learn how to play the game better
 */
public class OSUWikiTest {

	static WebDriver driver = new HtmlUnitDriver();
	static WebDriverWait wait = new WebDriverWait(driver, 10); //Wait up to 10 seconds
	
	//Start at the OSU home page for each test
	@Before
	public void setUp() throws Exception {
		driver.get("https://osu.ppy.sh");
	}
	
	/*
	 * Given that I'm on the mainpage
	 * When I search click the wiki/faq link
	 * Then I will be sent to the wiki page
	 */
	@Test
	public void testWikiLink()
	{
		driver.findElement(By.partialLinkText("Wiki / Help")).click();
		assertEquals(driver.getTitle(), "osu!wiki");
	}
	
	/*
	 * Given that I'm on the mainpage
	 * When I search click the dropdown faq link
	 * Then I will be sent to the faq page
	 */
	@Test
	public void testFAQLink()
	{
		driver.findElement(By.linkText("FAQ")).click();
		assertEquals(driver.getTitle(), "FAQ - osu!wiki");
	}
	
	/*
	 * Given that I'm on the FAQ page
	 * When I click an FAQ link
	 * Then I will be navigated to that part of the page
	 */
	@Test
	public void testFAQNavigation()
	{
		driver.findElement(By.linkText("FAQ")).click();
		driver.findElement(By.xpath("//table[@id='toc']/tbody/tr/td/ul/li[3]/a/span[2]")).click();
		assertEquals(driver.getCurrentUrl(), "https://osu.ppy.sh/wiki/FAQ#Scoring");
	}
	
	
	/*
	 * Given that I'm on the Wiki page
	 * When I click a different language
	 * Then I will be navigated to that wiki page
	 */
	@Test
	public void testWikiTranslation()
	{
		driver.findElement(By.partialLinkText("Wiki / Help")).click();
		driver.findElement(By.linkText("Deutsch")).click();
		assertEquals(driver.getCurrentUrl(), "http://osu.ppy.sh/wiki/Deutsch"); 
	}
	
	/*
	 * Given that I'm on the Wiki page
	 * When I click a Wiki link
	 * Then I will be navigated to that page
	 */
	@Test
	public void testWikiNavigation()
	{
		driver.findElement(By.partialLinkText("Wiki / Help")).click();
		driver.findElement(By.linkText("Insane")).click();
		assertEquals(driver.getCurrentUrl(), "http://osu.ppy.sh/wiki/Insane");
	}
}
