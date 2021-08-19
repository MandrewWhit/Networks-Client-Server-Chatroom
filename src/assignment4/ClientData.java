package assignment4;

public class ClientData {
	private int port;
	private String name;
	
	ClientData(int port, String name){
		this.port = port;
		this.name = name;
	}
	
	int getPort() {
		return this.port;
	}
	
	void setPort(int port) {
		this.port = port;
	}
	
	String getName() {
		return this.name;
	}
	
	void setName(String name) {
		this.name = name;
	}
}
