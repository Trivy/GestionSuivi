package gestion.data.quotation;

import java.sql.Date;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import javax.swing.JOptionPane;

import gestion.data.dao.DaoException;

public class Boursorama_ETF extends Transfer {
	
	// Une seule instance attendue (mais un singleton paraissait un peu trop complexe)
	// En outre, les "Transfer" seront sans doute appelés via Transfer.values()
	public Boursorama_ETF() {
		super("Boursorama ETF");
	}
	
	// Identifiant de cette classe :
	public int getIdTransf(){
		return 5;
	}

	@Override
	public Date getDate(String text) throws DaoException{
		Date date = new Date(0);
		
        // extraction de la date :
        String fromString = "Valeur liquidative au";
        int from = text.indexOf(fromString);
        int to = text.indexOf("/",from);
        
        System.out.println(this.name+".getDate -- from = "+from+", to = "+to);

        // ajout de la taille de fromString dans "from"
        String dateString= text.substring(from + fromString.length(), to);
        
        System.out.println(this.name+".getDate -- dateString ="+dateString);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yy",Locale.FRANCE);
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
		
		//String pString = "<meta itemprop=\"price\" ";
        //int p = text.indexOf(pString);
        String fromString = "<div class=\"c-ticker__item c-ticker__item--value\">";
        int from = text.indexOf(fromString);
        int to = text.indexOf("<span class=\"c-ticker__currency\">",from);
        
        System.out.println(this.name+".getPrice -- from = "+from+", to = "+to);
        // ajout de la taille de fromString dans "from"
        String rawPriceString= text.substring(from + fromString.length(), to);
        String priceString = rawPriceString.replace("\n", "").trim();
        
        System.out.println(this.name+".getPrice -- priceString ="+priceString);
		NumberFormat format = NumberFormat.getInstance(Locale.US);
        try{
        	price = format.parse((String)priceString).floatValue();
        	System.out.println("Prix récupéré = "+price);
		} catch (IllegalArgumentException e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, "priceString de la mauvaise forme ? "+e.getMessage(),"ERREUR ds "+this.name+".getPrice",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		} catch (Exception e){
			e.printStackTrace();
			JOptionPane jop = new JOptionPane();
			jop.showMessageDialog(null, e.getMessage(),"ERREUR ds "+this.name+".getPrice",JOptionPane.ERROR_MESSAGE);
			throw new DaoException();
		}
        return price;
	}
}
