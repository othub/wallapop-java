package com.uniovi.tests;

import static org.junit.Assert.assertTrue;

import java.util.List;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

import com.uniovi.tests.pageobjects.PO_LoginView;
import com.uniovi.tests.pageobjects.PO_NavView;
import com.uniovi.tests.pageobjects.PO_PrivateView;
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DeleteOffersTests {

    // En Windows (Debe ser la versión 65.0.1 y desactivar las actualizacioens
    // automáticas)):
    static String PathFirefox65 = "C:\\Program Files\\Mozilla Firefox\\firefox.exe";
    // CHANGE THIS
    static String Geckdriver024 = "D:\\Documents\\S6\\SDI\\Practicas\\PL-SDI-Sesion5-material\\PL-SDI-Sesión5-material\\geckodriver024win64.exe";

    static WebDriver driver = getDriver(PathFirefox65, Geckdriver024);
    static String URL = "http://localhost:8090";

    public static WebDriver getDriver(String PathFirefox, String Geckdriver) {
	System.setProperty("webdriver.firefox.bin", PathFirefox);
	System.setProperty("webdriver.gecko.driver", Geckdriver);
	WebDriver driver = new FirefoxDriver();
	return driver;
    }

    @Before
    public void setUp() {
	driver.navigate().to(URL);
    }

    // Después de cada prueba se borran las cookies del navegador
    @After
    public void tearDown() {
	driver.manage().deleteAllCookies();
    }

    @Test
    public void Prueba19() {
	PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "algo7@gmail.com", "123456");
	PO_View.checkElement(driver, "text", "Las ofertas actuales en My Wallapop son las siguientes :");

	List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'offers-menu')]/a");
	elementos.get(0).click();
	elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'offer/list')]");
	elementos.get(0).click();

	// hay 2 ofertas de este usuario
	elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
	assertTrue(elementos.size() == 3);

	// tiene tres ofertas

	clickOneCheckbox();

	driver.findElement(By.className("btn-danger")).click(); // borrar la oferta

	elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
	assertTrue(elementos.size() == 2);

	// desconectamos
	PO_PrivateView.clickOption(driver, "logout", "text", "Correo :");

    }

    @Test
    public void Prueba20() {
	PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "algo7@gmail.com", "123456");
	PO_View.checkElement(driver, "text", "Las ofertas actuales en My Wallapop son las siguientes :");

	List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'offers-menu')]/a");
	elementos.get(0).click();
	elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'offer/list')]");
	elementos.get(0).click();

	// hay 1 oferta de este usuario
	elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
	assertTrue(elementos.size() == 2);

	clickOneCheckbox();

	driver.findElement(By.className("btn-danger")).click(); // borrar la oferta

	elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
	assertTrue(elementos.size() == 1);

	// desconectamos
	PO_PrivateView.clickOption(driver, "logout", "text", "Correo :");

    }

    private void clickOneCheckbox() {
	int count = 1;
	for (WebElement e : driver.findElements(By.xpath("//input[@type='checkbox']"))) {
	    if (!e.isSelected() && count > 0 && !e.getAttribute("id").equals("checkAll")) {
		e.click();
		count -= 1;
	    }
	}
    }

    // Antes de la primera prueba
    @BeforeClass
    static public void begin() {
    }

    // Al finalizar la última prueba
    @AfterClass
    static public void end() {
	// Cerramos el navegador al finalizar las pruebas
	driver.quit();
    }
}
