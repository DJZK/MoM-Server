package com.Bryan.Server;

/**
 *
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) {
        try (var listener = new ServerSocket(59898)) {
            println("Server is now running ");
            var pool = Executors.newFixedThreadPool(20);
            while (true) {
                pool.execute(new netServer(listener.accept()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static class netServer implements Runnable {
        public static Socket socket;

        netServer(Socket socket) throws IOException {
            this.socket = socket;
        }

        @Override
        public void run() {

            //Variable Declaration
            boolean loginLoop = true;
            boolean accountFunction = true;
            boolean logoutLoop = true;

            println("Client: " + socket + " Connected");

            try {
                var in = new Scanner(socket.getInputStream());
                var out = new PrintWriter(socket.getOutputStream(), true);

                // Loop for Logging in

                while (loginLoop) {
                    out.println("Enter Username");
                    User.inputUsername = in.next();
                    out.println("Enter Password for " + User.inputUsername);
                    User.inputPassword = in.next();

                    // Method that checks account
                    DatabaseReader.checkAccount(User.inputUsername);

                    if (User.loginLogic()) { // Method that compares the input from user to the database

                        // Methods that loads the user at User Class.
                        DatabaseReader.loadUser(User.inputUsername);
                        out.println("Welcome " + User.getType() + " " + User.getUsername());

                        // Terminates the Loop of Login
                        loginLoop = false;

                        // Initializes te loop for what the account can do.
                        accountFunction = true;
                    } else {
                        out.println("Invalid Username or Password");
                    }
                }

                // Loop for Account function
                while (accountFunction) {

                    if (User.getType().equals("SM")) { // Separates the function for Senior Manager to Regional Director
                        out.println("What do you want to do? \n [1] Generate Region A \n [2] Generate Region B \n [3] Generate Region C \n [4] Generate All Regions  \n [0] Logout \n Enter the number of your choice");
                        String choice = in.next();

                        switch (Integer.parseInt(choice)) {
                            case 1: { // Generate Region A
                                out.println("Set the target number of client");
                                String goal = in.next();
                                DatabaseReader.genReport("A");
                                if (DatabaseReader.clientCounter < Integer.parseInt(goal)) {
                                    out.println("");
                                    out.println("Number of Clients: " + DatabaseReader.clientCounter);
                                    out.println("Region Failed");
                                } else {
                                    out.println("Number of Clients: " + DatabaseReader.clientCounter);
                                    out.println("Region Success");
                                }
                                out.println("");
                                break;
                            }
                            case 2: { // Generate Region B
                                out.println("Set the target number of client");
                                String goal = in.next();
                                DatabaseReader.genReport("B");
                                if (DatabaseReader.clientCounter < Integer.parseInt(goal)) {
                                    out.println("");
                                    out.println("Number of Clients: " + DatabaseReader.clientCounter);
                                    out.println("Region Failed");
                                } else {
                                    out.println("Number of Clients: " + DatabaseReader.clientCounter);
                                    out.println("Region Success");
                                }
                                out.println("");
                                break;

                            }
                            case 3: { // Generate Region C
                                out.println("Set the target number of client");
                                String goal = in.next();
                                DatabaseReader.genReport("C");
                                if (DatabaseReader.clientCounter < Integer.parseInt(goal)) {
                                    out.println("");
                                    out.println("Number of Clients: " + DatabaseReader.clientCounter);
                                    out.println("Region Failed");
                                } else {
                                    out.println("Number of Clients: " + DatabaseReader.clientCounter);
                                    out.println("Region Success");
                                }
                                out.println("");
                                break;
                            }
                            case 4: { // Generate All Regions
                                out.println("Set the target number of client");
                                String goal = in.next();

                                // Region A
                                out.println("Region A");
                                DatabaseReader.genReport("A");
                                if (DatabaseReader.clientCounter < Integer.parseInt(goal)) {
                                    out.println("");
                                    out.println("Number of Region A Clients: " + DatabaseReader.clientCounter);
                                    out.println("Region A Failed");
                                } else {
                                    out.println("Number of Region A Clients: " + DatabaseReader.clientCounter);
                                    out.println("Region A Success");
                                }

                                out.println("");

                                //Region B
                                out.println("Region B");
                                DatabaseReader.genReport("B");
                                if (DatabaseReader.clientCounter < Integer.parseInt(goal)) {
                                    out.println("");
                                    out.println("Number of Region B Clients: " + DatabaseReader.clientCounter);
                                    out.println("Region B Failed");
                                } else {
                                    out.println("Number of Region B Clients: " + DatabaseReader.clientCounter);
                                    out.println("Region B Success");
                                }
                                out.println("");

                                //Region C
                                out.println("Region C");
                                DatabaseReader.genReport("C");
                                if (DatabaseReader.clientCounter < Integer.parseInt(goal)) {
                                    out.println("");
                                    out.println("Number of Region C Clients: " + DatabaseReader.clientCounter);
                                    out.println("Region C Failed");
                                } else {
                                    out.println("Number of Region C Clients: " + DatabaseReader.clientCounter);
                                    out.println("Region C Success");
                                }
                                out.println("");
                                break;
                            }
                            case 0: { // Logout
                                logoutLoop = true;
                                while (logoutLoop) {
                                    out.println("Are you sure  to logout? \n [1 or Y] Yes \n [2 or N] No");
                                    String choice2 = in.next();

                                    switch (choice2) {
                                        case "1":
                                        case "Y":
                                        case "y": {
                                            out.println("Logged Out");
                                            out.println("Thank you for using the program :D");

                                            // Terminates the Account function loop
                                            accountFunction = false;
                                            // Terminates the logout loop;
                                            logoutLoop = false;
                                            break;
                                        }
                                        case "2":
                                        case "N":
                                        case "n": {
                                            out.println("Ok then stay");
                                            logoutLoop = false;
                                            break;
                                        }
                                        default: {
                                            out.println("Invalid choice!");
                                            break;
                                        }
                                    }
                                }
                                out.println("");
                                break;
                            }
                            default: {
                                out.println("Invalid choice!");
                                break;
                            }
                        }
                    } else {
                        // Non SM
                        out.println("What do you want to do? \n [1] Generate Region " + User.getRegion() + " report \n [0] Logout \n Enter the number of your choice");
                        String choice = in.next();
                        switch (Integer.parseInt(choice)){
                            case 1:{
                                out.println("Set the target number of client");
                                String goal = in.next();
                                DatabaseReader.genReport(User.getRegion());
                                if (DatabaseReader.clientCounter < Integer.parseInt(goal)) {
                                    out.println("");
                                    out.println("Number of Clients: " + DatabaseReader.clientCounter);
                                    out.println("Region Failed");
                                } else {
                                    out.println("Number of Clients: " + DatabaseReader.clientCounter);
                                    out.println("Region Success");
                                }
                                out.println("");
                                break;
                            }
                            case 0: { // Logout
                                logoutLoop = true;
                                while (logoutLoop) {
                                    out.println("Are you sure  to logout? \n [1 or Y] Yes \n [2 or N] No");
                                    String choice2 = in.next();

                                    switch (choice2) {
                                        case "1":
                                        case "Y":
                                        case "y": {
                                            out.println("Logged Out");
                                            out.println("Thank you for using the program :D");

                                            // Terminates the Account function loop
                                            accountFunction = false;
                                            // Terminates the logout loop;
                                            logoutLoop = false;
                                            break;
                                        }
                                        case "2":
                                        case "N":
                                        case "n": {
                                            out.println("Ok then stay");
                                            logoutLoop = false;
                                            break;
                                        }
                                        default: {
                                            out.println("Invalid choice!");
                                            break;
                                        }
                                    }
                                }
                                out.println("");
                                break;
                            }
                            default: {
                                out.println("Invalid choice!");
                                break;
                            }
                        }
                    }
                }
            } catch (Exception e) {
                println("Error: " + socket);
            } finally {
                try {
                    socket.close();
                } catch (IOException e) {

                }
                println("Client: " + socket + " disconnected");

            }

        }
    }

    public static void println(String message) {
        System.out.println(message);
    }
}
