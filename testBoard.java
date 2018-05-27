package es.codeurjc.ais.tictactoe;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;



public class testBoard {

	@Test
	public void testWinner1() {
		//Creamos el tablero 
		Board board = new Board();
		
		//Creamos los jugadores
		Player jugador1 = new Player(1,"x", "Diego");
		Player jugador2 = new Player(2,"o", "Belen");
		
		//Creamos un array de posiciones donde guardaremos la posicion del jugador ganador
		int[] posicion = new int[3];
		
		//Los jugadores hacen sus respectivas jugadas
		board.getCell(0).value = jugador1.getLabel();
		posicion[0] = 0;
		board.getCell(4).value = jugador2.getLabel();
		board.getCell(1).value = jugador1.getLabel();
		posicion[1] = 1;
		board.getCell(5).value = jugador2.getLabel();
		board.getCell(2).value = jugador1.getLabel();
		posicion[2] = 2;
		
		//Creamos una posición esperada que guarda las posiciones que harían ganador al jugador
		int[] posicionEsperada = {0,1,2};
		
		//Posiciones de cada jugador
		int[] posicionJugador1 = board.getCellsIfWinner(jugador1.getLabel());
		int[] posicionJugador2 = board.getCellsIfWinner(jugador2.getLabel());
		
		//Comprobamos que se ha ganado la partida
		assertThat(posicionEsperada).isEqualTo(posicionJugador1); 
		assertNull(posicionJugador2);
		
		//Comprobamos que no hay empate
		assertEquals(false, board.checkDraw()); 
		
		
	}
	
	@Test
	public void testWinner2() {
		
		//Creamos el tablero 
		Board board = new Board();
		
		//Creamos los jugadores
		Player jugador1 = new Player(1,"x", "Diego");
		Player jugador2 = new Player(2,"o", "Belen");
		
		//Creamos un array de posiciones donde guardaremos la posicion del jugador ganador
		int[] posicion = new int[3];
		
		//Los jugadores hacen sus respectivas jugadas
		board.getCell(0).value = jugador1.getLabel();
		board.getCell(3).value = jugador2.getLabel();
		posicion[0] = 3;
		board.getCell(6).value = jugador1.getLabel();
		board.getCell(4).value = jugador2.getLabel();
		posicion[1] = 4;
		board.getCell(8).value = jugador1.getLabel();
		board.getCell(5).value = jugador2.getLabel();
		posicion[2] = 5;
		
		//Creamos una posición esperada que guarda las posiciones que harían ganador al jugador
		int[] posicionEsperada = {3,4,5};
		
		//Posiciones de cada jugador
		int[] posicionJugador1 = board.getCellsIfWinner(jugador1.getLabel());
		int[] posicionJugador2 = board.getCellsIfWinner(jugador2.getLabel());
		
		//Comprobamos que se ha ganado la partida
		assertThat(posicionEsperada).isEqualTo(posicionJugador2); 
		assertNull(posicionJugador1);
		
		//Comprobamos que no hay empate
		assertEquals(false, board.checkDraw()); 

	}
	
	@Test
	public void testDraw() {
		//Creamos el tablero y los dos jugadores
		Board board = new Board();
		
		Player jugador1 = new Player(1,"x", "Diego");
		Player jugador2 = new Player(2,"o", "Belen");
		
		//Los jugadores hacen sus respectivas jugadas
		board.getCell(0).value = jugador1.getLabel();
		board.getCell(4).value = jugador2.getLabel();
		board.getCell(2).value = jugador1.getLabel();
		board.getCell(1).value = jugador2.getLabel();
		board.getCell(7).value = jugador1.getLabel();
		board.getCell(8).value = jugador2.getLabel();
		board.getCell(6).value = jugador1.getLabel();
		board.getCell(3).value = jugador2.getLabel();
		board.getCell(5).value = jugador1.getLabel();
		
		//Posiciones de cada jugador
		int[] posicionJugador1 = board.getCellsIfWinner(jugador1.getLabel());
		int[] posicionJugador2 = board.getCellsIfWinner(jugador2.getLabel());
		
		//Comprobamos que no se ha ganado la partida
		assertNull(posicionJugador1); 
		assertNull(posicionJugador2);  
		
		//Comprobamos que hay empate
		assertTrue(board.checkDraw()); 
	}
	
}
