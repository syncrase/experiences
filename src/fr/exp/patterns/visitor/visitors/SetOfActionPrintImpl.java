package fr.exp.patterns.visitor.visitors;

import fr.exp.patterns.visitor.visitedelements.AbstractElement;
import fr.exp.patterns.visitor.visitedelements.extended.Element;
import fr.exp.patterns.visitor.visitedelements.extended.ElementContainer;
import fr.exp.patterns.visitor.visitedelements.extended.TheMostSimpleElement;

public class SetOfActionPrintImpl implements ISetOfAction {

	@Override
	public void performOn(TheMostSimpleElement el) {
		System.out.print(el.getName());
	}

	@Override
	public void performOn(Element el) {
		System.out.print(el.getName());
	}

	@Override
	public void performOn(ElementContainer el) {
		System.out.print(el.getName());
	}

	@Override
	public void performOn(AbstractElement aEl) {
		System.out.print(aEl.getName());

	}

}
