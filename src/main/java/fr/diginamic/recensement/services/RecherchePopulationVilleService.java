package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.InputException;
import fr.diginamic.recensement.exceptions.NomVilleInconnuException;

/**
 * Recherche et affichage de la population d'une ville
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationVilleService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) throws InputException {

		System.out.println("Quel est le nom de la ville recherchée ? ");
		String choix = scanner.nextLine();

		boolean NoMatchingNomVille = true;

		List<Ville> villes = rec.getVilles();
		for (Ville ville : villes) {
			if (ville.getNom().equalsIgnoreCase(choix)
					|| ville.getNom().toLowerCase().startsWith(choix.toLowerCase())) {
				NoMatchingNomVille = false;
				System.out.println(ville);
			}
		}

		if (NoMatchingNomVille) {
			throw new NomVilleInconnuException("Le nom de ville saisi n'est pas connu de la base de données.");
		}
	}
}
