
public class Tabel {
	static String[] tabel = {"1","2","3","4","5","6","7","8","9"};
	
	public static void näitaTabelit(){
		System.out.println(" "+tabel[0]+" | "+tabel[1]+" | "+tabel[2]);
		System.out.println("-----------");
		System.out.println(" "+tabel[3]+" | "+tabel[4]+" | "+tabel[5]);
		System.out.println("-----------");
		System.out.println(" "+tabel[6]+" | "+tabel[7]+" | "+tabel[8]);
	}
	
//	Sisestatakse arv String kujul ja kui tabelis sellel kohal on arv, siis kirjutatakse sinna x või o
//  Boolean ehk tõeväärtus on sellepärast, et mängutsükklis saaks korraldada nii, et ükski käik raisku ei läheks
	
	public static boolean kirjutaTabelisse(String arv, String xo){
		if(tabel[Integer.valueOf(arv)-1].equals(arv)){
			tabel[Integer.valueOf(arv)-1] = xo;
			return true;
		}
		else {
			return false;
		}
	}
	
// võidukontroll tagastab ainult tõeväärtuse, mida kasutatakse mängutsükklis
	
	public static boolean kontrolliVõitu(String xo){
		// Võidu kontroll ridade kaupa
		if(tabel[0].equals(xo)&&tabel[1].equals(xo)&&tabel[2].equals(xo)){
			return true;
		}
		if(tabel[3].equals(xo)&&tabel[4].equals(xo)&&tabel[5].equals(xo)){
			return true;
		}
		if(tabel[6].equals(xo)&&tabel[7].equals(xo)&&tabel[8].equals(xo)){
			return true;
		}
		//Võidu kontroll veergude kaupa
		if(tabel[0].equals(xo)&&tabel[3].equals(xo)&&tabel[6].equals(xo)){
			return true;
		}
		if(tabel[1].equals(xo)&&tabel[4].equals(xo)&&tabel[7].equals(xo)){
			return true;
		}
		if(tabel[2].equals(xo)&&tabel[5].equals(xo)&&tabel[8].equals(xo)){
			return true;
		}
		//Võidu kontroll üle diagonaalide
		if(tabel[0].equals(xo)&&tabel[4].equals(xo)&&tabel[8].equals(xo)){
			return true;
		}
		if(tabel[2].equals(xo)&&tabel[4].equals(xo)&&tabel[6].equals(xo)){
			return true;
	    }	
		else{
			return false;
		}
     }

	public static void puhasta(){
		for (int i = 0; i < tabel.length; i++) {
			tabel[i]=Integer.toString(i+1);			
		}
	}
}
	

