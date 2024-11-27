package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
  // Pushes a value onto the calculator's stack.
  void pushValue(int val) throws RemoteException;
  // Pushes an operation onto the calculator's stack (min, max, lcm, gcd)
  void pushOperation(String operator) throws RemoteException;
  // Return true if the stack is empty
  boolean isEmpty() throws RemoteException;
  // Pops the values on the stack
  int pop() throws RemoteException;
  // Waits for millis milliseconds before performing the pop operation
  int delayPop(int millis) throws RemoteException;
}
