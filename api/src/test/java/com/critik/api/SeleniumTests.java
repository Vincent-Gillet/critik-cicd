package com.critik.api;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.Wait;
import org.springframework.boot.test.context.SpringBootTest;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

class SeleniumTests {

    // Selenium test - v33

    @Test
    void main() {

        WebDriver driver = new ChromeDriver();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        driver.get("http://localhost:4200");

        Wait<WebDriver> wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"navbarNav\"]/ul/li[4]/a"))
        );

        driver.findElement(By.xpath("//*[@id=\"navbarNav\"]/ul/li[4]/a")).click();

        driver.findElement(By.id("firstNameSubscription")).sendKeys("john");
        driver.findElement(By.id("emailUserSubscription")).sendKeys("john@gmail.com");
        driver.findElement(By.id("passwordUserSubscription")).sendKeys("john1234");
        driver.findElement(By.id("passwordUserValidationSubscription")).sendKeys("john1234");
        driver.findElement(By.xpath("/html/body/app-root/app-subcribe/section/div/form/div[6]/button")).click();

        Wait<WebDriver> waitLogin = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitLogin.until(
                ExpectedConditions.elementToBeClickable(By.id("emailLogin"))
        );

        driver.findElement(By.id("emailLogin")).sendKeys("john@gmail.com");
        driver.findElement(By.id("passwordLogin")).sendKeys("john1234");
        driver.findElement(By.xpath("/html/body/app-root/app-login/form/div[3]/button")).click();

        Wait<WebDriver> waitNavbar = new WebDriverWait(driver, Duration.ofSeconds(10));
        waitNavbar.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"navbarNav\"]/ul/li[1]/a"))
        );

        driver.findElement(By.xpath("//*[@id=\"navbarNav\"]/ul/li[1]/a\n")).click();

    }
}
