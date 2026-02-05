import java.net.*;
import java.io.*;

public class Iperfer {
    public static void main(String[] args) {
        if (args.length == 7){
            if (!args[0].equals("-c") || !args[1].equals("-h") || !args[3].equals("-p") || !args[5].equals("-t")){
                System.out.println("Error: missing or additional arguments");
                System.exit(1);
            }
            
            try{
                String hostName = args[2];
                int portNumber = Integer.parseInt(args[4]);
                int timeMillis = Integer.parseInt(args[6]) * 1000;
                
                if (portNumber < 1024 || portNumber > 65535){
                    System.out.println("Error: port number must be in the range 1024 to 65535");
                    System.exit(1);
                }

                try {
                    Socket socket = new Socket(hostName, portNumber);
                    PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

                    long startTime = System.currentTimeMillis();
                    long endTime = startTime + timeMillis;

                    while (System.currentTimeMillis() < endTime){
                        out.write(1);
                    }

                }
                catch (Exception e){
                    System.out.println("Can't connect");
                    System.exit(1);
                }

            }

            catch (NumberFormatException e){
                System.out.println("Error: missing or additional arguments");
                System.exit(1);
            }

        }
        else if (args.length == 3){
            if (!args[0].equals("-s") || !args[1].equals("-p")){
                System.out.println("Error: missing or additional arguments");
                System.exit(1);
            }
            try{
                int portNumber = Integer.parseInt(args[2]);
                
                try{
                    ServerSocket serverSocket = new ServerSocket(portNumber);

                    Socket socket = serverSocket.accept();
                    System.out.println("Client accepted");

                }
                catch(IOException e)
                {
                    System.out.println("Cant Connect");
                }

            }
            catch (NumberFormatException e){
                System.out.println("Error: missing or additional arguments");
                System.exit(1);
            }
            
        }
        else {
            System.out.println("Error: missing or additional arguments");
            System.exit(1);
        }
    }
}