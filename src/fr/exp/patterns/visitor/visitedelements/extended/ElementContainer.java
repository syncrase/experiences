package fr.exp.patterns.visitor.visitedelements.extended;

import java.util.ArrayList;

import fr.exp.patterns.visitor.visitedelements.AbstractElement;
import fr.exp.patterns.visitor.visitedelements.IElement;
import fr.exp.patterns.visitor.visitors.ISetOfAction;

public class ElementContainer extends AbstractElement {

	public ElementContainer(String name) {
		super();
		this.name = name;

		// Create new Array of elements

//		this.childElements = ;
		this.resetChildren(new ArrayList<IElement>());
		
		this.addChild(new Element("Albert"));
		this.addChild(new TheMostSimpleElement("Alain"));

		IElement el = new Element("Robert");
		ArrayList<IElement> children = new ArrayList<IElement>();
		children.add(new Element("Robin"));
		children.add(new Element("Roger"));
		children.add(new Element("Rivoli"));
		children.add(new Element("Rollo"));
		children.add(new Element("Richard"));
		el.resetChildren(children);
		el.addChild(new TheMostSimpleElement("Rolland"));
		
		this.addChild(el);

	}

//	@Override
//	public void performSetOfAction(ISetOfAction setOfAction) {
//		setOfAction.performOn(this);
//		System.out.print("{");
//		
//		
//		// Appel du code commun � tous les IElement
//		for (IElement element : this.childElements) {
//			element.performSetOfAction(setOfAction);
//		}
//		// This signature must appears in the ISetOfAction.java
//		System.out.print("}");
//
//	}
}