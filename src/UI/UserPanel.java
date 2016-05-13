package UI;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.*;
import javax.swing.border.LineBorder;


public class UserPanel extends JPanel{
	private static final long serialVersionUID = 1L;
	private String username;
	private JLabel usernameLabel, pointsLabel, bidLabel;

	public UserPanel(String name){
		this.username = name;
		this.setPreferredSize(new Dimension(200,200));
		this.setLayout(new BorderLayout());
		this.setBackground(Color.BLACK);
		init();
		this.setInactive();
	}
	
	private void init(){
		usernameLabel = new JLabel(username);
		usernameLabel.setFont(new Font("Arial", Font.BOLD, 30));
		usernameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		usernameLabel.setForeground(Color.WHITE);

		pointsLabel = new JLabel("Punkte: 0");
		pointsLabel.setFont(new Font("Arial", Font.BOLD, 20));
		pointsLabel.setHorizontalAlignment(SwingConstants.CENTER);
		pointsLabel.setForeground(Color.WHITE);

		bidLabel = new JLabel("Gebot: -");
		bidLabel.setFont(new Font("Arial", Font.BOLD, 20));
		bidLabel.setHorizontalAlignment(SwingConstants.CENTER);
		bidLabel.setForeground(Color.WHITE);
		
		this.add(usernameLabel,BorderLayout.NORTH);
		this.add(pointsLabel,BorderLayout.CENTER);
		this.add(bidLabel,BorderLayout.SOUTH);
	}
	
	public void setActive(){
		this.setBorder(new LineBorder(Color.ORANGE, 3, false));
	}
	
	public void setInactive(){
		this.setBorder(new LineBorder(Color.BLACK, 3, false));
	}
	
	public void setBidding(){
		this.setBorder(new LineBorder(Color.GREEN, 3, false));
	}
	
	public void updateScore(int score){
		pointsLabel.setText("Punkte: " + score);
	}
	
	public void updateBid(int bid){
		bidLabel.setText("Gebot: " + bid);
	}
	
	public String getUsername(){
		return username;
	}
	
	public static void main(String[] args) {
		try {
		JFrame frame = new JFrame();
		UserPanel panel = new UserPanel("Jonas");
		UserPanel panel2 = new UserPanel("Philipp");
		UserPanel panel3 = new UserPanel("Sabine");
		frame.setLayout(new FlowLayout());
		frame.add(panel);
		frame.add(panel2);
		frame.add(panel3);
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.pack();
		
		Thread.sleep(1000);
		panel.setBidding();
		
		Thread.sleep(1000);
		panel.updateBid(4);
		panel.setInactive();
		panel2.setBidding();
		
		Thread.sleep(1000);
		panel2.updateBid(6);
		panel2.setInactive();
		panel3.setBidding();
		
		Thread.sleep(1000);
		panel3.updateBid(2);
		panel3.setInactive();
		panel.setActive();
		
		Thread.sleep(1000);
		panel.setActive();
		panel2.updateScore(150);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
}
