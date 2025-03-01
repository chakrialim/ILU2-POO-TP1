package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nombreEtalMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche = new Marche(nombreEtalMaximum);

	}

	private static class Marche {
		private Etal[] etals;

		private Marche(int nombreEtal) {
			this.etals = new Etal[nombreEtal];
			for (int i = 0; i < etals.length; i++) {
				etals[i] = new Etal();
			}

		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			Etal etal = etals[indiceEtal];
			if (!etal.isEtalOccupe()) {
				etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
			}

		}

		private int trouverEtalLibre() {
			int numeroEtalLibre = -1;
			for (int i = 0; i < etals.length && numeroEtalLibre == -1; i++) {
				if (!etals[i].isEtalOccupe()) {
					numeroEtalLibre = i;
				}
			}
			return numeroEtalLibre;
		}

		private Etal[] trouverEtals(String produit) {
			int nombreEtals = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nombreEtals++;
				}
			}
			Etal[] etalsAvecProduit = new Etal[nombreEtals];
			for (int i = 0, j = 0; i < etals.length && j < etalsAvecProduit.length; i++) {
				if (etals[i].contientProduit(produit)) {
					etalsAvecProduit[j] = etals[i];
					j++;
				}
			}
			return etalsAvecProduit;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			Etal etalTrouv� = null;
			for (int i = 0; i < etals.length && etalTrouv� != null; i++) {
				Gaulois vendeur = etals[i].getVendeur();
				if (gaulois.getNom().equals(vendeur.getNom())) {
					etalTrouv� = etals[i];
				}
			}
			return etalTrouv�;
		}

		private void afficherMarche() {
			StringBuilder texte = new StringBuilder();
			texte.append("Le Marche contient: ");
			int counterReste = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i] != null) {
					texte.append(etals[i].afficherEtal());
				} else {
					counterReste++;
				}
			}
			if (counterReste != 0) {
				texte.append("Il reste " + counterReste + "�tals non utilis�s dans le march�.");
			}
			System.out.println(texte);
		}
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
	
	public String installerVendeur(Gaulois vendeur, String produit, int nbProduit) {
		StringBuilder texte = new StringBuilder();
		texte.append(vendeur.getNom()+" cherche un endroit pour vendre "+nbProduit+" "+produit+".\n");
		int numeroEtalLibre = marche.trouverEtalLibre();
		if (numeroEtalLibre != -1) {
			marche.etals[numeroEtalLibre].occuperEtal(vendeur, produit, nbProduit);
			texte.append("Le vendeur "+vendeur.getNom()+" vend des "+produit+" a l'etal n�"+(numeroEtalLibre+1)+".\n");
		} else {
			texte.append("Il n'y a plus d'�tal libre \n");
		}
		return texte.toString();
	}
	
	public String rechercherVendeursProduit(String produit) {
		StringBuilder texte = new StringBuilder();
		Etal[] etalsAvecProduit = marche.trouverEtals(produit);
		if (etalsAvecProduit.length<=0) {
			texte.append("Il n'y pas de vendeur qui propose des fleurs au march�.");
		} else {
			texte.append("Les vendeurs qui proposent des fleurs sont: \n");
			for (int i = 0; i < etalsAvecProduit.length; i++) {
				texte.append("- "+ etalsAvecProduit[i].getVendeur()+"\n");
			}
		}
		return texte.toString();
	}
	
}