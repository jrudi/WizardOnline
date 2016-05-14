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
	private CardColor trump,follow;
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
	
	public CardColor getFollow() {
		return follow;
	}

	public void setFollow(CardColor follow) {
		this.follow = follow;
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
		conn.send(new MessageBID(bid, conn.getUsername()));
	}
	
	public void playCard(Card c) {
		conn.send(new MessageCARDPLAYED(c, conn.getUsername()));
		cards.remove(c);
		this.setActive(false);
	}

	public boolean cardPlayable(Card c) {
		if (c.getColor().equals(CardColor.FOOL) || c.getColor().equals(CardColor.WIZARD)) {
			return true;
		} else if (hasFollow()) {
			return c.getColor().equals(follow);
		} else {
			return true;
		}
	}

	private boolean hasFollow() {
		for (Card c : cards) {
			if (c.getColor().equals(follow)) {
				return true;
			}
		}
		return false;
	}
	
	public GameBoard getGameBoard(){
		return this.gb;
	}
	
	public void initializeGame(ArrayList<String> players){
		gb = new GameBoard(players);
	}
	
	public void roundEnd(MessageROUNDEND mr){
		this.setFollow(null);
	}

	public void setTrump(CardColor trump2) {
		this.trump = trump2;
	}

}
