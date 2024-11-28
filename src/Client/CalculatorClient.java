package Client;

import Interface.Calculator;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

public class CalculatorClient {

  public CalculatorClient() {}

  public static void main(String[] args) {
    String host = (args.length < 1) ? null : args[0];

    char exit;

    try {
      // Connect to the registry
      Registry registry = LocateRegistry.getRegistry(host);
      // Lookup the calculator
      Calculator calculator = (Calculator) registry.lookup("Calculator");

      // Register this client and get a unique ID
      String clientId = calculator.registerClient();
      System.out.println("Connected with Client ID: " + clientId);

      Scanner userInput = new Scanner(System.in);

      // Loop to continue the program
      do {
        System.out.println("Enter a number or operation:");

        while (true) {
          String x = userInput.nextLine();
          // Checks if the input is a number
          if (isNumber(x)) {
            try {
              calculator.pushValue(clientId, Integer.parseInt(x));
            } catch (Exception e) {
              System.err.println("Failed to push value : " + e.getMessage());
              e.printStackTrace();
            }
          } else {
            try {
              calculator.pushOperation(clientId, x);
              int result = calculator.delayPop(clientId, 1000);

              System.out.println("Result: " + result);
              break;
            } catch (Exception e) {
              System.err.println(
                "Failed to push operation : " + e.getMessage()
              );
              e.printStackTrace();
            }
          }
        }

        System.out.println("Do you want to continue? (y/n)");
        exit = userInput.nextLine().charAt(0);
      } while (exit == 'y');
      userInput.close();
    } catch (Exception e) {
      System.err.println("Client exception: " + e.toString());
      e.printStackTrace();
    }
  }

  /**
   * Checks if the input is a number.
   *
   * @param x The input to check.
   * @return True if the input is a number, false otherwise.
   */
  public static boolean isNumber(String x) {
    try {
      Integer.parseInt(x);
      return true;
    } catch (Exception e) {
      return false;
    }
  }
}
