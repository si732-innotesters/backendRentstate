package com.example.rentstate;

import org.junit.Test;
import org.junit.Before;
import org.junit.After;
import static org.junit.Assert.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

public class PruebaFuncionalAñadirPropiedad {
    private WebDriver driver;
    private Map<String, Object> vars;
    JavascriptExecutor js;
    WebDriverWait wait;

    @Before
    public void setUp() {
        // Establecer la ruta del ChromeDriver
        System.setProperty("webdriver.chrome.driver", "chromedriver-win64/chromedriver.exe");

        driver = new ChromeDriver();
        js = (JavascriptExecutor) driver;
        vars = new HashMap<>();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));  // Espera explícita de 10 segundos
        driver.manage().window().setSize(new Dimension(974, 1040));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    @Test
    public void aadirpropiedad() throws InterruptedException {
        driver.get("http://localhost:4200/home");

        // Iniciar sesión
        WebElement loginButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button")));
        loginButton.click();

        WebElement emailInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[formcontrolname='username']")));
        emailInput.sendKeys("example@gmail.com");

        WebElement passwordInput = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[formcontrolname='password']")));
        passwordInput.sendKeys("1234");

        WebElement submitButton = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("button")));
        submitButton.click();

        wait.until(ExpectedConditions.urlContains("/welcome"));
        driver.navigate().refresh();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("button[routerlink='/my-profile']")));

        WebElement myProfileButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@routerlink, '/my-profile')]")));
        myProfileButton.click();

        WebElement yourPropertiesButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(@routerlink, '/your-properties/1')]")));
        yourPropertiesButton.click();

        WebElement addPropertyButton = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//button[text()='Add Property']")));
        addPropertyButton.click();

        String currentUrl = driver.getCurrentUrl();
        assertEquals("http://localhost:4200/your-properties/1", currentUrl);

        // Rellenar los campos de texto
        WebElement nameField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[formcontrolname='name']")));
        nameField.sendKeys("Cuarto Vender Rápido (San Miguel)");  // Nombre que se espera encontrar después

        WebElement descriptionField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("textarea[formcontrolname='description']")));
        descriptionField.sendKeys("Cuarto ubicado en Lima Perú, cerca a la universidad UPC de sede San Miguel");

        WebElement locationField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("input[formcontrolname='location']")));
        locationField.sendKeys("Lima - Perú");

        WebElement urlImgField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("textarea[formcontrolname='urlImg']")));
        urlImgField.sendKeys("https://besco.com.pe/blog/wp-content/uploads/2022/04/ubicacion-cama.jpeg");

        WebElement characteristicsField = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("textarea[formcontrolname='characteristics']")));
        characteristicsField.sendKeys("-Barato, -Bonito, -Cerca a la universidad, -Cerca a la estación de tren, -Cerca a la estación de bus, -Cerca a la estación de metro");

        WebElement categoryLabel = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-label[contains(text(), 'Category')]")));
        categoryLabel.click();

        WebElement dropdownPanel = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-select-0-panel")));
        WebElement roomsOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Rooms')]")));
        js.executeScript("arguments[0].scrollIntoView(true);", roomsOption);
        roomsOption.click();

        WebElement availableLabel = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//mat-label[contains(text(), 'Available')]")));
        availableLabel.click();  // Activa el dropdown

        WebElement dropdownPanelDos = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("mat-select-2-panel")));
        WebElement yesOption = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(), 'Yes')]")));
        js.executeScript("arguments[0].scrollIntoView(true);", yesOption);
        yesOption.click();


        wait.until(ExpectedConditions.urlContains("your-properties/1"));

        WebElement addButton = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[contains(@class, 'button-save') and text()=' Add ']")));

        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", addButton);
        Thread.sleep(500);

        addButton.click();


        Thread.sleep(5000);

        driver.navigate().refresh();

        Thread.sleep(5000);


        WebElement addedPropertyRow = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//td[text()='asfasfa']"))); // Verifica el nombre
        assertNotNull(addedPropertyRow);

        WebElement categoryCell = driver.findElement(By.xpath("//td[text()='Cuarto Vender Rápido (San Miguel)']/following-sibling::td[text()='room']"));
        assertNotNull(categoryCell);

        Thread.sleep(10000);
    }
}
