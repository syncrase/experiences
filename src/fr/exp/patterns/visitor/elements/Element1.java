package fr.exp.patterns.visitor.elements;

import fr.exp.patterns.visitor.visitors.ISetOfAction;

public class Element1 implements IElement {

	private String name;

	public Element1(String name) {
		super();
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public void performSetOfAction(ISetOfAction setOfAction) {
		setOfAction.performOn(this);
	}

}
