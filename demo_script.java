import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class demo_script {

    public static void main(String agrs[]) throws Exception {
        //default
        final String chromeDriverPath = "/home/anika/Desktop/Software/chromedriver_linux64/chromedriver";
        System.setProperty("webdriver.chrome.driver", chromeDriverPath);
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--ignore-certificate-errors", "--silent");
        options.addArguments("--user-agent=Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/87.0.4280.141 Safari/537.36");
        WebDriver driver = new ChromeDriver(options);
        // Get the login page
        driver.get("https://register.fca.org.uk/s/prohibited-individual?predefined=PI");
        Thread.sleep(10000);
        //**************************************************************************************
        String page = driver.getPageSource();

        List<WebElement> paginationCounter = driver.findElements(By.xpath("//span[@class='pagination__numbers']"));
        int pageSize = Integer.parseInt(paginationCounter.get(1).getText());
        System.out.println(pageSize);
        Map<String, String> dataMap = new HashMap<>();

        int i = 1;

        while (i <= pageSize) {
            List<WebElement> elementList = driver.findElements(By.xpath("//div[contains(@class, 'result-card_main_info')]"));
            int k = 0;
            System.out.println("Page " + i);
            for (WebElement element : elementList) {
                String url = "--", name = "--", ref = "--";
                try {
                    String block_id = "individual-result-card-" + k + "_main-name";
                    WebElement data = element.findElement(By.id(block_id));
                    url = data.getAttribute("href");
                    name = data.getText();
                    //retrieve Reference Number
                    String ref_id = "individual-result-card-" + k + "_main-reference";
                    WebElement ref_elem = element.findElement(By.id(ref_id));
                    ref = ref_elem.getText();
                    //createEntity
                    System.out.println(name + " " + ref + " " + url + "\n");
                    k++;

                } catch (Exception e) {
                    System.out.println(element.toString() + "\n" + name + " " + url + " " + ref + "\n");

                }
            }
            WebElement next = driver.findElement(By.id("-pagination-next-btn"));
            next.click();
            elementList.clear();
            i++;
           // return;
        }
        System.out.println("PAGES Parsed: " + (i-1));

    }
}
