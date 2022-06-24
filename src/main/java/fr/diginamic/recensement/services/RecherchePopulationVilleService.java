package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.FunctionalException;
import fr.diginamic.recensement.exceptions.IllegalInputException;

/**
 * Recherche et affichage de la population d'une ville
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationVilleService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) throws FunctionalException {

		System.out.println("Quel est le nom de la ville recherchée ? ");
		String choix = scanner.nextLine();
		if (StringUtils.isEmpty(choix)) {
			throw new IllegalInputException("Le nom de la ville doit être renseigné.");
		}

		List<Ville> villes = rec.getVilles();
		for (Ville ville : villes) {
			if (ville.getNom().equalsIgnoreCase(choix)
					|| ville.getNom().toLowerCase().startsWith(choix.toLowerCase())) {
				System.out.println(ville);
			}
		}
	}

}
