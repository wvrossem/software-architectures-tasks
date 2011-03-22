package org.eclipse.gef.examples.shapes.model;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

public class TriangularShape extends Shape {

// MODIFIED by Ken & Wouter
private static final RGB defaultColor = new RGB(0, 0, 100);
//public static RGB getDefaultColor() {
//	return new RGB(0, 0, 100);
//}
public TriangularShape() {
	color = defaultColor;
}

/** A 16x16 pictogram of a rectangular shape. */
private static final Image TRIANGLE_ICON = createImage("icons/triangle16.gif");

private static final long serialVersionUID = 1;

public Image getIcon() {
	return TRIANGLE_ICON;
}

public String toString() {
	return "Triangle " + hashCode();
}
}
