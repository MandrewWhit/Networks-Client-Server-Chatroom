package assignment4;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.*;
import java.util.Scanner;

public class ClientThread extends Thread {
	
	private Socket s = null;
	private DatagramSocket ds;
	private InetAddress add;
	//private DataOutputStream dout;
	private String name;
	static private Scanner scan;
	
	ClientThread(Scanner scan){
		try {
			ds = new DatagramSocket();
			add = InetAddress.getByName("localhost");
			/*
			s=new Socket("localhost",8888);
			BufferedWriter init = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			
			synchronized(scan) {
				System.out.println("Enter a name for this client : ");
				this.name = scan.nextLine();
			}
			
			init.write("<init> " + this.name);
			init.flush();
			s.close();
			init.close();
			
			this.scan = scan;
			*/
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
	
		try {
			
			//System.out.println("Hi " + name);
			boolean leaveChat = false;
			//s=new Socket("localhost",8888);
			//dout=new DataOutputStream(s.getOutputStream());
			//BufferedWriter dout = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
			
			while(!leaveChat) {
				
				String message = null;
				s=new Socket("localhost",8888);
				//dout=new DataOutputStream(s.getOutputStream());
				BufferedWriter dout = new BufferedWriter(new OutputStreamWriter(s.getOutputStream()));
				synchronized(scan) {
					System.out.println("<" + name + "> :");
					message = scan.nextLine();
				}
				if(message!=null) {
					if(message.equals("quit")) {
						System.out.println(name + " left chat");
						leaveChat = true;
					}else {
						//System.out.println("sending message...");
						dout.write("<message> " + name + message);
						dout.flush();
					}
					
				}
				s.close();
				dout.close();
				Thread.yield();
				
				
				/*
				count++;
				if(count>10) {
					leaveChat = true;
				}
				*/
				
			}
			//s.close();
			//dout.close();
	
		}catch(Exception e) {
			System.out.println(name + " client failed\n");
			e.printStackTrace();
		}
		
	}
	
}
