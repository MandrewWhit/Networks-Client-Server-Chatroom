package assignment4;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.concurrent.TimeUnit;

public class MyServer {
	public static void main(String[] args){
		try{
				
				
				
				
				
				//set up a datagram socket
				boolean quit = false;
				byte[] buffer = new byte[256];
				//create a list to hold all clients
				ArrayList<ClientData> clients = new ArrayList<ClientData>();
				System.out.println("Server running...");
				DatagramSocket s = new DatagramSocket(8888);
				while(!quit) {
					//get a new pack from a client
					DatagramPacket pack = new DatagramPacket(buffer, buffer.length);
					s.receive(pack);
					//get the port message comes from
					int port = pack.getPort();
					//System.out.println(port);
					InetAddress add = InetAddress.getByName("localhost");
					String input = new String(pack.getData(), 0, pack.getLength());
					System.out.println(input);
					//tokenize the input into acceptable format
					StringTokenizer tokenizer = new StringTokenizer(input);
					ArrayList<String> tokens = new ArrayList<String>();
					while(tokenizer.hasMoreTokens()) {
						tokens.add(tokenizer.nextToken());
					}
					String code = tokens.get(0);
					//if the client is new to the room, and to list and greet other clients
					if(code.equals("<init>")) {
						if(tokens.size()>1) {
							ClientData cd = new ClientData(port, tokens.get(1));
							clients.add(cd);
							for(int i=0;i<clients.size();i++) {
								String welcome = tokens.get(1) + " has entered the chat!";
								pack = new DatagramPacket(welcome.getBytes(), welcome.getBytes().length, add, clients.get(i).getPort());
								s.send(pack);
							}
						}
					//if client is not new to the room, send message to respective client
					}else if(code.equals("<message>")) {
						if(tokens.size()>1) {
							String destinationClient = tokens.get(1);
							String destinationName = "";
							for(int i=0;i<destinationClient.length();i++) {
								if(destinationClient.charAt(i)=='<') {
									for(int j=i+1;j<destinationClient.length();j++) {
										if(destinationClient.charAt(j)!='>') {
											destinationName += destinationClient.charAt(j);
										}
									}
								}
							}
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
								String destMessage = "";
								for(int i=0;i<clients.size();i++) {
									if(port==clients.get(i).getPort()) {
										destMessage += clients.get(i).getName();
									}
								}
								destMessage = "<<" + destMessage + ">>" + " " + message;
								pack = new DatagramPacket(destMessage.getBytes(), destMessage.getBytes().length, add, destinationPort);
								s.send(pack);
							}else {
								System.out.println("Invalid Port");
							}
						}
						
						//if client wants to leave chat inform other clients
					}else if(code.equals("<leaving>")) {
						if(tokens.size()>1) {
							for(int i=0;i<clients.size();i++) {
								if(tokens.get(1).equals(clients.get(i).getName())) {
									String leaveChat = "shutdown_quit";
									pack = new DatagramPacket(leaveChat.getBytes(), leaveChat.getBytes().length, add, clients.get(i).getPort());
									s.send(pack);
									clients.remove(i);
								}
							}
							for(int i=0;i<clients.size();i++) {
								String leaveChat = tokens.get(1) + " has left the chat :(";
								pack = new DatagramPacket(leaveChat.getBytes(), leaveChat.getBytes().length, add, clients.get(i).getPort());
								s.send(pack);
							}
						}
						//shutdown the server
					}else if(code.equals("<shutdown>")){
						System.out.println("Shutting down...");
						for(int i=0;i<clients.size();i++) {
							String leaveChat = "shutdown_quit";
							pack = new DatagramPacket(leaveChat.getBytes(), leaveChat.getBytes().length, add, clients.get(i).getPort());
							s.send(pack);
						}
						quit = true;
					}
					
				}
				s.close();
				
	
		}catch(Exception e){e.printStackTrace();}
	}

}
