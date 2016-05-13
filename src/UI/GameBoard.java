package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;

import javax.swing.*;
import javax.swing.border.LineBorder;

import application.GameController;
import connect.Connection;
import domain.Card;
import domain.CardColor;
import messages.MessageBID;

public class GameBoard extends JFrame {

	private static final long serialVersionUID = 1L;
	private ArrayList<UserPanel> users;
	private ArrayList<CardPanel> cards;
	// private ArrayList<CardPanel> playedCards;
	private GameController gc;
	private JPanel cardsPanel, usersPanel;
	private JFrame bidFrame;
	private JButton plus, minus, send;
	private JTextField num;

	public GameBoard(ArrayList<String> players) {
		this.setLayout(new BorderLayout());
		this.cardsPanel = new JPanel();
		this.usersPanel = new JPanel();
		this.setSize(1700, 1000);

		initPlayers(players);

		this.add(cardsPanel);
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.addWindowListener(new CloseListener());
		JPanel midPanel = new JPanel();
		midPanel.setBackground(Color.DARK_GRAY);
		midPanel.setPreferredSize(new Dimension(1000, 400));
		this.add(midPanel, BorderLayout.CENTER);
		createbidFrame();
	}
	
	public void createbidFrame(){
		bidFrame = new JFrame("Bieten");
		bidFrame.setLayout(new FlowLayout());
		plus = new JButton(">>");
		minus = new JButton("<<");
		send = new JButton("Senden");
		num = new JTextField(2);
		num.setText("2");
		num.setEditable(false);
		bidFrame.add(minus);
		bidFrame.add(num);
		bidFrame.add(plus);
		bidFrame.add(send);

		bidFrame.setPreferredSize(new Dimension(200,120));
		bidFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		bidFrame.setVisible(true);
		bidFrame.setLocationRelativeTo(null);
		bidFrame.pack();
		
		plus.addActionListener(new ButtonListener());
		minus.addActionListener(new ButtonListener());
		send.addActionListener(new ButtonListener());

	}

	public void setGameController(GameController gamec) {
		this.gc = gamec;

	}

	public UserPanel getUser(String name) {
		for (UserPanel up : users) {
			if (up.getUsername().equalsIgnoreCase(name)) {
				return up;
			}
		}
		return null;
	}

	public void initPlayers(ArrayList<String> players) {
		users = new ArrayList<UserPanel>();
		int width = (this.getWidth() - (players.size() * 200)) / players.size();

		for (String s : players) {
			UserPanel p = new UserPanel(s);
			System.out.println(width + "");
			users.add(p);
			usersPanel.add(p);
			usersPanel.add((Box.createRigidArea(new Dimension(width, 10))));
		}
		this.add(usersPanel, BorderLayout.NORTH);
	}

	public void updateCards(ArrayList<Card> newCards) {
		ArrayList<CardPanel> newPanels = new ArrayList<CardPanel>();
		for (Card c : newCards) {
			newPanels.add(new CardPanel(c));
		}
		cards = newPanels;
		drawCards();
	}

	public void drawCards() {
		this.remove(cardsPanel);
		cardsPanel = new JPanel();
		cardsPanel.setLayout(new GridLayout(2, cards.size() / 2));
		for (CardPanel cp : cards) {
			cp.setBorder(new LineBorder(Color.DARK_GRAY, 2));
			cp.addMouseListener(new ClickListener());
			cardsPanel.add(cp);
		}
		this.add(cardsPanel, BorderLayout.SOUTH);
		this.pack();
	}

	
	private class ButtonListener implements ActionListener{
		public void actionPerformed(ActionEvent e){
			int i = Integer.parseInt(num.getText());
			if(e.getSource()==plus){
				if(i<gc.getCurrentRound()){
					i++;
					num.setText(i+"");
				}
			}else if(e.getSource()==minus){
				if(i>0){
					i--;
					num.setText(i+"");

				}
			}else if(e.getSource()==send){
				gc.getConnection().send(new MessageBID(i));
				bidFrame.dispose();
			}
		}
	}
	private class ClickListener extends MouseAdapter {
		public void mouseClicked(MouseEvent e) {
			if (gc.isActive()) {

				CardPanel cp = (CardPanel) e.getSource();
				if (gc.cardPlayable(cp.getCard())) {
					gc.playCard(cp.getCard());
					updateCards(gc.getCards());
				}
			}
		}
	}

	private class CloseListener extends WindowAdapter {
		public void windowClosing(WindowEvent e) {
			gc.getConnection().quit();
			System.exit(0);
		}
	}

	public static void main(String[] args) {
		ArrayList<String> players = new ArrayList<String>();
		players.add("Jonas1");
		players.add("Jonas2");
		players.add("Jonas3");
		players.add("Jonas4");
		// players.add("Jonas5");
		// players.add("Jonas6");
		GameBoard gb = new GameBoard(players);
		Connection c = new Connection();
		GameController gc = new GameController(c);
		gc.setCurrentRound(10);
		gb.setGameController(gc);
		gb.getUser("Jonas4").updateBid(22);
		gb.getUser("Jonas1").updateScore(150);
		ArrayList<Card> cards = new ArrayList<Card>();
		cards.add(new Card(12, CardColor.BLUE));
		cards.add(new Card(5, CardColor.BLUE));
		cards.add(new Card(2, CardColor.BLUE));
		cards.add(new Card(1, CardColor.BLUE));
		// cards.add(new Card(11,CardColor.BLUE));
		// cards.add(new Card(5,CardColor.YELLOW));

		cards.add(new Card(1, CardColor.RED));
		cards.add(new Card(13, CardColor.RED));
		cards.add(new Card(12, CardColor.RED));
		// cards.add(new Card(2,CardColor.RED));
		// cards.add(new Card(5,CardColor.RED));
		//
		cards.add(new Card(5, CardColor.GREEN));
		cards.add(new Card(5, CardColor.GREEN));
		cards.add(new Card(5, CardColor.GREEN));
		// cards.add(new Card(5,CardColor.GREEN));
		// cards.add(new Card(5,CardColor.GREEN));

		cards.add(new Card(5, CardColor.WIZARD));
		cards.add(new Card(5, CardColor.WIZARD));
		cards.add(new Card(5, CardColor.FOOL));
		cards.add(new Card(5, CardColor.FOOL));
		gc.setCards(cards);
		gc.setActive(true);
		gb.updateCards(cards);

	}

}