package tests;

import Selenium.DriverManager;
import Selenium.DriverManagerFactory;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

public class BaseTest {

    public WebDriver driver;
    DriverManager driverManager;

    public void init() throws Exception {
        driverManager = DriverManagerFactory.getDriverManager("CHROME");
        driver = driverManager.getWebDriver();
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
    }

    public void openApp(String env) {
        switch (env.toUpperCase()){
            case "PROD" :{
                driver.get("https://www.casinomeister.com/");
            }
            break;
            case "QA" :{
                driver.get("qa://www.casinomeister.com/");
            }
            break;
            case "DEV" :{
                driver.get("dev://www.casinomeister.com/");
            }
            break;
        }

    }

    public void quit(){
        driverManager.quitWebDriver();
    }
}
