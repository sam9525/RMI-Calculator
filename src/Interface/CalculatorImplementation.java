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

  /**
   * This method is synchronized to ensure thread safety.
   * Executes and pushes an operation on the calculator's stack.
   * Creates a new stack numStack that stores only the Integers from the stack.
   * The result of the operation is then pushed back onto the stack.
   *
   * @param operator The operation to be performed on the stack. Supported operations are "min", "max", "lcm", and "gcd".
   * @throws RemoteException If the operation is invalid or the stack is empty.
   */
  public synchronized void pushOperation(String operator)
    throws RemoteException {
    // Checks if the stack is empty
    if (stack.isEmpty()) {
      throw new RemoteException("There are no numbers to be calculated.");
    }

    stack.push(operator);

    Stack<Integer> numStack = new Stack<>();

    for (Object obj : stack) {
      if (obj instanceof Integer) {
        numStack.push((Integer) obj);
      }
    }

    int result;

    // Changes the operator to lowercase to make it case-insensitive
    switch (operator.toLowerCase()) {
      case "min":
        result = numStack.stream().min(Integer::compare).get();
        break;
      case "max":
        result = numStack.stream().max(Integer::compare).get();
      case "lcm":
        result = numStack.stream().reduce(1, (x, y) -> lcm(x, y));
      case "gcd":
        result = numStack.stream().reduce(0, (x, y) -> gcd(x, y));
      default:
        throw new RemoteException("Invalid operation.");
    }

    stack.push(result);
  }

  /**
   * Calculates the greatest common divisor of two numbers using the Euclidean algorithm.
   *
   * @param a The first number.
   * @param b The second number.
   * @return The greatest common divisor of the two numbers.
   */
  public int gcd(int a, int b) {
    return b == 0 ? a : gcd(b, a % b);
  }
}
