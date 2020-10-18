import spos.lab1.demo.IntOps;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

public class FunctionG {

    private SocketChannel socketChannel;
    public FunctionG(int port) throws IOException  {
        InetSocketAddress address = new InetSocketAddress("localhost", port);
        socketChannel = SocketChannel.open(address);
    }
    void run() throws IOException, InterruptedException {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        byte[] message = new byte[socketChannel.read(buffer)];
        buffer.flip();
        buffer.get(message);
        String messageStr = new String(message);
        buffer.clear();
        int value = IntOps.funcG(Integer.parseInt(messageStr));
        String result = ""+value;
        buffer.put(result.getBytes());
        buffer.flip();
        socketChannel.write(buffer);
    }
    public static void main(String[] args) throws IOException, InterruptedException {
        new FunctionG(9000).run();
    }
}
