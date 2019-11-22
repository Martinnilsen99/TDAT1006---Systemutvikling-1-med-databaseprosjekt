import static javax.swing.JOptionPane.*;
import java.io.*;

class Land_Klientprogram {
	public static Land lesFraFil(String filnavn) throws Exception {
		try{
			FileInputStream innstrom = new FileInputStream(filnavn);
			ObjectInputStream inn = new ObjectInputStream(innstrom);
			Land land = (Land)inn.readObject();  // kaster eofexception ved tom fil, FileNotFound
			inn.close();
			return land;
		}catch(FileNotFoundException e){
			System.out.println("fil ikke funnet!");
		}catch(EOFException e){
			System.out.println("fil funnet, men tom!");
		}
		return null;
	}

	public static boolean skrivTilFil(Land l, String filnavn){
		boolean ok = false;
		if (l != null){
			try{
				FileOutputStream utstrom = new FileOutputStream(filnavn); // true = append
				ObjectOutputStream ut = new ObjectOutputStream(utstrom);
				ut.writeObject(l);
				ut.close();
				ok = true;
			}catch(FileNotFoundException e){
				System.out.println("fil ikke funnet!");
			}catch(Exception e){
				System.out.println("Noe gikk galt med skriving til fil!");
			}
		}
		return ok;
	}

	public static void main(String[] args) {
		String[] muligheter = {"Nytt land", "Registrer by", "List ordf�rere", "List alle byer", "Avslutt"};
		final int NYTT_LAND = 0;
		final int REG_BY = 1;
		final int LIST_ORDF�RERE = 2;
		final int LIST_ALLE_BYER = 3;
		final int AVSLUTT = 4;
		int valg = showOptionDialog(null, "Velg operasjon", "Eksamen v�r 2016", YES_NO_OPTION, INFORMATION_MESSAGE, null, muligheter, muligheter[0]);
		Land land = null;
		final String filnavn = "landdata.ser";
		// les eventuelle data fra fil
		try{
			land = lesFraFil(filnavn);
			if (land == null){ // legger inn noen testdata, her kunne en valgt � ta inn brukerinput istd
				land = new Land("Norge", "Kong Harald V", 10);
				land.regBy(new By("Trondheim", "Otervik, Rita", 170000, 200000));
				land.regBy(new By("Oslo", "Borgen, Marianne", 650000, 700000));
				land.regBy(new By("Stj�rdal", "Vigdenes, Ivar", 23308, 23400));
				land.regBy(new By("Verdal", "Iversen, Bj�rn", 14855, 15000));
			}
		}catch(Exception e){
			System.out.println("noe gikk galt ved lesing fra fil" + e.toString());
		}
		while (valg != AVSLUTT){
			switch (valg){
				case NYTT_LAND:
					String navn = showInputDialog("Navn: ");
					String statsoverhode = showInputDialog("Statsoverhode: ");
					int antallByer =  Integer.parseInt(showInputDialog("Maks antallbyer: "));
					int sikker = showConfirmDialog(null, "Sikker? - Du vil slette alle tidligere registrerte data");
					if (sikker == YES_OPTION)	land = new Land(navn, statsoverhode, antallByer);
					else showMessageDialog(null, "Avbryter");
					break;
				case REG_BY:
					if (land != null){
						String bynavn = showInputDialog("Navn: ");
						String ordf�rer = showInputDialog("Ordf�rer(Etternavn, Fornavn): ");
						int antallInnbyggere = Integer.parseInt(showInputDialog("Antall innbyggere: " ));
						int maksAntallInnbyggere = Integer.parseInt(showInputDialog("Maks antall innbyggere: " ));
						try{
							boolean ok = land.regBy(new By(bynavn, ordf�rer, antallInnbyggere, maksAntallInnbyggere));
				 			if (ok) showMessageDialog(null, "By registrert");
							else showMessageDialog(null, "By er registrert fra f�r/ ikke plass til by/ feil inndata");
						} catch(Exception e){
							showMessageDialog(null, "Antall innbyggere kan ikke v�re h�yere enn maksAntallInnbyggere" + e.toString());
						}
					} else showMessageDialog(null, "Du m� registrere ett land f�rst");
					break;
				case LIST_ORDF�RERE:
					if (land != null){
						String[] oListe = land.getSortertOrdf�rerListe();
						if (oListe != null){
							String res = "";
							for (String s: oListe){
								res += s + "\n";
							}
							showMessageDialog(null, "Ordf�rere:\n----------------\n" + res);
						}
					} else showMessageDialog(null, "Du m� registrere ett land f�rst");
					break;
				case LIST_ALLE_BYER:
					if (land != null){
						showMessageDialog(null, land);
					} else showMessageDialog(null, "Du m� registrere ett land f�rst");
					default: break;
				}
				valg = showOptionDialog(null,"Velg operasjon","Eksamen v�r 2016", DEFAULT_OPTION, PLAIN_MESSAGE, null, muligheter, muligheter[0]);
			}

			// skriv til fil ved avslutt
			try{
				if (land != null) skrivTilFil(land, filnavn);
				System.out.println("Skriv til fil; " + land);
			}catch(Exception e) {
				System.out.println("Noe gikk galt ved skriving til fil. "  + e.toString());
			}
	}
}