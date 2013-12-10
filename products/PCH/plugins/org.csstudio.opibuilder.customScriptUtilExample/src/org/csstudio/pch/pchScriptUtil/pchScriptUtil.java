package org.csstudio.pch.pchScriptUtil;

import org.csstudio.opibuilder.scriptUtil.ConsoleUtil;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.swt.widgets.Display;

/**A BOY ScriptUtil example.
 * @author Xihui Chen
 *
 */
public class pchScriptUtil {
	
	public static void helloWorld(){
		ConsoleUtil.writeInfo("Hello World!");
		MessageDialog.openInformation(Display.getCurrent().getActiveShell(), "Hello", 
				"PchScriptUtil says: Hello World!");
	}

}
