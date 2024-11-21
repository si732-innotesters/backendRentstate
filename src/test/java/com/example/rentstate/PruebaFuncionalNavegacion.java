package com.example.rentstate;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.time.Duration;

public class PruebaFuncionalNavegacion {
    private WebDriver driver;
    private WebDriverWait wait;
//ComentarioDos
    @Before
    public void setUp() {
        System.setProperty("webdriver.chrome.driver", "chromedriver-win64/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        driver.manage().window().setSize(new Dimension(1920, 1039));
    }

    @After
    public void tearDown() {
        driver.quit();
    }

    @Test
    public void pruebaNavegacionALoginYBienvenida() {
        driver.get("http://localhost:4200/home");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Lets Start')]"))).click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:4200/login"));
        assertEquals("http://localhost:4200/login", driver.getCurrentUrl());

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formcontrolname='username']")))
                .sendKeys("example@gmail.com");

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input[formcontrolname='password']")))
                .sendKeys("1234");

        wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'Login')]"))).click();

        wait.until(ExpectedConditions.urlToBe("http://localhost:4200/welcome"));
        assertEquals("http://localhost:4200/welcome", driver.getCurrentUrl());
    }
}
