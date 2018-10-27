package cn.johnyu.im.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Map;

public class ServerClient implements Runnable{
	private String userName;
	private Socket socket;
	private Hashtable<String, ServerClient> chatRoom;
	
	private OutputStream out;
	private BufferedReader reader;
	
	public ServerClient(Socket socket, Hashtable<String, ServerClient> chatRoom) {
		super();
		this.socket = socket;
		this.chatRoom = chatRoom;
		try {
			out=socket.getOutputStream();
			reader=new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
	public String getUserName() {
		return userName;
	}
	public Socket getSocket() {
		return socket;
	}
	public void setSocket(Socket socket) {
		this.socket = socket;
	}
	
	public void setChatRoom(Hashtable<String, ServerClient> chatRoom) {
		this.chatRoom = chatRoom;
	}

	@Override
	public void run() {
		try {
			String userName=reader.readLine();
			this.userName=userName;
			chatRoom.put(userName, this);
			System.out.println("server rec username: "+userName);
			out.write(("Hello "+userName+"\n").getBytes());
			String otherWord=null;
			while((otherWord=reader.readLine())!=null) {
				if(otherWord.trim().equals("bye")) {
					out.write(("bye "+userName+"\n").getBytes());
					break;
				}
				String[] wordParts=otherWord.split(":");
				if("all".equals(wordParts[0])) {
					//广播
					for(String un:chatRoom.keySet()) {
						ServerClient targetClient=chatRoom.get(un);
						if(!targetClient.equals(this))
							targetClient.getSocket().getOutputStream().write((userName+" to all :"+wordParts[1]+"\n").getBytes());
					}
				}
				else {
					//p2p
					ServerClient targetClient=chatRoom.get(wordParts[0]);
						targetClient.getSocket().getOutputStream().write((userName+" to you :"+wordParts[1]+"\n").getBytes());
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			try {
				System.out.println("finally....close");
				reader.close();
				out.close();
				socket.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		System.out.println("end.....");
		
	}
	

}
