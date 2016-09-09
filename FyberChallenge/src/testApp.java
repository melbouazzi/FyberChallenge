
import static org.junit.Assert.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


public class testApp{

	public WebDriver driver;
	public String URL, Node;

	
	public static boolean isThereAtLeastOnMovieInTheList(WebDriver driver, List<WebElement> elementsList){
		int moviesSize;

		moviesSize = elementsList.size();
		if( moviesSize >= 1)
			return true;
		
		return false;
	}
	
	@Before
	public void setUp()
	{
		System.setProperty("webdriver.chrome.driver", "chrome/chromedriver.exe");
		driver = new ChromeDriver();
		driver.get("http://www.imdb.com/chart/top");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
	}

	
	@Test
	public void test1() throws InterruptedException{
		boolean actual = false;
		List<WebElement> moviesList;
		
		moviesList = driver.findElements(By.xpath("//*[@id='main']/div/span/div/div/div[3]/table/tbody/tr"));
		actual = isThereAtLeastOnMovieInTheList(driver, moviesList);
		
		assertEquals( true, actual);
	}
	
	@Test
	public void test2(){
		boolean actual = true;
		Select sortingOptions;
		int optionsSize;
		List<WebElement> moviesList;
		
		sortingOptions = new Select(driver.findElement(By.xpath("//*[@id='main']/div/span/div/div/div[3]/div/div/div[1]/select")));
		optionsSize = sortingOptions.getOptions().size();
		moviesList = driver.findElements(By.xpath("//*[@id='main']/div/span/div/div/div[3]/table/tbody/tr"));
		
		for(int i=0; i<optionsSize; i++){
			sortingOptions.selectByIndex(i);
			if( !isThereAtLeastOnMovieInTheList(driver, moviesList) ){
				actual = false;
				break;
			}
		}

		assertEquals( true, actual);
	}
	
	@Test
	public void test3(){
		boolean actual;
		List<WebElement> moviesList;
		
		//click on western genre
		driver.findElement(By.xpath("//*[@id='sidebar']/div[6]/span/ul/li[21]/a")).click();
		//wait page to redirect
		WebDriverWait wait = new WebDriverWait(driver, 10);
		wait.until(ExpectedConditions.titleIs("IMDb: Highest Rated Western Feature Films With At Least 25000 Votes - IMDb"));
		
		moviesList =  driver.findElements(By.xpath("//*[@id='main']/div/div/div[3]/div"));
		actual = isThereAtLeastOnMovieInTheList(driver, moviesList);
		
		assertEquals( true, actual);
	}
	
	
	@After
	public void closeBrowser()
	{
		driver.quit();
	}
}