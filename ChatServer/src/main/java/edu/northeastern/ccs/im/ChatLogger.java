package edu.northeastern.ccs.im;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class ChatLogger {

  private static final String LOGNAME = ChatLogger.class.getName();
  private static final Logger LOGGER = Logger.getLogger(LOGNAME);
  private static final String DIR = System.getProperty("user.dir");
  private static final String PATH = String.format("%s/%s.log", DIR, LOGNAME);

  static {
    createFileHandler();
    LOGGER.setUseParentHandlers(false);
  }

  /**
   * Private constructor. This class cannot be instantiated.
   */
  private ChatLogger() {
    throw new IllegalStateException("ChatLogger not instantiable");
  }

  /**
   * Makes a handler for the logger to use.
   */
  private static final void createFileHandler() {
    try {
      Handler fileHandler = new FileHandler(PATH);
      Formatter simpleFormatter = new SimpleFormatter();
      LOGGER.addHandler(fileHandler);
      fileHandler.setLevel(Level.ALL);
      LOGGER.setLevel(Level.ALL);
      fileHandler.setFormatter(simpleFormatter);
    } catch (SecurityException | IOException e) {
      LOGGER.log(Level.SEVERE, e.toString(), e);
      throw new IllegalStateException(e.toString());
    }
  }

  private static final boolean write(Level lvl, Object obj) {
    boolean done = false;
    try {
      String msg = String.valueOf(obj);
      LOGGER.log(lvl, msg);
      done = true;
    } catch (SecurityException ex) {
      error(ex.toString());
    }
    return done;
  }

  /**
   * Logs the error messages.
   * @param msg error message to be logged
   */
  public static final void error(Object msg) {
    write(Level.SEVERE, msg);
  }

  /**
   * Logs the warnings.
   * @param msg warning to be logged
   */
  public static final void warning(Object msg) {
    write(Level.WARNING, msg);
  }

  /**
   * Logs the general messages.
   * @param msg message to be logged
   */
  public static final void info(Object msg) {
    write(Level.INFO, msg);
  }
}
