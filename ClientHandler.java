import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable{
	Socket client;
	
	public ClientHandler(Socket client){
		this.client = client;
	}
	
	@Override
	public void run(){
		try(BufferedReader reader = new BufferedReader(new InputStreamReader(this.client.getInputStream()));
				BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.client.getOutputStream()));){
				this.client.setSoTimeout(100000);
				String message="";
				String response="";
				if((message=reader.readLine())!=null){
					String path="./FileServer/"+message;
					FileReader readerfile = null;
					try {
						readerfile = new FileReader(path);
						response="T";
						writer.write(response+"\r\n");
						writer.flush();
						BufferedReader bufferread = new BufferedReader(readerfile);
						String line="";
						try {line = bufferread.readLine();}
						catch (IOException e){e.printStackTrace();}
						while(line!=null){
							writer.write(line+"\r\n");
							writer.flush();
							try {line = bufferread.readLine();}
							catch (IOException e){e.printStackTrace();}
						}
						System.out.println(message+": File sent correctly");
					} catch (FileNotFoundException e){
						response="F";
						writer.write(response+"\r\n");
						writer.flush();
						System.out.println(message+": File Not Found");
					}
				}
		}catch(IOException e){
			System.out.println("Client closed connection or an error appeared");
		}finally{
			try{
				this.client.close();
			}catch(IOException e){}
		}
	}
}