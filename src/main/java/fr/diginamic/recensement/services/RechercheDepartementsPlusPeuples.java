package fr.diginamic.recensement.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import fr.diginamic.recensement.entites.Departement;
import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.services.comparators.EnsemblePopComparateur;

/**
 * Affichage des N départements les plus peuplés
 * 
 * @author DIGINAMIC
 *
 */
public class RechercheDepartementsPlusPeuples extends MenuService {

	@Override
	public void traiter(Recensement recensement, Scanner scanner) {

		System.out.println("Veuillez saisir un nombre de départements:");
		String nbDeptsStr = scanner.nextLine();

		int nbDepts;
		try {
			nbDepts = Integer.parseInt(nbDeptsStr);
		}
		catch (NumberFormatException e) {
			throw new NumberFormatException("le nombre de départements saisi n'est pas un nombre entier.");
		}

		List<Ville> villes = recensement.getVilles();
		Map<String, Departement> mapDepts = new HashMap<>();

		for (Ville ville : villes) {

			Departement departement = mapDepts.get(ville.getCodeDepartement());
			if (departement == null) {
				departement = new Departement(ville.getCodeDepartement());
				mapDepts.put(ville.getCodeDepartement(), departement);
			}
			departement.addVille(ville);
		}

		List<Departement> departements = new ArrayList<Departement>();
		departements.addAll(mapDepts.values());

		Collections.sort(departements, new EnsemblePopComparateur(false));

		for (int i = 0; i < nbDepts; i++) {
			Departement departement = departements.get(i);
			System.out.println(
					"Département " + departement.getCode() + " : " + departement.getPopulation() + " habitants.");
		}

	}

}
