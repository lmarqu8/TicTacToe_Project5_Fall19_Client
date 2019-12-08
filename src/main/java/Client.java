import javafx.animation.PauseTransition;
import javafx.util.Duration;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.function.Consumer;


public class Client extends Thread {

    // data members to hold ip address and port #
    int portNumber;
    String ipAddress;
    Socket socketClient;

    ObjectOutputStream out;
    ObjectInputStream in;

    private Consumer<GameInfo> callback;

    // Client constructor
    Client(String ipAddress, int portNumber) {
        this.ipAddress = ipAddress;
        this.portNumber = portNumber;
    }

    public void setCallback(Consumer<GameInfo> callback) {
        this.callback = callback;
    }

    @Override
    public void run() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // try and create a client thread from the input ip address and port #
        try {
            socketClient = new Socket(ipAddress, portNumber);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        }
        // if the server does not exist, pause for 3 seconds before exit
        catch (Exception e) {
            e.printStackTrace();
            PauseTransition pause1 = new PauseTransition(Duration.seconds(3));
            pause1.setOnFinished(a -> {
                System.exit(0);
            });
            pause1.play();
        }

        while (true) {
            try {
                GameInfo gameInfo = (GameInfo) in.readObject();
                if (callback != null) {
                    callback.accept(gameInfo);
                }
            } catch (Exception e) {
                e.printStackTrace();
                break;
            }
        }

    }


    public void send(GameInfo gameInfo) {
        try {
            out.writeObject(gameInfo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    public void send(String data) {
//
//        try {
//            out.writeObject(data);
//        } catch (IOException e) {
//            // TODO Auto-generated catch block
//            e.printStackTrace();
//        }
//    }

}
