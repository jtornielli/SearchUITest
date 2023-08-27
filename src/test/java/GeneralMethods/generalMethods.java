package GeneralMethods;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class generalMethods {
    public WebElement findElementById(WebDriver driver, String id){
       WebElement element = driver.findElement(By.id(id));
        return element;
    }
    public WebElement findElementByCss(WebDriver driver, String css){
        WebElement element = driver.findElement(By.cssSelector(css));
        return element;
    }
    public static void refreshBrowser(WebDriver driver) {
        driver.navigate().refresh();
    }

    public static WebElement findElementByXpath(WebDriver driver , String xpath){
        return driver.findElement(By.xpath(xpath));
    }

}
