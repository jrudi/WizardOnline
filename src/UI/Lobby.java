package UI;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.*;

import connect.Connection;

import messages.MessageREADY;

public class Lobby extends JFrame{

	private static final long serialVersionUID = 1L;
	//	private JList<String> gamesList;
	private Connection conn;
	private JButton ready,quit;
	
	public Lobby(Connection c){
		
		this.conn = c;
//		gamesList = new JList<String>();
//		gamesList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		JPanel panel = new JPanel();
		ready = new JButton("   BEREIT   ");
		quit = new JButton("BEENDEN");
		
		ready.setBackground(Color.RED);
		quit.setBackground(Color.BLACK);
		quit.setForeground(Color.WHITE);
		panel.setLayout(new FlowLayout());
		panel.setBackground(Color.RED);
		panel.add(ready);
		panel.add(quit);
		this.add(panel);
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(new CloseListener());
		ready.addActionListener(new ButtonListener());
		quit.addActionListener(new ButtonListener());

	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (((JButton)e.getSource()).getText()){
			case "   BEREIT   ":
				conn.send(new MessageREADY(true));
				ready.setText("NICHT BEREIT");
				ready.setBackground(Color.GREEN);
				pack();
				break;
			case "NICHT BEREIT":
				conn.send(new MessageREADY(false));
				ready.setText("   BEREIT   ");
				ready.setBackground(Color.RED);
				break;
			case "BEENDEN":
				conn.quit();
				dispose();				
				break;
			}
		}
		
	}
	
	private class CloseListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			conn.quit();
			System.exit(0);
		}
	}
	
	public static void main(String[] args) {
		Lobby lobby = new Lobby(new Connection());
		lobby.setVisible(true);
	}
}
