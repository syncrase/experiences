package fr.exp.patterns.behavioral.visitor;

import fr.exp.patterns.behavioral.visitor.visitedelements.IElement;
import fr.exp.patterns.behavioral.visitor.visitedelements.extended.ElementContainer;
import fr.exp.patterns.behavioral.visitor.visitors.ISetOfAction;
import fr.exp.patterns.behavioral.visitor.visitors.SetOfActionPrintImpl;

public class Main {

	public static void main(String[] args) {

		System.out.println("[run] " + Main.class);
		// All Elements are created in this one.
		// The Element4 object contains a list of Element that implements
		// IElement (performSetOfAction() method)
		// Construction de la structure
		IElement element = new ElementContainer("Alfred");

		// Which action will I do on each object. An IElementVISITOR is a set of
		// action to perform specified for each IElement. With that I can change
		// the all
		ISetOfAction printVisitor = new SetOfActionPrintImpl();
		element.performSetOfAction(printVisitor);

		// Now I want to perform another set of actions
		// ISetOfAction doVisitor = new SetOfActionDoImpl();
		// element.performSetOfAction(doVisitor);

	}

}
