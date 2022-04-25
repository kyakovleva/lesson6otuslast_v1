import config.ServerConfig;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.MainPage;
import pages.PersPage;

import java.time.Duration;
import java.util.List;


public class MainTest {
	private WebDriver driver;
	private Wait<WebDriver> wait;
	private static final Logger logger = LogManager.getLogger(MainTest.class);
//	private final ServerConfig serverConfig = ConfigFactory.create(ServerConfig.class);
	private JavascriptExecutor js;

	@BeforeClass
	public static void startUp() {
		WebDriverManager.chromedriver().setup();
	}

	@After
	public void end() {
		if (driver != null)
			driver.quit();
	}

	private void initDriver(String chromeMode) {
		ChromeOptions chromeOptions = new ChromeOptions();
		chromeOptions.addArguments(chromeMode);
		driver = new ChromeDriver(chromeOptions);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
		logger.info("Драйвер поднят в режиме = {}", chromeMode);

		wait = new WebDriverWait(driver, Duration.ofSeconds(5), Duration.ofSeconds(1));
		js = (JavascriptExecutor) driver;
	}

	//Перейти на https://otus.ru + авторизация
//	private void authNew() {
//		//Перейти на https://otus.ru
//		driver.get(serverConfig.otusUrl());
//
//		//Авторизоваться на сайте
//		driver.findElement(By.xpath("//span[@class='header2__auth-reg']")).click();
//
//		driver.findElement(By.xpath("//div[@class='new-log-reg__body']//input[@name='email']")).sendKeys(serverConfig.email());
//		driver.findElement(By.xpath("//div[@class='new-log-reg__body']//input[@name='password']")).sendKeys(serverConfig.password());
//		driver.findElement(By.xpath("//div[@class='new-log-reg__body']//button")).click();
//
//		String newUserName = driver.findElement(By.xpath("//p[contains(@class,'header2-menu__item-text__username')]")).getText();
//
//		Assert.assertEquals("Пользователь не верен", "toyey", newUserName);
//		logger.info("Пользователь авторизован");
//	}

//	private void openPers() {
//		WebElement authMenu = driver.findElement(By.xpath("//p[contains(@class,'header2-menu__item-text__username')]"));
//		Actions actions = new Actions(driver);
//		actions.moveToElement(authMenu).build().perform();
//		WebElement profile = driver.findElement(By.xpath("//b[contains(@class,'header2-menu__dropdown-text_name')]"));
//		actions.moveToElement(profile).build().perform();
//		driver.findElement(By.xpath("//b[contains(@class,'header2-menu__dropdown-text_name')]")).click();
//		logger.info("Открыта страница Персональные данные");
//	}




	@Test
	public void testPersPage() {
		// Открыть Chrome в режиме полного экрана
		initDriver("start-maximized");


		MainPage mainPage = new MainPage(driver);
		PersPage perspage = new PersPage(driver);

		//Перейти на https://otus.ru
		mainPage.open();
		mainPage.auth();

		logger.info("Начали заполнять персональные данные");

		//Открыть страницу Персональные данные
		perspage.open();


		//Заполнение Персональных данных
	perspage.fillPersonalData();

		//Заполнение данных о местоположении
		//perspage.fillCountryData();
//
		//Добавление двух контактов
		perspage.addContacts();
//
		//Нажать сохранить
		perspage.saveData();
		//Закрыть страницу
//		driver.close();

//		checkData();
	}
//

//
//	private void checkData() {
//		// Открыть https://otus.ru в "чистом браузере"
//		initDriver("start-maximized");
//	//	authNew();
//		openPers();
//		//Проверить, что в разделе "О себе" отображаются указанные ранее данные
//		String getCountry = driver.findElement(By.xpath("//input[@name='country']/following-sibling::div")).getText();
//		String getCity = driver.findElement(By.xpath("//input[@name='city']/following-sibling::div")).getText();
//		String getEngLevel = driver.findElement(By.xpath("//input[@name='english_level']/following-sibling::div")).getText();
//
//		WebElement tgElement = driver.findElement(By.xpath("//input[@type='text' and @name='contact-0-value']"));
//		moveToElement(tgElement);
//		String getTg = tgElement.getDomAttribute("value");
//
//		WebElement vkElement = driver.findElement(By.xpath("//input[@type='text' and @name='contact-1-value']"));
//		moveToElement(vkElement);
//		String getVk = vkElement.getDomAttribute("value");
//
//		Assert.assertEquals("Город не корректен", "Россия", getCountry);
//		Assert.assertEquals("Город не корректен", "Москва", getCity);
//		Assert.assertEquals("Уровень языка не корректен", "Начальный уровень (Beginner)", getEngLevel);
//		Assert.assertEquals("Контакт1 не корректен", "https://t.me/test", getTg);
//		Assert.assertEquals("Контакт2 не корректен", "https://vk.com/test", getVk);
//		logger.info("Данные отображаются корректно");
//	}
//

//

//
//

}








