# JAVA RMI Calculator

Using Java RMI to create a calculator for practice and to understand how to build a distributed system.

This calculator allows clients to enter multiple numbers and choose the method including min, max, lcm (Least Common Multiple) and gcd (Greatest Common Divisor).

## Compiling

Open a terminal at the (calculator) folder,

```
make
```

This should compile and generate all `.class` files inside `/bin` folder.

## Run rmiregistry

This should open a new terminal for the next code, run code `cd bin` in the previous terminal and run `rmiregistry`

```
make runRegistry
```

## Run Server

This should open a new terminal for the next code, run code `cd bin` in the previous terminal and run `java Server.CalculatorServer`

```
make runServer
```

## Run Client

This should open a new terminal for the next code, run code `cd bin` in the previous terminal and run `java Client.CalculatorClient`

```
make runClient
```

## Clean

Clean `/bin` folder which including all `.class` files

```
make clean
```

> [!Note]
>
> ## Server
>
> ```
> CalculatorImplementation calculator = new CalculatorImplementation();
> Calculator stub = (Calculator) UnicastRemoteObject.exportObject(calculator, 0);
> ```
>
> Create a stub for the calculator in default port(1099).
>
> The `0` is the default port number for the RMI registry.
>
> ## Client
>
> ```
> Registry registry = LocateRegistry.getRegistry(host);
> Calculator calculator = (Calculator) registry.lookup("Calculator");
> ```
>
> Connect to the registry and lookup the calculator.
>
> ## Implementations
>
> ```
> switch (operator.toLowerCase()) {
> case "min":
>   result = numStack.stream().min(Integer::compare).get();
>   break;
> case "max":
>   result = numStack.stream().max(Integer::compare).get();
>   break;
> case "lcm":
>   result = numStack.get(0);
>   for (int i = 1; i < numStack.size(); i++) {
>     result = lcm(result, numStack.get(i));
>   }
>   break;
> case "gcd":
>   result = numStack.get(0);
>   for (int i = 1; i < numStack.size(); i++) {
>     result = gcd(result, numStack.get(i));
>   }
>   break;
> default:
>   throw new RemoteException("Invalid operation.");
> }
> ```
>
> ### min, max
>
> Converts the stack to the stream and finds the result based on the operator.
>
> ### lcm, gcd
>
> Iterates through the stack and calculates the result using the numStack which only inclucdes the numbers from the stack.
