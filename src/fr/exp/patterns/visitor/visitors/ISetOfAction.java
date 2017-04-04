package fr.exp.patterns.visitor.visitors;

import fr.exp.patterns.visitor.elements.Element1;
import fr.exp.patterns.visitor.elements.Element2;
import fr.exp.patterns.visitor.elements.Element3;
import fr.exp.patterns.visitor.elements.Element4;

public interface ISetOfAction {
	void performOn(Element1 el);

	void performOn(Element2 el);

	void performOn(Element3 el);

	void performOn(Element4 el);
}
