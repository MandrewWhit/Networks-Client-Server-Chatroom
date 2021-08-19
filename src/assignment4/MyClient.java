package assignment4;
import java.io.*;
import java.net.*;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class MyClient {
	public static void main(String[] args) {
		Scanner s = new Scanner(System.in);
		/*
		ClientThread t1 = new ClientThread(s);
		ClientThread t2 = new ClientThread(s);
		t1.start();
		t2.start();
		*/
		Queue<String> q = new LinkedList<String>();
		DatagramClient t1 = new DatagramClient(s, q);
		//DatagramClient t2 = new DatagramClient(s);
		t1.start();
		//t2.start();
	}
}
