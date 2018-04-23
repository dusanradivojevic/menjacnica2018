package menjacnica;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.LinkedList;

import sistemskeOperacije.SODodajValutu;
import sistemskeOperacije.SOObrisiValutu;
import sistemskeOperacije.SOSacuvajUFajl;
import sistemskeOperacije.SOUcitajIzFajla;

public class Menjacnica implements MenjacnicaInterface{
	
	private LinkedList<Valuta> kursnaLista = new LinkedList<Valuta>();

	@Override
	public void dodajValutu(Valuta valuta) {
		
		SODodajValutu.izvrsi(valuta, kursnaLista);
	}

	@Override
	public void obrisiValutu(Valuta valuta) {
		
		SOObrisiValutu.izvrsi(valuta, kursnaLista);
	}

	@Override
	public double izvrsiTransakciju(Valuta valuta, boolean prodaja, double iznos) {
		if (prodaja)
			return iznos*valuta.getProdajni();
		else
			return iznos*valuta.getKupovni();
	}

	@Override
	public LinkedList<Valuta> vratiKursnuListu() {
		return kursnaLista;
	}

	@Override
	public void ucitajIzFajla(String putanja) {
		
		kursnaLista = SOUcitajIzFajla.izvrsi(putanja, kursnaLista);		
	}

	@Override
	public void sacuvajUFajl(String putanja) {
		
		SOSacuvajUFajl.izvrsi(putanja, kursnaLista);
	}

	
}
