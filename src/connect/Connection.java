package connect;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import application.GameController;
import messages.*;

public class Connection extends Thread {
	private Socket socket;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private String username;
	private boolean running = true;
	private GameController gc;

	public Connection() {
		initialize();
	}

	private void initialize() {
		try {
			this.socket = new Socket("localHost", 13337);
			this.output = new ObjectOutputStream(socket.getOutputStream());
			this.input = new ObjectInputStream(socket.getInputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void send(Message m) {
		try {
			output.writeObject(m);
			output.flush();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Message receive() {
		try {
			return (Message) input.readObject();
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
		running = false;
		disconnect();

	}

	public void run() {
		while (running) {
			Message m = receive();
			switch (m.getType()) {
			case MATCHFOUND:
				JOptionPane.showMessageDialog(null, "Ein Spiel wurde gefunden!\nEs wird gleich gestartet.");
				gc = new GameController(this);
				break;
			case USERLIST:
				gc.initializeGame(((MessageUSERLIST) m).getUserlist());
				break;
			case HANDCARDS:
				gc.setCards(((MessageHANDCARDS) m).getHandcards());
				break;
			case BID:
				gc.getGameBoard().getUser(((MessageBID) m).getName()).updateBid(((MessageBID) m).getBid());
				break;
			case TRUMPSUIT:
				gc.setTrump(((MessageTRUMPSUIT) m).getTrump());
				break;
			case CARDPLAYED:
				MessageCARDPLAYED cp = (MessageCARDPLAYED)m;
				gc.getGameBoard().playCard(cp.getName(), cp.getCard());
				if(gc.getFollow()==null){
					gc.setFollow(cp.getCard().getColor());
				}
				break;
			case ACTIVEPLAYER:
				setActivePlayer((MessageACTIVEPLAYER) m);
				break;
			case ACTIVEBID:
				setBiddingPlayer((MessageACTIVEBID) m);
			case ROUNDEND:
				gc.roundEnd((MessageROUNDEND) m);
			default:
				break;
				
			}
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	private void setActivePlayer(MessageACTIVEPLAYER mp) {
		if (mp.getName().equalsIgnoreCase(username)) {
			gc.setActive(true);
		}
		gc.getGameBoard().setOneUserActive(mp.getName(),true);
	}

	private void setBiddingPlayer(MessageACTIVEBID mb) {
		if (mb.getName().equalsIgnoreCase(username)) {
			gc.setBidding(true);
		}
		gc.getGameBoard().setOneUserActive(mb.getName(), false);

	}
}
