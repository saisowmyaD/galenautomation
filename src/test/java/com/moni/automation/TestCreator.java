package com.moni.automation;

import static com.moni.automation.utils.ConfigPropertyReader.getProperty;
import static com.moni.automation.utils.YamlReader.getYamlValue;
import static com.moni.automation.utils.YamlReader.setYamlFilePath;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;

import com.moni.automation.utils.TakeScreenshot;

import com.moni.sample.keywords.LoginPageActions;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;

public class TestCreator {

    protected WebDriver driver;
    private final WebDriverFactory wdfactory;
    String browser;
    String seleniumserver;
    String seleniumserverhost;
    String appbaseurl;
    String applicationpath;
    String chromedriverpath;
    String datafileloc = "";
    static int timeout;
    Map<String, Object> chromeOptions = null;
    DesiredCapabilities capabilities;

    /**
     * Initiating the page objects
     */
    public TakeScreenshot takescreenshot;

    private String testname;

    public Random randomGenerator;
    
    public LoginPageActions loginPage;

    public WebDriver getDriver() {
        return this.driver;
    }

    private void _initPage() {
    loginPage = new LoginPageActions(driver);
    }

    /**
     * Page object Initiation done
     *
     * @param testname
     */
    public TestCreator(String testname) {
        wdfactory = new WebDriverFactory();
        testInitiator(testname);
        this.testname = testname;
    }

    public TestCreator(String testname, String browserName) {
        wdfactory = new WebDriverFactory(browserName);
        testInitiator(testname);
        this.testname = testname;

    }

    private void testInitiator(String testname) {
        setYamlFilePath();
        _configureBrowser();
        _initPage();
        takescreenshot = new TakeScreenshot(testname, this.driver);
    }

    private void _configureBrowser() {
        driver = wdfactory.getDriver(_getSessionConfig());
        if (!_getSessionConfig().get("browser").toLowerCase().trim().equalsIgnoreCase("mobile")) {
            driver.manage().window().maximize();
        }
        // driver.manage().window().setSize(new Dimension(414, 628));
        driver.manage()
                .timeouts()
                .implicitlyWait(Integer.parseInt(getProperty("timeout")),
                        TimeUnit.SECONDS);
    }

    private Map<String, String> _getSessionConfig() {
        String[] configKeys = {"tier", "browser", "seleniumserver",
            "seleniumserverhost", "timeout", "driverpath", "appiumServer",
            "mobileDevice"};
        Map<String, String> config = new HashMap<>();
        for (String string : configKeys) {
            config.put(string, getProperty("./Config.properties", string));
        }
        return config;
    }

    public void launchApplication() {
        launchApplication(getYamlValue("base_url"));
    }

    public void launchApplication(String base_url) {
        Reporter.log("\n[INFO]: The application url is :- " + base_url, true);
        driver.manage().deleteAllCookies();
        driver.get(base_url);

    }

    public void openUrl(String url) {
        driver.get(url);
    }

    public void closeBrowserSession() {
        Reporter.log("[INFO]: The Test: " + this.testname.toUpperCase() + " COMPLETED!"
                + "\n", true);

        driver.quit();
        //	Thread.sleep(3000);// [INFO]: this to wait before you close every

    }

    public void closeTestSession() {
        closeBrowserSession();
    }
}
