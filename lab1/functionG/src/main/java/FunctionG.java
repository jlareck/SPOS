import spos.lab1.demo.IntOps;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class FunctionG {

    private SocketChannel socketChannel;
    public FunctionG() throws IOException  {}
    public int calculateG(String command) throws InterruptedException {

        switch (command) {
            case "0":
                Thread.sleep(1000);
                return 5;
            case "1":
                return 5;
            case "2":
                while (true) {}
            case "3":
                return 0;
            case "4" : {
                while(true){}
            }
            case "5": {
               return 5;
            }
        }
        return 0;
    }
    void run(String messageStr) throws IOException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
//        byte[] message = new byte[socketChannel.read(buffer)];
//        buffer.flip();
//        buffer.get(message);
//        String messageStr = new String(message);
//        buffer.clear();
      //  Thread.sleep(5000);
        int value = IntOps.funcG(Integer.parseInt(messageStr));
        socketChannel = SocketChannel.open(new InetSocketAddress("localhost", 9000));
        System.out.println(value);
        String result = "G "+ value;
        buffer.put(result.getBytes());
        buffer.flip();
        socketChannel.write(buffer);
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        new FunctionG().run(args[0]);
    }
}
