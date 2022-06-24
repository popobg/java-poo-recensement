package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.FunctionalException;
import fr.diginamic.recensement.exceptions.IllegalInputException;
import fr.diginamic.recensement.exceptions.NotNumberException;

/**
 * Recherche et affichage de toutes les villes d'un département dont la
 * population est comprise entre une valeur min et une valeur max renseignées
 * par l'utilisateur.
 * 
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationBorneService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) throws FunctionalException {

		System.out.println("Quel est le code du département recherché ? ");
		String choix = scanner.nextLine();
		if (StringUtils.isEmpty(choix)) {
			throw new IllegalInputException("Le code du département doit être renseigné.");
		}

		System.out.println("Choississez une population minimum (en milliers d'habitants): ");
		String saisieMin = scanner.nextLine();
		if (!NumberUtils.isDigits(saisieMin)) {
			throw new NotNumberException("La population minimum doit être un nombre entier");
		}
		
		System.out.println("Choississez une population maximum (en milliers d'habitants): ");
		String saisieMax = scanner.nextLine();
		if (!NumberUtils.isDigits(saisieMax)) {
			throw new NotNumberException("La population minimum doit être un nombre entier");
		}

		int min = Integer.parseInt(saisieMin) * 1000;
		int max = Integer.parseInt(saisieMax) * 1000;
		if (min >= max) {
			throw new IllegalInputException("La population min doit être strictement inférieure à la population max.");
		}
		
		List<Ville> villes = rec.getVilles();
		for (Ville ville : villes) {
			if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
				if (ville.getPopulation() >= min && ville.getPopulation() <= max) {
					System.out.println(ville);
				}
			}
		}
	}

}
