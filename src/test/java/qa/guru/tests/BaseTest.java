package qa.guru.tests;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.remote.DesiredCapabilities;
import qa.guru.helpers.Attach;
import qa.guru.pages.*;

import java.util.Map;

import static com.codeborne.selenide.Selenide.closeWebDriver;

public class BaseTest {

    MainPage mainPage = new MainPage();
    LoginPage loginPage = new LoginPage();
    PythonCoursePage pythonCoursePage = new PythonCoursePage();
    JavaCoursePage javaCoursePage = new JavaCoursePage();

    JavaPlusCoursePage javaPlusCoursePage = new JavaPlusCoursePage();

    @BeforeAll
    @Step("Настройка окружения")
    static void setUp() {
        SelenideLogger.addListener("allure", new AllureSelenide());
        Configuration.browser = System.getProperty("browser", "chrome");
        Configuration.browserSize = System.getProperty("browserSize", "1920x1080");
        Configuration.browserVersion = System.getProperty("browserVersion", "113.0");
        Configuration.baseUrl = System.getProperty("baseUrl", "https://qa.guru");
        Configuration.remote = System.getProperty("selenoid");
        Configuration.pageLoadStrategy = "eager";
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability("selenoid:options", Map.<String, Object>of("enableVNC", true, "enableVideo", true));
        Configuration.browserCapabilities = capabilities;
    }

    @AfterEach
    @Step("Добавление вложений")
    void addAttachments() {
        Attach.screenshotAs("Last screenshot");
        Attach.pageSource();
        Attach.browserConsoleLogs();
        Attach.addVideo();
        closeWebDriver();
    }
}