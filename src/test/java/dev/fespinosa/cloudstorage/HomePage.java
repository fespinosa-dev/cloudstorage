package dev.fespinosa.cloudstorage;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class HomePage extends Page {

    @FindBy(id = "logOutButton")
    private WebElement logOutButton;

    @FindBy(id = "addNoteButton")
    private WebElement addNoteButton;

    @FindBy(id = "addCredentialButton")
    private WebElement addCredentialButton;

    private NoteModal noteModal;

    private CredentialModal credentialModal;


    public HomePage(WebDriver driver) {
        super(driver);
        noteModal = new NoteModal(driver);
        credentialModal = new CredentialModal(driver);
    }


    public void doLogOut() {
        logOutButton.submit();
    }

    public void addNote(String title, String description) {
        wait.until(ExpectedConditions.visibilityOf(this.addNoteButton));
        addNoteButton.click();
        noteModal.getTitleField().sendKeys(title);
        noteModal.getDescriptionField().sendKeys(description);
        noteModal.saveNote();
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("note_list")));
    }

    public void editNoteByTitle(String oldTitle, String newTitle, String description) {
        WebElement element = driver.findElement(By.xpath("//button[@data-title='" + oldTitle + "']"));
        element.click();
        noteModal.getTitleField().clear();
        noteModal.getDescriptionField().clear();
        noteModal.getTitleField().sendKeys(newTitle);
        noteModal.getDescriptionField().sendKeys(description);
        noteModal.saveNote();
    }

    public void deleteNoteByTitle(String title) {
        final String SEARCH_DELETE_BUTTON = "//button[@data-title='" + title + "']//following-sibling::a";
        WebElement element = driver.findElement(By.xpath(SEARCH_DELETE_BUTTON));
        element.click();
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void addCredential(String url, String username, String password) {
        wait.until(ExpectedConditions.visibilityOf(this.addCredentialButton));
        addCredentialButton.click();
        credentialModal.getCredentialUrlField().sendKeys(url);
        credentialModal.getCredentialUsernameField().sendKeys(username);
        credentialModal.getCredentialPasswordField().sendKeys(password);
        credentialModal.saveCredential();
    }

    public void editCredentialByUrl(String oldUrl, String newUrl, String username, String password) {
        WebElement editCredentialBtn = driver.findElement(By.xpath("//button[@data-url='" + oldUrl + "']"));
        editCredentialBtn.click();
        credentialModal.clearFields();
        credentialModal.getCredentialUrlField().sendKeys(newUrl);
        credentialModal.getCredentialUsernameField().sendKeys(username);
        credentialModal.getCredentialPasswordField().sendKeys(password);
        credentialModal.saveCredential();
    }

    public void deleteCredentialByUrl(String url) {
        final String SEARCH_DELETE_BUTTON = "//button[@data-url='" + url + "']//following-sibling::a";
        WebElement deleteButton = driver.findElement(By.xpath(SEARCH_DELETE_BUTTON));
        deleteButton.click();
        wait.until(ExpectedConditions.invisibilityOf(deleteButton));
    }


    public void selectTab(String tabName) {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(tabName))).click();
        if ("Notes".equals(tabName)) {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("note_list")));
        }
        if ("Credentials".equals(tabName)) {
            wait.until(ExpectedConditions.elementToBeClickable(By.id("credential_list")));
        }
    }

}
