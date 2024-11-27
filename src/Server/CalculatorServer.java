package Server;

import Interface.Calculator;
import Interface.CalculatorImplementation;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class CalculatorServer {

  public CalculatorServer() {}

  public static void main(String[] args) {
    try {
      // Create a new calculator
      CalculatorImplementation calculator = new CalculatorImplementation();
      // Create a stub for the calculator in default port(1099)
      Calculator stub = (Calculator) UnicastRemoteObject.exportObject(
        calculator,
        0
      );

      // Bind the stub to the registry
      Registry registry = LocateRegistry.getRegistry();
      registry.bind("Calculator", stub);

      System.out.println("Server ready.");
    } catch (Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();
    }
  }
}
