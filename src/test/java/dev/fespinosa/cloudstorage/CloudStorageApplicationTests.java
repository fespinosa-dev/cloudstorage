package dev.fespinosa.cloudstorage;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.WebElement;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.MethodOrderer.OrderAnnotation;

@TestMethodOrder(OrderAnnotation.class)
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

        homePage.addNote("Note Title", "Note description");

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

        homePage.editNoteByTitle("Learning Spring Boot", "New title title", "New description 2");

        Optional<WebElement> noteTitleEditedOpt = findElementByText("New title title");
        Optional<WebElement> noteDescriptionEditedOpt = findElementByText("New description 2");

        assertThat(noteTitleEditedOpt).isNotEmpty();
        assertThat(noteDescriptionEditedOpt).isNotEmpty();
        assertThat(noteTitleEditedOpt.get().getText()).isEqualTo("New title title");
        assertThat(noteDescriptionEditedOpt.get().getText()).isEqualTo("New description 2");

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

        homePage.addNote("Note to be deleted", "We are going to delete you");

        Optional<WebElement> noteToBeDeleted = findElementByText("Note to be deleted");

        assertThat(noteToBeDeleted).isNotEmpty();

        homePage.deleteNoteByTitle("Note to be deleted");

        Optional<WebElement> noteDeletedOpt = findElementByText("Note to be deleted");

        assertThat(noteDeletedOpt).isEmpty();


    }

    @Test
    public void testAddCredential() {
        LoginPage loginPage = new LoginPage(driver);
        getPage("/login");

        loginPage.doLogin("fespinosa", "locotron");

        assertThat(driver.getTitle()).isEqualTo("Home");

        HomePage homePage = new HomePage(driver);

        getPage("/home");

        homePage.selectTab("Credentials");

        homePage.addCredential("https://www.google.com/", "username", "password");

        Optional<WebElement> urlOpt = findElementByText("https://www.google.com/");
        Optional<WebElement> usernameOpt = findElementByText("username");
        Optional<WebElement> passwordOpt = findElementByText("password");


        assertThat(urlOpt).isNotEmpty();
        assertThat(usernameOpt).isNotEmpty();
        assertThat(passwordOpt).isNotEmpty();
        assertThat(urlOpt.get().getText()).isEqualTo("https://www.google.com/");
        assertThat(usernameOpt.get().getText()).isEqualTo("username");
        assertThat(passwordOpt.get().getText()).isEqualTo("password");

    }

    @Test
    public void testEditCredential() {
        LoginPage loginPage = new LoginPage(driver);
        getPage("/login");

        loginPage.doLogin("fespinosa", "locotron");

        assertThat(driver.getTitle()).isEqualTo("Home");

        HomePage homePage = new HomePage(driver);

        getPage("/home");

        homePage.selectTab("Credentials");

        homePage.editCredentialByUrl("http://test.com", "https://www.google2.com/",
                "new username", "new password");

        Optional<WebElement> urlOpt = findElementByText("https://www.google2.com/");
        Optional<WebElement> usernameOpt = findElementByText("new username");
        Optional<WebElement> passwordOpt = findElementByText("new password");


        assertThat(urlOpt).isNotEmpty();
        assertThat(usernameOpt).isNotEmpty();
        assertThat(passwordOpt).isNotEmpty();
        assertThat(urlOpt.get().getText()).isEqualTo("https://www.google2.com/");
        assertThat(usernameOpt.get().getText()).isEqualTo("new username");
        assertThat(passwordOpt.get().getText()).isEqualTo("new password");

    }

    @Test
    public void testDeleteCredential() {
        LoginPage loginPage = new LoginPage(driver);
        getPage("/login");

        loginPage.doLogin("fespinosa", "locotron");

        assertThat(driver.getTitle()).isEqualTo("Home");

        HomePage homePage = new HomePage(driver);

        getPage("/home");

        homePage.selectTab("Credentials");

        homePage.addCredential("https://www.credential-to-delete.com/", "fespinosa", "password");

        Optional<WebElement> credentialToBeDeleted = findElementByText("https://www.credential-to-delete.com/");

        assertThat(credentialToBeDeleted).isNotEmpty();

        homePage.deleteCredentialByUrl("https://www.credential-to-delete.com/");

        Optional<WebElement> deletedCredential = findElementByText("https://www.credential-to-delete.com/");

        assertThat(deletedCredential).isEmpty();

    }


}
