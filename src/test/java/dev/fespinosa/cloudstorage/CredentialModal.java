package dev.fespinosa.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CredentialModal extends Page {

    @FindBy(id = "credential-url")
    private WebElement credentialUrlField;

    @FindBy(id = "credential-username")
    private WebElement credentialUsernameField;

    @FindBy(id = "credential-password")
    private WebElement credentialPasswordField;

    @FindBy(id = "saveCredentialChangesBtn")
    private WebElement saveChangesButton;


    public CredentialModal(WebDriver driver) {
        super(driver);
    }


    public void saveCredential() {
        saveChangesButton.click();
    }

    public WebElement getCredentialPasswordField() {
        return credentialPasswordField;
    }

    public WebElement getCredentialUrlField() {
        return credentialUrlField;
    }

    public WebElement getCredentialUsernameField() {
        return credentialUsernameField;
    }

    public void clearFields() {
        credentialPasswordField.clear();
        credentialUrlField.clear();
        credentialUsernameField.clear();
    }
}
