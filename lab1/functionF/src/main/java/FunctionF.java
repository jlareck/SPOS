import spos.lab1.demo.IntOps;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class FunctionF {
    private SocketChannel socketChannel;
    public FunctionF() throws IOException  {}
    public int calculateF(String command) throws InterruptedException {

        switch (command) {
            case "0":
                return 3;
            case "1":
                Thread.sleep(3000);
                return 3;
            case "2":
                return 0;
            case "3":
                while (true) {}
            case "4" : {
                return 3;
            }
            case "5": {
                while(true){}
            }
        }
        return 0;
    }
    void run(String messageStr) throws IOException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        //calculateF(messageStr);
        int value =  IntOps.funcF(Integer.parseInt(messageStr));
        socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 9000));

        String result = "F "+ value;
        buffer.put(result.getBytes());
        buffer.flip();
        socketChannel.write(buffer);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new FunctionF().run(args[0]);
    }
}
