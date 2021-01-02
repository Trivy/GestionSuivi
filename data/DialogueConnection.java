package gestion.data;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;


/**
 * 
 * @author Trivy
 * Classe pour paramétrer la connexion. 
 * Choix parmi prédéfinis ; 
 * _ String host (localhost)
 * _ String dataBase (postgres, JIBApp) 
 * Récupère :
 * _ String user
 * _ String passwd
 */

public class DialogueConnection extends JDialog{
	private String user, passwd, host, dataBase;
	private Boolean sendData;
	private JTextField userJtf;
	private JPasswordField passwdJtf;
	private JComboBox<String> hostCombo;
	private JComboBox<String> dbCombo;
	
	public DialogueConnection(){
		super();
		this.setTitle("Paramétrage de la connexion");
		this.setModal(true);
		this.setSize(400,250);
		this.setLocationRelativeTo(null);
		
		JPanel panChoix = new JPanel();
		JPanel panControl = new JPanel();
		
		// Choix de l'hôte :
		JLabel hostLabel = new JLabel("Choix de l'hôte :");
		hostCombo = new JComboBox<String>(new String[]{"localhost:5432"});
		
		// Choix de la base de données :
		JLabel dbLabel = new JLabel("Choix de la base :");
		dbCombo = new JComboBox<String>(new String[]{"postgres", "jibapp"});
		
		// user :
		JLabel userLabel = new JLabel("Utilisateur :");
		userJtf = new JTextField("postgres");
		
		// entrée mot de passe 
		JLabel passwdLabel = new JLabel("Mot de passe :");
		passwdJtf = new JPasswordField();
		
		panChoix.setLayout(new GridLayout(4,2));
		panChoix.add(hostLabel);
		panChoix.add(hostCombo);
		panChoix.add(dbLabel);
		panChoix.add(dbCombo);
		panChoix.add(userLabel);
		panChoix.add(userJtf);
		panChoix.add(passwdLabel);
		panChoix.add(passwdJtf);
		
	    JButton cancelBouton = new JButton("Annuler");
	    cancelBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	    	// termine le dialogue en arrêtant le programme
	    	System.exit(ABORT);
	      }      
	    });
	    
	    JButton okBouton = new JButton("OK");
	    okBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0){
	    	  // récupération des infos :
	    	  user = userJtf.getText();
	    	  passwd = passwdJtf.getText();
	    	  host = (String)hostCombo.getSelectedItem();
	    	  dataBase = (String)dbCombo.getSelectedItem();
	    		    	
	    	  // termine le dialogue en rendant la boite invisible
	    	  setVisible(false);
	      }
	    });
	    
	    panControl.add(cancelBouton);
	    panControl.add(okBouton);
	    
	    this.getContentPane().add(panChoix, BorderLayout.CENTER);
	    this.getContentPane().add(panControl, BorderLayout.SOUTH);
	}
	
	public String[] showDialogueConnection(){
		this.sendData = false;
		this.setVisible(true);
		String[] infoConn = new String[] {this.user, this.passwd, this.host, this.dataBase};
		return infoConn;
	}

}
