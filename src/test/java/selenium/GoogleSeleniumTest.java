//package selenium;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//
//import static org.junit.Assert.assertTrue;
//
//public class GoogleSeleniumTest {
//    private ChromeDriver driver;
//
//    @Before
//    public void setup() {
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Arcero\\blackjack-backend\\src\\test\\resources\\chromedriver.exe");
//        driver = new ChromeDriver();
//    }
//
//    @Test
//    public void createAccountTest() {
//        driver.manage().window().maximize();
//        driver.get("http://35.205.127.204/");
//        sleep(5000);
//
//    }
//
//    private void sleep(long millis) {
//        long original = System.currentTimeMillis();
//        while (true) {
//            if (System.currentTimeMillis() - original >= millis) {
//                break;
//            }
//        }
//    }
//
//    @After
//    public void teardown() {
//        driver.close();
//    }
//}
