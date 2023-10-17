package org.gr40in.app;

import org.gr40in.fakeDatabase.DataBase;
import org.gr40in.model.Contact;
import org.gr40in.model.ContactService;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class AppTest {


    // Тест меню приложения  (Unit-test)
    @Test
    void menuSelect() {
        App app = new App();
        List<Commands> commandsList = app.getMenu();
        assertNotNull(commandsList);
        assert !commandsList.isEmpty();
    }

    // Тест парсинга строки в контакт  (Unit-test)
    @Test
    void parseTest() {
        String validString = "Sergey Nemchinsky JavaGod 8-937-455-98-74";
        String wrongString = "Poperechny Danila RedHead just@try.dont.giveUp";
        assertEquals(ContactService.parseToContact(validString).getClass(), Contact.class);
        assertNotEquals(ContactService.parseToContact(wrongString).getClass(), Contact.class);
    }

    // Тест парсинга мобильного номера  (Unit-test)
    @Test
    void parsePhone() {
        String validString = "8-937-455-98-74";
        String wrongString = "8-a128838";
        assertTrue(validString.matches("(\\+?)([8|7|9])(-?)\\d{3}(-?)" +
                "\\d{1}(-?)\\d{1}(-?)\\d{1}(-?)\\d{1}(-?)\\d{1}(-?)\\d{1}(-?)\\d{1}"));
        assertFalse(wrongString.matches("(\\+?)([8|7|9])(-?)\\d{3}(-?)" +
                "\\d{1}(-?)\\d{1}(-?)\\d{1}(-?)\\d{1}(-?)\\d{1}(-?)\\d{1}(-?)\\d{1}"));

    }


    // Тест соответствия вычитывания списков из базы  (Интеграционный)
    @Test
    void showAll() {
        String baseDirector = "\\Somewhere";
        DataBase dataBase = new DataBase(baseDirector);
        App app = new App(dataBase);

        assertEquals(app.getAll().size(), dataBase.getAllContacts().size());
    }


    // Сквозной по web интерфейсу

    @Test
    public void checkTestNewContact() {
        WebDriver driver = new EdgeDriver();
        driver.get("https://www.BestOfTheBestContactAppEver");
        WebElement userBox = driver.findElement(By.name("user-name"));
        userBox.sendKeys("JustMe");
        userBox.submit();
        WebElement passwordBox = driver.findElement(By.name("password"));
        passwordBox.sendKeys("Qwerty12345");
        passwordBox.submit();

        List<WebElement> webElementList = driver.findElements(By.cssSelector("div"));
        boolean yep = false;
        for (WebElement webElement : webElementList) {
            if (webElement.getText().contains("All your Contacts")) yep = true;
        }
        assertTrue(yep);  // значит авторизовались и открыли список контактов

        WebElement addBox = driver.findElement(By.name("add-new"));
        addBox.submit();

        String newName = "random name" + new Random().nextInt();
        String newPhoneNumber = "89274646431";

        WebElement newNameBox = driver.findElement(By.name("new-contact-name"));
        newNameBox.sendKeys(newName);
        newNameBox.submit();
        WebElement phoneBox = driver.findElement(By.name("new-Phone"));
        phoneBox.sendKeys(newPhoneNumber);
        phoneBox.submit();


        wait(2000);  // ждем? =)


        List<WebElement> webElementList = driver.findElements(By.cssSelector("div"));
        boolean yep2 = false;
        for (WebElement webElement : webElementList) {
            if (webElement.getText().contains(ContactService.parseToContact(newName + newPhoneNumber).getname()))
                yep = true;
        }
        assertTrue(yep2);  // значит контакт появился на странице и все хорошо

        driver.close();
    }


}