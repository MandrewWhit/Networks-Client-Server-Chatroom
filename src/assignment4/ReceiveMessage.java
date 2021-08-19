package assignment4;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.concurrent.BlockingQueue;

public class ReceiveMessage extends Thread {
	
	private BlockingQueue<String> q;
	private DatagramSocket ds;
	private InetAddress add;

	ReceiveMessage(BlockingQueue<String> q, DatagramSocket ds, InetAddress add) throws IOException{
		this.q = q;
		this.ds = ds;
		this.add = add;
	}
	
	public void run() {
		boolean leave = false;
		while(!leave) {
			byte[] buf = new byte[100];
			//System.out.println("waiting...");
			DatagramPacket pack2 = new DatagramPacket(buf,buf.length);
			try {
				ds.receive(pack2);
			} catch (IOException e) {
				
				e.printStackTrace();
			}
			String myMessage = new String(pack2.getData(), 0, pack2.getLength());
			if(myMessage.equals("shutdown_quit")) {
				leave = true;
				try {
					q.put(myMessage);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}else {
				try {
					q.put(myMessage);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
		}
		ds.close();
		
		
	}
	
}
