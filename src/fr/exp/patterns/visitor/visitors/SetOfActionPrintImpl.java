package fr.exp.patterns.visitor.visitors;

import fr.exp.patterns.visitor.elements.Element1;
import fr.exp.patterns.visitor.elements.Element2;
import fr.exp.patterns.visitor.elements.Element3;
import fr.exp.patterns.visitor.elements.Element4;

public class SetOfActionPrintImpl implements ISetOfAction {

	@Override
	public void performOn(Element1 el) {
		System.out.println("Print " + el.getName());
	}

	@Override
	public void performOn(Element2 el) {
		System.out.println("Print " + el.getName());
	}

	@Override
	public void performOn(Element3 el) {
		System.out.println("Print " + el.getName());
	}

	@Override
	public void performOn(Element4 el) {
		System.out.println("Print " + el.getName());
	}

}
