package fr.exp.files;

public class PearltreesFacade {

	// Voir quelle est la meilleure solution pour le pattern à utiliser
	// (static?, factory?, facade?, ...)
	// Pour l'instant, appel uniquement les méthodes opérant sur l'export de
	// PearlTrees

	/**
	 * 
	 * @param html
	 * @return Objet représentatif des données contenues dans le fichier html
	 *         issu de l'export PearlTrees.
	 */
	public PearltreesModel getPearltreesModelFromHtml(String html) {
		// PearltreesHandler.processHtml(html);

		/*
		 * Ici le code pour construire l'objet PearlTreesModel
		 */
		return null;
	}

	public String getHtmlFromPearltreesModel(PearltreesModel pearltreesData) {
		// pearltreesData.getHtml();// pattern visitor ???
		/*
		 * Ici le code pour construire le fichier html depuis l'objet PearltreesModel
		 */
		return null;
	}
}
