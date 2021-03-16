import net.sourceforge.tess4j.ITesseract;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.io.FileHandler;

import java.io.File;
import java.io.IOException;

public class capcha_testing {

    public static void main(String args[]) throws InterruptedException, IOException {
        final String chromeDriverPath = "/home/anika/Desktop/Software/chromedriver_linux64/chromedriver" ;
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        WebDriver driver=new ChromeDriver();

        final String testUrl="http://www.captcha.net/";
        driver.get(testUrl);
        Thread.sleep(10000);

        WebElement img=driver.findElements(By.xpath("//img")).get(0);
        //-----------
        String path=System.getProperty("user.dir")+"/src/captcha.png";
        File src=img.getScreenshotAs(OutputType.FILE);
        FileHandler.copy(src, new File(path));
    }
}
