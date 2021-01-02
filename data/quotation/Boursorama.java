package gestion.data.quotation;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JOptionPane;

import gestion.data.dao.DaoException;

public class Boursorama extends Transfer {
	
	// Une seule instance attendue (mais un singleton paraissait un peu trop complexe)
	// En outre, les "Transfer" seront sans doute appelés via Transfer.values()
	public Boursorama() {
		super("Boursorama");
	}
	
	// Identifiant de cette classe :
	public int getIdTransf(){
		return 3;
	}

	@Override
	public Date getDate(String text) throws DaoException{
		Date date = new Date(0);
   
        // extraction de la date :
        int p = text.indexOf("c-faceplate__real-time");
        String fromString = "dernier cours connu au ";
        int from = text.indexOf(fromString,p);
        int to = text.indexOf("</div>",from);
        
        System.out.println(this.name+".getDate -- from = "+from+", to = "+to);

        // ajout de la taille de fromString dans "from"
        String dateString= text.substring(from + fromString.length(), to);
        
        System.out.println(this.name+".getDate -- dateString ="+dateString);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy",Locale.FRANCE);
        try{
        	date = new Date(sdf.parse(dateString).getTime());
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "Date pas au format dd/MM/yyyy ? "+e.getMessage(),"ERREUR ds "+this.name+".getDate",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR ds "+this.name+".getDate",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		}
        return date;
	}

	@Override
	public float getPrice(String text) throws DaoException{
		float price=0.f;
		
		String pString = "<div class=\"c-faceplate__price c-faceplate__price--inline\"><span class=\"c-instrument c-instrument--last\"";
        int p = text.indexOf(pString);
        String fromString = "data-ist-last>";
        int from = text.indexOf(fromString,p);
        int to = text.indexOf("</span><span class=\"c-faceplate",from);
        
        System.out.println(this.name+".getPrice -- from = "+from+", to = "+to);
        // ajout de la taille de fromString dans "from"
        String priceString= text.substring(from + fromString.length(), to);
        
        System.out.println(this.name+".getPrice -- priceString ="+priceString);
		NumberFormat format = NumberFormat.getInstance(Locale.US);
        try{
        	// remove white spaces
        	priceString = priceString.replaceAll("\\s+", "");
        	price = format.parse((String)priceString).floatValue();
        	System.out.println("Prix récupéré = "+price);
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			//JOptionPane jop = new JOptionPane();
			//jop.showMessageDialog(null, "priceString de la mauvaise forme ? "+e.getMessage(),"ERREUR ds "+this.name+".getPrice",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		} catch (Exception e){
			e.printStackTrace();
			//JOptionPane jop = new JOptionPane();
			//jop.showMessageDialog(null, e.getMessage(),"ERREUR dans "+this.name+".getPrice",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		}
        return price;
	}
}
