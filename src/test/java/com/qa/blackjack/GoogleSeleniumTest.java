package com.qa.blackjack;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.Assert.assertTrue;

public class GoogleSeleniumTest {
    private ChromeDriver driver;

    @Before
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Admin\\blackjack-backend\\src\\test\\java\\resources\\chromedriver.exe");
        driver = new ChromeDriver();
    }

    @Test
    public void searchTest() throws InterruptedException {
        driver.manage().window().maximize();
        driver.get("localhost:3000");
        Thread.sleep(1000);
        WebElement startButton = driver.findElement(By.id("startGameButton"));
        startButton.click();
        Thread.sleep(1000);
        WebElement betField = driver.findElement(By.name("nameField"));
        assertTrue(betField.isDisplayed());
        betField.sendKeys("marty");
        Thread.sleep(5000);
    }

    @After
    public void teardown() {
        driver.close();
    }
}
