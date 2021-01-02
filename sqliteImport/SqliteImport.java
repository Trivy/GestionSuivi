package gestion.sqliteImport;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;

/**
 * 
 * @author Trivy
 * A class to import CSV files
 */
public class SqliteImport extends JDialog{
	AbstractImportReader air = new BasicImportReader();
	String path = "";
	JPanel lineOnePan;
	
	public SqliteImport(){
		super();
		this.setTitle("JIBapp — connection parameters");
		this.setModal(true);
		this.setSize(300,125);
		this.setLocationRelativeTo(null);
		
		JLabel fileLabel = new JLabel("Choose file:");
		JButton fileButton = new JButton("file...");
		lineOnePan = new JPanel();
		lineOnePan.setLayout(new BoxLayout(lineOnePan, BoxLayout.LINE_AXIS));
		lineOnePan.add(fileLabel);
		lineOnePan.add(fileButton);
		
		class FileListener implements ActionListener{

			@Override
			public void actionPerformed(ActionEvent e) {
				JFileChooser chooser = new JFileChooser();
				FileNameExtensionFilter filter = new FileNameExtensionFilter("SQLite db", "db3","sqlite");
				chooser.setFileFilter(filter);
				int returnVal = chooser.showOpenDialog(lineOnePan);
				if(returnVal == JFileChooser.APPROVE_OPTION) {
					path = chooser.getSelectedFile().getAbsolutePath();
				}
			}
			
		}
		fileButton.addActionListener(new FileListener());
		
		JLabel readerLabel = new JLabel("Choose reader:");
		AbstractImportReader[] airVect = new AbstractImportReader[1];
		airVect[0] = new BasicImportReader();
		final JComboBox<AbstractImportReader> readerCombo = new JComboBox<AbstractImportReader>(airVect);		
		class ComboListener implements ActionListener{
			@Override
			public void actionPerformed(ActionEvent e) {
				air = (AbstractImportReader) readerCombo.getSelectedItem();
			}
		}
		readerCombo.addActionListener(new ComboListener());
		
		JPanel lineTwoPan = new JPanel();
		lineTwoPan.setLayout(new BoxLayout(lineTwoPan, BoxLayout.LINE_AXIS));
		lineTwoPan.add(readerLabel);
		lineTwoPan.add(readerCombo);
		
		
		// Cancel and Ok button + listeners
	    JButton cancelBouton = new JButton("Cancel");
	    cancelBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0) {
	    	// ends the dialog and stops the program:
	    	setVisible(false);
	      }      
	    });
	    
	    JButton okBouton = new JButton("Ok");
	    okBouton.addActionListener(new ActionListener(){
	      public void actionPerformed(ActionEvent arg0){
	    	  if (path.equals("")){
	    		  JOptionPane jop = new JOptionPane();
	    		  jop.showMessageDialog(null, null,"No file selected...",JOptionPane.ERROR_MESSAGE);
	    	  }
	    	  air.importReader(path);
	    	  System.out.println("SqliteImport -- selected path: "+path);

	    	  // ends dialog by making the box invisible
	    	  setVisible(false);
	      }
	    });
	    
		JPanel lineThreePan = new JPanel();
		lineThreePan.setLayout(new BoxLayout(lineThreePan, BoxLayout.LINE_AXIS));
		lineThreePan.add(cancelBouton);
		lineThreePan.add(okBouton);
		
		this.getContentPane().setLayout(new BoxLayout(this.getContentPane(), BoxLayout.PAGE_AXIS));
		this.getContentPane().add(lineOnePan);
		this.getContentPane().add(lineTwoPan);
		this.getContentPane().add(lineThreePan);
	}

	public void importSqlite(){
		SqliteImport sqliteImport = new SqliteImport();
		sqliteImport.setVisible(true);
	}
}
