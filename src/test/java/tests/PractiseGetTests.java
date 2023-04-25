package tests;

import org.testng.annotations.*;
import pages.ForumPage;
import pages.RegistrationPage;
import pages.LoginPage;

import java.io.IOException;
import java.text.ParseException;

public class PractiseGetTests extends BaseTest{

    @BeforeMethod
    public void setup() throws Exception {
        init();
        openApp("Prod");
    }

    @AfterMethod(enabled = true)
    public void tearDown() {
        quit();
    }

    @Parameters({"password", "month","day", "year", "location", "country"})
    @Test(enabled = true)
    public void ValidRegister(String password, String month, String day, String year, String location, String country) throws IOException {
        new RegistrationPage(driver).SubmitRegistrationForm(password, month, day, year, location, country);

    }
    @Parameters({"password", "userName","email","month","day", "year", "location", "country"})
    @Test(enabled = true)
    public void InvalidRegister(String password, @Optional String userName,@Optional String email, String month, String day, String year, String location, String country) throws IOException {
        new RegistrationPage(driver).InvalidSubmitRegistrationForm(password,userName,email, month, day, year, location, country);
    }

    @Parameters({"password", "email"})
    @Test(enabled = true)
    public void LoginForum(String password,@Optional String email) throws  ParseException, InterruptedException {
        new LoginPage(driver).Login(password, email);
        new ForumPage(driver).CheckNumberOfTopics();
    }

    @Parameters({"password", "email"})
    @Test(enabled = true)
    public void ForumTopicsCheck(String password, String email) throws  InterruptedException {
        new LoginPage(driver).Login(password, email);
        new ForumPage(driver).CheckTheWhatsNew();
    }

    @Parameters({"password", "email", "keyword"})
    @Test(enabled = true)
    public void SearchCheck(String password, String email, String keyword) {
        new LoginPage(driver).Login(password, email);
        new ForumPage(driver).CheckSearch(keyword);
    }
}
