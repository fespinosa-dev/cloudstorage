package dev.fespinosa.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends Page {

    @FindBy(id = "inputUsername")
    private WebElement usernameField;

    @FindBy(id = "inputPassword")
    private WebElement passwordField;

    @FindBy(id = "loginButton")
    private WebElement loginButton;


    public LoginPage(WebDriver driver) {
        super(driver);

    }


    public void doLogin(String userName, String password) {
//        wait.until(ExpectedConditions.visibilityOf(this.usernameField));
        usernameField.sendKeys(userName);
        passwordField.sendKeys(password);
        loginButton.submit();
    }


}
