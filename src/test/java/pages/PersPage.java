package pages;

import config.ServerConfig;
import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Wait;

import java.util.List;

public class PersPage {

    private WebDriver driver;
    private static final Logger logger = LogManager.getLogger(PersPage.class);
    private final ServerConfig serverConfig = ConfigFactory.create(ServerConfig.class);
    private Wait<WebDriver> wait;

    private By authMenu = By.xpath("//p[contains(@class,'header2-menu__item-text__username')]");
    private By profile = By.xpath("//b[contains(@class,'header2-menu__dropdown-text_name')]");

    private By nameField = By.xpath("//input[@type='text'][@name='fname']");
    private By nameEngField = By.xpath("//input[@type='text'][@name='fname_latin']");
    private By surnameField = By.xpath("//input[@type='text'][@name='lname']");
    private By surnameEngField = By.xpath("//input[@type='text'][@name='lname_latin']");
    private By nickNameField = By.xpath("//input[@type='text'][@name='blog_name']");
    private By dateOfBirthField = By.xpath("//input[@name='date_of_birth']");
    private By dateOfBirthArea = By.xpath("//label[contains(text(),'Дата рождения')]");

    private By countryField = By.xpath("//input[@name='country']/following-sibling::div");
    private By countryList2 = By.xpath("//div[contains(@class,'lk-cv-block__select-scroll_country')]/button[2]");
    private By cityField = By.xpath("//input[@name='city']/following-sibling::div");
    private By cityList2 = By.xpath("//div[contains(@class,'lk-cv-block__select-scroll_city')]/button[2]");

	private By deleteButtonContainer = By.xpath(".//div[contains(@class, 'container__col container__col_12 container__col_md-0')]");
	private By deleteButton = By.xpath(".//button[contains(@class, 'js-formset-delete')]");
	private By contactContainer = By.xpath("//div[contains(@class,'js-formset-row')]");
	private By contactAddButton = By.xpath("//button[contains(text(),'Добавить')]");

	private By saveLaterButton = By.xpath("//button[@title='Сохранить и заполнить позже']");
	private By messageSaveAlert = By.xpath("//span[contains(@class,'messages')]");



    public PersPage(WebDriver driver) {
        this.driver = driver;
    }

    public void open(){
        Actions actions = new Actions(driver);
        WebElement authMenuElement = driver.findElement(authMenu);
        WebElement profileElement = driver.findElement(profile);
        actions.moveToElement(authMenuElement).build().perform();
        actions.moveToElement(profileElement).build().perform();
        driver.findElement(profile).click();
        logger.info("Открыта страница Персональные данные");
    }

    public void fillPersonalData() {
		WebElement addName = driver.findElement(nameField);//Поле Имя
		addName.clear();
		addName.click();
		addName.sendKeys(serverConfig.name());

		WebElement addEnName = driver.findElement(nameEngField);//Поле Имя (латиницей)
		addEnName.clear();
		addEnName.click();
		addEnName.sendKeys(serverConfig.name());

		WebElement addSurname = driver.findElement(surnameField);//Поле Фамилия
		addSurname.clear();
		addSurname.click();
		addSurname.sendKeys(serverConfig.surname());

		WebElement addEnSurname = driver.findElement(surnameEngField);//Поле Фамилия (латиницей)
		addEnSurname.clear();
		addEnSurname.click();
		addEnSurname.sendKeys(serverConfig.surname());

		WebElement addNick = driver.findElement(nickNameField);//Имя в блоге
		addNick.clear();
		addNick.click();
		addNick.sendKeys(serverConfig.name());

		WebElement addBirth = driver.findElement(dateOfBirthField);//Дата рождения
		addBirth.clear();
		addBirth.click();
		addBirth.sendKeys(serverConfig.birth());

		driver.findElement(dateOfBirthArea).click();
	}

	public void fillCountryData() {
		WebElement country = driver.findElement(countryField);//Страна
		country.click();
		WebElement countryList = driver.findElement(countryList2);
		countryList.click();

		WebElement city = driver.findElement(cityField);//Город
		waitUntilTextPresents(city, "Город");
		city.click();
		WebElement cityList = driver.findElement(cityList2);
		cityList.click();
//
//		WebElement engLevel = driver.findElement(By.xpath("//input[@name='english_level']/following-sibling::div")); //Уровень английского
//
//		engLevel.click();
//		WebElement engLevelList = driver.findElement(By.xpath("//div[contains(@class,'lk-cv-block__select-scroll') and not(contains(@class,'country')) and not(contains(@class,'city'))]/button[2]"));
//		engLevelList.click();
	}

	private void waitUntilTextPresents(WebElement element, String text) {
            wait.until(ExpectedConditions.textToBePresentInElement(element, text));
        }

        private void waitUntilAttrPresents(WebElement save, String attr, String value) {
            wait.until(ExpectedConditions.attributeToBe(save, attr, value));
        }

		private void deleteContact(WebElement contact) {
		WebElement deleteButtonDiv = contact.findElement(deleteButtonContainer);
		deleteButtonDiv.findElement(deleteButton).click();
	}

	private void setContact(int cntNumber, String title, String text) {
		String xpath = String.format("//input[@name='contact-%1d-service']/following::div", cntNumber);

		WebElement contact = driver.findElement(By.xpath(xpath));
		contact.click();

		xpath = String.format("//div[(contains(@class,'lk-cv-block__select-options') or contains(@class,'lk-cv-block__select-options_left')) and not(contains(@class,'hide'))]//button[@title='%1s']", title);

		WebElement contactType = driver.findElement(By.xpath(xpath));
		contactType.click();

		xpath = String.format("//input[@type='text'][@name='contact-%1d-value']", cntNumber);

		WebElement setContact = driver.findElement(By.xpath(xpath));
		setContact.clear();
		setContact.sendKeys(text);

		waitUntilAttrPresents(setContact, "value", text);
	}

	public void addContacts() {
		//ищем все контакты,а точнее их верхние div формы
		List<WebElement> existingContacts = driver.findElements(contactContainer);
		//надо запоминать сколько было контактов,т.к. если создавать новые, то он продолжает нумерацию на форме
		int contactCount = existingContacts.size();

		//если контакты нашлись,то удаляем их
		if (CollectionUtils.isNotEmpty(existingContacts)) {
			for (WebElement existingContact : existingContacts) {
				deleteContact(existingContact);
			}
		}

		logger.info("Добавляем 1 контакт");
		WebElement addButton = driver.findElement(contactAddButton);
		addButton.click();

		setContact(contactCount++, "VK", serverConfig.vk());

		logger.info("Добавляем 2 контакт");

		addButton = driver.findElement(contactAddButton);
		addButton.click();
		setContact(contactCount++, "Тelegram", serverConfig.tg());
	}

		public void saveData() {
		Actions actions = new Actions(driver);
		WebElement save = driver.findElement(saveLaterButton);
		actions.moveToElement(save);
		save.click();

		save = driver.findElement(messageSaveAlert);
		waitUntilTextPresents(save, "Данные успешно сохранены");

		logger.info("Данные сохранены");
	}
}
