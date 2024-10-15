package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.*;
import org.apache.commons.lang3.math.NumberUtils;

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
	public void traiter(Recensement rec, Scanner scanner) throws InputException, NumberFormatException {

		System.out.println("Quel est le code du département recherché ? ");
		String choix = scanner.nextLine();

		System.out.println("Choississez une population minimum (en milliers d'habitants): ");
		String saisieMin = scanner.nextLine();
		
		System.out.println("Choississez une population maximum (en milliers d'habitants): ");
		String saisieMax = scanner.nextLine();

		int min;

		// Une façon de gérer la conversion de l'input utilisateur
		// vers un nombre
		try {
			min = Integer.parseInt(saisieMin) * 1000;
		}
		catch (NumberFormatException e) {
			throw new NumberFormatException("le nombre correspondant à la population minimum saisi n'est pas un nombre entier.");
		}

		// Alternative avec NumberUtils et une classe
		// customisée héritant d'Exception.
		// isCreatable(String str) retourne true si la string est
		// convertible en un type Numeric de Java.
		if (!NumberUtils.isCreatable(saisieMax)) {
			throw new IncorrectIntInput("le nombre correspondant à la population maximum saisi n'est pas un nombre entier.");
		}

		int max = Integer.parseInt(saisieMax) * 1000;

		if (min < 0 || max < 0) {
			String minMax = min < 0?"minimum":"maximum";

			throw new NegativeIntException("le nombre correspondant à la population " + minMax + " est négatif.");
		}

		if (min > max) {
			throw new IncoherentMinMaxException("le nombre correspondant à la population minimum saisi est supérieur au nombre maximum saisi.");
		}

		// On checke si le code département existe dans la boucle
		// de traitement - cela évite de faire plusieurs boucles.
		boolean noMatchingCodeDepartement = true;

		List<Ville> villes = rec.getVilles();
		for (Ville ville : villes) {
			if (ville.getCodeDepartement().equalsIgnoreCase(choix)) {
				if (ville.getPopulation() >= min && ville.getPopulation() <= max) {
					noMatchingCodeDepartement = false;
					System.out.println(ville);
				}
			}
		}

		if (noMatchingCodeDepartement) {
			throw new DepartementInconnuException("Le code du département saisi n'est pas connu de la base de données.");
		}
	}

}
