package fr.exp.patterns.visitor.visitors;

import fr.exp.patterns.visitor.visitedelements.AbstractElement;
import fr.exp.patterns.visitor.visitedelements.extended.Element;
import fr.exp.patterns.visitor.visitedelements.extended.ElementContainer;
import fr.exp.patterns.visitor.visitedelements.extended.TheMostSimpleElement;

public class SetOfActionDoImpl implements ISetOfAction {

	@Override
	public void performOn(TheMostSimpleElement el) {
		// CSV
		System.out.println("Do " + el.getName());
	}

	@Override
	public void performOn(Element el) {
		// XLS
		System.out.println("Do " + el.getName());
	}

	@Override
	public void performOn(ElementContainer el) {
		// PDF
		System.out.println("Do " + el.getName());
	}

	@Override
	public void performOn(AbstractElement aEl) {
		try {
			throw new Exception("PerformOn depuis la classe abstraite");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
