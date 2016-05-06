package pl.edu.agh.logLibrary;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DefaultLoggerTest {
	private String loggerTestStringWarning = ""; 
	private String loggerTestStringInfo = ""; 
	private String loggerTestStringError = ""; 
	
	private String constTestLoggerString = "";

	private LoggerStrategy logger = new LoggerStrategy() {
		
		@Override
		public void log(LogLevel level, String message) {			
			if (level == LogLevel.INFO)
				loggerTestStringInfo = message; 
			else if (level == LogLevel.ERROR)
				loggerTestStringError = message;
			else if (level == LogLevel.WARNING)
				loggerTestStringWarning = message;
		}
	};
	
	private LoggerStrategy constLogger = new LoggerStrategy() {
		
		@Override
		public void log(LogLevel level, String message) {
			constTestLoggerString = "Message from logger";
		}
	};
	

	@Test
	public void infoLogggingTest() {
		DefaultLogger testLogger = new DefaultLogger(logger);
		String message = "Info";
		testLogger.info(message);
		assertTrue(loggerTestStringInfo.contains(message));
	}
	
	@Test
	public void warningLogggingTest() {
		DefaultLogger testLogger = new DefaultLogger(logger);
		String message = "Warning";
		testLogger.warning(message);
		assertTrue(loggerTestStringWarning.contains(message));
	}
	
	@Test
	public void errorLogggingTest() {
		DefaultLogger testLogger = new DefaultLogger(logger);
		String message = "Error";
		testLogger.error(message);
		assertTrue(loggerTestStringError.contains(message));
	}

	@Test 
	public void setAnotherLoggerTest() { 
		DefaultLogger testLogger = new DefaultLogger(logger);
		testLogger.setLogger(constLogger);
		testLogger.error("error");
		assertTrue(constTestLoggerString.contains("Message from logger"));
	}
	
	@Test 
	public void setAndGetInfoTest() { 
		DefaultLogger testLogger = new DefaultLogger(logger);
		testLogger.setSaveInfo(true);
		assertTrue(testLogger.isSaveInfo());
		testLogger.setSaveInfo(false);
		assertFalse(testLogger.isSaveInfo());	
	}
	
	@Test 
	public void setAndGetWarningTest() { 
		DefaultLogger testLogger = new DefaultLogger(logger);
		testLogger.setSaveWarning(true);
		assertTrue(testLogger.isSaveWarning());
		testLogger.setSaveWarning(false);
		assertFalse(testLogger.isSaveWarning());	
	}
	
	@Test 
	public void setAndGetErrorTest() { 
		DefaultLogger testLogger = new DefaultLogger(logger);
		testLogger.setSaveError(true);
		assertTrue(testLogger.isSaveError());
		testLogger.setSaveError(false);
		assertFalse(testLogger.isSaveError());	
	}
}

