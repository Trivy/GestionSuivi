package gestion.aexecuter;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import gestion.data.DataCenter;
import gestion.util.FenetreCommun;

public class FenetreAExecuter extends FenetreCommun {
private AExecuterModel dModelA;
private AExecuterModel dModelV;

	public FenetreAExecuter() {
		super();
		this.setLayout(new BoxLayout(this,BoxLayout.PAGE_AXIS));
		
		// Panneau des achats
		JPanel panAchats = new JPanel(new BorderLayout());
		JLabel achats = new JLabel("Achats");
		
		// Initialiser le modà¨le
		Object[][] dataAchats = DataCenter.getInstance().achatsAExecuter();
		String[] titleAchats = {"Placement", "Volume (UC)", "Cours ordre", "Dernier cours", "Date cours"};
		dModelA = new AExecuterModel(dataAchats,titleAchats);

		JTable tabAchats = new TableauAExecuter(dModelA);

		
		panAchats.add(achats,BorderLayout.NORTH);
		panAchats.add(new JScrollPane(tabAchats), BorderLayout.CENTER);
		//panAchats.setPreferredSize(new Dimension(400,300));
		
		
		// Panneau des ventes
		JPanel panVentes = new JPanel(new BorderLayout());
		JLabel ventes = new JLabel("Ventes");
		Object[][] dataVentes = DataCenter.getInstance().ventesAExecuter();
		String[] titleVentes = {"Placement", "Volume (UC)", "Cours ordre", "Dernier cours", "Date cours"};
		dModelV = new AExecuterModel(dataVentes,titleVentes);
		
		JTable tabVentes = new TableauAExecuter(dModelV);
		
		panVentes.add(ventes,BorderLayout.NORTH);
		panVentes.add(new JScrollPane(tabVentes), BorderLayout.CENTER);
		
		// bouton de mise à  jour 
		JButton maJ = new JButton("MàJ des données");
	    class AExecListener implements ActionListener{
			
			public void actionPerformed(ActionEvent event){
				// recherche des achats à  exà©cuter
				Object[][] dataA = DataCenter.getInstance().achatsAExecuter();
				// on utilise setData + fireTableDataChanged() plutà´t que setDataVector, 
				// car setDataVector perd les "renderers"
				dModelA.setData(dataA);
				dModelA.fireTableDataChanged();
				System.out.println("AExecListener : mise à  jour des achats à  effectuer terminée !");
				
				Object[][] dataV = DataCenter.getInstance().ventesAExecuter();
				dModelV.setData(dataV);
				dModelV.fireTableDataChanged();
				System.out.println("AExecListener : mise à  jour des ventes à  effectuer terminée !");
			}
		}
	    maJ.addActionListener(new AExecListener());
		
		this.add(panAchats);
		this.add(panVentes);
		this.add(maJ);
	}

}
