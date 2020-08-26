package dev.fespinosa.cloudstorage;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;

import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CloudStorageApplicationTests {

    @LocalServerPort
    private int port;

    private WebDriver driver;

    @BeforeAll
    static void beforeAll() {
        WebDriverManager.chromedriver().setup();
    }

    @BeforeEach
    public void beforeEach() {
        this.driver = new ChromeDriver();
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);

    }

    @AfterEach
    public void afterEach() {
        if (this.driver != null) {
            driver.quit();
        }
    }

    @Test
    public void testLoginUser() {
        getPage("/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin("fespinosa", "locotron");

        assertThat(driver.getTitle()).isEqualTo("Home");

    }

    @Test
    public void testHomePageIsNotAccessible() {
        getPage("/home");
        LoginPage loginPage = new LoginPage(driver);
        assertThat(driver.getTitle()).isEqualTo("Login");

    }


    @Test
    public void testLoginAndLogoutFlow() {
        getPage("/login");
        LoginPage loginPage = new LoginPage(driver);
        loginPage.doLogin("fespinosa", "locotron");

        assertThat(driver.getTitle()).isEqualTo("Home");

        HomePage homePage = new HomePage(driver);

        homePage.doLogOut();

        getPage("/home");

        assertThat(driver.getTitle()).isEqualTo("Login");

    }


    @Test
    public void testSignsUpLoginAndLogoutFlow() {
        SignUpPage signUpPage = new SignUpPage(driver);
        getPage("/signup");

        signUpPage.doSignUp("Marco", "Espinosa",
                "mespinosa", "secretPassword");

        LoginPage loginPage = new LoginPage(driver);
        getPage("/login");

        loginPage.doLogin("mespinosa", "secretPassword");

        assertThat(driver.getTitle()).isEqualTo("Home");

        HomePage homePage = new HomePage(driver);

        homePage.doLogOut();

        getPage("/home");

        assertThat(driver.getTitle()).isEqualTo("Login");

    }


    private void getPage(String page) {
        driver.get("http://localhost:" + this.port + page);
    }

}
