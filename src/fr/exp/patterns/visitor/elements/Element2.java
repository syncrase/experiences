package fr.exp.patterns.visitor.elements;

import fr.exp.patterns.visitor.visitors.ISetOfAction;

public class Element2 implements IElement {
	private String name;

	public Element2(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	@Override
	public void performSetOfAction(ISetOfAction setOfAction) {
		setOfAction.performOn(this);
	}

}
