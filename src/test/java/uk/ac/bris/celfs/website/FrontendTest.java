package uk.ac.bris.celfs.website;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;

public class FrontendTest {
    public static void main(String[] args){
        FirefoxDriver driver=new FirefoxDriver();
        WebElement username=driver.findElement(By.xpath("//input[@name='username']"));
        WebElement button=driver.findElement(By.tagName("button"));
        WebElement password=driver.findElement(By.xpath("//input[@name='password']"));

        //test wrong email and no password
        driver.get("https://celfs.spe.cs.bris.ac.uk/login");
        username.sendKeys("bad_teacher");
        button.click();
        assertEquals((Object) "https://celfs.spe.cs.bris.ac.uk/login", (Object) driver.getCurrentUrl());

        //test correct email/password
        driver.get("https://celfs.spe.cs.bris.ac.uk/login");
        username.sendKeys("teacher1");
        password.sendKeys("teacher");
        button.click();
        assertEquals((Object) "https://celfs.spe.cs.bris.ac.uk", (Object) driver.getCurrentUrl());

        //test correct email and incorrect password
        driver.get("https://celfs.spe.cs.bris.ac.uk/login");
        username.sendKeys("teacher1");
        password.sendKeys("bad_password");
        button.click();
        assertEquals((Object) "https://celfs.spe.cs.bris.ac.uk/login", (Object) driver.getCurrentUrl());
    }
}
