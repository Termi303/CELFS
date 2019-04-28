package uk.ac.bris.celfs.website;

import org.apache.commons.lang3.SystemUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

@RunWith(SpringRunner.class)
@DataJpaTest
public class FrontendTest {

    @Test
    public void testIndex() throws InterruptedException {
        if(SystemUtils.IS_OS_WINDOWS) System.setProperty("webdriver.chrome.driver","chromedriver_windows.exe");
        else System.setProperty("webdriver.chrome.driver","chromedriver_linux");
        
        ChromeDriver driver = new ChromeDriver();

        //test home link
        driver.get("https://celfs.spe.cs.bris.ac.uk");
        WebElement home = driver.findElement(By.linkText("Home"));
        home.click();
        assertEquals((Object) "https://celfs.spe.cs.bris.ac.uk/", (Object) driver.getCurrentUrl());

        //test login link
        driver.get("https://celfs.spe.cs.bris.ac.uk");
        WebElement login = driver.findElement(By.linkText("Login"));
        /*login.click();
        assertEquals((Object) "https://celfs.spe.cs.bris.ac.uk/login", (Object) driver.getCurrentUrl());
        */
        driver.close();
    }

    @Test
    public void testLogin(){
        if(SystemUtils.IS_OS_WINDOWS) System.setProperty("webdriver.chrome.driver","chromedriver_windows.exe");
        else System.setProperty("webdriver.chrome.driver","chromedriver_linux");
        
        ChromeDriver driver = new ChromeDriver();
        /*
        //test wrong email and no password
        driver.get("https://celfs.spe.cs.bris.ac.uk/login");
        WebElement username=driver.findElement(By.xpath("//input[@name='username']"));
        WebElement button=driver.findElement(By.tagName("button"));
        username.sendKeys("bad_teacher");
        button.click();
        assertEquals((Object) "https://celfs.spe.cs.bris.ac.uk/login?error", (Object) driver.getCurrentUrl());

        //test correct email/password
        driver.get("https://celfs.spe.cs.bris.ac.uk/login");
        username=driver.findElement(By.xpath("//input[@name='username']"));
        button=driver.findElement(By.tagName("button"));
        WebElement password=driver.findElement(By.xpath("//input[@name='password']"));
        username.sendKeys("teacher1");
        password.sendKeys("teacher");
        button.click();
        assertEquals((Object) "https://celfs.spe.cs.bris.ac.uk/", (Object) driver.getCurrentUrl());
        WebElement logout = driver.findElement(By.tagName("button"));

        //test logout
        logout.click();
        assertEquals((Object) "https://celfs.spe.cs.bris.ac.uk/index", (Object) driver.getCurrentUrl());

        //test correct email and incorrect password
        driver.get("https://celfs.spe.cs.bris.ac.uk/login");
        username=driver.findElement(By.xpath("//input[@name='username']"));
        button=driver.findElement(By.tagName("button"));
        password=driver.findElement(By.xpath("//input[@name='password']"));
        username.sendKeys("teacher1");
        password.sendKeys("bad_password");
        button.click();
        assertEquals((Object) "https://celfs.spe.cs.bris.ac.uk/login?error", (Object) driver.getCurrentUrl());
        */
        driver.close();
    }

}
