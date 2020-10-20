package controller;

import interaction.KeyInteractor;
import interaction.PauseHandler;

import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.net.InetSocketAddress;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Server {
    private ServerSocketChannel serverSocketChannel;
    private static final int PORT = 9000;

    public static boolean cancel = true;
    private boolean check = false;
    private List<Integer> results;
    private String action;
    private InetSocketAddress address;

    public static Process getProcessF() {
        return processF;
    }

    public static Process getProcessG() {
        return processG;
    }

    private static Process processF;
    private static Process processG;
    private int count = 0;

    public Server(String action) {
        this.address = new InetSocketAddress("localhost", PORT);
        this.action = action;
        results = new ArrayList<>();

    }
    public void run() throws IOException {
        new Thread( ( ) -> {
            KeyInteractor keyHandler = new KeyInteractor();
            keyHandler.start();
        } ).start();
        try {
            long start, end;
            start = System.nanoTime();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.socket().bind(address);
            serverSocketChannel.configureBlocking(false);
            String pathF = "/Users/mykolamedynsky/Desktop/spos/lab1/out/artifacts/functionF_jar/functionF.jar";
            String pathG = "/Users/mykolamedynsky/Desktop/spos/lab1/out/artifacts/functionG_jar/functionG.jar";
            ProcessBuilder builderF = new ProcessBuilder("java", "-jar", pathF,String.valueOf(action));
            ProcessBuilder builderG = new ProcessBuilder("java", "-jar", pathG, String.valueOf(action));
            if (action.equals("0") || action.equals("2") || action.equals("4")) {
                processF = builderF.start();
                processG = builderG.start();
            }
            else {
                processG = builderG.start();
                processF = builderF.start();
            }
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            while(cancel) {
                SocketChannel socketChannel = serverSocketChannel.accept();

                if (socketChannel != null) {

                    buffer.clear();
                    buffer.put(action.getBytes());
                    buffer.flip();
                    socketChannel.write(buffer);
                    buffer.clear();
                    int size = socketChannel.read(buffer);
                    if (size == -1) {
                        socketChannel.close();
                        continue;
                    }
                    byte[] data = new byte[size];
                    buffer.flip();
                    buffer.get(data);
                    buffer.clear();
                    String gotData = new String(data);
                    System.out.println(gotData);

                    String[] parsedData = gotData.split(" ");
                    if (parsedData[1].equals("0")) {
                        System.out.println("The function " + parsedData[0] + " returned zero");
                        end = System.nanoTime();
                        System.out.println("Time of execution: " + (end - start));
                        PauseHandler.stop();
                        break;
                    }
                    results.add(Integer.parseInt(parsedData[1]));
                    count++;
                    if (count == 2) {
                        int res = 1;
                        for (Integer value: results) {
                            res *= value;
                        }
                        System.out.println("Multiplication result: " + res);
                        break;
                    }
                }
            }

          //  PauseHandler.stop();
        }catch (IOException error) {
            serverSocketChannel.close();
            PauseHandler.stop();
            System.out.println("fdsfsdfsad");
        }

        PauseHandler.stop();

    }

    public void calculateResult() {

    }

    public static void main(String[] args) throws IOException {
        new Server( "4").run();
    }


}
