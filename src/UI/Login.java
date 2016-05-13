package UI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class Login extends JFrame{
	private static final long serialVersionUID = 1L;
	
	private JButton loginBtn, registerBtn, cancelBtn;
	private JTextField nameField;
	//private JPasswordField passwordField;
	
	public Login(){
		
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
//TODO WindowListener
		createComponents();
		addListeners();
		this.pack();
		this.setVisible(true);
		
		
	 	
	}
	
	public  void createComponents(){
		
		this.setLayout(new BorderLayout());
		
		loginBtn = new JButton("Login");
		registerBtn = new JButton("Registrieren");
	 	cancelBtn = new JButton("Abbrechen");
	 	ButtonGroup bg = new ButtonGroup();
	 	
	 	JPanel btnPanel = new JPanel();
	 	btnPanel.setLayout(new GridLayout(3,1));
	 	btnPanel.add(loginBtn);
	 	btnPanel.add(registerBtn);
	 	btnPanel.add(cancelBtn);
	 	
	 	nameField = new JTextField(25);
	 	
	 	JPanel textPanel = new JPanel();
	 	textPanel.setLayout(new GridLayout (2,1));
	 	JLabel name = new JLabel("Name: ");
	 	name.setHorizontalAlignment(SwingConstants.CENTER);
	 	textPanel.add(name);
	 	textPanel.add(nameField);
	 	
	 	getContentPane().add(textPanel, BorderLayout.CENTER);
	 	getContentPane().add(btnPanel, BorderLayout.SOUTH);

	}
	
	public void addListeners(){
		loginBtn.addActionListener(new ButtonListener());
		registerBtn.addActionListener(new ButtonListener());
		cancelBtn.addActionListener(new ButtonListener());
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (((JButton)e.getSource()).getText()){
			case "Login":
				break;
			case "Registrieren":
				break;
			case "Abbrechen":
				break;
			}
		}
		
	}
	
	public static void main(String[] args) {
		Login l = new Login();
	}
}
