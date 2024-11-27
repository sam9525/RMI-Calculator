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
    if (isEmpty()) {
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
   * Checks if the stack is empty.
   *
   * @return True if the stack is empty, false otherwise.
   */
  public synchronized boolean isEmpty() throws RemoteException {
    return stack.empty();
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

  /**
   * Calculates the least common multiple of two numbers.
   *
   * @param a The first number.
   * @param b The second number.
   * @return The least common multiple of the two numbers.
   */
  public int lcm(int a, int b) {
    return a * (b / gcd(a, b));
  }
}
