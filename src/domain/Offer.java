package domain;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Offer {
@Id
private Date date;

private int tripleNumber;
private int doubleNumber;
private int singleNumber;

@Id
private RuralHouse rh;

public Offer(Date date, int tripleNumber, int doubleNumber, int singleNumber, RuralHouse rh) {
	super();
	this.date = date;
	this.tripleNumber = tripleNumber;
	this.doubleNumber = doubleNumber;
	this.singleNumber = singleNumber;
	this.rh = rh;
}
 
public Date getDate() {
	return date;
}
public void setDate(Date date) {
	this.date = date;
}
public int getTripleNumber() {
	return tripleNumber;
}
public void setTripleNumber(int tripleNumber) {
	this.tripleNumber = tripleNumber;
}
public int getDoubleNumber() {
	return doubleNumber;
}
public void setDoubleNumber(int doubleNumber) {
	this.doubleNumber = doubleNumber;
}
public int getSingleNumber() {
	return singleNumber;
}
public void setSingleNumber(int singleNumber) {
	this.singleNumber = singleNumber;
}
public RuralHouse getRuralHouse() {
	return rh;
}

public String toString() {
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
	return rh.toString()+"/ "+df.format(date);}
}


