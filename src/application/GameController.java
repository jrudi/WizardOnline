package application;

import java.util.*;

import UI.GameBoard;
import domain.*;
import connect.Connection;
import messages.*;

public class GameController {

	private ArrayList<Card> cards;
	private int currentRound;
	private Connection conn;
	private CardColor trump;
	private GameBoard gb;
	private boolean active, bidding;

	public GameController(Connection c) {
		this.conn = c;
		trump = CardColor.BLUE;
	}
	
	public int getCurrentRound() {
		return currentRound;
	}

	public void setCurrentRound(int currentRound) {
		this.currentRound = currentRound;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public boolean isBidding() {
		return bidding;
	}

	public ArrayList<Card> getCards() {
		return cards;
	}

	public void setCards(ArrayList<Card> cards) {
		this.cards = cards;
	}

	public void setBidding(boolean bidding) {
		this.bidding = bidding;
	}

	public Connection getConnection(){
		return this.conn;
	}
	
	public void placeBid(int bid){
		conn.send(new MessageBID(bid));
	}
	
	public void playCard(Card c) {
		conn.send(new MessageCARDPLAYED(c));
		cards.remove(c);
		this.setActive(false);
	}

	public boolean cardPlayable(Card c) {
		if (c.getColor().equals(CardColor.FOOL) || c.getColor().equals(CardColor.WIZARD)) {
			return true;
		} else if (hasTrump()) {
			return c.getColor().equals(trump);
		} else {
			return true;
		}
	}

	private boolean hasTrump() {
		for (Card c : cards) {
			if (c.getColor().equals(trump)) {
				return true;
			}
		}
		return false;
	}
	
	public void initializeGame(ArrayList<String> players){
		gb = new GameBoard(players);
	}
}
