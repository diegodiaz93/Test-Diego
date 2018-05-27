package es.codeurjc.ais.tictactoe;

import static org.mockito.Mockito.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import static org.mockito.hamcrest.MockitoHamcrest.argThat;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.mockito.ArgumentCaptor;

import es.codeurjc.ais.tictactoe.TicTacToeGame.EventType;
import es.codeurjc.ais.tictactoe.TicTacToeGame.WinnerValue;

public class testTicTacToe {
	

	
	@Test
	public void testTicTacToeWinner1() {
		
		//Creamos el juego, las conexiones y los jugadores
		TicTacToeGame tictactoegame = new TicTacToeGame();
		
		Connection conexion1 = mock(Connection.class);
		tictactoegame.addConnection(conexion1);
		
		Connection conexion2 = mock(Connection.class);
		tictactoegame.addConnection(conexion2);
		
		Player jugador1 = new Player(1,"x", "Diego");
		Player jugador2 = new Player(2,"o", "Belen");
		
		//Añadimos el jugador 1 y comprobamos que las conexiones reciben el evento JOIN_GAME
		tictactoegame.addPlayer(jugador1);
		verify(conexion1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(jugador1)));
		verify(conexion2).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(jugador1)));
		reset(conexion1);
		reset(conexion2);
		
		//Añadimos el jugador 2 y comprobamos que las conexiones reciben el evento JOIN_GAME
		tictactoegame.addPlayer(jugador2);
		verify(conexion1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(jugador1,jugador2)));
		verify(conexion2).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(jugador1,jugador2)));
		
		//Comprobamos que las dos conexiones reciben el evento SET_TURN con el jugador 1
		verify(conexion1).sendEvent(eq(EventType.SET_TURN), eq(jugador1));
		verify(conexion2).sendEvent(eq(EventType.SET_TURN), eq(jugador1));
		reset(conexion1);
		reset(conexion2);
	
		//Los jugadores realizan su jugada (mark marca la casilla y cambia el turno)
		tictactoegame.mark(0);	
		tictactoegame.mark(3);
		tictactoegame.mark(1);
		tictactoegame.mark(4);
		//Comprobamos las veces que han recibido SET_TURN las conexiones
		verify(conexion1,times(2)).sendEvent(eq(EventType.SET_TURN), eq(jugador1));
		verify(conexion2,times(2)).sendEvent(eq(EventType.SET_TURN), eq(jugador1));
		verify(conexion1,times(2)).sendEvent(eq(EventType.SET_TURN), eq(jugador2));
		verify(conexion2,times(2)).sendEvent(eq(EventType.SET_TURN), eq(jugador2));
		
		tictactoegame.mark(2);
		
		//Comprobamos que ha ganado el jugador 1
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
		verify(conexion1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		WinnerValue event = (WinnerValue) argument.getValue();
		verify(conexion2).sendEvent(eq(EventType.GAME_OVER), eq(event));
		
		assertThat(event.player.equals(jugador1));		
		assertThat(!event.player.equals(jugador2));		
		assertNotNull(event);
		
				
	}
	
	@Test
	public void testTicTacToeWinner2() {
		
		//Creamos el juego, las conexiones y los jugadores
		TicTacToeGame tictactoegame = new TicTacToeGame();
		
		Connection conexion1 = mock(Connection.class);
		tictactoegame.addConnection(conexion1);
		
		Connection conexion2 = mock(Connection.class);
		tictactoegame.addConnection(conexion2);
		
		Player jugador1 = new Player(1,"x", "Diego");
		Player jugador2 = new Player(2,"o", "Belen");
		
		//Añadimos el jugador 1 y comprobamos que las conexiones reciben el evento JOIN_GAME
		tictactoegame.addPlayer(jugador1);
		verify(conexion1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(jugador1)));
		verify(conexion2).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(jugador1)));
		reset(conexion1);
		reset(conexion2);
		
		//Añadimos el jugador 2 y comprobamos que las conexiones reciben el evento JOIN_GAME
		tictactoegame.addPlayer(jugador2);
		verify(conexion1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(jugador1,jugador2)));
		verify(conexion2).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(jugador1,jugador2)));
		
		//Comprobamos que las dos conexiones reciben el evento SET_TURN con el jugador 1
		verify(conexion1).sendEvent(eq(EventType.SET_TURN), eq(jugador1));
		verify(conexion2).sendEvent(eq(EventType.SET_TURN), eq(jugador1));
		reset(conexion1);
		reset(conexion2);
	
		//Los jugadores realizan su jugada (mark marca la casilla y cambia el turno)
		tictactoegame.mark(0);	
		tictactoegame.mark(3);
		tictactoegame.mark(6);
		tictactoegame.mark(4);
		tictactoegame.mark(1);
		
		//Comprobamos las veces que han recibido SET_TURN las conexiones
		verify(conexion1,times(2)).sendEvent(eq(EventType.SET_TURN), eq(jugador1));
		verify(conexion2,times(2)).sendEvent(eq(EventType.SET_TURN), eq(jugador1));
		verify(conexion1,times(3)).sendEvent(eq(EventType.SET_TURN), eq(jugador2));
		verify(conexion2,times(3)).sendEvent(eq(EventType.SET_TURN), eq(jugador2));
		
		tictactoegame.mark(5);
		
		//Comprobamos que ha ganado el jugador 2
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
		verify(conexion1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		WinnerValue event = (WinnerValue) argument.getValue();
		verify(conexion2).sendEvent(eq(EventType.GAME_OVER), eq(event));
		
		assertThat(event.player.equals(jugador2));
		assertThat(!event.player.equals(jugador1));	
		assertNotNull(event);
				
	}
	@Test
	public void testTicTacToeDraw() {
		
		//Creamos el juego, las conexiones y los jugadores
		TicTacToeGame tictactoegame = new TicTacToeGame();
		
		Connection conexion1 = mock(Connection.class);
		tictactoegame.addConnection(conexion1);
		
		Connection conexion2 = mock(Connection.class);
		tictactoegame.addConnection(conexion2);
		
		Player jugador1 = new Player(1,"x", "Diego");
		Player jugador2 = new Player(2,"o", "Belen");

		//Añadimos el jugador 1 y comprobamos que las conexiones reciben el evento JOIN_GAME
		tictactoegame.addPlayer(jugador1);
		verify(conexion1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(jugador1)));
		verify(conexion2).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(jugador1)));
		reset(conexion1);
		reset(conexion2);
		
		//Añadimos el jugador 2 y comprobamos que las conexiones reciben el evento JOIN_GAME
		tictactoegame.addPlayer(jugador2);
		verify(conexion1).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(jugador1,jugador2)));
		verify(conexion2).sendEvent(eq(EventType.JOIN_GAME), argThat(hasItems(jugador1,jugador2)));
		
		//Comprobamos que las dos conexiones reciben el evento SET_TURN con el jugador 1
		verify(conexion1).sendEvent(eq(EventType.SET_TURN), eq(jugador1));
		verify(conexion2).sendEvent(eq(EventType.SET_TURN), eq(jugador1));
		reset(conexion1);
		reset(conexion2);
	
		//Los jugadores realizan su jugada (mark marca la casilla y cambia el turno)
		tictactoegame.mark(0);	
		tictactoegame.mark(4);
		tictactoegame.mark(2);
		tictactoegame.mark(1);
		tictactoegame.mark(7);
		tictactoegame.mark(8);	
		tictactoegame.mark(6);
		tictactoegame.mark(3);
		
		//Comprobamos las veces que han recibido SET_TURN las conexiones
		verify(conexion1,times(4)).sendEvent(eq(EventType.SET_TURN), eq(jugador1));
		verify(conexion2,times(4)).sendEvent(eq(EventType.SET_TURN), eq(jugador1));
		verify(conexion1,times(4)).sendEvent(eq(EventType.SET_TURN), eq(jugador2));
		verify(conexion2,times(4)).sendEvent(eq(EventType.SET_TURN), eq(jugador2));
		
		tictactoegame.mark(5);
		
		//Comprobamos que hay empate
		ArgumentCaptor<WinnerValue> argument = ArgumentCaptor.forClass(WinnerValue.class);
		verify(conexion1).sendEvent(eq(EventType.GAME_OVER), argument.capture());
		Object event =  argument.getValue();
		verify(conexion2).sendEvent(eq(EventType.GAME_OVER), eq(event));
		
		assertNull(event);
				
	}
}