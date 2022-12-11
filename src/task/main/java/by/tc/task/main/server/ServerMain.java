package by.tc.task.main.server;

import by.tc.task.main.requests.UserClient;
import by.tc.task.service.exception.ServiceException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerMain {
    public static void main(String[] args) {
        BufferedReader  in;
        BufferedWriter out;
        ServerSocket server;
        UserClient userClient = new UserClient();
        boolean stop = false;
        try {
            server = new ServerSocket(8000);
        } catch (IOException e)
        {
            System.err.println("Server can not be initialized");
            return;
        }
        try {
            while (!stop) {
                try {
                System.out.println("Started..");
                Socket clientSocket = server.accept();

                in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                boolean exit = false;
                while (!exit) {
                    try {
                        String stringRequest = in.readLine();
                        System.out.println("Request:");
                        System.out.println(stringRequest);
                        try {
                            userClient.parseRequest(stringRequest);
                            out.write(userClient.processRequest() + "\n");
                            out.flush();
                        } catch (ServiceException e) {
                            System.out.println(e.getMessage());
                            out.write("some problem occurred\n");
                            out.flush();
                        }
                    } catch (Exception e) {
                        exit = true;
                        System.out.println(e.getMessage());
                        out.write("some problem occurred\n");
                        out.flush();
                    }
                }
                clientSocket.close();
                in.close();
                out.close();
                }
                catch (Exception e) {
                    stop = true;
                }
            }
            }
            finally
             {
                System.out.println("Closing server..");
                try {
                server.close(); }
                catch (IOException e) {
                    System.out.println("could not close server normally");
            }
        }
    }
}
