package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.io.IOException;

public class LoginPage extends BasePage{

    public LoginPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".navbar>.container>.main-menu>#search-action+.d-none+.d-none")
    WebElement LoginNavButton;

    @FindBy(css = ".js-noticeDismiss .button-text")
    WebElement AcceptCookiesButton;

    @FindBy(css = "[autocomplete='username']")
    WebElement usernameField;

    @FindBy(css = "[autocomplete='current-password']")
    WebElement passwordField;

    @FindBy(css = ".formSubmitRow-controls .button--primary")
    WebElement loginButton;

    @FindBy(css = ".p-title-value")
    WebElement loginElement;


    public void NavigateToLoginPage(){
        clickElement(LoginNavButton, "Login button from navigation bar.");
        clickElement(AcceptCookiesButton, "Accept cookies on the login form page.");
        WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
        webDriverWait.until(ExpectedConditions.visibilityOf(loginElement));
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.casinomeister.com/forums/login");
    }


    public void insertUsername(String email){
        insertText(usernameField, email, "username field.");
    }

    public void insertPassword(String password) {
        insertText(passwordField, password, "password field.");
    }

    public void submitLoginForm(){
        clickElement(loginButton, "Submit button on login form.");
    }

    public void Login(String password, String email) {
        NavigateToLoginPage();
        insertUsername(email);
        insertPassword(password);
        submitLoginForm();
    }

}
