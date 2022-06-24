package fr.diginamic.recensement.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;

import fr.diginamic.recensement.entites.Departement;
import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.FunctionalException;
import fr.diginamic.recensement.exceptions.NotNumberException;
import fr.diginamic.recensement.services.comparators.EnsemblePopComparateur;

/**
 * Affichage des N départements les plus peuplés de France où N est saisi par l'utilisateur
 * 
 * @author DIGINAMIC
 *
 */
public class RechercheDepartementsPlusPeuplees extends MenuService {

	@Override
	public void traiter(Recensement recensement, Scanner scanner) throws FunctionalException {

		System.out.println("Veuillez saisir un nombre de départements:");
		String nbDeptsStr = scanner.nextLine();
		if (!NumberUtils.isDigits(nbDeptsStr)) {
			throw new NotNumberException("Le nombre de départements doit être un entier.");
		}
		
		int nbDepts = Integer.parseInt(nbDeptsStr);

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

		for (int i = 0; i < nbDepts && i<departements.size(); i++) {
			Departement departement = departements.get(i);
			System.out.println(
					"Département " + departement.getCode() + " : " + departement.getPopulation() + " habitants.");
		}

	}

}
