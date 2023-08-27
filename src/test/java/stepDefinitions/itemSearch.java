package stepDefinitions;
import GeneralMethods.generalMethods;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.FileWriter;
import java.io.IOException;
import java.security.Key;
import java.time.Duration;
import java.util.Random;

public class itemSearch extends generalMethods{
    private WebDriver driver;
    private FileWriter writer;

    @Before
    public void setUp(){

        //webdriver instance creation
        System.setProperty("webdriver.edge.driver","C:\\Users\\jtornielli\\IdeaProjects\\abstractaUITest\\src\\main\\java\\Drivers\\msedgedriver.exe");
        EdgeOptions edgeOptions = new EdgeOptions();
        driver = new EdgeDriver(edgeOptions);
        // random number for the filename
        Random random = new Random();
        int randomNumber = random.nextInt(100000); // The large range is to assure that we will not get a match

        try {
            String filename = "product_details_" + randomNumber + ".txt";
            writer = new FileWriter(filename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @After
    public void tearDown(){
        driver.quit();
        closeFileWriter();
    }

    @Given(": I have access to the url requested")
    public void i_have_access_to_the_url_requested() {
        String url = "https://www.mercadolibre.com.uy/";
        driver.get(url);
    }
    @And(": I use the search bar and input the require search")
    public void i_use_the_search_bar_and_input_the_require_search() {
        //Using the id Label for maintainability
        WebElement searchBar = findElementById(driver,"cb1-edit");
        searchBar.sendKeys("camisetas");
        searchBar.sendKeys(Keys.ENTER);
    }
    @Given(": I click on the products and get the required information")
    public void i_click_on_the_products_and_get_the_required_information() {
        // Wait for the search results to be loaded
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Wait up to 5 seconds
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.ui-search-layout__item.shops__layout-item")));
        java.util.List<WebElement> productElements = driver.findElements(By.cssSelector("li.ui-search-layout__item.shops__layout-item"));

        //with this I check the overall html structure to verify each webelement
        /*for (WebElement productElement : productElements) {
            System.out.println(productElement.getAttribute("outerHTML")); // Print the text content of the element
            System.out.println("--------");
        }*/
        for (WebElement productElement : productElements) {

            WebElement titleElement = productElement.findElement(By.cssSelector("h2.ui-search-item__title"));
            WebElement priceElement = productElement.findElement(By.cssSelector("span.andes-money-amount__fraction"));
            WebElement linkElement = productElement.findElement(By.cssSelector("a.ui-search-link"));

            String title = titleElement.getText();
            String price = priceElement.getText();
            String link = linkElement.getAttribute("href");

            writeProductInfoToFile(title,price,link);

        }
    }
    @Given(": I repeat the process of the previous step in the second and third result page")
    public void i_repeat_the_process_of_the_previous_step_in_the_second_and_third_result_page() {
        //I´m trying to disregard the cookie consent that is getting in the way of the next page button
        try {
            WebElement cookieBanner = driver.findElement(By.className("cookie-consent-banner-opt-out__container"));
            cookieBanner.click();
        } catch (Exception e) {
            // Banner not found or could not be clicked
        }

        //navigate to second result page
        findElementByXpath(driver,"//main[@id='root-app']/div/div[2]/section//nav//a[@role='button']/span[@class='andes-pagination__arrow-title']").click();
        // Wait for the search results to be loaded
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5)); // Wait up to 5 seconds
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.ui-search-layout__item.shops__layout-item")));
        java.util.List<WebElement> productElements = driver.findElements(By.cssSelector("li.ui-search-layout__item.shops__layout-item"));

        for (WebElement productElement : productElements) {

            WebElement titleElement = productElement.findElement(By.cssSelector("h2.ui-search-item__title"));
            WebElement priceElement = productElement.findElement(By.cssSelector("span.andes-money-amount__fraction"));
            WebElement linkElement = productElement.findElement(By.cssSelector("a.ui-search-link"));
            String link = linkElement.getAttribute("href");
            String title = titleElement.getText();
            String price = priceElement.getText();
            writeProductInfoToFile(title,price,link);

        }
        //navigate to third result page
        findElementByXpath(driver,"//main[@id='root-app']/div/div[2]/section//nav//a[@role='button']/span[@class='andes-pagination__arrow-title']").click();
        WebDriverWait wait2 = new WebDriverWait(driver, Duration.ofSeconds(5)); // Wait up to 5 seconds
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("li.ui-search-layout__item.shops__layout-item")));
        java.util.List<WebElement> productElements2 = driver.findElements(By.cssSelector("li.ui-search-layout__item.shops__layout-item"));

        for (WebElement productElement : productElements2) {

            WebElement titleElement = productElement.findElement(By.cssSelector("h2.ui-search-item__title"));
            WebElement priceElement = productElement.findElement(By.cssSelector("span.andes-money-amount__fraction"));
            WebElement linkElement = productElement.findElement(By.cssSelector("a.ui-search-link"));

            String title = titleElement.getText();
            String price = priceElement.getText();
            String link = linkElement.getAttribute("href");

            writeProductInfoToFile(title,price,link);
        }
    }

    @Then(": I should have a list of all the products in a text file")
    public void i_should_have_a_list_of_all_the_products_in_a_text_file() {

    }

    private void closeFileWriter() {
        try {
            if (writer != null) {
                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void writeProductInfoToFile(String title, String price, String link) {
        try {
            writer.write("Titulo: " + title + "\n");
            writer.write("Precio: " + price + "\n");
            writer.write("Link del producto: " + link + "\n");
            writer.write("--------\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
