package com.je.gui;

/**
 * A screen is a displayable container that
 * can contain GUI components.
 */
public interface Screen {
	/**
	 * Sets the screen visible and set the previous
	 * screen invisible if it exists.
	 */
	void setVisible();
}