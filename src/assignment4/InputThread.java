package assignment4;

import java.util.Queue;
import java.util.Scanner;
import java.util.concurrent.BlockingQueue;

public class InputThread extends Thread {
	
	private BlockingQueue<String> q;
	private Scanner scan;
	
	
	InputThread(BlockingQueue<String> q, Scanner scan){
		this.q = q;
		this.scan = scan;
		
	}
	
	public void run() {
		
        String input = "";
        boolean leave = false;
        while (!leave) {
        	try {
	            System.out.println("Send a message: ");
	            
	            input = scan.nextLine();
	            
	            if(input.equals("quit")) {
	            	leave = true;
	            }
	            
	           
	            
				q.put(input);
				
        	}catch(InterruptedException e) {
        		leave = true;
        	}
           
        }
        
        
        
     
            
	}
}
