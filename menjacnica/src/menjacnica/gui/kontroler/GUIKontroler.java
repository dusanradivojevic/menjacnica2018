package menjacnica.gui.kontroler;

import java.awt.EventQueue;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;

import menjacnica.Menjacnica;
import menjacnica.Valuta;
import menjacnica.gui.DodajKursGUI;
import menjacnica.gui.IzvrsiZamenuGUI;
import menjacnica.gui.MenjacnicaGUI;
import menjacnica.gui.ObrisiKursGUI;
import menjacnica.gui.models.MenjacnicaTableModel;

public class GUIKontroler {

	public static MenjacnicaGUI gp;
	public static Menjacnica m;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUIKontroler.gp = new MenjacnicaGUI();
					gp.setVisible(true);
					m = new Menjacnica();
			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public static void prikaziDodajKursGUI() {
		DodajKursGUI prozor = new DodajKursGUI();
		prozor.setLocationRelativeTo(gp);
		prozor.setVisible(true);
	}

	public static void prikaziObrisiKursGUI(JTable table) {

		if (table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel) (table.getModel());
			ObrisiKursGUI prozor = new ObrisiKursGUI(model.vratiValutu(table.getSelectedRow()));
			prozor.setLocationRelativeTo(gp);
			prozor.setVisible(true);
		}
	}
	
	public static void prikaziIzvrsiZamenuGUI(JTable table) {
		if (table.getSelectedRow() != -1) {
			MenjacnicaTableModel model = (MenjacnicaTableModel)(table.getModel());
			
			IzvrsiZamenuGUI prozor = new IzvrsiZamenuGUI(model.vratiValutu(table.getSelectedRow()));
			
			prozor.setLocationRelativeTo(gp);
			prozor.setVisible(true);
		}
	}
	
	public static void ucitajIzFajla(JTable table) {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showOpenDialog(gp);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				m.ucitajIzFajla(file.getAbsolutePath());
				prikaziSveValute(table);
			}	
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(gp, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void prikaziSveValute(JTable table) {
		MenjacnicaTableModel model = (MenjacnicaTableModel)(table.getModel());
		model.staviSveValuteUModel(m.vratiKursnuListu());
	}
	
	public static void sacuvajUFajl() {
		try {
			JFileChooser fc = new JFileChooser();
			int returnVal = fc.showSaveDialog(gp);

			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				m.sacuvajUFajl(file.getAbsolutePath());
			}
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(gp, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void unesiKurs(String Naziv, String skrNaziv, int sifra,
			double prodajni, double kupovni, double srednji) {
		try {
			Valuta valuta = new Valuta();

			// Punjenje podataka o valuti
			valuta.setNaziv(Naziv);
			valuta.setSkraceniNaziv(skrNaziv);
			valuta.setSifra(sifra);
			valuta.setProdajni(prodajni);
			valuta.setKupovni(kupovni);
			valuta.setSrednji(srednji);
			
			// Dodavanje valute u kursnu listu
			m.dodajValutu(valuta);

			// Osvezavanje glavnog prozora
			prikaziSveValute(gp.getTable());
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(gp, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static void obrisiValutu(Valuta valuta) {
		try{
			m.obrisiValutu(valuta);
			
			prikaziSveValute(gp.getTable());
			
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(gp, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
		}
	}
	
	public static String izvrsiZamenu(Valuta valuta, boolean rdbtn, double iznos){
		try{
			double konacniIznos = 
					m.izvrsiTransakciju(valuta,
							rdbtn, iznos);
		
			return "" + konacniIznos;
		} catch (Exception e1) {
			JOptionPane.showMessageDialog(gp, e1.getMessage(),
					"Greska", JOptionPane.ERROR_MESSAGE);
			return null;
		}
	}

}
