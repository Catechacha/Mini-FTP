import java.io.*;
import java.net.*;

public class Client {

	public static void main(String[] args) {
		Socket socket=new Socket();
		BufferedReader reader=null;
		BufferedWriter writer=null;
		try{
			try{
				socket.setSoTimeout(100000);
				socket.setTcpNoDelay(true);
				socket.connect(new InetSocketAddress(InetAddress.getLocalHost(), 1500));
				reader=new BufferedReader(new InputStreamReader(socket.getInputStream()));
				writer=new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				BufferedReader localReader = new BufferedReader(new InputStreamReader(System.in));
				System.out.println("WELCOME TO SONG-TORRENT: give me a title");
				String title;
				if(!(title=localReader.readLine()).equals("exit"));{
					writer.write(title+"\r\n");
					writer.flush();
					String reply=reader.readLine();
					if(reply.equals("F")){
						System.out.println("File Not Found on the server");}
					else{
						String path="./FileClient/"+title;
						BufferedWriter bufferwriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(path)));
						String line=reader.readLine();
						while(line!=null){
							bufferwriter.write(line+"\r\n");
							bufferwriter.flush();
							line=reader.readLine();
						}
						System.out.println(title+": File received correctly");
					}
				}
				System.out.println("Communication ended by client");
			}catch(SocketException e){
				System.out.println("Server closed connection or an error appeared");
			}catch(UnknownHostException e){
				e.printStackTrace();
			}catch(IOException e){
				System.out.println("Server closed connection or an error appeared");
			}
		}finally{
			try{
				if(reader!=null) reader.close();
				if(writer!=null) writer.close();
				socket.close();
			}catch(IOException e) {}
		}
	}
}