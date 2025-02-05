package com.alexandre.readinglist;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;

import io.github.bonigarcia.wdm.WebDriverManager;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc(addFilters = false)
@WithMockUser(username = "alexandre", password = "password", roles = "READER")
public class ServerWebTests {
    @Autowired
    private static EdgeDriver browser;

    @Value("${local.server.port}")
    private int port;

    @BeforeAll
    public static void openBrowser() {
        WebDriverManager.edgedriver().driverVersion("132.0.2957.140").setup();
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--headless");
        browser = new EdgeDriver(options);

        browser.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
    }

    @AfterAll
    public static void closeBrowser() {
        browser.quit();
    }

    @Test
    public void addBookToEmptyList() {
        String baseUrl = "http://localhost:" + port;
        browser.get(baseUrl);
        browser.findElement(By.id("username")).sendKeys("alexandre");
        browser.findElement(By.id("password")).sendKeys("password");
        browser.findElement(By.className("primary")).click();
        assertEquals("You have no books in your book list", browser.findElement(By.tagName("div")).getText());
        browser.findElement(By.name("title")).sendKeys("BOOK TITLE");
        browser.findElement(By.name("author")).sendKeys("BOOK AUTHOR");
        browser.findElement(By.name("isbn")).sendKeys("1234567890");
        browser.findElement(By.name("description")).sendKeys("DESCRIPTION");
        browser.findElement(By.tagName("form")).submit();

        WebElement dl = browser.findElement(By.className("bookHeadline"));
        assertEquals("BOOK TITLE by BOOK AUTHOR (ISBN: 1234567890)", dl.getText());

        WebElement dt = browser.findElement(By.className("bookDescription"));
        assertEquals("DESCRIPTION", dt.getText());
    }
}
