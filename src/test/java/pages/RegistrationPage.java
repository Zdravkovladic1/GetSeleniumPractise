package pages;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RegistrationPage extends BasePage {

    public RegistrationPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".navbar>.container>.extra>a:nth-child(2)")
    WebElement RegisterNavButton;

    @FindBy(css = ".js-noticeDismiss .button-text")
    WebElement AcceptCookiesButton;

    @FindBy(css = ".block-body [autocomplete='username']")
    WebElement UsernameField;

    @FindBy(css = ".block-body [autocomplete='email']")
    WebElement EmailField;

    @FindBy(css = ".block-body .inputGroup [type='password']")
    WebElement PasswordField;

    @FindBy(css = ".block-body .inputGroup [aria-label='Month']")
    WebElement DateOfBirthMonthSelectField;

    @FindBy(css = ".block-body .inputGroup [aria-label='Month'] [value='7']")
    WebElement DateOfBirthMonthField;

    @FindBy(css = ".block-body .inputGroup [name='dob_day']")
    WebElement DateOfBirthDayField;

    @FindBy(css = ".block-body .inputGroup [name='dob_year']")
    WebElement DateOfBirthYearField;

    @FindBy(css = ".block-body [name='location']")
    WebElement LocationField;

    @FindBy(css = "[name='custom_fields[country]']")
    WebElement CountryField;

    @FindBy(css = ".field_mmail [value='None']")
    WebElement NewsletterCheckbox;

    @FindBy(css = ".iconic [name='accept']")
    WebElement PrivacyAndPolicyCheckbox;

    @FindBy(css = ".formSubmitRow-controls>#js-signUpButton")
    WebElement RegisterFormButton;

    @FindBy(css = ".block-body [autocomplete='username']~.is-active")
    WebElement ErrorMessage;

    @FindBy(css = ".block-body [autocomplete='username']~.is-active")
    List<WebElement> errorMsag;

    @FindBy(css = ".blockMessage")
    WebElement BlockMessage;

    @FindBy(css = ".is-active .overlay")
    List<WebElement> blockMsag;

    @FindBy(css = ".is-active .js-overlayClose")
    WebElement PopupCloseButton;


    public void NavigateToRegistrationPage() {
        clickElement(RegisterNavButton, "Registration button from navigation bar.");
        clickElement(AcceptCookiesButton, "Accept cookies on the registration form page.");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.casinomeister.com/forums/login/register");
    }

    String expectedTitles[] = {"Usernames must be unique. The specified username is already in use.", "Please enter a name that is at least 3 characters long."};
    List<String> expectedTitlesList = Arrays.asList(expectedTitles);

    String expectedErrorMessages[] = {"Please enter a valid date of birth.", "Email addresses must be unique. The specified email address is already in use.", "Country: Please enter a value for the required field.", "Usernames must be unique. The specified username is already in use.", "Please enter a name that is at least 3 characters long."};
    List<String> errorMsgList = Arrays.asList(expectedErrorMessages);

    String username = generateUsername();


    public void InsertUsername(String user) {
        insertText(UsernameField, user, "username field.");
        if (errorMsag.size() > 0) {
            WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
            webDriverWait.until(ExpectedConditions.visibilityOf(ErrorMessage));
            System.out.println(ErrorMessage.getText());
            Assert.assertTrue(expectedTitlesList.contains(ErrorMessage.getText()));
        }
    }

    String emailGenerated = generateEmail();

    public void InsertEmail(String mail) {
        insertText(EmailField, mail, "email field.");

    }

    public void setExcelRow(String fileName, String sheetName, String generatedUsername, String generatedEmail) throws IOException {

        FileOutputStream fos = new FileOutputStream("src/test/test_data/" + fileName + ".xlsx");
        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet(sheetName);
        Row row = sheet.createRow(0);
        Cell cell0 = row.createCell(0);
        cell0.setCellValue(generatedUsername);
        System.out.println("Updated Username in xlsx file with string:" + generatedUsername);
        Row row2 = sheet.createRow(1);
        Cell cell2 = row2.createCell(0);
        cell2.setCellValue(generatedEmail);
        System.out.println("Updated Email in xlsx file with string:" + generatedEmail);
        workbook.write(fos);
        fos.flush();
        fos.close();
    }

    public void InsertPassword(String pass) {
        insertText(PasswordField, pass, "password field.");
    }

    public void InsertDateOfBirth(String month, String day, String year) {
        selectDropdown(DateOfBirthMonthSelectField, month, "month");
        insertText(DateOfBirthDayField, day, "day field.");
        insertText(DateOfBirthYearField, year, "year field.");
    }

    public void InsertLocation(String location) {
        insertText(LocationField, location, "location field.");
    }

    public void InsertCountry(String country) {
        selectDropdown(CountryField, country, "country");
    }

    public void NewsletterCheckbox() {
        clickElement(NewsletterCheckbox, "None from the newsletter checkbox.");
    }

    public void TermsAndPrivacyCheckbox() {
        clickElement(PrivacyAndPolicyCheckbox, "Privacy&Policy checkbox.");
    }

    public void SubmitForm() {
        clickElement(RegisterFormButton, "Register button.");
        if (blockMsag.size() > 0) {
            WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
            webDriverWait.until(ExpectedConditions.visibilityOf(BlockMessage));
            System.out.println(BlockMessage.getText());
            Assert.assertTrue(errorMsgList.contains(BlockMessage.getText()));
            clickElement(PopupCloseButton, "Close popup.");
        }
    }

    public List<WebElement> FieldsElement() {
        List<WebElement> inputFields = new ArrayList<WebElement>();
        inputFields.add(UsernameField);
        inputFields.add(EmailField);
        inputFields.add(PasswordField);
        return inputFields;
    }

    public void ValidateErrorMessages() {
        List<String> errorMsg = new ArrayList<>();
        for (WebElement field :
                FieldsElement()) {
            String msg = getTooltipErrorMessages(field);
            if (msg != null && !msg.trim().isEmpty()) {
                errorMsg.add(msg);
            }
        }
        if (!errorMsg.isEmpty()) {
            System.out.println(errorMsg);
            Assert.assertTrue(errorMsg.contains("Please fill in this field."));
        }
    }

    public void SubmitRegistrationForm(String pass, String month, String day, String year, String location, String country) throws IOException {
        NavigateToRegistrationPage();
        InsertUsername(username);
        InsertEmail(emailGenerated);
        InsertPassword(pass);
        setExcelRow("UsernameAndPassword", "UsernameAndPasswordSheet", username, emailGenerated);
        InsertDateOfBirth(month, day, year);
        InsertLocation(location);
        InsertCountry(country);
        NewsletterCheckbox();
        TermsAndPrivacyCheckbox();
        SubmitForm();
    }

    //Username xml: 2 or less digits, empty field, existing username (from excel or xml)
    //Email xml: existing username (from excel or xml), empty field
    public void InvalidSubmitRegistrationForm(String pass, String userName, String email, String month, String day, String year, String location, String country) throws IOException {
        NavigateToRegistrationPage();
        InsertUsername(userName);
//        InsertUsername(ReadFromXlsx(0,0,0,0).get(1));
//        InsertEmail(ReadFromXlsx(0,0,1,0).get(1));
//        InsertEmail(emailGenerated);
        InsertEmail(email);
        InsertPassword(pass);
        InsertDateOfBirth(month, day, year);
        InsertLocation(location);
        InsertCountry(country);
        NewsletterCheckbox();
        TermsAndPrivacyCheckbox();
        SubmitForm();
        ValidateErrorMessages();
    }

}
