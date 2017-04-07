package fr.exp.files;

import java.util.Map;

public class Balise {

	/**
	 * Nom de la balise. (doctype, ul, div, etc.)
	 */
	private String balise;
	/**
	 * Map des attributs et des valeurs associées
	 */
	private Map<String, String> attributs;
	private String content;
	private boolean baliseOuverte;

}
