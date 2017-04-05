package fr.exp.patterns.visitor.visitedelements;

import java.util.ArrayList;

import fr.exp.patterns.visitor.visitors.ISetOfAction;

/**
 * Cette classe abstraite permet d'h�riter de l'ensemble impl�ment� des m�thodes
 * de l'interface
 * 
 * @author I310911
 *
 */
public abstract class AbstractElement implements IElement {

	public String name;

	public ArrayList<IElement> childElements;

	public AbstractElement() {
		super();
		this.name = "";
		this.childElements = new ArrayList<IElement>();
	}

	public String getName() {
		return name;
	}

	@Override
	public void performSetOfAction(ISetOfAction setOfAction) {
		// // Tous les Elements peuvent avoir un comportement diff�rents (code
		// // �crit ici) mais ne sont finalement trait�s que dans
		// l'impl�mentation
		// //
		// // Some code before...
		// //
		// for (IElement element : childElements) {
		// element.performSetOfAction(setOfAction);
		// }
		// //
		// // Some code after...
		// //
		// setOfAction.performOn(this);

		// This signature must appears in the ISetOfAction.java
		setOfAction.performOn(this);

		// Appel du code commun � tous les IElement
		if (this.childElements != null && this.childElements.size() > 0) {
			System.out.print(" p�re de {");
			// for (IElement element : this.childElements) {
			// element.performSetOfAction(setOfAction);
			// }
			
			//TODO: heap space issue!?
			for (int i = 0; i++ < this.childElements.size(); this.childElements.get(i)
					.performSetOfAction(setOfAction)) {

			}
			System.out.print("}");
		}

	}

	@Override
	public void resetChildren(ArrayList<IElement> childs) {
		this.childElements = childs;
	}

	@Override
	public void addChild(IElement element) {
		this.childElements.add(element);
	}
}
