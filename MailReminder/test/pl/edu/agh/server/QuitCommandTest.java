package pl.edu.agh.server;

import static org.junit.Assert.*;

import org.junit.Test;

import pl.edu.agh.server.QuitCommand;
import pl.edu.agh.server.Responses;

public class QuitCommandTest {

	@Test
	public void testQuit() {
		assertEquals(Responses.BYE_221, QuitCommand.newInstance().execute());
	}

}
