package fr.diginamic.recensement.services;

import java.util.Scanner;

import fr.diginamic.recensement.entites.Recensement;
import fr.diginamic.recensement.exceptions.InputException;

/**
 * Classe représentant un service
 * 
 * @author DIGINAMIC
 *
 */
public abstract class MenuService {

	/**
	 * Méthode abstraite de traitement que doivent posséder toutes les méthodes de
	 * services
	 * 
	 * @param recensement  lignes du fichier
	 * @param scanner scanner
	 */
	public abstract void traiter (Recensement recensement, Scanner scanner) throws Exception;
}
