import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.crome.CromeDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testing.Assert;
import org.testing.annotations.BeforeMethod;
import org.testing.annotations.AfterMethod;
import org.testing.annotations.Test;
import java.time.Duration;
import java.util.Set;
import java.util.Iterator;

public class pacticeselenium {

   private WebDriver driver;
   private WebDriverWait wait;
   private Actions actions;
   private final String Base_URL = "https://www.amazon.ca";

   @BeforeMethod
   public void setup() {
    System.setProperty("webdriver.crome.driver", "path/to/crome/driver.exe");
    driver = new CromeDriver();
    driver.manage().window().maximize();
    driver.manage().timeouts.implicitWait(Duration.ofSeconds(10));
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    actions = new Action(driver);
   }

   @Test
   public void testing1() {

    System.out.print("start testing");
    try {
        String actualTitle = driver.getTitle();
        Assert.assertEquals(actualTitle, "wlecome to amazon.ca", "actual title doesnot match with expected title value");
        // 1. Navigate to the login page (or homepage if login is pop-up/modal)
        driver.get(Base_URL + "route=account/login");
        System.out.println("navigated to url" + driver.getCurrentUrl());
        // 2. Perform Login
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email_input")));
        emailInput.sendKeys("abc@gmail.com");
        WebElement passwordInput = driver.findElement(By.id("password_input"));
        passwordInput.sendKeys("xyz@1234");
        WebElement loginButton = driver.findElement(By.xpath("//input[@value = 'login']"));
        loginButton.click();
        // 3. Verify successful login (e.g., check for "My Account" header)
        WebElement accountHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h2[normalize-space()='My Account']")));
        Assert.assertTrue(accountHeader.isDisplayed(), "login failed, account header is not visible");
        // 4. Search for a product
        WebElement searchBar = driver.findElement(By.name("search-products"));
        searchBar.sendKeys("bose headsets");
        //searchBar.sendKeys(Keys:ENTER);
        // Click the search icon
        WebElement searchIconBT = driver.findElement(By.cssSelector("#search-icon"));
        searchIconBT.click();

        // 5. Verify search results
        WebElement searchResult = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains (@class, 'main-search-result-container')]/..")));
        Assert.assertTrue(searchResult.isDisplayed(), "could not find search results ");

        // 6. Hover over a product image and click "Quick View" (if available, or direct click)
        WebElement firstProductLink = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//div[@class = 'product-text']//a")));
        // For simplicity, let's just click on the first product link
        // If the element is covered by something else, this click might fail.
        // Let's try to click the product title link instead, which is usually more stable.
        String productText = firstProductLink.getText();
        firstProductLink.click();
        // 7. Verify product details page
        WebElement productDetailsHeader = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//h1[normalize-space()='" + productText + "']")));
        Assert.assertTrue(productDetailsHeader.isDisplayed(), "expected product title is not found");
        // 8. Open a new tab and navigate to a related page (e.g., Contact Us)

        String currentUrlHandle = driver.getWindowHandle();
        driver.switchTo.newWindow(WindowType:TAB);
        driver.get(Base_URL + "route=account/orders");
        Assert.assertTrue(driver.getTitle().contains("previous order history"),"error msg");


        // Open a new tab (Selenium 4+ way)
        // 9. Switch back to the original product details tab
        driver.switchTo.window(currentUrlHandle);
        // 10. Example of an Action Chain (e.g., hover over an element)
        WebElement someElement = driver.findElement(By.id("main-menu"));
        actions.moveToElement(someElement).Perform();

    } catch (Exception e) {
        e.printStackTrace();
        Assert.fail("test case failed due to exception" + e.getMessage());
        // TODO: handle exception
    }
   }
   @AfterMethod
   public void teardown() {
    driver.quit();
   }

}
