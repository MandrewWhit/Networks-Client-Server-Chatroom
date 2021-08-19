package assignment4;
import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class MyClient {
	public static void main(String[] args) throws IOException, InterruptedException {
		//create queues to receive input from server and user
		BlockingQueue<String> q = new LinkedBlockingQueue<String>();
		BlockingQueue<String> data = new LinkedBlockingQueue<String>();
		
		Scanner canner = new Scanner(System.in);
		InputThread t1 = new InputThread(q, canner);
		
		
		
		System.out.println("client started...");
		DatagramSocket ds = new DatagramSocket();
		InetAddress add = InetAddress.getByName("localhost");
		ReceiveMessage t2 = new ReceiveMessage(data, ds, add);
		Scanner scan = new Scanner(System.in);
		String name;
		//get the name for new client and communicate with the server
		synchronized(scan) {
			System.out.println("Enter a name for this client: ");
			name = scan.nextLine();
		}
		
		String initMessage = "<init> " + name;
		//send the initial message to the server, that has new client information
		byte[] buffer = initMessage.getBytes();
		DatagramPacket pack = new DatagramPacket(buffer, buffer.length, add, 8888);
		ds.send(pack);
		
		//start the input threads
		t1.start();
		t2.start();
		
		boolean dontLeave = true;
		boolean shutdown = false;
	
		
		
		
		//main chat loop for getting and sending information
		while(dontLeave) {
		
			String message = q.poll();
			if(message!=null) {
				
				if(message.equals("quit")) {
					dontLeave = false;
					String leavingMessage = "<leaving> " + name;
					//send the initial message to the server, that has new client information
					byte[] buf = leavingMessage.getBytes();
					DatagramPacket pa = new DatagramPacket(buf, buf.length, add, 8888);
					ds.send(pa);
					
				}
				
				else if (message.equals("shutdown")) {
				
					String sentMessage = "<shutdown> " + message;
					buffer = sentMessage.getBytes();
					pack = new DatagramPacket(buffer, buffer.length, add, 8888);
					ds.send(pack);
				}else {
					String sentMessage = "<message> " + message;
					buffer = sentMessage.getBytes();
					pack = new DatagramPacket(buffer, buffer.length, add, 8888);
					ds.send(pack);
				}
			}
			message = null;
			
			String sentData = data.poll();
			if(sentData!=null) {
				if(sentData.equals("shutdown_quit")) {
					dontLeave = false;
				}else {
					System.out.println(sentData);
				}
			}
			
			try {
				Thread.sleep(1000);
			}catch(Exception e) {
				e.printStackTrace();
			}
			
		}
		//after leaving main loop send message to the server that you are no longer in chat room
		
		
		canner.close();
		
		
		
	}
}
