package dataAccess;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import domain.Offer;
import domain.RuralHouse;


import dataAccess.DataAccess;

public class DataAccess {
	private static EntityManager db;
	private EntityManagerFactory emf;
	String fileName = "RuralHouses.odb";
	
	
	/**
	 * Contructora DataAccess
	 * @param initializeDB si true elimina la base anterior y crea una nueva, si es false abre la existente
	 */
	public DataAccess(final boolean initializeDB) {
		
		if(initializeDB) { //Eliminar anterior
			fileName = String.valueOf(fileName) + ";drop";
			//MyOfferManager n = new MyOfferManager();
			//inicializarBD(n.getRuralHouses());
		} 
			//abre BD, en caso de no existir la crea
		emf = Persistence.createEntityManagerFactory("objectdb:"+fileName);
		db = emf.createEntityManager();
	
		
	}
	
	public void inicializarBD(ArrayList<RuralHouse> ruralHouses) {
		deleteAll();
		db.getTransaction().begin();
		
		try {
			for(RuralHouse r: ruralHouses) {
				if(!db.contains((Object)r)) {
					db.persist((Object)r);
				}
						
				for(Offer o: r.getOffers()) {
					if(!db.contains((Object)o)) {
						db.persist((Object)o);
					}
				}
			}
			db.getTransaction().commit();
		} catch (Exception e){
			e.printStackTrace();
		}
		
	}
	/**
	 * Cerrar base de datos
	 */
	public void close(){
		db.close();
		
	}
	
	/**
	 * Guarda la oferta en la base de datos
	 * @param date fecha
	 * @param tripleNumber cantidad de habitaciones triples disponibles
	 * @param doubleNumber cantidad de habitaciones dobles disponibles
	 * @param singleNumber cantidad de habitaciones individuales disponibles
	 * @param rh casa rural a la que pertenece la oferta
	 */
	public void storeOffer(Date date, int tripleNumber, int doubleNumber, int singleNumber, RuralHouse rh) {
		TypedQuery<RuralHouse> query = db.createQuery("SELECT r FROM RuralHouse r WHERE r.ruralCode='"+rh.getRuralCode()+"'",RuralHouse.class);
		List<RuralHouse> rural = query.getResultList();
		RuralHouse ruralUno = rural.get(0);
		db.getTransaction().begin();
		ruralUno.addOffer(date, tripleNumber, doubleNumber, singleNumber);
		
		db.persist((Object)ruralUno);
		db.getTransaction().commit();
		System.out.println("nueva oferta insertada");
		
	}
	
	
	
	public ArrayList<Offer> getConcreteOffers(String city, Date date) {
		TypedQuery<RuralHouse> query = db.createQuery("SELECT r FROM RuralHouse r WHERE r.city='"+city+"'",RuralHouse.class);
		
		//Lista con RuralHouses de esa ciudad
		List<RuralHouse> ruralHouses = query.getResultList();
		
		ArrayList<Offer> res = new ArrayList<Offer>();
		for (RuralHouse rh : ruralHouses) 
			for (Offer off : rh.getOffers()) 
				if ((off.getDate().compareTo(date)) == 0) 
					res.add(off);
		return res;	
	}
	
	
	public void deleteAll() {
		db.getTransaction().begin();
		Query query = db.createQuery("DELETE FROM Offer o", Offer.class);
		query.executeUpdate();
		query = db.createQuery("DELETE FROM RuralHouse r", RuralHouse.class);
		query.executeUpdate();
		db.getTransaction().commit();
		System.out.println();
	}
	
	/**
	 * Imprime todas las ofertas de la bd
	 */
	public void getAllOffers() {
		TypedQuery<Offer> query = db.createQuery("SELECT o FROM Offer o",Offer.class);
		List<Offer> offerList = query.getResultList();
		System.out.println("Contenido de la base de datos:");
		for(Offer o: offerList) {
			System.out.println(o.toString());
		}
	}
	
	/**
	 * Devuelve la oferta correspondiente a la fecha y casa rural, en caso de no haber ninguna devuelve null
	 * @param date fecha
	 * @param rh casa rural
	 * @return oferta
	 */
	public Offer getOfferById(Date date, RuralHouse rh) {
		RuralHouse rural = db.find(RuralHouse.class, rh.getRuralCode());	//casa rural introducida
		
		for (Offer o : rural.getOffers()) {
			if ((o.getDate().compareTo(date)) == 0)
				return o;	//oferta
		}
			
		return null;	//no existe la oferta
	}
	
	/**
	 * Cambia la cantidad de habitaciones triples disponibles 
	 * @param offer oferta a la que cambiar el numero
	 * @param num nuevo numero
	 */
	public void restarTripleNumber(Offer offer, int num) {
		Offer o = getOfferById(offer.getDate(), offer.getRuralHouse());
		if(o == null) {
			System.out.println("La oferta indicada no esta en la base de datos");
		} else {
			db.getTransaction().begin();
			o.setTripleNumber(num);
			db.getTransaction().commit();
			System.out.println("Las habitaciones triple de " + o + " han sido actualizadas");
		}
	}
	
	/**
	 * Cambia la cantidad de habitaciones dobles disponibles 
	 * @param offer oferta a la que cambiar el numero
	 * @param num nuevo numero
	 */
	public void restarDoubleNumber(Offer offer, int num) {
		Offer o = getOfferById(offer.getDate(), offer.getRuralHouse());
		if(o == null) {
			System.out.println("La oferta indicada no esta en la base de datos");
		} else {
			db.getTransaction().begin();
			o.setDoubleNumber(num);
			db.getTransaction().commit();
			System.out.println("Las habitaciones double de " + o + " han sido actualizadas");
		}
	}
	
	/**
	 * Cambia la cantidad de habitaciones individuales disponibles 
	 * @param offer oferta a la que cambiar el numero
	 * @param num nuevo numero
	 */
	public void restarSingleNumber(Offer offer, int num) {
		Offer o = getOfferById(offer.getDate(), offer.getRuralHouse());
		if(o == null) {
			System.out.println("La oferta indicada no esta en la base de datos");
		} else {
			db.getTransaction().begin();
			o.setSingleNumber(num);
			db.getTransaction().commit();
			System.out.println("Las habitaciones single de " + o + " han sido actualizadas, ahora quedan " + num);
		}
	}

	
}
