package UI;

import java.awt.Color;

import javax.swing.*;

import connect.Connection;

public class Lobby extends JFrame{
	private JList<String> gamesList;
	private Connection conn;
	
	public Lobby(Connection c){
		
		this.conn = c;
		gamesList = new JList<String>();
		gamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JPanel panel = new JPanel();
		JButton button = new JButton();
		panel.setSize(300,300);
		panel.setBackground(Color.RED);
		panel.add(button);
		this.add(panel);
		this.pack();
	}
}
