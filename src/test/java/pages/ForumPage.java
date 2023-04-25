package pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ForumPage extends BasePage{

    public ForumPage(WebDriver driver) {
        super(driver);
        PageFactory.initElements(driver, this);
    }

    @FindBy(css = ".navbar>.container>.main-menu>a:nth-child(5)")
    WebElement ForumNavButton;

    @FindBy(css = ".js-noticeDismiss .button-text")
    WebElement AcceptCookiesButton;

    @FindBy(css = ".p-navEl>[data-nav-id='newPosts']")
    WebElement ForumElement;

    @FindBy(css = "[data-widget-definition='new_posts'] .structItem--thread .structItem-title [data-tp-primary='on']")
    List<WebElement> ForumTopics;

    @FindBy(css = ".p-body-pageContent >.block--messages .block-outer:nth-child(2) .pageNav-main li:last-child")
    WebElement LastPage;

    @FindBy(css = ".p-body-pageContent >.block--messages .block-outer:nth-child(2) .pageNav-main li:last-child")
    List<WebElement> Pages;

    @FindBy(css = ".message-attribution-opposite--list li:last-child a")
    List<WebElement> Comments;

    @FindBy(css = ".p-navEl >[data-nav-id='newPosts']")
    WebElement NewPostsNavBarItem;

    @FindBy(css = ".p-body-main  .hScroller >.hScroller-scroll .tabs-tab:first-child")
    WebElement WhatsNewNavBarItem;

    @FindBy(css = "[data-widget-key='newthreadshome'] h3 a")
    WebElement NewTopicsLabel;

    @FindBy(css = "[data-widget-key='whats_new_new_posts'] h3 a")
    WebElement LatestPostsLabel;

    @FindBy(css = ".p-discovery [title='Search'] .p-navgroup-linkText")
    WebElement SearchNavBarItem;

    @FindBy(css = ".inputList [name='keywords']")
    WebElement SearchInputField;

    @FindBy(css = ".formSubmitRow-controls .button--primary")
    WebElement SearchButton;

    @FindBy(css = ".contentRow-title a")
    List<WebElement> SearchTitles;

    @FindBy(css = ".contentRow-snippet")
    List<WebElement> SearchTexts;

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_RESET = "\u001B[0m";

    public void OpenForumPage(){
        clickElement(ForumNavButton, "Pick Forum item from navigation bar.");
        WebDriverWait webDriverWait = new WebDriverWait(driver, waitTime);
        webDriverWait.until(ExpectedConditions.visibilityOf(ForumElement));
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.casinomeister.com/forums/");
    }

    public void OpenRandomTopic() {
        clickElementJS(getRandomElement(ForumTopics), "Open random Topic.");
    }

    public void openLastTopicPage(){
        if(Pages.size()>0){
            clickElement(LastPage, "Open last page.");
        }
    }

    public void SelectLastComment() throws ParseException {
        int Comment = Comments.size();
        WebElement lastComment = Comments.get(Comment-1);
        scrollToElement(lastComment);
        Integer elementValue = Integer.valueOf(lastComment.getText().replaceAll("[#,]", ""));
        if(elementValue > CalculateDifferenceBetweenDates()){
            System.out.println("There is more comments than days in this year.");
        }    else {
            System.out.println("There is less comments than days in this year.");
        }

    }

    public long CalculateDifferenceBetweenDates() throws ParseException {
        String myDate = "2023/01/01";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        Date date = sdf.parse(myDate);
        long millis = date.getTime();
        Calendar dates = Calendar.getInstance();
        long millisecondsDate = dates.getTimeInMillis();
        long timeDiff = Math.abs(millisecondsDate - millis);
        long daysDiff = TimeUnit.DAYS.convert(timeDiff, TimeUnit.MILLISECONDS);
        return daysDiff;
    }

    private void CheckNewTopicsSection() {
        scrollToElement(NewTopicsLabel);
        Assert.assertTrue(NewTopicsLabel.isDisplayed());
    }

    private void CheckLatestPostSection() {
        scrollToElement(LatestPostsLabel);
        Assert.assertTrue(LatestPostsLabel.isDisplayed());
    }
    private void OpenWhatsNewFeed() throws InterruptedException {
        clickElement(WhatsNewNavBarItem, "Open What's new feed.");
        Thread.sleep(3000);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.casinomeister.com/forums/whats-new/");
    }

    private void OpenNewPostsFeed() {
        clickElement(NewPostsNavBarItem, "Open New posts feed.");
        Assert.assertTrue(driver.getCurrentUrl().contains("https://www.casinomeister.com/forums/whats-new/posts"));
    }

    private void OpenSearchPage(){
        clickElement(SearchNavBarItem, "Open Search page.");
    }

    private void InsertKeyword(String keyword){
        insertText(SearchInputField, keyword, "keyword field.");
        clickElement(SearchButton, "Search button.");
    }

    private void CheckKeywordInResults(String keyword){
        List<String> resultsText = new ArrayList<>();
        for (int i = 0; i < SearchTitles.size(); i++) {
            String tempItem = SearchTitles.get(i).getText() + SearchTexts.get(i).getText();
            resultsText.add(tempItem);
        }
        for (String element :
                resultsText) {
            System.out.println(ANSI_RED+"Check does element: "+ANSI_RESET+  element.toLowerCase()+ ANSI_RED+ " contains: " +ANSI_RESET + keyword);
            Assert.assertTrue(element.toLowerCase().contains(keyword));
        }
    }

    public void CheckNumberOfTopics() throws ParseException {
        OpenForumPage();
        OpenRandomTopic();
        openLastTopicPage();
        SelectLastComment();
    }
    public void CheckTheWhatsNew() throws InterruptedException {
        OpenForumPage();
        OpenNewPostsFeed();
        OpenWhatsNewFeed();
        CheckLatestPostSection();
        CheckNewTopicsSection();
    }
    public void CheckSearch(String keyword){
        OpenForumPage();
        OpenSearchPage();
        InsertKeyword(keyword);
        CheckKeywordInResults(keyword);
    }


}
