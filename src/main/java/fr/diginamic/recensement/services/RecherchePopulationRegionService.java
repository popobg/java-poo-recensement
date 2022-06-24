package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.FunctionalException;
import fr.diginamic.recensement.exceptions.IllegalInputException;

/**
 * Recherche et affichage de la population d'une région
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationRegionService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) throws FunctionalException {

		System.out.println("Quel est le nom (ou le début de nom) de la région recherchée ? ");
		String choix = scanner.nextLine();
		if (StringUtils.isEmpty(choix)) {
			throw new IllegalInputException("Le nom de la région doit être renseignée.");
		}

		List<Ville> villes = rec.getVilles();
		int somme = 0;
		String nom = null;
		for (Ville ville : villes) {
			if (ville.getNomRegion().toLowerCase().startsWith(choix.toLowerCase())
					|| ville.getCodeRegion().toLowerCase().equals(choix.toLowerCase())) {
				somme += ville.getPopulation();
				nom = ville.getNomRegion();
			}
		}
		if (somme > 0) {
			System.out.println("Population de la région " + nom + " : " + somme);
		} else {
			System.out.println("Région " + choix + " non trouvée.");
		}
	}

}
