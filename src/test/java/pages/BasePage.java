package pages;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BasePage {

    WebDriver driver;
    int waitTime = 60;
    public BasePage(WebDriver driver) {
        this.driver = driver;
    }

    public void clickElement(WebElement element, String log){
        WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            new Actions(driver).moveToElement(element).click().perform();
            System.out.println("Clicked element: " + log);
        } catch (Exception e) {
            e.printStackTrace();
            element.click();
            System.out.println("Clicked element: " + log);
        }
    }

    public void clickElementJS(WebElement element, String log) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            scrollToElement(element);
            ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            System.out.println("Clicked element: " + log);
        } catch (Exception e) {
            e.printStackTrace();
            element.click();
            System.out.println("Clicked element: " + log);
        }
    }

    public String generateUsername(){
        String prefix = "pera";
        String date = LocalTime.now().toString();
        String fullUsername = prefix + date;
        return fullUsername;
    }

    public String generateEmail(){
        Random numb = new Random();
        int upperbound = 20000;
        String prefix = "pera";
        String fullUsername = prefix + numb.nextInt(upperbound) + "@lovac.com" ;
        return fullUsername;
    }

    public void insertText(WebElement element, String text, String log) {
        WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));
        try {
            scrollToElement(element);
            new Actions(driver).moveToElement(element).perform();
            element.click();
            element.clear();
            element.sendKeys(text);
            System.out.println("Entered text: "+text+" to element: " + log);
        }catch (Exception e){
            e.printStackTrace();
            element.sendKeys(text);
            System.out.println("Entered text: "+text+" to element: " + log);
        }
    }

    public void selectDropdown(WebElement element, String value, String log){
        Select dropdownItem = new Select(element);
        WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));

        try {
            scrollToElement(element);
            new Actions(driver).moveToElement(element).perform();
            dropdownItem.selectByValue(value);
            System.out.println("Pick from dropdown by value: "+value+" to element: " + log);
        }catch (Exception e){
            e.printStackTrace();
            dropdownItem.selectByValue(value);
            System.out.println("Entered text: "+value+" to element: " + log);
        }
    }

    public void selectDropdown(WebElement element, int index, String log){
        Select dropdownItem = new Select(element);
        WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
        webDriverWait.until(ExpectedConditions.visibilityOf(element));
        webDriverWait.until(ExpectedConditions.elementToBeClickable(element));

        try {
            scrollToElement(element);
            new Actions(driver).moveToElement(element).perform();
            dropdownItem.selectByIndex(index);
            System.out.println("Pick from dropdown by value: "+index+" to element: " + log);
        }catch (Exception e){
            e.printStackTrace();
            dropdownItem.selectByIndex(index);
            System.out.println("Entered text: "+index+" to element: " + log);
        }
    }

    public String getTooltipErrorMessages(WebElement element){
//        JavascriptExecutor js = (JavascriptExecutor)driver;
//        String message = (String)js.executeScript("return arguments[0].validationMessage;", element);
//        return message;
        String message = element.getAttribute("validationMessage");
        return message;
    }

    public WebElement getRandomElement(List<WebElement> list){
        Random random = new Random();
        int randomNumber = random.nextInt(list.size()-1);
        WebElement randomElement = list.get(randomNumber);
        return randomElement;
    }

    public void scrollToElement(WebElement element) {
        ((JavascriptExecutor)driver).executeScript("arguments[0].scrollIntoView(true)",element);
    }

    public List<String> ReadFromXlsx(int rowUsername, int cellUsername, int rowEmail, int cellEmail) throws IOException {
        File file = new File("src/test/test_data/UsernameAndPassword.xlsx");
        FileInputStream inputStream = new FileInputStream(file);
        XSSFWorkbook wb=new XSSFWorkbook(inputStream);
        XSSFSheet sheet=wb.getSheet("UsernameAndPasswordSheet");
        Row row1=sheet.getRow(rowUsername);
        Cell cell=row1.getCell(cellUsername);
        String username= cell.getStringCellValue();
        Row row2=sheet.getRow(rowEmail);
        Cell cell2=row2.getCell(cellEmail);
        String email= cell2.getStringCellValue();
        System.out.println("username is :"+ username);
        System.out.println("email is :"+ email);
        ArrayList<String> excelData = new ArrayList<>();
        excelData.add(username);
        excelData.add(email);
        return excelData;
    }

}
