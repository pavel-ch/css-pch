/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.pch.eventlist.gui;

import org.csstudio.pch.eventlist.model.Message;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.widgets.Composite;

/** CellLabelProvider that fills cell with property of a Message
 *  and provides coloring assuming that the column displays
 *  a severity.
 *  @author Kay Kasemir
 */
public class SeverityLabelProvider extends PropertyLabelProvider
{
	/** Mapping of severities to colors */
	final private SeverityColumnPreference color_prefs;
	
	/** Constructor
	 *  @param property Message property to display in column
	 *  @param parent Parent widget, used to register DisposeListener
	 *                because we need to dispose the colors
	 *  @throws Exception
	 */
	public SeverityLabelProvider(final String property,
			final Composite parent) throws Exception
	{
		super(property);
		color_prefs = new SeverityColumnPreference(parent);
	}

    @Override
    public void update(ViewerCell cell)
    {
        final Message message = (Message) cell.getElement();
        final String severity = message.getProperty(property);
		cell.setText(severity);
		final Color color = color_prefs.getColor(severity);
		if (color != null)
			cell.setBackground(color);
    }
}
