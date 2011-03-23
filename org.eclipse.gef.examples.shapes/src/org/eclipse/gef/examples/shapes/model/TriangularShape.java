package org.eclipse.gef.examples.shapes.model;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

// MODIFIED by Wouter & Ken
/**
 * The whole class was added: TRIANGLE_ICON, serialVersionUID, getIcon() and toString()
 *	were added in accordance with the other shapes (EllipticalShape, RectangularShape, ...)
 */
public class TriangularShape extends Shape {

// MODIFIED by Ken & Wouter
/**	The default color for this type of Shape. */
private static final RGB defaultColor = new RGB(0, 0, 100);

// MODIFIED by Ken & Wouter
/**	The member 'color' added to Shape is set to the default color during creation. */
public TriangularShape() {
	color = defaultColor;
}

/** A 16x16 pictogram of a triangular shape. */
private static final Image TRIANGLE_ICON = createImage("icons/triangle16.gif");

private static final long serialVersionUID = 1;

public Image getIcon() {
	return TRIANGLE_ICON;
}

public String toString() {
	return "Triangle " + hashCode();
}
}
