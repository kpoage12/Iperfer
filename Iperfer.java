import java.io.*;
import java.net.*;

public class Iperfer {
    public static final String ERR_CONNECT_MESSAGE = "Can't connect";
    public static final String ERR_MISSING_ARGS = "Error: missing or additional arguments";

    private static void checkPortRange(int port) {
        if (port < 1024 || port > 65535) {
            System.out.println("Error: port number must be in the range 1024 to 65535");
            System.exit(1);
        }
    }

    private static double toKB(long bytes) {
        return bytes / 1000.0;
    }

    private static double toMbps(long bytes, double elapsedTimeMs) {
        return (bytes * 8.0) / 1000000.0 / (elapsedTimeMs / 1000.0);
    }

    public static void main(String[] args) {
        if (args.length == 7) {
            if (!args[0].equals("-c") || !args[1].equals("-h") || !args[3].equals("-p") || !args[5].equals("-t")) {
                System.out.println(ERR_MISSING_ARGS);
                System.exit(1);
            }
            try {
                String hostName = args[2];
                int portNumber = Integer.parseInt(args[4]);
                int timeMillis = Integer.parseInt(args[6]) * 1000;
                checkPortRange(portNumber);
                try (Socket socket = new Socket(hostName, portNumber);
                        OutputStream out = socket.getOutputStream()) {

                    byte[] buf = new byte[1000];
                    long totalBytes = 0;

                    long startTime = System.currentTimeMillis();
                    long endTime = startTime + timeMillis;
                    while (System.currentTimeMillis() < endTime) {
                        out.write(buf);
                        totalBytes += buf.length;
                    }
                    out.flush();

                    double elapsedTimeMs = System.currentTimeMillis() - startTime;
                    System.out.printf("sent=%.0f KB rate=%.3f Mbps%n", toKB(totalBytes),
                            toMbps(totalBytes, elapsedTimeMs));
                } catch (IOException e) {
                    System.out.println(ERR_CONNECT_MESSAGE);
                    System.exit(1);
                }
            } catch (NumberFormatException e) {
                System.out.println(ERR_MISSING_ARGS);
                System.exit(1);
            }

        } else if (args.length == 3) {
            if (!args[0].equals("-s") || !args[1].equals("-p")) {
                System.out.println(ERR_MISSING_ARGS);
                System.exit(1);
            }
            try {
                int portNumber = Integer.parseInt(args[2]);
                checkPortRange(portNumber);

                try (ServerSocket serverSocket = new ServerSocket(portNumber);
                        Socket socket = serverSocket.accept();
                        InputStream in = socket.getInputStream()) {

                    byte[] buf = new byte[1000];
                    long totalBytes = 0;
                    long startTime = 0;

                    int bytesRead;
                    while ((bytesRead = in.read(buf)) != -1) {
                        if (totalBytes == 0) {
                            startTime = System.currentTimeMillis();
                        }
                        totalBytes += bytesRead;
                    }

                    double elapsedTimeMs = System.currentTimeMillis() - startTime;
                    System.out.printf("received=%.0f KB rate=%.3f Mbps%n", toKB(totalBytes),
                            toMbps(totalBytes, elapsedTimeMs));
                } catch (IOException e) {
                    System.out.println(ERR_CONNECT_MESSAGE);
                }

            } catch (NumberFormatException e) {
                System.out.println(ERR_MISSING_ARGS);
                System.exit(1);
            }

        } else {
            System.out.println(ERR_MISSING_ARGS);
            System.exit(1);
        }
    }
}