package Interface;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.Stack;

public interface Calculator extends Remote {
  // Pushes a value onto the calculator's stack.
  void pushValue(String clientId, int val) throws RemoteException;
  // Pushes an operation onto the calculator's stack (min, max, lcm, gcd)
  void pushOperation(String clientId, String operator) throws RemoteException;
  // Return true if the stack is empty
  boolean isEmpty(String clientId) throws RemoteException;
  // Pops the values on the stack
  int pop(String clientId) throws RemoteException;
  // Waits for millis milliseconds before performing the pop operation
  int delayPop(String clientId, int millis) throws RemoteException;
  // Shows all the elements in the stack
  void showAll(String clientId) throws RemoteException;
  // Gets the current client's ID
  Stack<Object> getCurrentStack(String clientId) throws RemoteException;
  // Registers a new client
  String registerClient() throws RemoteException;
}
