package dev.fespinosa.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class NoteModal extends Page {

    @FindBy(id = "note-title")
    private WebElement titleField;

    @FindBy(id = "note-description")
    private WebElement descriptionField;

    @FindBy(id = "saveNoteChangesBtn")
    private WebElement saveChangesButton;


    public NoteModal(WebDriver driver) {
        super(driver);
    }


    public WebElement getTitleField() {
        wait.until(ExpectedConditions.elementToBeClickable(titleField)).click();
        return titleField;
    }


    public WebElement getDescriptionField() {
        wait.until(ExpectedConditions.elementToBeClickable(descriptionField)).click();
        return descriptionField;
    }

    public void saveNote() {
        saveChangesButton.click();
    }
}
