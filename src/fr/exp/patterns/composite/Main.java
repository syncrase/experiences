package fr.exp.patterns.composite;

public class Main {
	public static void main(String[] args) {
		// Initialize four ellipses
		EllipseLEAF ellipse1 = new EllipseLEAF("ellipse1");
		EllipseLEAF ellipse2 = new EllipseLEAF("ellipse2");
		EllipseLEAF ellipse3 = new EllipseLEAF("ellipse3");
		EllipseLEAF ellipse4 = new EllipseLEAF("ellipse4");
		// Initialize three composite graphics
		CompositeGraphic graphic = new CompositeGraphic("graphic");
		CompositeGraphic graphic1 = new CompositeGraphic("graphic1");
		CompositeGraphic graphic2 = new CompositeGraphic("graphic2");
		// Composes the graphics
		graphic1.add(ellipse1);
		graphic1.add(ellipse2);
		graphic1.add(ellipse3);
		graphic2.add(ellipse4);
		graphic.add(graphic1);
		graphic.add(graphic2);
		// Prints the complete graphic (four times the string "Ellipse").
		graphic.print();
	}
}
