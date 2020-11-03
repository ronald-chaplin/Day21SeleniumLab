import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SeleniumLabTest {

    private WebDriver driver;

    @BeforeEach
    void setUp() {
        System.setProperty("webdriver.chrome.driver", "d:/selenium/chromedriver.exe");
        driver = new ChromeDriver();
        driver.get("https://fayetteville.craigslist.org/");
    }

    @Test
    void backTest() {
        WebElement anchor = driver.findElement(By.linkText("free"));
        anchor.click();
        driver.navigate().back();
        assertEquals("https://fayetteville.craigslist.org/", driver.getCurrentUrl());
    }

    @Test
    void clickFreeTest(){
        WebElement anchor = driver.findElement(By.linkText("free"));
        anchor.click();
        assertEquals("https://fayetteville.craigslist.org/d/free-stuff/search/zip", driver.getCurrentUrl());
    }

    @Test
    void searchTest(){
        driver.findElement(By.name("query")).sendKeys("GTX 2080" + Keys.ENTER);
        assertEquals("fayetteville, NC for sale \"GTX 2080\" - craigslist", driver.getTitle());
    }

    @Test
    void checkBoxTest(){
        driver.findElement(By.name("query")).sendKeys("rocking chair" + Keys.ENTER);
        WebElement anchor = driver.findElement(By.name("bundleDuplicates"));
        anchor.click();
        assertEquals("https://fayetteville.craigslist.org/search/sss?query=rocking+chair&sort=rel&bundleDuplicates=1", driver.getCurrentUrl());
    }

    @Test
    void languageDropdownTest(){
        WebElement anchor = driver.findElement(By.name("lang"));
        Select select = new Select(anchor);
        select.selectByValue("?lang=it&setlang=1");
        assertEquals("https://fayetteville.craigslist.org/?lang=it", driver.getCurrentUrl());
    }

    @Test
    void gotServicesOpenCategoriesAndUncheckBoxTest(){
        WebElement anchor = driver.findElement(By.linkText("services"));
        anchor.click();
        anchor = driver.findElement(By.className("maincats"));
        anchor.click();
        anchor = driver.findElement(By.id("cat_cps"));
        anchor.click();
        assertFalse(anchor.isSelected());
    }

    @Test
    void loginTest(){
        WebElement anchor = driver.findElement(By.xpath("//*[@id=\"postlks\"]/li[2]/a"));
        anchor.click();
        anchor = driver.findElement(By.name("inputEmailHandle"));
        anchor.sendKeys("generic@generic.com");
        anchor = driver.findElement(By.name("inputPassword"));
        anchor.sendKeys("password", Keys.ENTER);
        boolean verification = driver.getPageSource().contains("Further verification required");
        assertTrue(verification);
    }

    @AfterEach
    void tearDown(){
        driver.close();
    }
}
