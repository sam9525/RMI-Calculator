package Server;

import Interface.Calculator;
import Interface.CalculatorImplementation;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

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

      Scanner userInput = new Scanner(System.in);

      while (true) {
        // Get the user input and split it into parts
        String[] parts = userInput.nextLine().split("\\s+", 2);
        String firstPart = parts[0];

        // Check if the command is valid
        try {
          if (firstPart.equals("showall")) {
            String clientId = parts[1];
            calculator.showAll(clientId);
          } else {
            // If the command is not valid, print an error message
            System.err.println(
              "Error: Invalid command. Example: \"showall + clientId\""
            );
          }
        } catch (ArrayIndexOutOfBoundsException e) {
          // If the command misses the client ID, print an error message
          System.err.println("Error: Missing client ID");
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      System.err.println("Server exception: " + e.toString());
      e.printStackTrace();
    }
  }
}
