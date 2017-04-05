package fr.exp.patterns.visitor.visitedelements;

import java.util.ArrayList;

import fr.exp.patterns.visitor.visitors.ISetOfAction;

/**
 * Permet de prendre en compte la classe interfaçée dans les éléments
 * @author I310911
 *
 */
public interface IElement {
	/**
	 * Cette méthode est, par convention, censée appeler toute les autres mêmes méthodes dans les Elements fils et finalement appeler la méthode performOn(?)
	 * @param Elementvisitor
	 */
	void performSetOfAction(ISetOfAction Elementvisitor); // Elements have to provide
													// accept().

	/**
	 * Un Element à nécessaire une liste contenant tout ses parents
	 * @param childs
	 */
	void resetChildren(ArrayList<IElement> childs);

	void addChild(IElement element);
}
