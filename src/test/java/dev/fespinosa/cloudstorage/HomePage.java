package dev.fespinosa.cloudstorage;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends Page {

    @FindBy(id = "logOutButton")
    private WebElement logOutButton;


    public HomePage(WebDriver driver) {
        super(driver);
    }


    public void doLogOut() {
        logOutButton.submit();
    }

}
