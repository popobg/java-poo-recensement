package fr.diginamic.recensement.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.DepartementInconnuException;
import fr.diginamic.recensement.exceptions.IncorrectIntInput;
import fr.diginamic.recensement.exceptions.InputException;
import fr.diginamic.recensement.services.comparators.EnsemblePopComparateur;
import org.apache.commons.lang3.math.NumberUtils;

/**
 * Cas d'utilisation: affichage des N villes les plus peuplées d'une département
 * donné
 * 
 * @author DIGINAMIC
 *
 */
public class RechercheVillesPlusPeupleesDepartement extends MenuService {

	@Override
	public void traiter(Recensement recensement, Scanner scanner) throws InputException {

		System.out.println("Veuillez saisir un numéro de département:");
		String nomDept = scanner.nextLine();

		System.out.println("Veuillez saisir un nombre de villes:");
		String nbVillesStr = scanner.nextLine();

		if (NumberUtils.isCreatable(nbVillesStr)) {
			throw new IncorrectIntInput("le nombre de villes saisi n'est pas un nombre entier.");
		}

		int nbVilles = Integer.parseInt(nbVillesStr);

		List<Ville> villesDept = new ArrayList<Ville>();

		boolean noMatchingDepartement = true;

		List<Ville> villes = recensement.getVilles();
		for (Ville ville : villes) {
			if (ville.getCodeDepartement().equalsIgnoreCase(nomDept)) {
				noMatchingDepartement = false;
				villesDept.add(ville);
			}
		}

		if (noMatchingDepartement) {
			throw new DepartementInconnuException("Le nom du département saisi n'est pas connu de la base de données.");
		}

		Collections.sort(villesDept, new EnsemblePopComparateur(false));

		if (villesDept.size() > 0) {
			System.out.println("Les " + nbVilles + " villes les plus peuplées du département " + nomDept + " :");
			for (int i = 0; i < nbVilles; i++) {
				Ville ville = villesDept.get(i);
				System.out.println(ville.getNom() + " : " + ville.getPopulation() + " habitants.");
			}
		} 
	}

}
