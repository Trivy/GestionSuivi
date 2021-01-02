package gestion;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;

import gestion.accueil.FenetreAccueil;
import gestion.aexecuter.FenetreAExecuter;
import gestion.compta.Compte;
import gestion.data.DataCenter;
import gestion.edition.FenetreEdition;
import gestion.operation.FenetreOperation;
import gestion.sqliteImport.SqliteImport;
import gestion.util.FenetreCommun;


public class GestionSuivi {
	//private JFrame fg;
	private JPanel content;
	private FenetreCommun[] listFenetre;
	private JPanel panCompte;
	private JComboBox<String> comboG;
	private CardLayout cl;
	
	private String[] listContent;
	
	private Color secondColor = Color.lightGray;
	
	FenetreOperation fenetreOperation;
	
	
	public GestionSuivi(){
		JFrame fg = new JFrame();
		content = new JPanel();
		fg.setTitle("JIBApp");
		fg.setSize(700, 600);
		fg.setLocationRelativeTo(null);
		fg.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		cl = new CardLayout();
		content.setLayout(cl);
		
		DataCenter dataCenter = DataCenter.getInstance();
		
		// ===============================
		// Create the menu bar
		// ===============================
		JMenuBar menuBar = new JMenuBar();
		JMenu filesMenu = new JMenu("Menu");
		JMenuItem item1 = new JMenuItem("Importer...");
		JMenuItem item2 = new JMenuItem("About: JIBApp © 2017 by O. Gabriel.");
		menuBar.add(filesMenu);
		filesMenu.add(item1);
		filesMenu.add(item2);

		item1.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent arg0){
				System.out.println("GestionBilicence: 'Import' of MenuBar activated!");
				SqliteImport sqliteImport = new SqliteImport();
				sqliteImport.importSqlite();
			}
		});
		
		// Initialisation de fenetreOperation
		fenetreOperation = new FenetreOperation();
		
		// Création des différentes fenêtres et ajout dans "listFenetre" et "content"
		listFenetre = new FenetreCommun[4];
		listFenetre[0] = new FenetreAccueil();
		listFenetre[1] = fenetreOperation;
		listFenetre[2] = new FenetreAExecuter();
		listFenetre[3] = new FenetreEdition();
				
		listContent=new String[] {"Accueil", "Opérations et cours", "À exécuter", "Edition"};
		
		int j =0;
		for (FenetreCommun fenetre : listFenetre){
			content.add(fenetre, listContent[j]);
			j++;
		}
		
		// ===============================
		// Choix de la page à afficher (haut de la fenêtre)
		// ===============================
		JLabel label = new JLabel("Sélection de la page : ");
		comboG = new JComboBox<String>(listContent);
		comboG.setPreferredSize(new Dimension(200,20));
		// Gestion de la sélection de la page
		// Page sélectionnée => MàJ et affichage.
		comboG.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				listFenetre[comboG.getSelectedIndex()].updateFenetre();
				cl.show(content, listContent[comboG.getSelectedIndex()]);
			}
		});
		
		JPanel panHaut = new JPanel();
		panHaut.add(label);
		panHaut.add(comboG);
		panHaut.setBackground(secondColor);
		
		// ===============================
		// panCompte
		// Choix des comptes à considérer (côté gauche de la fenêtre)
		// ===============================
		LinkedList<Compte> listCompte = dataCenter.getCompteDAO().getData();
		//int n = listCompte.size();
		// Tous les comptes du dataCenter sont sélectionnés :
		for (Compte compte : listCompte){
			dataCenter.addComptesCourants(compte);
		}
		
		panCompte = new JPanel();
		panCompte.setBackground(secondColor);
		updatePanCompte();
		
		// ================================
		// panGauche définissant le panneau de gauche
		// dont bouton "Rafraichir" et panCompte
		// ================================
		
		// ActionListener pour le bouton de rafraichissement global
		class BoutonSvgListener implements ActionListener{
			public void actionPerformed(ActionEvent e) {
				// Update panCompte
				updatePanCompte();
				// Update FenetreOperation
				listFenetre[1].updateFenetre();	
			}
		}
		
		JPanel panGauche = new JPanel();
		panGauche.setBackground(secondColor);
		panGauche.setLayout(new BoxLayout(panGauche, BoxLayout.PAGE_AXIS));
		JButton buttonRaf = new JButton("Rafraichir");
		buttonRaf.setAlignmentX(Component.CENTER_ALIGNMENT);
		buttonRaf.addActionListener(new BoutonSvgListener());
		panGauche.add(buttonRaf);
		panGauche.add(panCompte);
		
		/*
		 * Initialisation finale des éléments
		*/
		
		try{
			// si possible, on sélectionne élément
			fenetreOperation.getComboPlacement().setSelectedIndex(0);
		} catch (Exception e){
			// sinon, on ne fait rien
		}	
		
		// Recherche des ordres à effectuer :
		//fenetreAccueil.updateFenetre();
		
		// Positionnement des différents éléments
		fg.setJMenuBar(menuBar);
		fg.getContentPane().setLayout(new BorderLayout());
		fg.getContentPane().add(panHaut, BorderLayout.NORTH);
		fg.getContentPane().add(content, BorderLayout.CENTER);
		fg.getContentPane().add(panGauche, BorderLayout.WEST);
		
		fg.setVisible(true);
	}
	
	/*
	public JFrame getFrame(){
		return this.fg;
	}
	*/
	
	public static void main(String[] args){
		new GestionSuivi();
	}
	
	// mise à jour de panCompte
	// une fonction "update" peut être utilisée dans d'autres classes
	public void updatePanCompte(){
		DataCenter dataCenter = DataCenter.getInstance();
		LinkedList<Compte> listCompte = dataCenter .getCompteDAO().getData();
		//
		Set<Integer> idComptesCourants = new HashSet(Arrays.asList(dataCenter.comptesCourantsArray()));
		int n = listCompte.size();
		
		JPanel panCompteBis = new JPanel();
		panCompteBis.setBackground(secondColor);
		panCompteBis.setLayout(new BoxLayout(panCompteBis, BoxLayout.PAGE_AXIS));
		// initialise avec BoutonCompte. NB : listener intégré !
		BoutonCompte[] tab_check = new BoutonCompte[n];
		panCompteBis.add(new JLabel("Choix de comptes"));
		// création des CheckBoxes pour les différents comptes
		// + ajout de ceux ci à panCompteBis
		// + toutes ces CheckBoxes sont initialement sélectionnées
		// + ajout d'actionsListeners...
		for (int i = 0; i<n; i++){
			tab_check[i]=new BoutonCompte(listCompte.get(i));
			tab_check[i].setBackground(secondColor);
			boolean test = idComptesCourants.contains(listCompte.get(i).getIdCompte());
			tab_check[i].setSelected(test);
			System.out.println("GestionSuivi.updatePanCompte, compte "+listCompte.get(i).toString());
			System.out.println("Résultat test = "+test);
			tab_check[i].addActionListener(((FenetreOperation) listFenetre[1]).getBoutonCompteListener());
			// NB: fenetreOperation met à jour la liste de DataCenter...
			panCompteBis.add(tab_check[i]);
		}
		System.out.println("GestionSuivi.updatePanCompte()");
		panCompte.removeAll();
		panCompte.add(panCompteBis);
		panCompte.repaint();
	}
}
