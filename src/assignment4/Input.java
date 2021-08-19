package assignment4;

import java.util.Queue;
import java.util.Scanner;

public class Input extends Thread {
	private Queue<String> q;
	
	public Input(Queue<String> q) {
		this.q = q;
	}
	
	public void run() {
		System.out.println("Input thread started...");
		Scanner s = new Scanner(System.in);
		boolean dontLeave = true;
		while(dontLeave) {
			System.out.println("Send Message:");
			String input = s.nextLine();
			q.add(input);
			if(input.equals("quit")) {
				dontLeave = false;
			}
			try {
				sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		s.close();
	}
	
}
