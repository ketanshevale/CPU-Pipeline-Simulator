package utillities;

// http://stackoverflow.com/questions/15758685/how-to-write-logs-in-text-file-when-using-java-util-logging-logger
import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class LoggerFile {

	public static Logger logger;

	public static void log() {

		logger = Logger.getLogger("ApexLog");
		FileHandler filehandler;

		try {

			filehandler = new FileHandler("ApexLogFile.log");
			logger.addHandler(filehandler);
			SimpleFormatter formatter = new SimpleFormatter();
			filehandler.setFormatter(formatter);
			logger.setUseParentHandlers(false);

		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
