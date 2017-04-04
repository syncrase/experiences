package fr.exp.patterns.factory;

public abstract class AbstractConceptFactory {

	public static AbstractConceptFactory getAbstactConcept(int i) {
		// Decide which type to instanciate
		if (i == 1)
			return new ConcreteConcept1();
		return new ConcreteConcept2();
	}

	public abstract boolean is();

	public abstract String toString();

	private double sum(double d1, double d2) {
		return d1 + d2;
	}

	// We'll make it protected so that subclasses can see it
	protected String sayHello() {
		return "Hello, I'm an abstract class";
	}

	private static class ConcreteConcept1 extends AbstractConceptFactory {

		ConcreteConcept1() {

		}

		@Override
		public boolean is() {
			return false;
		}

		@Override
		public String toString() {
			return "ConcreteConcept1";
		}

		@Override
		public String sayHello() {
			return "Hello, I'm an ConcreteConcept1 class";
		}

	}

	private static class ConcreteConcept2 extends AbstractConceptFactory {

		ConcreteConcept2() {

		}

		@Override
		public boolean is() {
			return true;
		}

		@Override
		public String toString() {
			return "ConcreteConcept2";
		}

		@Override
		public String sayHello() {
			return "Hello, I'm an ConcreteConcept2 class";
		}

	}

}
