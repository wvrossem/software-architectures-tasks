package org.eclipse.gef.examples.shapes.model.commands;

import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.examples.shapes.model.Shape;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.gef.requests.ChangeBoundsRequest;
import org.eclipse.gef.requests.DirectEditRequest;

// MODIFIED by Wouter & Ken
/**
 * The whole class was added to be able to set shape colors, again with undo/redo in mind.
 */
public class ShapeSetColorCommand extends Command {
	
	/** Stores the old color */
	private RGB oldColor;
	/** Stores the new color */
	private final RGB newColor;
	/** A request to do a direct edit */
	private final DirectEditRequest request;
	
	/** Shape to manipulate. */
	private final Shape shape;
	
	public ShapeSetColorCommand(Shape shape, DirectEditRequest req, RGB newColor) {
		if (shape == null || req == null || newColor == null) {
			throw new IllegalArgumentException();
		}
		this.shape = shape;
		this.request = req;
		this.newColor = newColor;
		setLabel("change color");
	}
	
	@Override
	public boolean canExecute() {
		// TODO Auto-generated method stub
		return super.canExecute();
	}

	public void execute() {
		oldColor = new RGB(shape.getColor().red, shape.getColor().green, shape.getColor().blue);
		shape.setColor(newColor);
	}

	public void redo() {
		shape.setColor(newColor);
	}

	public void undo() {
		shape.setColor(oldColor);
	}

}
