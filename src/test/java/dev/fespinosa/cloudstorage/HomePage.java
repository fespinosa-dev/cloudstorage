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

    private NoteModal noteModal;


    public HomePage(WebDriver driver) {
        super(driver);
        noteModal = new NoteModal(driver);
    }


    public void doLogOut() {
        logOutButton.submit();
    }

    public void doAddNote(String title, String description) {
        wait.until(ExpectedConditions.visibilityOf(this.addNoteButton));
        addNoteButton.click();
        noteModal.getTitleField().sendKeys(title);
        noteModal.getDescriptionField().sendKeys(description);
        noteModal.saveNote();
    }

    public void selectTab(String tabName) {
        wait.until(ExpectedConditions.elementToBeClickable(By.linkText(tabName))).click();
    }

}
