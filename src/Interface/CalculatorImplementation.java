package Interface;

import java.rmi.RemoteException;
import java.util.Stack;

public class CalculatorImplementation implements Calculator {

  private Stack<Object> stack;

  // Initializes the stack for the calculator on the server
  public CalculatorImplementation() throws RemoteException {
    stack = new Stack<>();
  }

  /**
   * Pushes a value onto the calculator's stack.
   * This method is synchronized to ensure thread safety.
   *
   * @param val The value to be pushed onto the stack.
   */
  public synchronized void pushValue(int val) throws RemoteException {
    stack.push(val);
  }
}
