package fr.exp.patterns.visitor;

import fr.exp.patterns.visitor.elements.Element4;
import fr.exp.patterns.visitor.elements.IElement;
import fr.exp.patterns.visitor.visitors.ISetOfAction;
import fr.exp.patterns.visitor.visitors.SetOfActionDoImpl;
import fr.exp.patterns.visitor.visitors.SetOfActionPrintImpl;

public class Main {

	public static void main(String[] args) {

		// All Elements are created in this one.
		// The Element4 object contains a list of Element that implements IElement (performSetOfAction() method)
		IElement element = new Element4("My element which call another ones in his constructor");

		// Which action will I do on each object. An IElementVISITOR is a set of
		// action to perform specified for each IElement. With that I can change the all 
		ISetOfAction printVisitor = new SetOfActionPrintImpl();
		element.performSetOfAction(printVisitor);

		//Now I want to perform another set of actions
		ISetOfAction  doVisitor = new SetOfActionDoImpl();
		element.performSetOfAction(doVisitor);

	}

}
