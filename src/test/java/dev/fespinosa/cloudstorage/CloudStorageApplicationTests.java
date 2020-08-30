package dev.fespinosa.cloudstorage;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import static org.assertj.core.api.Assertions.assertThat;

class CloudStorageApplicationTests extends BaseTest {


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

    @Test
    public void testAddNote() {
        LoginPage loginPage = new LoginPage(driver);
        getPage("/login");

        loginPage.doLogin("fespinosa", "locotron");

        assertThat(driver.getTitle()).isEqualTo("Home");

        HomePage homePage = new HomePage(driver);

        homePage.selectTab("Notes");

        homePage.doAddNote("Note Title", "Note description");

        WebElement noteTitle = findElementByText("Note Title");
        WebElement noteDescription = findElementByText("Note description");

        assertThat(noteTitle.isDisplayed()).isTrue();
        assertThat(noteDescription.isDisplayed()).isTrue();


    }

}
