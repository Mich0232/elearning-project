package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import javax.swing.SwingConstants;

import main.DBConnector;

import javax.swing.JTextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPasswordField;
import javax.swing.JButton;
import main.UserClient;

import models.User;

public class LoginWindow {

	private JFrame frame;
	private JTextField loginField;
	private JPasswordField passwordField;
	private JButton btnZarejestrujSi;
	private static UserClient client;
	private static User currentUser;


	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					LoginWindow window = new LoginWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public LoginWindow() {
		try {
			
			initialize();
			this.frame.setVisible(true);
			client = new UserClient();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	class LoginButton extends JButton implements ActionListener {

		LoginButton(String title) {
    		super(title);
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			LoginWindow.currentUser = DBConnector.checkLogin(loginField.getText(), passwordField.getText());

			if(currentUser == null)
			{
				System.out.println("Brak danych uzytkownika");
				return;
			}
			
			
			if(currentUser.accountType.equals("Teacher"))
			{
				new TeacherWindow(currentUser);
				frame.dispose();
			}
			else if(currentUser.accountType.equals("Student"))
			{
				new StudentWindow(currentUser);
				frame.dispose();
			}
			else
			{
				JOptionPane.showMessageDialog(frame, "Wrong login or password.\nTry again!","Wrong input data", JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	class SignUpButton extends JButton implements ActionListener {

		SignUpButton(String title) {
    		super(title);
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			
			new SignUpWindow();
			frame.dispose();
		}
	}
	
	public static UserClient getClient() {
		return client;
	}

	public static void setClient(UserClient client) {
		LoginWindow.client = client;
	}

	
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSignInTo = new JLabel("Zaloguj się do konta:");
		lblSignInTo.setBounds(0, 21, 415, 16);
		lblSignInTo.setHorizontalAlignment(SwingConstants.CENTER);
		frame.getContentPane().add(lblSignInTo);
		
		loginField = new JTextField();
		loginField.setBounds(159, 70, 100, 26);
		loginField.setToolTipText("Login");
		frame.getContentPane().add(loginField);
		loginField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(159, 130, 100, 26);
		passwordField.setColumns(10);
		passwordField.setToolTipText("Password");
		frame.getContentPane().add(passwordField);
		
		LoginButton btnLogIn = new LoginButton("Zaloguj");
		btnLogIn.setBounds(101, 195, 105, 29);
		frame.getContentPane().add(btnLogIn);
		
		btnZarejestrujSi = new SignUpButton("Zarejestruj się");
		btnZarejestrujSi.setBounds(204, 195, 133, 29);
		frame.getContentPane().add(btnZarejestrujSi);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(72, 75, 61, 16);
		frame.getContentPane().add(lblLogin);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(72, 135, 75, 16);
		frame.getContentPane().add(lblPassword);
		
		DBConnector dbConnector = new DBConnector();
	}
}
