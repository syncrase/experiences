package fr.exp.patterns.visitor.elements;

import fr.exp.patterns.visitor.visitors.ISetOfAction;

public interface IElement {
	void performSetOfAction(ISetOfAction Elementvisitor); // Elements have to provide
													// accept().
}
