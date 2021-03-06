package com.home.exercise.board;

import java.util.Arrays;
import java.util.concurrent.Callable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.home.exercise.board.elements.Ball;
import com.home.exercise.board.elements.Stick;

/**
 * Prints the current state of the board on the console.
 * @author rodrigo.mendoza
 */
public final class BoardConsolePrinter implements Callable<Void> {
  /** Logging reference */
  private final static Logger LOG = LogManager.getLogger(BoardConsolePrinter.class);
  
  private final Ball ball;
  private final Stick leftStick;
  private final int rows;
  private final int cols;
  private final char[][] charBoard;
  private final int velocity;
  private boolean stop;
  
  /** Recives the board that is going to be printing. */
  public BoardConsolePrinter(Board board, int velocity) {
    this.ball = board.getBall();
    this.leftStick = board.getLeftStick();
    this.velocity = velocity;
    this.rows = board.getRows();
    this.cols = board.getCols();
    charBoard = new char[rows][cols];
    stop = true;
  }
  
  public void stop() {
    stop = true;
  }
  
  @Override
  public Void call() throws Exception {
    stop = false;
    while(!stop) {
      try {
        Thread.sleep(velocity);
      } catch (InterruptedException e) {
        e.printStackTrace();
      }
      
      // Clear the board
      for(int i = 0; i < charBoard.length; i++) {
        Arrays.fill(charBoard[i], ' ');
      }
      
      // Get position of the ball
      int ballRow = ball.getRow();
      int ballCol = ball.getCol();
      charBoard[ballRow][ballCol] = 'O';
      
      // If the ball has already touched the limit the game is over
      if(ballCol == 0) {
        charBoard[ballRow][2] = 'E';
        charBoard[ballRow][3] = 'N';
        charBoard[ballRow][4] = 'D';
        charBoard[ballRow][5] = '!';
        stop = true;
      }
      
      // Get position of the stick
      int leftStickRow = leftStick.getRow();
      int leftStickCol = leftStick.getCol();
      int leftStickSize = leftStick.getSize();
      for(int i = 0; i < leftStickSize; i++) {
        charBoard[leftStickRow + i][leftStickCol] = 'Ξ';
      }
      
      // Get the board in a String representation
      StringBuilder builder = new StringBuilder();
      builder.append("\n");
      for(int j = 0; j < charBoard[0].length; j++) {
        builder.append("---");
      }
      builder.append("\n");
      for(int i = 0; i < charBoard.length; i++) {
        for(int j = 0; j < charBoard[i].length; j++) {
          builder.append(" " + charBoard[i][j] + " ");
        }
        builder.append("\n");
      }
      for(int j = 0; j < charBoard[0].length; j++) {
        builder.append("---");
      }
      builder.append("\n");
      
      // Print the board into the console
      LOG.info(builder.toString());
    }
    return null;
  }
  
}
