package com.critik.api;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.time.Duration;

public class NavigationTests {

    @Test
    void main() {

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless", "--no-sandbox", "--disable-dev-shm-usage", "window-size=1920,1080");

        WebDriver driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));

        driver.get("http://localhost:4200");

        Wait<WebDriver> wait1 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait1.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"navbarNav\"]/ul/li[2]/a"))
        );

        driver.findElement(By.xpath("//*[@id=\"navbarNav\"]/ul/li[2]/a")).click();

        Wait<WebDriver> wait2 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait2.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"navbarNav\"]/ul/li[3]/a"))
        );

        driver.findElement(By.xpath("//*[@id=\"navbarNav\"]/ul/li[3]/a")).click();

        Wait<WebDriver> wait3 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait3.until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"navbarNav\"]/ul/li[4]/a"))
        );

        driver.findElement(By.xpath("//*[@id=\"navbarNav\"]/ul/li[4]/a")).click();

        Wait<WebDriver> wait4 = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait4.until(
                ExpectedConditions.elementToBeClickable(By.xpath("/html/body/app-root/app-header/nav/div/a"))
        );

        driver.findElement(By.xpath("/html/body/app-root/app-header/nav/div/a")).click();

        driver.quit();
    }

}
