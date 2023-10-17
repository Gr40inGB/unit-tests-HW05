### Задание №5
Нужно написать сквозной тест с использованием Selenium, который авторизует пользователя на
сайте https://www.saucedemo.com/.
Данные для входа - логин: "standard_user", пароль: "secret_sauce".

Проверить, что авторизация прошла успешно и отображаются товары.


```java

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
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

```

* Задание 1. 

Представьте, что вы работаете над разработкой простого приложения для записной книжки, которое позволяет пользователям добавлять, редактировать и удалять контакты.
Ваша задача - придумать как можно больше различных тестов (юнит-тесты, интеграционные тесты, сквозные тесты) для этого приложения. Напишите название каждого теста, его тип и краткое описание того, что этот тест проверяет.

```java

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
```

* Задание 2. 

Ниже список тестовых сценариев. Ваша задача - определить тип каждого теста (юнит-тест, интеграционный тест, сквозной тест) и объяснить, почему вы так решили. 
1. Проверка того, что функция addContact корректно добавляет новый контакт в список контактов"".

```Unit тест - так как это проверка одного действия```
2. Проверка того, что при добавлении контакта через пользовательский интерфейс, контакт корректно отображается в списке контактов.

```Интеграционный - проверяется взаимодейстивие модулей```
3.   Проверка полного цикла работы с контактом: создание контакта, его редактирование и последующее удаление.

```Скозной тест - так как проверяется полноценная работа и взаимодействие через все модули```