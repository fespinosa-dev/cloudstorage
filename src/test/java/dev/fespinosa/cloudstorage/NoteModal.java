package dev.fespinosa.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

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
        return titleField;
    }


    public WebElement getDescriptionField() {
        return descriptionField;
    }

    public void saveNote() {
        saveChangesButton.click();
    }
}
