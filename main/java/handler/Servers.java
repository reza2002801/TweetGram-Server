package handler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Servers extends Thread {
    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(8000);
            while (true) {
                Socket socket = serverSocket.accept();
                EventHandler signUpHandler = new EventHandler(socket);
                signUpHandler.start();
            }
        } catch (IOException e) {
        }
    }

    public Servers(){
        System.out.println("---Server Is Running---");

    }
}
