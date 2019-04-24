package uk.ac.bris.celfs.website;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import static org.junit.Assert.assertEquals;

public class FrontendTest {
    public static void main(String[] args){
        FirefoxDriver driver=new FirefoxDriver();
        driver.get("https://celfs.spe.cs.bris.ac.uk/login");
        WebElement username=driver.findElement(By.xpath("//input[@name='username']"));
        username.sendKeys("bad_teacher");
        WebElement button=driver.findElement(By.tagName("button"));
        button.click();
//        System.out.println("The URL is " + driver.getCurrentUrl());
        assertEquals((Object) "https://celfs.spe.cs.bris.ac.uk/login", (Object) driver.getCurrentUrl());

        driver.get("https://celfs.spe.cs.bris.ac.uk/login");
        WebElement password=driver.findElement(By.xpath("//input[@name='password']"));
        username.sendKeys("teacher1");
        password.sendKeys("teacher");
        button.click();
        assertEquals((Object) "https://celfs.spe.cs.bris.ac.uk", (Object) driver.getCurrentUrl());
    }
}
