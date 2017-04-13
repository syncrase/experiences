package fr.exp.files.pearltrees.composite;

import java.util.ArrayList;
import java.util.List;

public class PearltreesComposite implements PearltreesComponent {

	// Collection of child Component
	private List<PearltreesComponent> childComponent = new ArrayList<PearltreesComponent>();

	@Override
	public String printAsHtml() {
		return null;
	}

	// Adds the graphic to the composition.
	public void add(PearltreesComponent component) {
		childComponent.add(component);
	}

	// Removes the graphic from the composition.
	public void remove(PearltreesComponent component) {
		childComponent.remove(component);
	}

}
