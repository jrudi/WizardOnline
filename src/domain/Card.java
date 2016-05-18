package domain;

import java.io.Serializable;

public class Card implements Serializable, Comparable<Card>{
	
	private static final long serialVersionUID = 1L;
	private int value;
	private CardColor color;

	public Card(int v, CardColor cc){
		this.value=v;
		this.color = cc;
	}
	
	public int getValue(){
		return this.value;
	}
	
	public CardColor getColor(){
		return this.color;
	}

	@Override
	public int compareTo(Card o) {
		if(this.value==o.value){
			return 0;
		}else if(this.color==CardColor.WIZARD){
			return 1;
		}else if(this.color==CardColor.FOOL){
			return -1;
		}else return this.value<o.value? -1 : 1;
	}

}
