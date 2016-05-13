package application;

import UI.Login;
import connect.Connection;

public class Main {
	
	public static void main(String[] args) {
		Connection c = new Connection();
		Login l = new Login(c);
		l.setVisible(true);
	}
}
