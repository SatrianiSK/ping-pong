package com.home.exercise.board;

import com.home.exercise.board.elements.Ball;
import com.home.exercise.board.elements.Stick;

/**
 * It defines the current status and elements on the board.
 * @author rodrigo.mendoza
 */
public final class Board {
  private final int rows;
  private final int cols;
  private final Ball ball;
  private final Stick leftStick;
  private boolean stop;
  private final int velocity;
  
  /**
   * Creates a new boards with the given characteristics
   * @param rows number of rows of the board
   * @param cols number of columns of the board
   * @param velocity time in miliseconds at which the screen will refresh
   */
  public Board(int rows, int cols, int velocity) {
    this.rows = rows;
    this.cols = cols;
    this.velocity = velocity;
    leftStick = new Stick(3, 0, 0);   // We are defining that leftStick has 2 spaces of size and it is un the first column
    ball = new Ball(2, 1, leftStick); // We can change later these values for random ones
    stop = true;
  }
  
  /** Init the animation of the ball */
  public void init() {
    initBallMovement();
  }
  
  /** End the animation of the ball */
  public void end() {
    stopBallMovement();
  }
  
  private void initBallMovement() {
    stop = false;
    Thread thread = new Thread(() -> {
      while(!stop) {
        try {
          Thread.sleep(velocity);
          ball.advance(rows, cols);
        } catch (InterruptedException e) {
          e.printStackTrace();
        }
      }
    });
    thread.start();
  }
  
  private void stopBallMovement() {
    stop = true;
  }
  
  /** Move the left stick one position up */
  public void moveLeftStickUp() {
    int currentRow = leftStick.getRow();
    if(currentRow > 0) {
      leftStick.setRow(currentRow - 1);
    }
  }
  
  /** Move the left stick one positon down */
  public void moveLeftStickDown() {
    int currentRow = leftStick.getRow();
    if(currentRow + leftStick.getSize() < rows) {
      leftStick.setRow(leftStick.getRow() + 1);
    }
  }
  
  // Getters and setters
  public int getCols() {
    return cols;
  }
  
  public int getRows() {
    return rows;
  }
  
  public Ball getBall() {
    return ball;
  }
  
  public Stick getLeftStick() {
    return leftStick;
  }
  
}
