package fr.diginamic.recensement.services;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.commons.lang3.math.NumberUtils;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Region;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.FunctionalException;
import fr.diginamic.recensement.exceptions.NotNumberException;
import fr.diginamic.recensement.services.comparators.EnsemblePopComparateur;

/**
 * Affichage des N régions les plus peuplées de France où N est saisi par l'utilisateur
 * 
 * @author DIGINAMIC
 *
 */
public class RechercheRegionsPlusPeuplees extends MenuService {

	@Override
	public void traiter(Recensement recensement, Scanner scanner) throws FunctionalException {

		System.out.println("Veuillez saisir un nombre de régions:");
		String nbRegionsStr = scanner.nextLine();
		if (!NumberUtils.isDigits(nbRegionsStr)) {
			throw new NotNumberException("Le nombre de régions doit être un entier.");
		}
		int nbRegions = Integer.parseInt(nbRegionsStr);

		// On récupére la liste des villes du recensement
		List<Ville> villes = recensement.getVilles();

		// On créé une HashMap pour stocker les régions
		// - Clé: nom de la région
		// - Valeur: instance de région
		Map<String, Region> mapRegions = new HashMap<>();

		// On parcourt les 35800 villes, une par une
		for (Ville ville : villes) {

			// On regarde si pour une ville donnée, la région existe dans la map ou non
			Region region = mapRegions.get(ville.getNomRegion());

			// Si la région n'existe pas, on la créée
			if (region == null) {
				region = new Region(ville.getCodeRegion(), ville.getNomRegion());
				mapRegions.put(ville.getNomRegion(), region);
			}

			// Une fois qu'on a une région, on lui ajoute la ville courante
			region.addVille(ville);
		}

		// Une fois la boucle terminée, on va récupérer toutes les régions qui sont dans
		// la HashMap pour les mettre dans une liste
		List<Region> regions = new ArrayList<Region>();
		regions.addAll(mapRegions.values());

		// On créé un comparateur de Region pour trier la liste des régions dans l'ordre
		// de populations décroissantes.
		Collections.sort(regions, new EnsemblePopComparateur(false));

		// On affiche les 10 premiére régions de la liste triée.
		for (int i = 0; i < nbRegions && i<regions.size(); i++) {
			Region region = regions.get(i);
			System.out.println("Region " + region.getNom() + " : " + region.getPopulation() + " habitants.");
		}

	}

}
