/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.pch.eventlist;

import org.csstudio.pch.eventlist.gui.GUI;
import org.csstudio.pch.eventlist.model.Model;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.junit.Test;

/** JUnit test of GUI as standalone SWT app.
 *  @author Kay Kasemir
 */
@SuppressWarnings("nls")
public class GUIDemo
{
    /** Initial window size */
    private static final int WIDTHS = 1000, HEIGHT = 800;


    @Test
    public void testGUI()
    {
        try
        {
            final Display display = new Display();
            final Shell shell = new Shell(display);
            final Rectangle screen = display.getBounds();
            shell.setBounds((screen.width-WIDTHS)/2,
                    (screen.height-HEIGHT)/2, WIDTHS, HEIGHT);

            final Model model = new Model(MessageRDBTest.URL, MessageRDBTest.USER, MessageRDBTest.PASSWORD, MessageRDBTest.SCHEMA, 1000, shell);
            new GUI(null, shell, model);

            shell.open();

            model.setTimerange("-1 days", "now");

            // Message loop left to the application
            while (!shell.isDisposed())
                if (!display.readAndDispatch())
                    display.sleep();
            display.dispose(); // !
        }
        catch (Throwable ex)
        {
            ex.printStackTrace();
        }
    }
}
