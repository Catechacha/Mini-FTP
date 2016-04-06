import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class Server {
	public static void main(String[] args){
		ExecutorService es = null;
		try(ServerSocket server=new ServerSocket()){
			server.bind(new InetSocketAddress(InetAddress.getLocalHost(),1500));
			es=Executors.newFixedThreadPool(20);
			while(true){
				try{
					Socket client=server.accept();
					ClientHandler handler=new ClientHandler(client);
					es.submit(handler);
				}catch(IOException e){
					System.out.println("Some error appeared");
				}
			}
		}catch(UnknownHostException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if (es!=null)
				es.shutdown();
		}
	}
}
