package fr.diginamic.recensement.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.IncorrectIntInput;
import fr.diginamic.recensement.exceptions.InputException;
import fr.diginamic.recensement.exceptions.RegionInconnueException;
import fr.diginamic.recensement.services.comparators.EnsemblePopComparateur;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Cas d'utilisation: affichage des N villes les plus peuplées d'une région
 * donnée
 * 
 * @author DIGINAMIC
 *
 */
public class RechercheVillesPlusPeupleesRegion extends MenuService {

	@Override
	public void traiter(Recensement recensement, Scanner scanner) throws InputException {

		System.out.println("Veuillez saisir un nom de région:");
		String nomRegion = scanner.nextLine();

		System.out.println("Veuillez saisir un nombre de villes:");
		String nbVillesStr = scanner.nextLine();

		if (!NumberUtils.isCreatable(nbVillesStr)) {
			throw new IncorrectIntInput("Le nombre de villes saisi par l'utilisateur n'est pas un nombre entier.");
		}

		int nbVilles = Integer.parseInt(nbVillesStr);

		List<Ville> villesRegions = new ArrayList<Ville>();

		boolean noMatchingRegion = true;

		List<Ville> villes = recensement.getVilles();
		for (Ville ville : villes) {
			if (ville.getNomRegion().toLowerCase().startsWith(nomRegion.toLowerCase())) {
				noMatchingRegion = false;
				villesRegions.add(ville);
			}
		}

		if (noMatchingRegion) {
			throw new RegionInconnueException("Le nom de la région saisi n'est pas connu de la base de données.");
		}

		Collections.sort(villesRegions, new EnsemblePopComparateur(false));
		System.out.println("Les " + nbVilles + " villes les plus peuplées de la région " + nomRegion + " sont :");

		if (villesRegions.size() > 0) {
			for (int i = 0; i < nbVilles; i++) {
				Ville ville = villesRegions.get(i);
				System.out.println(ville.getNom() + " : " + ville.getPopulation() + " habitants.");
			}
		}

	}

}
