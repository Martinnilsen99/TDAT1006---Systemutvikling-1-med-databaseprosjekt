class By implements java.io.Serializable{
        private String navn;
        private String ordf�rer;
        private int antallInnbyggere;
        private int maksAntallInnbyggere = 1000;

        public By(String navn, String ordf�rer, int antallInnbyggere, int maksAntallInnbyggere) throws Exception{
            this.navn = navn;
            this.ordf�rer = ordf�rer;
			if (antallInnbyggere <= maksAntallInnbyggere){
				this.antallInnbyggere = antallInnbyggere;
		        this.maksAntallInnbyggere = maksAntallInnbyggere;
			} else throw new Exception("Byen kan ikke ha flere innbyggere enn det er plass til!");
        }

        public By (){}

        public String getNavn(){ return navn; }
        public void setNavn(String newValue) { navn = newValue; }

        public String getOrdf�rer(){ return ordf�rer; }
        public void setOrdf�rer(String newValue) { ordf�rer = newValue; }

        public int getAntallInnbyggere(){return antallInnbyggere;}
        public void setAntallInnbyggere(int newValue){ maksAntallInnbyggere = newValue;}

		public int getMaksAntallInnbyggere(){ return maksAntallInnbyggere;}
		public void setMaksAntallInnbyggere(int newValue){maksAntallInnbyggere = newValue;}

     	public String toString() {
            return navn + " " + ordf�rer + " " + antallInnbyggere + " " + maksAntallInnbyggere;
        }
        public boolean equals(Object obj) {
            if (this==obj) return true;
            if (obj instanceof By) {
                By by = (By) obj;
                if (by.getNavn().equals(navn)) return true;
            }
            return false;
        }
        public int getLedigKapasitet(){return maksAntallInnbyggere - antallInnbyggere;}
		public boolean ledig(){ return (antallInnbyggere <= maksAntallInnbyggere);}
    }