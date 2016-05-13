package connect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import messages.*;

public class Connection {
	private Socket socket;
	private ObjectOutputStream output;
    private ObjectInputStream input;
    private String username;
	
    
    public Connection(){
    	initialize();
    }
    
    
    private void initialize(){
    	try {
			this.socket = new Socket("localHost", 13337);
			this.output = new ObjectOutputStream(socket.getOutputStream());
			this.input = new ObjectInputStream(socket.getInputStream());
			} catch (IOException e) {
			e.printStackTrace();
		}

    }
    public void send(Message m){
    	try {
			output.writeObject(m);
	    	output.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public Message receive(){
    	try {
			return (Message)input.readObject();
		} catch (ClassNotFoundException | IOException e) {
			e.printStackTrace();
			return null;
		}
    }

    public void disconnect() {

    	try {
			this.input.close();
			this.output.close();
			this.socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void quit() {
		send(new MessageQUIT());
		disconnect();
		
	}
}
