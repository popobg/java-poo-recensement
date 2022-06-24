package fr.diginamic.recensement.services;

import java.util.List;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.entites.Ville;
import fr.diginamic.recensement.exceptions.FunctionalException;
import fr.diginamic.recensement.exceptions.IllegalInputException;

/** Recherche et affichage de la population d'un département
 * @author DIGINAMIC
 *
 */
public class RecherchePopulationDepartementService extends MenuService {

	@Override
	public void traiter(Recensement rec, Scanner scanner) throws FunctionalException {
		
		System.out.println("Quel est le code du département recherché ? ");
		String choix = scanner.nextLine();
		if (StringUtils.isEmpty(choix)) {
			throw new IllegalInputException("Le code du département doit être renseigné.");
		}
		
		List<Ville> villes = rec.getVilles();
		int somme = 0;
		for (Ville ville: villes){
			if (ville.getCodeDepartement().equalsIgnoreCase(choix)){
				somme+=ville.getPopulation();
			}
		}
		if (somme>0){
			System.out.println("Population du département "+choix+" : "+ somme);
		}
		else {
			System.out.println("Département "+choix+ " non trouvé.");
		}
	}

}
