import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class PlaygroundTestSuite {

    @Test
    public void ExampleTest() {
        WebDriver driver = new ChromeDriver();

        driver.get("https://d1iw6mb9di5l9r.cloudfront.net/");

        driver.findElement(By.id("forename")).sendKeys("Mark");

        driver.quit();
    }
}
