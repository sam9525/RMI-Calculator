package Interface;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class CalculatorImplementation implements Calculator {

  // Maps each client to their own stack
  private Map<Long, Stack<Object>> clientStacks;

  // Initializes the stack for the calculator on the server
  public CalculatorImplementation() throws RemoteException {
    clientStacks = new HashMap<>();
  }

  // Gets current client's stack
  private Stack<Object> getCurrentStack() {
    long clientId = Thread.currentThread().threadId();
    return clientStacks.computeIfAbsent(clientId, _ -> new Stack<>());
  }

  /**
   * Pushes a value onto the calculator's stack.
   * This method is synchronized to ensure thread safety.
   *
   * @param val The value to be pushed onto the stack.
   */
  public synchronized void pushValue(int val) throws RemoteException {
    getCurrentStack().push(val);
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
    Stack<Object> stack = getCurrentStack();

    // Checks if the stack is empty
    if (isEmpty()) {
      throw new RemoteException("There are no numbers to be calculated.");
    }

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
        break;
      case "lcm":
        result = numStack.get(0);
        for (int i = 1; i < numStack.size(); i++) {
          result = lcm(result, numStack.get(i));
        }
        break;
      case "gcd":
        result = numStack.get(0);
        for (int i = 1; i < numStack.size(); i++) {
          result = gcd(result, numStack.get(i));
        }
        break;
      default:
        throw new RemoteException("Invalid operation.");
    }

    stack.push(operator);

    stack.push(result);
  }

  /**
   * Checks if the stack is empty.
   *
   * @return True if the stack is empty, false otherwise.
   */
  public synchronized boolean isEmpty() throws RemoteException {
    return getCurrentStack().empty();
  }

  /**
   * Pops the top element from the stack.
   *
   * @return The top element from the stack.
   * @throws RemoteException If the stack is empty.
   */
  public synchronized int pop() throws RemoteException {
    Stack<Object> stack = getCurrentStack();

    // Checks if the stack is empty
    if (isEmpty()) {
      throw new RemoteException("The stack is empty.");
    }

    // Checks if the top of the stack is an integer
    if (stack.peek() instanceof Integer) {
      return (int) stack.pop();
    } else {
      throw new RemoteException("The top of the stack is not an integer.");
    }
  }

  /**
   * Delays the execution of the pop method by the specified number of milliseconds.
   *
   * @param millis The number of milliseconds to delay the execution.
   * @return The result of the pop operation after the delay.
   * @throws RemoteException If the thread is interrupted.
   */
  public synchronized int delayPop(int millis) throws RemoteException {
    try {
      Thread.sleep(millis);
    } catch (InterruptedException e) {
      throw new RemoteException("Thread was interrupted.");
    }

    return pop();
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
    return a * b / gcd(a, b);
  }

  @Override
  public void showAll() throws RemoteException {
    Stack<Object> stack = getCurrentStack();

    if (stack.isEmpty()) {
      System.out.println("Stack is empty");
      return;
    }

    System.out.println("\nCurrent stack contents:");
    System.out.println("----------------------");
    for (int i = 0; i < stack.size(); i++) {
      System.out.printf("Position " + i + ": " + stack.get(i) + "\n");
    }
    System.out.println("----------------------");
  }
}
