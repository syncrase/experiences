package fr.exp.patterns.decorator;

public class Sprinkles extends CoffeeDecorator {
	public Sprinkles(Coffee decoratedCoffee) {
		super(decoratedCoffee);
	}

	public double getCost() {
		return super.getCost() + 0.2;
	}

	public String getIngredients() {
		return super.getIngredients() + ingredientSeparator + "Sprinkles";
	}
}
