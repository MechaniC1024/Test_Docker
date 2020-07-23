package com.Docker.TestDocker;

import org.testng.annotations.Test;

import io.qameta.allure.Allure;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Step;
import io.qameta.allure.Story;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;

@Epic("Тестовый набор 1.")
@Feature("Список ответов по запросу.")
public class NewTest {

	RemoteWebDriver driver;
	WebElement field;

	@Step("Браузер на котором выполняются тесты {browser}.")
	@BeforeTest(description = "Инициализация браузера.")
	@Parameters({ "browser" })
	public void beforeTest(@Optional("Chrome") String browser) throws MalformedURLException {
		DesiredCapabilities cap = new DesiredCapabilities();
		if (browser.contains("Firefox"))
			cap.setBrowserName("firefox");
		else
			cap.setBrowserName("chrome");
		//cap.setCapability("enableVNC", true);
		driver = new RemoteWebDriver(new URL("http://localhost:4444/wd/hub/"), cap);
	}

	@Step("Переход на страницу Google.")
	@Story("Переход на страницу Google.")
	@Test
	public void test1() {
		driver.get("http://www.google.com");
	}

	@Step("Ввод поискового запроса.")
	@Story("Ввод поискового запроса.")
	@Test(dependsOnMethods = "test1")
	public void test2() {
		field = driver.findElement(By.xpath("//input[@name='q']"));
		field.sendKeys("Selenium");
	}

	@Step("Нажатие кнопки поиск.")
	@Story("Нажатие кнопки поиск.")
	@Test(dependsOnMethods = "test2")
	public void test3() {
		field.sendKeys(Keys.ENTER);
	}

	@Step("Ввод логина и пароля, нажатие кнопки логин.")
	@Story("Ввод логина и пароля, нажатие кнопки логин.")
	@Test(dependsOnMethods = "test3")
	public void test4() {
		int i = 0;
		WebDriverWait wait = new WebDriverWait(driver, 10);
		List<WebElement> list = wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy((By.xpath("//div[@class='g']"))));
		for(WebElement l : list) {
			System.out.println(l.getText());
			i++;
			Allure.addAttachment(i+"", l.getText());
		}
	}

	@Step("Закрытие браузера.")
	@Story("Закрытие браузера.")
	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}
