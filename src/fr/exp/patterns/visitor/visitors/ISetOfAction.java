package fr.exp.patterns.visitor.visitors;

import fr.exp.patterns.visitor.visitedelements.AbstractElement;
import fr.exp.patterns.visitor.visitedelements.extended.Element;
import fr.exp.patterns.visitor.visitedelements.extended.ElementContainer;
import fr.exp.patterns.visitor.visitedelements.extended.TheMostSimpleElement;

public interface ISetOfAction {

	void performOn(AbstractElement aEl);

	void performOn(TheMostSimpleElement el);

	void performOn(Element el);

	void performOn(ElementContainer el);
}
