package UI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

import connect.Connection;
import messages.*;

public class Login extends JFrame{
	private static final long serialVersionUID = 1L;
	private Connection connect;
	private JButton loginBtn, cancelBtn;//,registerBtn;
	private JTextField nameField;
	//private JPasswordField passwordField;
	
	public Login(Connection c){
		this.connect = c;
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		createComponents();
		addListeners();
		this.pack();
		this.setVisible(true);
		
		//TODO WindowListener

	 	
	}
	
	public  void createComponents(){
		
		this.setLayout(new BorderLayout());
		
		loginBtn = new JButton("Login");
	 	cancelBtn = new JButton("Abbrechen");
	 	
	 	JPanel btnPanel = new JPanel();
	 	btnPanel.setLayout(new GridLayout(3,1));
	 	btnPanel.add(loginBtn);
	 //	registerBtn = new JButton("Registrieren");
	 //	btnPanel.add(registerBtn);
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
		//registerBtn.addActionListener(new ButtonListener());
		cancelBtn.addActionListener(new ButtonListener());
	}
	
	private class ButtonListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			switch (((JButton)e.getSource()).getText()){
			case "Login":
				String name = nameField.getText();
				connect.send(new MessageLOGIN(name));
				Message m = connect.receive();
				if(m instanceof MessageLOGIN_ERROR){
					JOptionPane.showMessageDialog(Login.this, ((MessageLOGIN_ERROR)m).getError());
					nameField.setText("");
				}else{
					connect.setUsername(name);
					connect.start();
					Lobby lobby = new Lobby(connect);
					lobby.setVisible(true);
					dispose();
				}
				break;
			case "Registrieren":
				break;
			case "Abbrechen":
				connect.quit();
				break;
			}
		}
		
	}
	
}
