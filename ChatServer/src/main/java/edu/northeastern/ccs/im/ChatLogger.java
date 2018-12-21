package edu.northeastern.ccs.im;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

/**
 * Logger class that handles logging of all levels of messages.
 * 
 * @author Maria Jump and Riya Nadkarni
 * @version 12-20-2018
 */
public class ChatLogger {
  /** Name of the logger file. */
  private static final String LOGNAME = ChatLogger.class.getName();
  /** The logger itself. */
  private static final Logger LOGGER = Logger.getLogger(LOGNAME);
  /** The directory holding the log file. */
  private static final String DIR = System.getProperty("user.dir");
  /** The path for the directory. */
  private static final String PATH = String.format("%s/%s.log", DIR, LOGNAME);

  /**
   * Static initializations for this class.
   */
  static {
    createFileHandler();
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
    Handler fileHandler;
    try {
      fileHandler = new FileHandler(PATH);
      LOGGER.addHandler(fileHandler);
      fileHandler.setLevel(Level.ALL);
      LOGGER.setLevel(Level.ALL);
      Formatter simpleFormatter = new SimpleFormatter();
      fileHandler.setFormatter(simpleFormatter);
      LOGGER.setUseParentHandlers(false);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  /**
   * Writes to the logger.
   * 
   * @param lvl the level of severity of the message being logged
   * @param msg the message being logged.
   * @return true if the message was logged, false otherwise
   */
  private static final boolean write(Level lvl, String msg) {
    boolean done = true;
    try {
//      String msg = String.valueOf(obj);
      LOGGER.log(lvl, msg);
    } catch (SecurityException ex) {
      done = false;
    }
    return done;
  }

  /**
   * Logs the error messages.
   * 
   * @param msg error message to be logged
   */
  public static final void error(String msg) {
    write(Level.SEVERE, msg);
  }

  /**
   * Logs the warnings.
   * 
   * @param msg warning to be logged
   */
  public static final void warning(String msg) {
    write(Level.WARNING, msg);
  }

  /**
   * Logs the general messages.
   * 
   * @param msg message to be logged
   */
  public static final void info(String msg) {
    write(Level.INFO, msg);
  }
}
