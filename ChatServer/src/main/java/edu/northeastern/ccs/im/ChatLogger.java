package edu.northeastern.ccs.im;

import java.io.IOException;
import java.util.logging.ConsoleHandler;
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
    setMode(HandlerType.BOTH);
  }

  /**
   * Private constructor. This class cannot be instantiated.
   */
  private ChatLogger() {
    throw new IllegalStateException("ChatLogger not instantiable");
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

  /**
   * Toggles between the handler types.
   * 
   * @param type the type of handler to be used by the logger
   */
  public static void setMode(HandlerType type) {

    switch (type.toString().toLowerCase()) {
      case ("file"):
        switchToFile();
        break;
      case ("console"):
        switchToConsole();
        break;
      case ("both"):
        switchToBoth();
        break;
      default:
        throw new IllegalArgumentException("Invalid handler type.");
    }
    LOGGER.setLevel(Level.ALL);
    LOGGER.setUseParentHandlers(false);
  }

  /**
   * Creates file Handler for the logger to use.
   */
  private static void switchToFile() {
    Handler fileHandler;
    try {
      fileHandler = new FileHandler(PATH);
      LOGGER.addHandler(fileHandler);
      fileHandler.setLevel(Level.ALL);
      Formatter simpleFormatter = new SimpleFormatter();
      fileHandler.setFormatter(simpleFormatter);
    } catch (IOException e) {
      throw new IllegalStateException(e.getMessage());
    }
  }

  /**
   * Creates console Handler for the logger to use.
   */
  private static void switchToConsole() {
    Handler consoleHandler;
    consoleHandler = new ConsoleHandler();
    LOGGER.addHandler(consoleHandler);
    consoleHandler.setLevel(Level.ALL);
    Formatter simpleFormatter = new SimpleFormatter();
    consoleHandler.setFormatter(simpleFormatter);
  }

  /**
   * Creates file and console handlers for the logger to use.
   */
  private static void switchToBoth() {
    switchToFile();
    switchToConsole();
  }

  /**
   * Private Enum class for Handler Types.
   */
  private enum HandlerType {
    /** The file handler. */
    FILE("File"),
    /** The console handler. */
    CONSOLE("Console"),
    /** Both handlers. */
    BOTH("Both");
    /** The name of the handler type. */
    private String type;

    /**
     * Private constructor for the enum.
     * 
     * @param abbrev the name of the handler type
     */
    private HandlerType(String abbrev) {
      type = abbrev;
    }

    /**
     * Converting the handler type to a String.
     */
    public String toString() {
      return type;
    }
  }
}
