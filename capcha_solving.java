import net.sourceforge.tess4j.ITesseract;
import net.sourceforge.tess4j.Tesseract;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.util.concurrent.TimeUnit;

public class capcha_solving {

    public static void main(String args[]) throws Exception{
        final String BASE_URL="https://www.irctc.co.in/nget/train-search";
        final String chromeDriverPath = "/home/anika/Desktop/Software/chromedriver_linux64/chromedriver" ;
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--disable-gpu", "--window-size=1920,1200","--ignore-certificate-errors", "--silent");
        options.addArguments("--user-agent=537.36 (KHTML, like Gecko) Ubuntu Chromium/87.0.4280.141 Chrome");
        WebDriver driver = new ChromeDriver(options);
        driver.get(BASE_URL);

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
//        /*NEW WINDOW*/
//        Actions actions = new Actions(driver);
//        JavascriptExecutor jse = (JavascriptExecutor)driver;
//        jse.executeScript("scroll(250, 0)"); // if the element is on top.
//        //-------------
        WebElement ok=driver.findElement(By.xpath("//button[contains(@class,'btn-primary')]"));
        ok.click();
        WebElement login=driver.findElement(By.xpath("//a[contains(@class,'loginText')]"));
        login.click();
        driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
        //--------
        String path=System.getProperty("user.dir")+"/src/captcha.png";
        WebElement captcha=driver.findElement(By.xpath("//*[@id='nlpImgContainer']/div[4]"));
        System.out.println(captcha.toString());
        WebElement pic=captcha.findElement(By.xpath("//img[@border]"));
        System.out.println(pic.getAttribute("src"));
        File src=pic.getScreenshotAs(OutputType.FILE);
        FileHandler.copy(src, new File(path));
         }
    }

