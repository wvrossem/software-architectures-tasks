/*******************************************************************************
 * Copyright (c) 2004, 2005 Elias Volanakis and others.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *    Elias Volanakis - initial API and implementation
 *******************************************************************************/
package org.eclipse.gef.examples.shapes.model;

import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.RGB;

/**
 * An elliptical shape.
 * @author Elias Volanakis
 */
public class EllipticalShape extends Shape {

// MODIFIED by Ken & Wouter
/**	The default color for this type of Shape. */
private static final RGB defaultColor = new RGB(100, 0, 0);

// MODIFIED by Ken & Wouter
/**	The member 'color' added to Shape is set to the default color during creation. */
public EllipticalShape() {
	color = defaultColor;
}
	
/** A 16x16 pictogram of an elliptical shape. */
private static final Image ELLIPSE_ICON = createImage("icons/ellipse16.gif");

private static final long serialVersionUID = 1;

public Image getIcon() {
	return ELLIPSE_ICON;
}

public String toString() {
	return "Ellipse " + hashCode();
}
}
