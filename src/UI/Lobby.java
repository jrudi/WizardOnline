package UI;

import javax.swing.*;

import connect.Connection;

public class Lobby extends JFrame{
	private JList<String> gamesList;
	private Connection conn;
	
	public Lobby(Connection c){
		this.conn = c;
		this.setVisible(true);
		gamesList = new JList<String>();
		gamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
	}
}
