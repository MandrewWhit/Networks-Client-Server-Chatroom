package assignment4;

import java.io.OutputStream;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Queue;
import java.util.Scanner;

public class DatagramClient extends Thread{
	private DatagramSocket ds;
	private InetAddress add;
	private Scanner scan;
	private String name;
	private Queue<String> q;
	
	
	
	DatagramClient(Scanner scan, Queue<String> q){
		try {
			System.out.println("client started...");
			ds = new DatagramSocket();
			add = InetAddress.getByName("localhost");
			this.scan = scan;
			this.q = q;
			synchronized(scan) {
				System.out.println("Enter a name for this client: ");
				this.name = scan.nextLine();
			}
			
			String initMessage = "<init> " + this.name;
			
			byte[] buffer = initMessage.getBytes();
			DatagramPacket pack = new DatagramPacket(buffer, buffer.length, add, 8888);
			ds.send(pack);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
		//System.out.println("client running...");
		try {
			//Input inp = new Input(q);
			//inp.start();
			boolean leave = false;
			while(!leave) {
				
				
				sleep(1000);
				String message = q.poll();
				if(message!=null) {
					System.out.println(message);
					String sentMessage = "<message> " + message;
					byte[] buffer = sentMessage.getBytes();
					DatagramPacket pack = new DatagramPacket(buffer, buffer.length, add, 8888);
					ds.send(pack);
				}
				
				
				/*
				String message = "";
				synchronized(scan) {
					System.out.println("<" + this.name + "> ");
					message = scan.nextLine();
				}
				
				String sentMessage = "<message> " + message;
				byte[] buffer = sentMessage.getBytes();
				DatagramPacket pack = new DatagramPacket(buffer, buffer.length, add, 8888);
				ds.send(pack);
				*/
				
				//receive message
				byte[] buf = new byte[100];
				DatagramPacket pack2 = new DatagramPacket(buf,buf.length);
				ds.receive(pack2);
				String myMessage = new String(pack2.getData(), 0, pack2.getLength());
				System.out.println(myMessage + " > " + this.name);
				
			}
			ds.close();
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
}
