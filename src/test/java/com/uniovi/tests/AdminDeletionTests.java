/*
 * See the NOTICE file distributed with this work for additional
 * information regarding copyright ownership.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 */
package com.uniovi.tests;

import static org.junit.Assert.assertFalse;
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
import com.uniovi.tests.pageobjects.PO_View;
import com.uniovi.utils.SeleniumUtils;

//Ordenamos las pruebas por el nombre del método
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class AdminDeletionTests {

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
    public void Prueba13() {
	PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "admin@email.com", "123456");
	PO_View.checkElement(driver, "text", "Bienvenido Administrador");

	List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");

	elementos.get(0).click();
	elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'user/list')]");
	elementos.get(0).click();

	// hay 7 usuarios en el sistema
	elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
	assertTrue(elementos.size() == 7);

	elementos = PO_View.checkElement(driver, "free",
		"//td[contains(text(), 'algo@gmail.com')]/following-sibling::*/a[contains(@href, 'user/delete')]");
	elementos.get(0).click();

	elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());

	// No existe el email, se ha borrado
	assertFalse(driver.getPageSource().contains("algo@gmail.com"));

    }

    @Test
    public void Prueba14() {
	PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "admin@email.com", "123456");
	PO_View.checkElement(driver, "text", "Bienvenido Administrador");

	List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");

	elementos.get(0).click();
	elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'user/list')]");
	elementos.get(0).click();

	elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
	assertTrue(elementos.size() == 6);

	elementos = PO_View.checkElement(driver, "free",
		"//td[contains(text(), 'algo5@gmail.com')]/following-sibling::*/a[contains(@href, 'user/delete')]");
	elementos.get(0).click();

	elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());

	// No existe el email, se ha borrado
	assertFalse(driver.getPageSource().contains("algo5@gmail.com"));
    }

    @Test
    public void Prueba15() {
	PO_NavView.clickOption(driver, "login", "class", "btn btn-primary");
	PO_LoginView.fillForm(driver, "admin@email.com", "123456");
	PO_View.checkElement(driver, "text", "Bienvenido Administrador");

	List<WebElement> elementos = PO_View.checkElement(driver, "free", "//li[contains(@id, 'users-menu')]/a");

	elementos.get(0).click();
	elementos = PO_View.checkElement(driver, "free", "//a[contains(@href,'user/list')]");
	elementos.get(0).click();

	elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
	assertTrue(elementos.size() == 5);

	clickThreeCheckboxes();

	driver.findElement(By.className("btn-danger")).click();

	// No existe el email, se ha borrado
	assertFalse(driver.getPageSource().contains("algo1@gmail.com"));
	assertFalse(driver.getPageSource().contains("algo2@gmail.com"));
	assertFalse(driver.getPageSource().contains("algo3@gmail.com"));

	elementos = SeleniumUtils.EsperaCargaPagina(driver, "free", "//tbody/tr", PO_View.getTimeout());
	assertTrue(elementos.size() == 2);

	assertTrue(driver.getPageSource().contains("alg4@gmail.com"));
	assertTrue(driver.getPageSource().contains("admin@email.com"));

    }

    private void clickThreeCheckboxes() {
	int count = 3;

	for (WebElement e : driver.findElements(By.xpath("//input[@type='checkbox']"))) {
	    if (!e.isSelected() && count > 0 && !e.getAttribute("id").equals("checkAll")
		    && !e.getText().equals("admin@email.com")) {
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
