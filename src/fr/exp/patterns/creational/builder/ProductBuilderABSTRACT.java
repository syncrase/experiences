package fr.exp.patterns.creational.builder;

abstract class ProductBuilderABSTRACT {
	protected Product product;

	public abstract void buildType1();

	public abstract void buildType2();

	public abstract void buildType3();

	public void createNewProduct() {
		this.product = new Product();
	}

	public Product getProduct() {
		return this.product;
	}

}
