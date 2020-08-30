package dev.fespinosa.cloudstorage;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.Optional;

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

        getPage("/home");

        homePage.selectTab("Notes");

        homePage.doAddNote("Note Title", "Note description");

        Optional<WebElement> noteTitleOpt = findElementByText("Note Title");
        Optional<WebElement> noteDescriptionOpt = findElementByText("Note description");

        assertThat(noteTitleOpt).isNotEmpty();
        assertThat(noteDescriptionOpt).isNotEmpty();
        assertThat(noteTitleOpt.get().getText()).isEqualTo("Note Title");
        assertThat(noteDescriptionOpt.get().getText()).isEqualTo("Note description");


    }

    @Test
    public void testEditNote() {
        LoginPage loginPage = new LoginPage(driver);
        getPage("/login");

        loginPage.doLogin("fespinosa", "locotron");

        assertThat(driver.getTitle()).isEqualTo("Home");

        HomePage homePage = new HomePage(driver);

        getPage("/home");

        homePage.selectTab("Notes");

        Optional<WebElement> noteTitleOpt = findElementByText("Learning Spring Boot");
        Optional<WebElement> noteDescriptionOpt = findElementByText("This is a new note desc");

        assertThat(noteTitleOpt).isNotEmpty();
        assertThat(noteDescriptionOpt).isNotEmpty();
        assertThat(noteTitleOpt.get().getText()).isEqualTo("Learning Spring Boot");
        assertThat(noteDescriptionOpt.get().getText()).isEqualTo("This is a new note desc");

        homePage.doEditNote("New title", "New description");

        Optional<WebElement> noteTitleEditedOpt = findElementByText("New title");
        Optional<WebElement> noteDescriptionEditedOpt = findElementByText("New description");

        assertThat(noteTitleEditedOpt).isNotEmpty();
        assertThat(noteDescriptionEditedOpt).isNotEmpty();
        assertThat(noteTitleEditedOpt.get().getText()).isEqualTo("New title");
        assertThat(noteDescriptionEditedOpt.get().getText()).isEqualTo("New description");

    }

    @Test
    public void testDeleteNote() {
        LoginPage loginPage = new LoginPage(driver);
        getPage("/login");

        loginPage.doLogin("fespinosa", "locotron");

        assertThat(driver.getTitle()).isEqualTo("Home");

        HomePage homePage = new HomePage(driver);

        getPage("/home");

        homePage.selectTab("Notes");

        homePage.doDeleteNote();

        Boolean notesPresent = driver.findElements(By.cssSelector("#note_list > tbody tr")).size() > 0;

        assertThat(notesPresent).isFalse();


    }

}
