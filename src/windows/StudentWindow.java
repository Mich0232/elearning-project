package windows;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SpinnerListModel;
import javax.swing.SpinnerModel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import main.DBConnector;
import main.Kolokwium;
import models.User;
import models.Task;

public class StudentWindow {

	private JFrame frame;
	private JTextField daneUzytkownika;
	private JPanel infoPanel;
	private JPanel kartaWyslijZadanie,kartaPodgladOcen,kartaZadania,kartaKontakt;
	private JButton przyciskWylogowania;
	JTabbedPane tabbedPane = new JTabbedPane();
	private JTextArea poleSciezka;
	private JLabel tekstWyslij;
	private JButton przyciskPrzegladaj;
	private JTextArea pole_Temat;
	private JLabel textTemat;
	private JTextArea poleNumerAlbumu;
	private JLabel tekstIDProwadzacy;
	private JLabel tekst_tresc;
	private JTextArea poleTresc;
	private JButton przyciskWyslij;
	private JList listaOcenionych;
	private JList listaZadan;
	private JSpinner answerSpinner;
	private User currentUser;
	private JLabel answerLabel;
	private JButton refreshButton;
	private JButton selectButton;
	
	private DBConnector db;
	private JLabel gradesLabel;
	private JLabel titleLabel;
	private JButton refreshGrades;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				try {
					StudentWindow window = new StudentWindow(new User(0, "Testowy", "User", "Student", "33i", null));
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
	public StudentWindow(User logged) {
		currentUser = logged;
		db = new DBConnector();
		initialize();
		this.frame.setVisible(true);
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setResizable(false);
		frame.setBounds(100, 100, 483, 416);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		
		//----- górny panel, info o u¿ytkowniku
		
		infoPanel = new JPanel();
		infoPanel.setBackground(SystemColor.control);
		infoPanel.setBounds(0, 0, 477, 39);
		infoPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		frame.getContentPane().add(infoPanel);
		infoPanel.setLayout(null);
		
		JLabel Uzytkownik = new JLabel(currentUser.toString());
		Uzytkownik.setBounds(10, 9, 79, 14);
		infoPanel.add(Uzytkownik);
		
		daneUzytkownika = new JTextField();
		daneUzytkownika.setBounds(99, 6, 199, 20);
		infoPanel.add(daneUzytkownika);
		daneUzytkownika.setBackground(SystemColor.control);
		daneUzytkownika.setForeground(SystemColor.control);
		daneUzytkownika.setEditable(false);
		daneUzytkownika.setBorder(null);
		daneUzytkownika.setColumns(10);
		
		przyciskWylogowania = new JButton("Wyloguj si\u0119");
		przyciskWylogowania.setForeground(SystemColor.desktop);
		przyciskWylogowania.setBounds(358, 7, 109, 19);
		infoPanel.add(przyciskWylogowania);
		
		
		
		//------ stworzenie poszczególnych wygl¹dów kazdej zak³adki
        
        kartaWyslijZadanie = new JPanel();
        kartaWyslijZadanie.setBackground(SystemColor.control);
        kartaWyslijZadanie.setBounds(0, 36, 550, 398);
        kartaWyslijZadanie.setLayout(null);
        
        kartaPodgladOcen = new JPanel();
        kartaPodgladOcen.setBackground(SystemColor.control);
        kartaPodgladOcen.setBounds(0, 36, 550, 398);
        kartaPodgladOcen.setLayout(null);
        
        kartaZadania = new JPanel();
        kartaZadania.setBackground(SystemColor.control);
        kartaZadania.setBounds(0, 36, 550, 398);
        kartaZadania.setLayout(null);
        
        kartaKontakt = new JPanel();
        kartaKontakt.setBackground(SystemColor.control);
        kartaKontakt.setBounds(0, 36, 550, 398);
        kartaKontakt.setLayout(null);
        
        
       //--------------- stworzenie zak³adek i dodanie do nich kart
        
        tabbedPane.setBounds(0, 36, 478, 354);
        frame.getContentPane().add(tabbedPane);
        tabbedPane.setBorder(null);
        tabbedPane.setBackground(SystemColor.control);
        tabbedPane.addTab("Wyœlij Zadanie", kartaWyslijZadanie);
        tabbedPane.addTab("SprawdŸ zadania", kartaZadania);
        tabbedPane.addTab("Podgl¹d Ocen", kartaPodgladOcen);
        tabbedPane.addTab("Kontakt z prowadzacym", kartaKontakt);
        
        
        //------------ komponenty do 1 zak³adki
        
        poleSciezka = new JTextArea();
        poleSciezka.setEditable(false);
        poleSciezka.setEnabled(false);
        poleSciezka.setFont(new Font("Times New Roman", Font.PLAIN, 12));
        poleSciezka.setBounds(10, 147, 453, 24);
        poleSciezka.setBorder(new LineBorder(new Color(0, 0, 0)));
        poleSciezka.setColumns(10);
        kartaWyslijZadanie.add(poleSciezka);

        
        tekstWyslij = new JLabel("Wy\u015Blij zadanie w formie pliku");
        tekstWyslij.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        tekstWyslij.setHorizontalAlignment(SwingConstants.CENTER);
        tekstWyslij.setBounds(102, 11, 288, 24);
        kartaWyslijZadanie.add(tekstWyslij);
        
        przyciskPrzegladaj = new JButton("Przegl\u0105daj");
        przyciskPrzegladaj.setBounds(174, 182, 126, 32);
        kartaWyslijZadanie.add(przyciskPrzegladaj);
        
       //---------- komponenty do 2 zak³adki
        
//        String[] answers = {"RED","BLUE","GREEN"};
        List<Task> taskList = db.getTasks(currentUser.group.toString());
        if (taskList == null || taskList.isEmpty()) {
        	taskList = new ArrayList<Task>();
        	taskList.add(new Task("Brak zadan", 999, "00"));
        }
              
        SpinnerListModel model = new SpinnerListModel(taskList);
	
		answerSpinner = new JSpinner(model);
		answerSpinner.setBounds(150, 11, 180, 26);
//		answerSpinner.addChangeListener(new ChangeListener() {
//
//	        @Override
//	        public void stateChanged(ChangeEvent e) {
//	            LOG.info("value changed: " + spinner.getValue());
//	        }
//	    });
		
		
		kartaZadania.add(answerSpinner);
		
		refreshButton = new JButton("Refresh");
		refreshButton.setBounds(50, 11, 100, 26);
		refreshButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
//				answerLabel.setText(((Task)answerSpinner.getValue()).getContent());
				model.setList(db.getTasks(currentUser.group.toString()));
			}
		});
		kartaZadania.add(refreshButton);
		
		selectButton = new JButton("Select");
		selectButton.setBounds(330, 11, 100, 26);
		selectButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				List<Task> taskList = db.getTasks(currentUser.group.toString());
				if(taskList != null && !taskList.isEmpty())
					answerLabel.setText(((Task)answerSpinner.getValue()).getContent());
			}
		});
		kartaZadania.add(selectButton);
		
		answerLabel = new JLabel(((Task)answerSpinner.getValue()).getContent());
		answerLabel.setVerticalAlignment(JLabel.TOP);
		//answerLabel.setMinimumSize()
		answerLabel.setBounds(10, 40, 500, 300);
		kartaZadania.add(answerLabel);
        
        //---------- komponenty do 3 zak³adki
       
       
       
        titleLabel = new JLabel("Twoje oceny:");
        titleLabel.setVerticalAlignment(JLabel.TOP);
        titleLabel.setBounds(10, 10, 500, 300);
		kartaPodgladOcen.add(titleLabel);
		
		gradesLabel = new JLabel("Brak ocen");
        gradesLabel.setVerticalAlignment(JLabel.TOP);
        gradesLabel.setBounds(10, 40, 500, 300);
		kartaPodgladOcen.add(gradesLabel);
		
		refreshGrades = new JButton("Odswiez");
		refreshGrades.setBounds(350, 10, 90, 20);
        kartaPodgladOcen.add(refreshGrades);
        refreshGrades.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				ArrayList<String> results = DBConnector.getGrades(currentUser.UID);
				gradesLabel.setText("<html> ");
				for(int i=0; i<results.size(); i++)
					gradesLabel.setText(gradesLabel.getText()+" <br> "+results.get(i));
				gradesLabel.setText( gradesLabel.getText() + " </html>");
		}
        });
        
        
        //---------- komponenty do 4 zak³adki
        
        pole_Temat = new JTextArea();
        pole_Temat.setBounds(10, 69, 453, 20);
        pole_Temat.setBorder(new LineBorder(new Color(0, 0, 0)));
        pole_Temat.setColumns(10);
        kartaKontakt.add(pole_Temat);
      
        textTemat = new JLabel("Temat:");
        textTemat.setBounds(10, 50, 46, 14);
        kartaKontakt.add(textTemat);
        
        poleNumerAlbumu = new JTextArea();
        poleNumerAlbumu.setBounds(10, 25, 131, 20);
        poleNumerAlbumu.setBorder(new LineBorder(new Color(0, 0, 0)));
        poleNumerAlbumu.setColumns(10);
        kartaKontakt.add(poleNumerAlbumu);
       
        tekstIDProwadzacy = new JLabel("ID_Prowadz\u0105cego");
        tekstIDProwadzacy.setBounds(10, 8, 117, 14);
        kartaKontakt.add(tekstIDProwadzacy);
        
        tekst_tresc = new JLabel("Tre\u015B\u0107:");
        tekst_tresc.setBounds(10, 100, 46, 14);
        kartaKontakt.add(tekst_tresc);
        
        poleTresc = new JTextArea();
        poleTresc.setBorder(new LineBorder(new Color(0, 0, 0)));
        poleTresc.setBounds(10, 117, 453, 160);
        poleTresc.setColumns(10);
        kartaKontakt.add(poleTresc);
       
        
        przyciskWyslij = new JButton("Wy\u015Blij");
        przyciskWyslij.setBounds(178, 288, 112, 27);
        kartaKontakt.add(przyciskWyslij);
        
        
	}
}
