package fr.exp.patterns.factory;

public class SimpleFactory {

	public static SimpleFactory getNewSimpleFactory() {
		// The real goal of this patern is to let the factory decide which type
		// instanciate
		return new SimpleFactory();
	}

	private SimpleFactory() {

	}
}
