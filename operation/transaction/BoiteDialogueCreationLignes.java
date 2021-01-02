package gestion.operation.transaction;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import gestion.compta.Compte;
import gestion.compta.Placement;
import gestion.compta.Transaction;
import gestion.data.DataCenter;
import gestion.util.ZModel;


/**
 * 
 * @author Trivy
 * Classe pour la création de nouvelles lignes (sur répartition prédéfinie).
 * 
 * Les nouvelles lignes à créer sont réparties sur plusieurs placements différents.
 * 
 */

public class BoiteDialogueCreationLignes extends JDialog{
	
	private DataCenter dataCenter = DataCenter.getInstance();
	private TableauTransaction tabTrans;
	private Placement[] listePlacement = {
			// corresponds to Carmignac Emergents
			dataCenter.getPlacementDAO().find(4),
			
			// corresponds to LYXOR ETF DJ INDUSTRIAL AVERAGE 
			dataCenter.getPlacementDAO().find(32),
			
			// corresponds to LYXOR INDEX FUND EURO
			dataCenter.getPlacementDAO().find(27),
			
			// corresponds to LYXOR JAPAN TOPIX ETF DLY HDG D 
			dataCenter.getPlacementDAO().find(33),
			
			// corresponds to LYXOR UCITS ETF EUROMTS 3-5Y IG
			dataCenter.getPlacementDAO().find(37)
		};
	// The predefined transaction only involves "AV - Epargnissimo"...
	private Compte currentCompte = dataCenter.getCompteDAO().find(1);
	
	InputRow[] activeRows;
	JFormattedTextField jtfDate;
	
	public BoiteDialogueCreationLignes(final TableauTransaction tabTrans){
		super();
		this.tabTrans = tabTransIni;
		this.setTitle("Données transaction prédéfinie");
		this.setModal(false);
		this.setSize(600,250);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
		
		JPanel panChoix = new JPanel();
		JPanel panControl = new JPanel();
		
		// date panel (default = now)
		JLabel dateLabel = new JLabel("Date ? (format AAAA-mm-JJ)", SwingConstants.RIGHT);
		jtfDate = new JFormattedTextField(new SimpleDateFormat("yyyy-MM-dd"));
		Date now = new Date(System.currentTimeMillis());
		jtfDate.setText(now.toString());
		JPanel datePanel = new JPanel();
		datePanel.setLayout(new GridLayout(1, 2));
		datePanel.add(dateLabel);
		datePanel.add(jtfDate);
		
		// === Label panel (has to be compatible with formatting in InputRow)
		// Placement's name
		JLabel placementLabel = new JLabel("Placement", SwingConstants.CENTER);
		
		// Compte's name
		JLabel compteLabel = new JLabel("Compte", SwingConstants.CENTER);
		
		// entrée nbr UC
		// + formatage des nombres à 4 décimales...
		JLabel totUCLabel = new JLabel("Nbr tot. UC", SwingConstants.CENTER);
		
		// entrée cours 
		JLabel coursLabel = new JLabel("Cours", SwingConstants.CENTER);
		
		// Formatting label Panel
		JLabel labelPanel = new JLabel();
		labelPanel.setLayout(new GridLayout(1, 4));
		labelPanel.add(placementLabel);
		labelPanel.add(compteLabel);
		labelPanel.add(totUCLabel);
		labelPanel.add(coursLabel);
		
		// Create list of InputRows:
		int nbActiveRows = listePlacement.length;
		activeRows = new InputRow[nbActiveRows];
		for (int i = 0; i < nbActiveRows ; i++) {
			Placement place = listePlacement[i];
			InputRow inputRow = new InputRow(place, currentCompte);
			activeRows[i] = inputRow;
		}
		
		// Final layout
		panChoix.setLayout(new GridLayout(nbActiveRows + 2, 1));
		panChoix.add(datePanel);
		panChoix.add(labelPanel);
		for (InputRow inputRow: activeRows) {
			panChoix.add(inputRow);
		}
		
	    JButton cancelBouton = new JButton("Annuler");
	    cancelBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	        setVisible(false);
	      }      
	    });
	    
	    JButton okBouton = new JButton("OK");
	    okBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0){
	    	  // Create suitable transactions and include them in 
		      // NB: to update the table accordingly, just use method updateTableau.
	    	  
	    	  // For each inputRow...
	    	  for (InputRow inputRow: activeRows) {
	    		  	// 1 - create a suitable transaction...
	    		  
	    		  	Placement place = inputRow.getPlacement();
	    		  	float cours = inputRow.getCours();
	    		  	float nbrUC = inputRow.getNbrUC();

					// ajoute la première ligne au tableau (attention ! Pas initialisée !)
					Transaction trans = new Transaction(
							ZModel.convertStringToDate(jtfDate.getText()),
							place,
							dataCenter.getCompteDAO().find(1),
							cours,
							nbrUC, 
							new Float(0), 
							nbrUC*cours,
							new Float(0)
					);
					
					// 2 - include this transaction in the table
					dataCenter.getTransacDAO().create(trans);
	    	 }
	    	 // after including the new data, update tabTrans     	
	    	 tabTrans.updateTableau();
	    	  
	         // termine le dialogue en rendant la boite invisible
	    	 setVisible(false);
	      }
	    });
	    
	    panControl.add(cancelBouton);
	    panControl.add(okBouton);
	    
	    this.getContentPane().add(panChoix, BorderLayout.CENTER);
	    this.getContentPane().add(panControl, BorderLayout.SOUTH);
	    
	}

}
