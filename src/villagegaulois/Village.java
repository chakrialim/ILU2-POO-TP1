package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;
import personnages.VillageSansChefException;

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
			Etal etalTrouve = null;
			for (int i = 0; (i < etals.length) && (etalTrouve == null); i++) {
				Gaulois vendeur = etals[i].getVendeur();
				if (gaulois.getNom().equals(vendeur.getNom())) {
					etalTrouve = etals[i];
				}
			}
			return etalTrouve;
		}

		private String afficherMarche() {
			StringBuilder texte = new StringBuilder();
			int counterReste = 0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					Gaulois vendeur = etals[i].getVendeur();
					texte.append(
							vendeur.getNom() + " vend " + etals[i].getQuantite() + " " + etals[i].getProduit() + " \n");
				} else {
					counterReste++;
				}
			}
			if (counterReste != 0) {
				texte.append("Il reste " + counterReste + " etals non utilises dans le marche.\n");
			}
			return texte.toString();
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

	public String afficherVillageois() throws VillageSansChefException {
		if (chef == null) {
			throw new VillageSansChefException("Le village n'a pas de chef");
		}
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
		texte.append(vendeur.getNom() + " cherche un endroit pour vendre " + nbProduit + " " + produit + ".\n");
		int numeroEtalLibre = marche.trouverEtalLibre();
		if (numeroEtalLibre != -1) {
			marche.utiliserEtal(numeroEtalLibre, vendeur, produit, nbProduit);
			texte.append("Le vendeur " + vendeur.getNom() + " vend des " + produit + " a l'etal n�"
					+ (numeroEtalLibre + 1) + ".\n");
		} else {
			texte.append("Il n'y a plus d'etal libre \n");
		}
		return texte.toString();
	}

	public String rechercherVendeursProduit(String produit) {
		StringBuilder texte = new StringBuilder();
		Etal[] etalsAvecProduit = marche.trouverEtals(produit);
		if (etalsAvecProduit.length <= 0) {
			texte.append("Il n'y pas de vendeur qui propose des fleurs au marche.\n");
		} else {
			if (etalsAvecProduit.length == 1) {
				Gaulois vendeur = etalsAvecProduit[0].getVendeur();
				texte.append("Seul le vendeur " + vendeur.getNom() + " propose des " + produit + " au marche. \n");
			} else {
				texte.append("Les vendeurs qui proposent des fleurs sont: \n");
				for (int i = 0; i < etalsAvecProduit.length; i++) {
					Gaulois vendeur = etalsAvecProduit[i].getVendeur();
					texte.append("- " + vendeur.getNom() + "\n");
				}
			}
		}

		return texte.toString();
	}

	public String partirVendeur(Gaulois vendeur) {
		Etal etal = marche.trouverVendeur(vendeur);
		StringBuilder texte = new StringBuilder();
		if (etal!=null) {
			texte.append(etal.libererEtal());
		}
		return texte.toString();

	}

	public String afficherMarche() {
		StringBuilder texte = new StringBuilder();
		texte.append("Le marche du village <<" + nom + ">> possede plusieurs etals: \n");
		texte.append(marche.afficherMarche());
		return texte.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		return marche.trouverVendeur(vendeur);
	}

}