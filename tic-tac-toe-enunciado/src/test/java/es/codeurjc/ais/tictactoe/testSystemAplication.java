package es.codeurjc.ais.tictactoe;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.ChromeDriverManager;

class AplicationSystemTest {
	
	WebDriver webDrive0, webDrive1;
	
	WebElement[] tableroJugador1,tableroJugador2;
	WebDriverWait wait0, wait1;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		ChromeDriverManager.getInstance().setup();
	}

	

	@BeforeEach
	void setUp() throws Exception {
		
		//Ejecutamos los drivers del navegador Google Chrome
		webDrive0 = new ChromeDriver();
		webDrive1 = new ChromeDriver();
		
		wait0 = new WebDriverWait(webDrive0, 30);
		wait1 = new WebDriverWait(webDrive1, 30);
		
		webDrive0.get("http://localhost:8080/");
		webDrive1.get("http://localhost:8080/");
		
		//El jugador 1 es Diego
		webDrive0.findElement(By.id("nickname")).sendKeys("Diego");
		webDrive0.findElement(By.id("startBtn")).click();
		
		//El jugador 2 es Belen
		webDrive1.findElement(By.id("nickname")).sendKeys("Belen");
		webDrive1.findElement(By.id("startBtn")).click();
		
		//Los tableros tienen 9 casillas
		tableroJugador1 = new WebElement[9];
		tableroJugador2 = new WebElement[9];
		
		//Rellenamos el tablero
		for (int i=0; i<9; i++) {
			tableroJugador1[i] = webDrive0.findElement(By.id("cell-"+i));
		}
		

		for (int i=0; i<9; i++) {
			tableroJugador2[i] = webDrive1.findElement(By.id("cell-"+i));
		}
	}

	@AfterEach
	void tearDown() throws Exception {
		webDrive0.quit();
		webDrive1.quit();
	}

	//Metodo para marcar las casillas
	void marcarCasilla(int jugador, int cell) {
		if (jugador == 0) {
			wait0.until(ExpectedConditions.elementToBeClickable(By.id("cell-"+cell)));
			tableroJugador1[cell].click();
		} else {
			wait1.until(ExpectedConditions.elementToBeClickable(By.id("cell-"+cell)));
			tableroJugador2[cell].click();
		}
	}
	
	@Test
	void testWinner1() {
		
		//Cada jugador realiza su jugada
		marcarCasilla(0,2);
		marcarCasilla(1,7);
		marcarCasilla(0,1);
		marcarCasilla(1,6);
		marcarCasilla(0,0);
		
		//Comprobamos que gana el jugador 1
		assertEquals(webDrive0.switchTo().alert().getText(),"Diego wins! Belen looses.");
		assertEquals(webDrive1.switchTo().alert().getText(),"Diego wins! Belen looses.");
	}
	
	@Test
	void testWinner2() {
		
		//Cada jugador realiza su jugada
		marcarCasilla(0,2);
		marcarCasilla(1,3);
		marcarCasilla(0,6);
		marcarCasilla(1,4);
		marcarCasilla(0,1);
		marcarCasilla(1,5);
		
		//Comprobamos que gana el jugador 2
		assertEquals(webDrive0.switchTo().alert().getText(),"Belen wins! Diego looses.");
		assertEquals(webDrive1.switchTo().alert().getText(),"Belen wins! Diego looses.");
	}
	
	@Test
	void testDraw() {
		
		//Cada jugador realiza su jugada
		marcarCasilla(0,4);
		marcarCasilla(1,2);
		marcarCasilla(0,8);
		marcarCasilla(1,0);
		marcarCasilla(0,1);
		marcarCasilla(1,7);
		marcarCasilla(0,3);
		marcarCasilla(1,5);
		marcarCasilla(0,6);
		
		//Comprobamos que hay empate
		assertEquals(webDrive0.switchTo().alert().getText(),"Draw!");
		assertEquals(webDrive1.switchTo().alert().getText(),"Draw!");
	}

}