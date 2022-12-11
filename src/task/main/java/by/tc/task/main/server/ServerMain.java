package by.tc.task.main.server;

import by.tc.task.main.requests.UserClient;
import by.tc.task.service.exception.ServiceException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.*;
public class ServerMain {
    public static void main(String[] args) {
        ServerSocket server;
        boolean stop = false;
        try {
            server = new ServerSocket(8000);
        } catch (IOException e)
        {
            System.err.println("Server can not be initialized");
            return;
        }
        try {
            System.out.println("Started..");
            while (!stop) {
                try {
                Socket clientSocket = server.accept();
                    System.out.println("new client");
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                new Thread(clientHandler).start();
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

    private static class ClientHandler implements Runnable {
        private final Socket clientSocket;
        public ClientHandler(Socket socket)
        {
            this.clientSocket = socket;
        }
        public void run() {
            UserClient userClient = new UserClient();
            BufferedReader in = null;
            BufferedWriter out = null;
            try {
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
                        try {
                            out.write("some problem occurred\n");
                            out.flush();
                        } catch (IOException ex) {
                            throw new RuntimeException(ex);
                        }
                    }
                }

            try {
                clientSocket.close();
                in.close();
                out.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    out.close();
                    in.close();
                    clientSocket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            } catch (IOException e) {
                throw new RuntimeException(e);
            } finally {
                try {
                    if (out != null) {
                        out.close();
                    }
                    if (in != null) {
                        in.close();
                        clientSocket.close();
                    }
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
