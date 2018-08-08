package com.home.exercise.main;

import java.util.Scanner;
import java.util.concurrent.Executors;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;
import com.google.common.util.concurrent.ListeningExecutorService;
import com.google.common.util.concurrent.MoreExecutors;
import com.home.exercise.board.Board;
import com.home.exercise.board.BoardConsolePrinter;

/**
 * Main class that is going to init all of the components and execute them.
 * @author rodrigo.mendoza
 */
public final class PingPong {
  /** Reference to the application logger. */
  private static final Logger LOG = LogManager.getLogger(PingPong.class);
  
  /** Velocity in miliseconds of the refresh of the console and the animation of the ball. */
  private static final int VELOCITY = 200;
  
  /** Row size of the board. */
  private static final int ROW_NUMBER = 8; // The minimum size is determined by the size of the stick and the initial position of it and the ball
  
  /** Column size of the board. */
  private static final int COL_NUMBER = 14; // Minimum size should be of 6 in order to show the END legend
  
  /** Thread pool to execute Callables. */
  private static final ListeningExecutorService threadPool =
      MoreExecutors.listeningDecorator(Executors.newCachedThreadPool());
  
  public static void main(String[] args) {
    // Creation of the board with default values
    Board board = new Board(PingPong.ROW_NUMBER, PingPong.COL_NUMBER, PingPong.VELOCITY);
    
    // Init and execute the boardPrinter
    BoardConsolePrinter boardPrinter = new BoardConsolePrinter(board, PingPong.VELOCITY);
    ListenableFuture<Void> boardPrinterFuture = threadPool.submit(boardPrinter);
    Futures.addCallback(boardPrinterFuture, new FutureCallback<Void>() {
      @Override
      public void onSuccess(Void result) {
        LOG.info("Game over: Press \"Q\" to stop the game: ");
      }
      @Override
      public void onFailure(Throwable t) {
        t.printStackTrace();
      }
    }, threadPool);
    
    // Init the ping pong game
    board.init();
    
    /* In the main thread we are going to be listening to the input for certain actions
     * like move the stick up or down, or finish the execution **/
    String instruction = null;
    Scanner scanner = new Scanner(System.in);
    boolean continueLoop = true;
    do {
      instruction = scanner.nextLine();
      if(instruction.equalsIgnoreCase("w")) {
        board.moveLeftStickUp();
      } else if(instruction.equalsIgnoreCase("s")) {
        board.moveLeftStickDown();
      } else if(instruction.equalsIgnoreCase("q")) {
        board.end();
        boardPrinter.stop();
        continueLoop = false;
        scanner.close();
        threadPool.shutdown();
      }
    } while(continueLoop);
    
  }
}
