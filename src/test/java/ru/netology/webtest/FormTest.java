package ru.netology.webtest;

import dev.failsafe.internal.util.Assert;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class FormTest {

    WebDriver driver;

    @BeforeAll
    static void setupAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--no-sandbox");
        options.addArguments("--headless");
        driver = new ChromeDriver(options);
        driver.get("http://localhost:9999");
    }

    @AfterEach
    void teardown() {
        driver.quit();
    }

    @Test
    public void shouldValidTest() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Смирнова Юлия");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldValidName1() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Смирнова-Иванова Юлия");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test //BUG
    public void shouldValidName2() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Ёлкина Алёна");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=order-success]")).getText();
        assertEquals("Ваша заявка успешно отправлена! Наш менеджер свяжется с вами в ближайшее время.", text.trim());
    }

    @Test
    public void shouldInvalidName1() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Smirnova");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] span .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldInvalidName2() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("12456 67");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] span .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldInvalidName3() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Смирнова@");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] span .input__sub")).getText();
        assertEquals("Имя и Фамилия указаные неверно. Допустимы только русские буквы, пробелы и дефисы.", text.trim());
    }

    @Test
    public void shouldInvalidPhone1() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Смирнова Юлия");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+798765432101");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] span .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldInvalidPhone2() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Смирнова Юлия");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+7987654321");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] span .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldInvalidPhone3() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Смирнова Юлия");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("@79876543210");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] span .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldInvalidPhone4() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Смирнова Юлия");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("Смирнова");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] span .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldInvalidPhone5() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Смирнова Юлия");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("79876543210");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] span .input__sub")).getText();
        assertEquals("Телефон указан неверно. Должно быть 11 цифр, например, +79012345678.", text.trim());
    }

    @Test
    public void shouldEmptyInputTest1() {
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79876543210");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=name] span .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void shouldEmptyInputTest2() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Смирнова Юлия");
        driver.findElement(By.cssSelector("[data-test-id=agreement]")).click();
        driver.findElement(By.className("button__content")).click();
        String text = driver.findElement(By.cssSelector("[data-test-id=phone] span .input__sub")).getText();
        assertEquals("Поле обязательно для заполнения", text.trim());
    }

    @Test
    public void shouldEmptyInputTest3() {
        driver.findElement(By.cssSelector("[data-test-id=name] input")).sendKeys("Смирнова Юлия");
        driver.findElement(By.cssSelector("[data-test-id=phone] input")).sendKeys("+79876543210");
        driver.findElement(By.className("button__content")).click();
        WebElement checkbox = driver.findElement(By.cssSelector("[data-test-id=agreement].input_invalid"));
        Assertions.assertTrue(checkbox.isDisplayed());
    }
}

