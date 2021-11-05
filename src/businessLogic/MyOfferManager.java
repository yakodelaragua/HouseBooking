package businessLogic;

import java.util.ArrayList;

import java.util.Calendar;
import java.util.Collection;
import java.util.Date;


import domain.RuralHouse;
import domain.Offer;
import dataAccess.DataAccess;

public class MyOfferManager implements OfferManager{
ArrayList<RuralHouse> ruralHouses;

	public MyOfferManager () {
		boolean initializeDB = false;
		DataAccess bd = new DataAccess(initializeDB);
		ruralHouses = new ArrayList<RuralHouse>();
		
		RuralHouse rh1 = new RuralHouse("Donostia","Avenida, 7");

		rh1.addOffer(newDate(2020,2,20),0,2,3);
		rh1.addOffer(newDate(2020,2,1),0,2,3);  
		rh1.addOffer(newDate(2020,2,2),3,3,0);
		rh1.addOffer(newDate(2020,2,3),3,3,0);
		rh1.addOffer(newDate(2020,2,4),3,3,0);
		rh1.addOffer(newDate(2020,2,5),3,3,0);
		rh1.addOffer(newDate(2020,2,6),3,3,0);
		rh1.addOffer(newDate(2020,2,7),3,3,0);
		rh1.addOffer(newDate(2020,2,8),0,0,3);
		rh1.addOffer(newDate(2020,2,9),0,0,3);
		rh1.addOffer(newDate(2020,2,10),0,0,3);
		rh1.addOffer(newDate(2020,2,11),0,0,3);
		rh1.addOffer(newDate(2020,2,12),0,1,3);
		rh1.addOffer(newDate(2020,2,13),1,1,1);
		
		RuralHouse rh2 = new RuralHouse("Donostia","San Martin, 13");
		rh2.addOffer(newDate(2020,2,20),1,1,1);
		
		RuralHouse rh3 = new RuralHouse("Bilbo","Zabalburu, 6");
		rh3.addOffer(newDate(2020,2,1),1,1,1);
		rh3.addOffer(newDate(2020,2,2),0,1,0);
		rh3.addOffer(newDate(2020,2,20),1,0,1);
		
		ruralHouses.add(rh1);	 
		ruralHouses.add(rh2);		
		ruralHouses.add(rh3);	
		
		
		if(initializeDB) {
			bd.inicializarBD(ruralHouses);
		}
	
		bd.close();
	
		
	}
	
	public ArrayList<RuralHouse> getRuralHouses() {
		return ruralHouses;
	}
	
	
	
		public Collection<Offer> getConcreteOffers(String city, Date date) {
			DataAccess bd = new DataAccess(false);
			ArrayList<Offer> res = new ArrayList<Offer>();
			res = bd.getConcreteOffers(city, date);	
			bd.close();
			return res;	
		}
		
		public Date newDate(int year,int month,int day) {

		     Calendar calendar = Calendar.getInstance();
		     calendar.set(year, month, day,0,0,0);
		     calendar.set(Calendar.MILLISECOND, 0);

		     return calendar.getTime();
		}
		
		
}