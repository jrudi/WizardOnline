package UI;

import java.awt.*;

import javax.swing.*;

import domain.*;

public class CardPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private Color color;
	private String value;
	private JLabel text;
	private Card c;
	
	public CardPanel(Card card){
		this.c = card;
		init(card);
		this.setPreferredSize(new Dimension(100,100));
		this.setLayout(new BorderLayout());
		text = new JLabel(value);
		text.setHorizontalAlignment(SwingConstants.CENTER);
		text.setFont(new Font("Arial", Font.BOLD, 50));
		this.add(text, BorderLayout.CENTER);
		
	}
	
	public void init(Card card){
		switch (card.getColor()){
		case RED:
			color = Color.RED;
			value = ""+card.getValue();
			break;
		case BLUE:
			color = Color.BLUE;
			value = ""+card.getValue();
			break;
		case GREEN:
			color = Color.GREEN;
			value = ""+card.getValue();
			break;
		case YELLOW:
			color = Color.YELLOW;
			value = ""+card.getValue();
			break;
		case WIZARD:
			color = Color.MAGENTA;
			value = "Z";
			break;
		case FOOL:
			color = Color.ORANGE;
			value = "N";
			break;
		
		}
		this.setBackground(color);

	}
	
	public Card getCard(){
		return this.c;
	}

public static void main(String[] args) {
	JFrame frame = new JFrame();
	CardPanel panel = new CardPanel(new Card(10,CardColor.RED));
	CardPanel panel2 = new CardPanel(new Card(14,CardColor.WIZARD));
	CardPanel panel3 = new CardPanel(new Card(0,CardColor.FOOL));
	frame.setLayout(new FlowLayout());
	frame.add(panel);
	frame.add(panel2);
	frame.add(panel3);
	frame.setVisible(true);
	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	frame.pack();
}
}
