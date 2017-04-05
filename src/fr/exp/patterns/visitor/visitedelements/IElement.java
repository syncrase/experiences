package fr.exp.patterns.visitor.visitedelements;

import java.util.ArrayList;

import fr.exp.patterns.visitor.visitors.ISetOfAction;

/**
 * Permet de prendre en compte la classe interfa��e dans les �l�ments
 * @author I310911
 *
 */
public interface IElement {
	/**
	 * Cette m�thode est, par convention, cens�e appeler toute les autres m�mes m�thodes dans les Elements fils et finalement appeler la m�thode performOn(?)
	 * @param Elementvisitor
	 */
	void performSetOfAction(ISetOfAction Elementvisitor); // Elements have to provide
													// accept().

	/**
	 * Un Element � n�cessaire une liste contenant tout ses parents
	 * @param childs
	 */
	void resetChildren(ArrayList<IElement> childs);

	void addChild(IElement element);
}
