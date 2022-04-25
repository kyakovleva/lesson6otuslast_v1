package Utils;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

public class MoveToElements {
    private WebDriver driver;
    private JavascriptExecutor js;

    public void moveToElement(WebElement element) {
		/*Actions actions = new Actions(driver);
		actions.moveToElement(element).build().perform();*/
        js.executeScript("arguments[0].scrollIntoView(false)", element);
    }

}
