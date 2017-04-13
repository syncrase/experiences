package fr.exp.patterns.structural.decorator.impl;

public abstract class CoffeeDecorator extends Coffee {

	protected final Coffee decoratedCoffee;
	protected String ingredientSeparator = ", ";

	public CoffeeDecorator(Coffee decoratedCoffee) {
		this.decoratedCoffee = decoratedCoffee;
	}

	@Override
	public double getCost() {
		return decoratedCoffee.getCost();
	}

	@Override
	public String getIngredients() {
		return decoratedCoffee.getIngredients();
	}

}
