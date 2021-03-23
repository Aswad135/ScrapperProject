import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Scrapper {

    private final String baseURL = "https://www.bvb.ro/FinancialInstruments/SelectedData/CurrentReports";
    JavascriptExecutor js;
    private int daysToScrap;
    private WebDriver driver;
    private Map<String, Object> vars;
    private Database db;

    public static void main(String[] args) {
        Scrapper scrapperObject = new Scrapper();
        scrapperObject.testMethod();
    }

    public void testMethod() {
        try {
            db = new Database("aswad", "chootay");
            Scanner input = new Scanner(System.in);
            System.out.println("Enter number of days to scrap");
            daysToScrap = input.nextInt();
            System.setProperty("webdriver.chrome.driver", "C:\\chromedriver.exe");
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--disable-extensions", "--no-sandbox", "--disable-dev-shm-usage");
            driver = new ChromeDriver(chromeOptions);
            driver.manage().window().maximize();
            js = (JavascriptExecutor) driver;
            driver.get(baseURL);
            int count = 1;
            int days = 10;
            while (days <= daysToScrap) {
                WebRecords records;
                boolean flag = false;
                Thread.sleep(10000);
                while (true) {
                    List<WebElement> list = driver.findElements(By.cssSelector("#gvv tr"));
                    for (int i = 1; i < list.size(); i++) {
                        String col1 = driver.findElement(By.cssSelector("#gvv tr:nth-child(" + i + ")  > td:nth-child(" + 1 + ")")).getText();
                        if (col1 == null || col1.equals("")) {
                            try {
                                col1 = driver.findElement(By.cssSelector("#gvv tr:nth-child(" + i + ") > td:nth-child(" + 1 + ")")).getAttribute("data-search");
                            } catch (Exception e) {

                            }
                        }
                        if (col1 == null) {
                            col1 = "";
                        }
                        String Symbol = "";
                        String ISIN = "";
                        if (col1.length() > 1) {

                            Symbol = col1.split("\n")[0];
                            ISIN = col1.split("\n")[1];
                        }
                        String Company = driver.findElement(By.cssSelector("#gvv tr:nth-child(" + i + ")  > td:nth-child(" + 2 + ")")).getText();
                        String Description = driver.findElement(By.cssSelector("#gvv tr:nth-child(" + i + ") > td:nth-child(" + 3 + ")")).getText();
                        String date = driver.findElement(By.cssSelector("#gvv tr:nth-child(" + i + ")  > td:nth-child(" + 4 + ")")).getText();
                        String FileType = driver.findElement(By.cssSelector("#gvv tr:nth-child(" + i + ")  > td:nth-child(" + 5 + ")")).getText();


                        List<WebElement> elements = driver.findElements(By.cssSelector("#gvv tr:nth-child(" + i + ")  > td:nth-child(" + 6 + ") > a"));
                        String link1 = "";
                        if (elements.size() == 1) {
                            link1 = elements.get(0).getAttribute("href");
                        }
                        String link2 = "";
                        if (elements.size() == 2) {
                            link2 = elements.get(0).getAttribute("href");
                        }


                        records = new WebRecords(Symbol, ISIN, Company, Description, date, FileType, link1, link2);
                        db.addRecordToDatabase(records);


                        System.out.println("Records added: " + count);
                        count++;

                    }
                    if (!(driver.findElement(By.linkText("Next")).getAttribute("class").contains("disabled")))
                        js.executeScript("arguments[0].click();", driver.findElement(By.linkText("Next")));
                    else
                        break;
                }
                js.executeScript("arguments[0].click();", driver.findElement(By.xpath("//*[(@id = \"lbleft\")]")));
                days+=10;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


}
