//package selenium;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//import org.openqa.selenium.By;
//import org.openqa.selenium.WebElement;
//import org.openqa.selenium.chrome.ChromeDriver;
//
//import static org.junit.Assert.assertTrue;
//
//public class IncorrectUsageTest {
//    private static ChromeDriver driver;
//
//    @BeforeClass
//    public static void setup() {
//        System.setProperty("webdriver.chrome.driver", "C:\\Users\\Arcero\\blackjack-backend\\src\\test\\resources\\chromedriver.exe");
//        driver = new ChromeDriver();
//        driver.manage().window().maximize();
//        driver.get("http://35.205.127.204/");
//        sleep(2000);
//    }
//
//    @Before
//    public void setUpTest() {
//        driver.getSessionStorage().clear();
//        driver.getLocalStorage().clear();
//        driver.navigate().refresh();
//        sleep(2000);
//    }
//
//    @Test
//    public void testTryToStartWithoutAnAccount() {
//        driver.findElementById("").click();
//        assertTrue();
//    }
//
//    private static void sleep(long millis) {
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
