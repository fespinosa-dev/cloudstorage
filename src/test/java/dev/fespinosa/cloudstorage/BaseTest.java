package dev.fespinosa.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.Optional;
import java.util.concurrent.TimeUnit;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseTest {

    @LocalServerPort
    protected int port;

    protected WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        this.driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
//            driver.quit();
        }
    }

    protected Optional<WebElement> findElementByText(String text) {
        Optional<WebElement> webElementOpt = Optional.empty();
        try {
            WebElement element = driver.findElement(By.xpath("//*[contains(text(),'" + text + "')]"));
            webElementOpt = Optional.of(element);
        } catch (NoSuchElementException ex) {
            ex.printStackTrace();
        }
        return webElementOpt;
    }

    protected void getPage(String page) {
        driver.get("http://localhost:" + this.port + page);
    }
}
