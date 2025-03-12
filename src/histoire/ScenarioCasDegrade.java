package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Etal etal = new Etal();
		Etal etalVide = new Etal();
//		etal.libererEtal();
		Gaulois bonemine = new Gaulois("Bonemine", 7);
		Gaulois asterix = new Gaulois("Astérix", 8);

		try {
			etal.occuperEtal(bonemine, "fleurs", 10);
			etal.acheterProduit(0, asterix);

		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}

		try {
			etalVide.acheterProduit(2, asterix);
		} catch (IllegalStateException e) {
			e.printStackTrace();
		}

		System.out.println("Fin du test");

	}

}
