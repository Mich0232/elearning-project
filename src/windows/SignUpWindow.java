package windows;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.SwingConstants;

import main.DBConnector;

public class SignUpWindow {

	private JFrame frame;
	private JTextField loginField;
	private JPasswordField passwordField;
	private JPasswordField repeatPasswordField;
	private JLabel lblRepeatPassword;
	private JComboBox comboBox;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					SignUpWindow window = new SignUpWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SignUpWindow() {
		initialize();
		this.frame.setVisible(true);
	}
	
	
	class SignUpButton extends JButton implements ActionListener {

		SignUpButton(String title) {
    		super(title);
			addActionListener(this);
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if(passwordField.getText().equals(repeatPasswordField.getText()))
			{
				if(DBConnector.addUser(loginField.getText(), passwordField.getText(), comboBox.getSelectedItem().toString()))
				{
					JOptionPane.showMessageDialog(frame, "Account has been created.\nYou can now log in!","Success", JOptionPane.INFORMATION_MESSAGE);
					new LoginWindow();
					frame.dispose();
				}
				else
				{
					JOptionPane.showMessageDialog(frame, "There was error while making new account","Error", JOptionPane.ERROR_MESSAGE);
				}
			}
			else
			{
				JOptionPane.showMessageDialog(frame, "Passwords aren't the same","Error", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSignUp = new JLabel("Sign up");
		lblSignUp.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUp.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblSignUp.setBounds(164, 16, 80, 25);
		frame.getContentPane().add(lblSignUp);
		
		loginField = new JTextField();
		loginField.setBounds(198, 53, 130, 26);
		frame.getContentPane().add(loginField);
		loginField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(198, 91, 130, 26);
		frame.getContentPane().add(passwordField);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(89, 58, 61, 16);
		frame.getContentPane().add(lblLogin);
		
		JLabel lblPassword = new JLabel("Password:");
		lblPassword.setBounds(89, 96, 77, 16);
		frame.getContentPane().add(lblPassword);
		
		repeatPasswordField = new JPasswordField();
		repeatPasswordField.setBounds(198, 129, 130, 26);
		frame.getContentPane().add(repeatPasswordField);
		
		lblRepeatPassword = new JLabel("Repeat password:");
		lblRepeatPassword.setBounds(89, 134, 110, 16);
		frame.getContentPane().add(lblRepeatPassword);
		
		JButton btnSignUp = new SignUpButton("Sign up");
		btnSignUp.setBounds(153, 222, 102, 29);
		frame.getContentPane().add(btnSignUp);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Student", "Teacher"}));
		comboBox.setBounds(198, 167, 130, 27);
		frame.getContentPane().add(comboBox);
		
		JLabel lblType = new JLabel("Type:");
		lblType.setBounds(89, 171, 61, 16);
		frame.getContentPane().add(lblType);
	}
}
