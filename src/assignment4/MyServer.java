package assignment4;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class MyServer {
	public static void main(String[] args){
		try{
				
				ServerSocket ss=new ServerSocket(8888);
				
				//Socket s=ss.accept();//establishes connection 
				/*
				DataInputStream dis=new DataInputStream(s.getInputStream());
				for(int i=0;i<100;i++) {
					String	str=(String)dis.readUTF();
					System.out.println("message= "+str);
				}
				*/
				//BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
				/*
				String message;
				boolean quit = false;
				while(!quit) {
					//System.out.println("Waiting for request...");
					Socket s=ss.accept();//establishes connection 
					//System.out.println("Processing request...");
					BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));
					//System.out.println("Reading message");
					message = in.readLine();
					//System.out.println("Message received");
					if(message!=null) {
						//tokenize the message
						StringTokenizer tokenizer = new StringTokenizer(message);
						ArrayList<String> tokens = new ArrayList<String>();
						while(tokenizer.hasMoreTokens()) {
							tokens.add(tokenizer.nextToken());
						}
						String code = tokens.get(0);
						if(code.equals("<init>")) {
							
						}
						//System.out.println(message);
					}
					//System.out.println("Message printed");
					Thread.yield();
				}
				
				System.out.println("server left loop");
				
				ss.close();
				*/
				
				
				boolean quit = false;
				byte[] buffer = new byte[256];
				ArrayList<ClientData> clients = new ArrayList<ClientData>();
				System.out.println("Server running...");
				DatagramSocket s = new DatagramSocket(8888);
				while(!quit) {
					
					DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
					s.receive(pack);
					int port = pack.getPort();
					//System.out.println(port);
					InetAddress add = InetAddress.getByName("localhost");
					String input = new String(pack.getData(), 0, pack.getLength());
					//System.out.println(input);
					StringTokenizer tokenizer = new StringTokenizer(input);
					ArrayList<String> tokens = new ArrayList<String>();
					while(tokenizer.hasMoreTokens()) {
						tokens.add(tokenizer.nextToken());
					}
					String code = tokens.get(0);
					if(code.equals("<init>")) {
						ClientData cd = new ClientData(port, tokens.get(1));
						clients.add(cd);
					}else if(code.equals("<message>")) {
						if(tokens.size()>1) {
							String destinationName = tokens.get(1);
							int destinationPort = -1;
							for(int i=0;i<clients.size();i++) {
								if(clients.get(i).getName().equals(destinationName)) {
									destinationPort = clients.get(i).getPort();
								}
							}
							
							String message = "";
							for(int i=2;i<tokens.size();i++) {
								
								if(i+1>=tokens.size()) {
									message += tokens.get(i);
								}else {
									message += tokens.get(i);
									message += " ";
								}
								
							}
							
							//if port exists then send message
							if(destinationPort!=-1) {
								pack = new DatagramPacket(message.getBytes(), message.getBytes().length, add, destinationPort);
								s.send(pack);
							}else {
								System.out.println("Invalid Port");
							}
						}
						
						
					}
					
				}
				s.close();
				
	
		}catch(Exception e){e.printStackTrace();}
	}

}
