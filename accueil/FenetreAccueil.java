package gestion.accueil;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gestion.compta.Placement;
import gestion.data.DataCenter;
import gestion.util.FenetreCommun;

public class FenetreAccueil extends FenetreCommun {
	protected JTable tableauRepartition;
	protected JLabel achats;
	protected JLabel ventes;
	private DataCenter dataSql;
	
	public FenetreAccueil(){
		super();

		dataSql = DataCenter.getInstance();
    
		// Chercher les données pour l'accueil :
		Object[][] data = dataSql.accueilData();
		
	    //Les titres des colonnes
	    final String  title[] = {"Type", "Euros", "%"};
	    
	    ZModel2 model = new ZModel2(data, title);
	    this.tableauRepartition = new JTable(model);
	    this.tableauRepartition.setAutoCreateColumnsFromModel(false); // pour ne pas perdre le formatage en cas de setDataVector... (voir http://www.chka.de/swing/table/faq.html)
	    this.tableauRepartition.setRowHeight(30);
	    
	    this.tableauRepartition.getColumn("Euros").setCellRenderer(new NumberRenderer());
	    this.tableauRepartition.getColumn("%").setCellRenderer(new PercentageRenderer());
	    
	    // Panneau des ordres :
	    JLabel texteAchats = new JLabel("Achats à  exéc. :");
	    achats = new JLabel(String.valueOf(DataCenter.getNbrAchatsAExec()));
	    JLabel texteVentes = new JLabel("Ventes à  exéc. :");
	    ventes = new JLabel(String.valueOf(DataCenter.getNbrVentesAExec()));
	    GridLayout gl2 = new GridLayout(1,4);
	    gl2.setHgap(5);
	    JPanel panOrdres = new JPanel(gl2);
	    panOrdres.add(texteAchats);
	    panOrdres.add(achats);
	    panOrdres.add(texteVentes);
	    panOrdres.add(ventes);	
	    
	    // Bouton Mà J et son listener
		JButton maJ = new JButton("Mà J");
	    class MaJListener implements ActionListener{
			
			public void actionPerformed(ActionEvent event){
				// mise à  jour du panneau synthà¨se (sur des données à  jour)
				((ZModel2) tableauRepartition.getModel()).setDataVector(dataSql.accueilData(),title);
				
				// mise à  jour des ordres d'achats et de ventes
				updateFenetre();
			}
		}
	    maJ.addActionListener(new MaJListener());
	    
	    // Bouton "Dernier cours" et son listener
	    JButton derniersCours = new JButton("Derniers cours (en ligne)");
	    class DCoursListener implements ActionListener{
			
			public void actionPerformed(ActionEvent event){
				QuotationUpdate qu = new QuotationUpdate();
				
//				// recherche des derniers cours, pour tous les placements
//				LinkedList<Placement> data = dataSql.getPlacementDAO().getAll();
//				for (Placement place: data){
//					dataSql.dernierCoursMaJ(place);
//				}
				
				System.out.println("DCoursListener : mise à  jour des cours terminée !");
				
				// Mà J des ordres
				updateFenetre();		
			}
		}
	    derniersCours.addActionListener(new DCoursListener());
	    
	    // Panneau pour contenir les boutons :
	    GridLayout gl1 = new GridLayout(1,2);
	    gl1.setHgap(5);
	    JPanel panBouton = new JPanel(gl1);
	    panBouton.add(derniersCours);
	    panBouton.add(maJ);    
		
	    // Panneau bas
	    JPanel panBas = new JPanel(new GridLayout(2,1));
	    panBas.add(panOrdres);
	    panBas.add(panBouton);

	    
		// Mise en place finale
	    JPanel pan = new JPanel();
	    pan.setPreferredSize(new Dimension(350,100));
	    pan.add(new JScrollPane(tableauRepartition));
	    
		BorderLayout bl = new BorderLayout(); 
		this.setLayout(bl);

		this.add(new JLabel("Répartition des sommes :"), BorderLayout.NORTH);
		this.add(pan, BorderLayout.CENTER);
		this.add(panBas,BorderLayout.SOUTH);
		this.setVisible(true);
	}
	
	@Override
	public void updateFenetre(){
		DataCenter dataCenter = DataCenter.getInstance();
		// Mise à  jour du nombre d'ordres à  effectuer
		dataCenter.achatsAExecuter();
		dataCenter.ventesAExecuter();
		this.achats.setText(String.valueOf(DataCenter.getNbrAchatsAExec()));
		this.ventes.setText(String.valueOf(DataCenter.getNbrVentesAExec()));
	}
}
