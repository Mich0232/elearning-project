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
	private JTextField nameField;
	private JTextField surnameField;
	private JTextField groupText;

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
				if(DBConnector.addUser(loginField.getText(), passwordField.getText(), nameField.getText(), surnameField.getText(), groupText.getText(), comboBox.getSelectedItem().toString()))
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
		frame.setBounds(100, 100, 450, 404);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblSignUp = new JLabel("Zarejestruj sie");
		lblSignUp.setHorizontalAlignment(SwingConstants.CENTER);
		lblSignUp.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		lblSignUp.setBounds(150, 16, 135, 25);
		frame.getContentPane().add(lblSignUp);
		
		loginField = new JTextField();
		loginField.setBounds(198, 79, 130, 26);
		frame.getContentPane().add(loginField);
		loginField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(198, 117, 130, 26);
		frame.getContentPane().add(passwordField);
		
		JLabel lblLogin = new JLabel("Login:");
		lblLogin.setBounds(89, 84, 61, 16);
		frame.getContentPane().add(lblLogin);
		
		JLabel lblPassword = new JLabel("Haslo:");
		lblPassword.setBounds(89, 122, 77, 16);
		frame.getContentPane().add(lblPassword);
		
		repeatPasswordField = new JPasswordField();
		repeatPasswordField.setBounds(198, 155, 130, 26);
		frame.getContentPane().add(repeatPasswordField);
		
		lblRepeatPassword = new JLabel("Powtorz haslo:");
		lblRepeatPassword.setBounds(89, 160, 110, 16);
		frame.getContentPane().add(lblRepeatPassword);
		
		JButton btnSignUp = new SignUpButton("Sign up");
		btnSignUp.setBounds(153, 330, 102, 29);
		frame.getContentPane().add(btnSignUp);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"Student", "Teacher"}));
		comboBox.setBounds(198, 45, 130, 27);
		frame.getContentPane().add(comboBox);
		
		
		JLabel lblType = new JLabel("Typ konta:");
		lblType.setBounds(89, 49, 77, 16);
		frame.getContentPane().add(lblType);
		
		nameField = new JTextField();
		nameField.setBounds(198, 193, 130, 26);
		frame.getContentPane().add(nameField);
		nameField.setColumns(10);
		
		surnameField = new JTextField();
		surnameField.setBounds(198, 228, 130, 26);
		frame.getContentPane().add(surnameField);
		surnameField.setColumns(10);
		
		JLabel lblImie = new JLabel("Imie:");
		lblImie.setBounds(89, 198, 61, 16);
		frame.getContentPane().add(lblImie);
		
		JLabel lblNazwisko = new JLabel("Nazwisko:");
		lblNazwisko.setBounds(89, 233, 77, 16);
		frame.getContentPane().add(lblNazwisko);
		
		groupText = new JTextField();
		groupText.setBounds(198, 266, 130, 26);
		frame.getContentPane().add(groupText);
		groupText.setColumns(10);
		
		JLabel groupLabel = new JLabel("Grupa:");
		groupLabel.setBounds(89, 271, 61, 16);
		frame.getContentPane().add(groupLabel);
		
		
		comboBox.addActionListener (new ActionListener () {
		    public void actionPerformed(ActionEvent e) {
		        if(comboBox.getSelectedItem().equals("Student"))
		        {
		        	groupLabel.setText("Group:");
		        }
		        else
		        {
		        	groupLabel.setText("Subject:");
		        }
		    }
		});
	}
}
