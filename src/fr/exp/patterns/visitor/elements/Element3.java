package fr.exp.patterns.visitor.elements;

import fr.exp.patterns.visitor.visitors.ISetOfAction;

public class Element3 implements IElement {

	private String name;

	public Element3(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * Accept the visitor.
	 * This method will call the method visit(Element3)
	 * and not visit(Element1) nor visit(Element2)
	 * because <code>this</code> is declared as Element3.
	 * That's why we need to define this code in each element class.
	 */
	
	@Override
	public void performSetOfAction(ISetOfAction setOfAction) {
		setOfAction.performOn(this);
	}

}
