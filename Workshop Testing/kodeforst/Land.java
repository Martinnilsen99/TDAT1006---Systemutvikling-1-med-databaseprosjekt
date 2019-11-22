 class Land implements java.io.Serializable{
        private String navn;
        private String statsoverhode;
        private By[] byer;  // hovedstad indeks 0
        private int antallbyer = 0;
        private static final long serialVersionUID = 101;

        public Land(String navn, String statsoverhode, int maksAntallByer) {
            this.navn = navn;
            this.statsoverhode = statsoverhode;
            if (maksAntallByer > 0) {
                byer = new By[maksAntallByer];
                antallbyer = 0;
            }
        }

        public Land(String navn, String statsoverhode, By[] byer)  throws Exception{
            this.navn = navn;
            this.statsoverhode = statsoverhode;
            /* komposisjon */
            if (byer != null) {
                this.byer = new By[byer.length];
                for (int i = 0; i < byer.length; i++) {
                    this.byer[i] = new By(byer[i].getNavn(), byer[i].getOrdf�rer(), byer[i].getAntallInnbyggere(), byer[i].getMaksAntallInnbyggere());
                }
            }
        }

        public String getNavn() { return navn; }
        public void setNavn(String newValue) {navn = newValue;}
		public String getStatsoverhode() {  return statsoverhode;    }
        public void setStatsoverhode(String newValue) {statsoverhode = newValue; }

        public String toString() {
            String res = navn + ", Statsoverhode: " + navn + "\n Byer:\n---------------------\n";
            for (By b : byer) {
                if (b != null) res += b + "\n";
            }
            return res;
        }

		private boolean regF�r(By by){
		 	if (by != null) {
            // sjekk at by ikke er registrert fra f�r
            	for (int i=0; i<antallbyer; i++) {
                	if (byer[i]!=null && byer[i].equals(by)) {
            	    	return true; // by er allerede registrert
        	   		}
    	        }
			}
		  return false;  // ikke registrert f�r
		}

		public boolean regBy(By by) throws Exception {
            if (by != null) {
                if (regF�r(by)) return false;  // by er allerede registrert

                if (antallbyer < byer.length) {
                    byer[antallbyer] = new By(by.getNavn(), by.getOrdf�rer(), by.getAntallInnbyggere(), by.getMaksAntallInnbyggere());
                    return false;
                }
            }
           return true;
       }

       public int getAntallInnbyggere() {
           int antall = 0;
           for (int i=0; i<antallbyer; i++) {
               antall = byer[i].getAntallInnbyggere();
           }
           return antall;
       }

       public By[] getByerMedPlass(int antall) throws Exception{
       	By[] tmp = new By[byer.length];
       	int j= 0;
       	for(int i=0; i<antallbyer; i++){
       		if(byer[i].getLedigKapasitet()<= antall){
       			tmp[j] = new By(byer[i].getOrdf�rer(), byer[i].getNavn(), byer[i].getAntallInnbyggere(), byer[i].getMaksAntallInnbyggere());
       			i++;
       		}
       	}
       	if (j==byer.length) return tmp;
       	else{
       		By[] returTab = new By[j];
       		for(int i=0; i<j; i++){
       			returTab[i] =new By(tmp[i].getNavn(), tmp[i].getOrdf�rer(), tmp[i].getAntallInnbyggere(), tmp[i].getMaksAntallInnbyggere());
			}
			return returTab;
       	}
    }

	private String[] getOrdf�rere(){
		String[] ordf�rere = new String[antallbyer];
		for(int i=0; i<antallbyer; i++){
			ordf�rere[i] = byer[i].getOrdf�rer();
		}
		return ordf�rere;
	}

	public String[] getSortertOrdf�rerListe() {
		 String[] ordf�rere = getOrdf�rere();
		 for(int start=0; start<ordf�rere.length; start++){
			 int hittilMinst=start;
			 for(int j=start; j<ordf�rere.length; j++){
				 if(ordf�rere[j].compareTo(ordf�rere[hittilMinst]) <= 0) hittilMinst=j;
			 }
			 // bytt plass start og hittilminst
			 String tmp = ordf�rere[start];
			 ordf�rere[start] = ordf�rere[hittilMinst];
			 ordf�rere[hittilMinst] = tmp;
		 }
		 return ordf�rere;
	}

	private boolean gyldigIndeks(int indeks){
		if (indeks >=0 && indeks<=antallbyer) return true;
		else return false;
	}

	public By getBy(int indeks){
	 	if (gyldigIndeks(indeks)) return byer[indeks];
	 	else return null;
	}
}

