# Starter code base

The starting code for the project is small, but it is multi-module - this lets us have a separate server executable and client executable, but share code between them

So, for instance

* To run the client, run 

    ```sh
    gradle5 client:run
    ```

* To run the server, run


    ```sh
    gradle5 server:run
    ```


**Note:** Gradle tasks only complete when they *finish*. So, for `gradle run` tasks, the task will sit at around 91% completion while the server (or client) is running, and will only say "100% complete" when you *exit* the server (or client)

## The modules


* **Core** - This is shared between the client and the server, so they both have access to the same sets of classes. Teams are welcome to add to core, but please
  keep it well-organised (use a unique package name for your feature). Or you can create your own module that the client and server will use.  
  **Note:** Don't put any UI classes (anything that requires a JavaFX class to be present) into core, because the server does not have the UI classes.

* **Network** - This is also shared between the client and the server. It uses Kryonet, which is a (fairly old) library for serialising classes over a network connection.
  The nice thing about Kryonet is that so long as you register classes in the same order on the server and client, it "just works". We ensure classes are registered in the
  same order on the client and server by putting the registration code into the `Protocol` class and calling `Protocol.register()` in the client and server. 

* **Client** - a JavaFX client that (at the moment) just opens a Kryonet connection to the server, and puts up a Hello World message

* **Server** - a Server process that (at the moment) just opens a Kryonet server and listens for connection. 

