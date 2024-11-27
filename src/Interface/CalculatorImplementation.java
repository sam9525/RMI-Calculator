package Interface;

import java.rmi.RemoteException;
import java.util.Stack;

public class CalculatorImplementation implements Calculator {

  private Stack<Object> stack;

  // Initializes the stack for the calculator on the server
  public CalculatorImplementation() throws RemoteException {
    stack = new Stack<>();
  }
}
