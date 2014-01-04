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
import org.csstudio.security.preferences.SecurePreferences;
import org.eclipse.core.runtime.Platform;
import org.eclipse.core.runtime.preferences.IPreferencesService;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.part.ViewPart;



/** Eclipse View for the Message History
 *  @author Kay Kasemir
 *  @author Xihui Chen
 */
public class MessageHistoryView extends ViewPart
{
    /** ID under which this view is registered in plugin.xml */
    final public static String ID = "org.csstudio.pch.eventlist.MessageHistoryView"; //$NON-NLS-1$
    
    @SuppressWarnings("nls")
    @Override
    public void createPartControl(final Composite parent)
    {
        try
        {
            // Read settings from preferences
            final IPreferencesService service = Platform.getPreferencesService();
            final String url =
                service.getString(Activator.ID, "rdb_url", null, null);
            final String user =
                SecurePreferences.get(Activator.ID, "rdb_user", null);           
            final String password =
            		SecurePreferences.get(Activator.ID, "rdb_password", null);          
            final String schema =
                service.getString(Activator.ID, "rdb_schema", null, null);
            
			final Model model = new Model(url, user, password, schema,
					Preferences.getMaxProperties(), getSite().getShell());
            final GUI gui = new GUI(getSite(), parent, model);

            // Trigger update
            model.setTimerange(model.getStartSpec(), model.getEndSpec());
            
            getSite().setSelectionProvider(gui.getSelectionProvider());
        }
        catch (Throwable ex)
        {
            MessageDialog.openError(parent.getShell(), "Error", ex.getMessage());
        }
    }

    @Override
    public void setFocus()
    {
        // NOP
    }
}
