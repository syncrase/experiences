package fr.exp.patterns.visitor.elements;

import fr.exp.patterns.visitor.visitors.ISetOfAction;

public class Element4 implements IElement {

	IElement[] iElements;
	private String name;

	public Element4(String name) {
		super();
		this.name = name;

		// Create new Array of elements
		IElement el1_1 = new Element1("Instance 1 of Element1");
		IElement el1_2 = new Element1("Instance 2 of Element1");
		IElement el1_3 = new Element1("Instance 3 of Element1");

		this.iElements = new IElement[] { el1_1, el1_2, el1_3, new Element2("An instance of Element2"),
				new Element3("An instance of Element3"), new Element3("An instance of Element3") };
	}

	public String getName() {
		return this.name;
	}

	@Override
	public void performSetOfAction(ISetOfAction setOfAction) {

		for (IElement element : iElements) {
			element.performSetOfAction(setOfAction);
		}
		setOfAction.performOn(this);

	}

}
