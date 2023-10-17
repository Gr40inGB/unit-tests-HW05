package selenium;


import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class SeleniumTest {
    @Test
    public void checkTestEnter() {
        WebDriver driver = new EdgeDriver();
        driver.get("https://www.saucedemo.com/");
        WebElement userBox = driver.findElement(By.name("user-name"));
        userBox.sendKeys("standard_user");
        userBox.submit();
        WebElement passwordBox = driver.findElement(By.name("password"));
        passwordBox.sendKeys("secret_sauce");
        passwordBox.submit();
//        WebElement loginButton = driver.findElement(By.name("login-button"));
//        loginButton.submit();

        List<WebElement> webElementList = driver.findElements(By.cssSelector("div"));
        boolean yep = false;
        for (WebElement webElement : webElementList) {
            if (webElement.getText().contains("Products")) yep = true;
        }
         assertTrue(yep);

        driver.close();
    }

    @Test
    public void checkTestButton() {
        WebDriver driver = new EdgeDriver();
        driver.get("https://www.saucedemo.com/");
        WebElement userBox = driver.findElement(By.name("user-name"));
        userBox.sendKeys("standard_user");
        userBox.submit();
        WebElement passwordBox = driver.findElement(By.name("password"));
        passwordBox.sendKeys("secret_sauce");
//        passwordBox.submit();
        WebElement loginButton = driver.findElement(By.name("login-button"));
        loginButton.submit();

        List<WebElement> webElementList = driver.findElements(By.cssSelector("div"));
        boolean yep = false;
        for (WebElement webElement : webElementList) {
            if (webElement.getText().contains("Products")) yep = true;
        }
         assertTrue(yep);
    }

}
