/*******************************************************************************
 * Copyright (c) 2013 Pavel Charvat
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.pch.widgets;

import org.csstudio.opibuilder.model.AbstractPVWidgetModel;
import org.csstudio.opibuilder.properties.BooleanProperty;
import org.csstudio.opibuilder.properties.ColorProperty;
import org.csstudio.opibuilder.properties.ComboProperty;
import org.csstudio.opibuilder.properties.StringProperty;
import org.csstudio.opibuilder.properties.WidgetPropertyCategory;
import org.csstudio.pch.widgets.BinaryFigure.BinaryLabelPosition;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

/**
 * The widget model for LED.
 * @author Pavel Charvat
 *
 */
public class BinaryModel extends AbstractPVWidgetModel {
	
	/** Label text when boolean widget is off. */
	public static final String PROP_LABEL_0 = "label_0"; //$NON-NLS-1$
	
	/** Label text when boolean widget is on. */
	public static final String PROP_LABEL_1 = "label_1"; //$NON-NLS-1$
	
	/** Label text when boolean widget is dif. */
	public static final String PROP_LABEL_2 = "label_2"; //$NON-NLS-1$
	
	/** Label text when boolean widget is fail. */
	public static final String PROP_LABEL_3 = "label_3"; //$NON-NLS-1$
	
	/** Widget color when boolean widget is off. */
	public static final String PROP_COLOR_0 = "color_0"; //$NON-NLS-1$
	
	/** Widget color when boolean widget is on. */
	public static final String PROP_COLOR_1 = "color_1"; //$NON-NLS-1$
	
	/** Widget color when boolean widget is on. */
	public static final String PROP_COLOR_2 = "color_2"; //$NON-NLS-1$
	
	/** Widget color when boolean widget is on. */
	public static final String PROP_COLOR_3 = "color_3"; //$NON-NLS-1$
	
	/** True if the boolean label should be visible. */
	public static final String PROP_BINARY_SHOW_LABEL = "binary_show_label"; //$NON-NLS-1$
	
	public static final String PROP_BINARY_LABEL_POS = "binary_label_position"; //$NON-NLS-1$
	
	/** The default color of the on color property. */
	private static final RGB DEFAULT_COLOR_1 = new RGB(0,255,0);
	/** The default color of the on color property. */
	private static final RGB DEFAULT_COLOR_2 = new RGB(255,255,0);
	/** The default color of the on color property. */
	private static final RGB DEFAULT_COLOR_3 = new RGB(255,0,0);
	/** The default color of the off color property. */
	private static final RGB DEFAULT_COLOR_0 = new RGB(0, 100 ,0);
	
	/** The default string of the off label property. */
	private static final String DEFAULT_LABEL_0 = "OFF";
	/** The default string of the on label property. */
	private static final String DEFAULT_LABEL_1 = "ON";
	/** The default string of the on label property. */
	private static final String DEFAULT_LABEL_2 = "DIFF";
	/** The default string of the on label property. */
	private static final String DEFAULT_LABEL_3 = "FAIL";
	/** The ID of the effect 3D property. */
	public static final String PROP_EFFECT3D = "effect_3d"; //$NON-NLS-1$
	
	/** The ID of the square LED property. */
	public static final String PROP_BINARY_AUTO = "binary_auto"; //$NON-NLS-1$
	
	
	/** The default value of the height property. */	
	private static final int DEFAULT_HEIGHT = 20;
	
	/** The default value of the width property. */
	private static final int DEFAULT_WIDTH = 40;
	
	public static final int MINIMUM_SIZE = 10;

	
	public BinaryModel() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setScaleOptions(true, true, true);
	}
	
	@Override
	protected void configureProperties() {
		addProperty(new BooleanProperty(PROP_BINARY_SHOW_LABEL, "Show Label",
				WidgetPropertyCategory.Display, true));		
		addProperty(new StringProperty(PROP_LABEL_0, "Label 0",
				WidgetPropertyCategory.Display, DEFAULT_LABEL_0));	
		addProperty(new StringProperty(PROP_LABEL_1, "Label 1",
				WidgetPropertyCategory.Display, DEFAULT_LABEL_1));	
		addProperty(new StringProperty(PROP_LABEL_2, "Label 2",
				WidgetPropertyCategory.Display, DEFAULT_LABEL_2));	
		addProperty(new StringProperty(PROP_LABEL_3, "Label 3",
				WidgetPropertyCategory.Display, DEFAULT_LABEL_3));	
		addProperty(new ColorProperty(PROP_COLOR_0, "Color 0",
				WidgetPropertyCategory.Display, DEFAULT_COLOR_0));		
		addProperty(new ColorProperty(PROP_COLOR_1, "Color 1",
				WidgetPropertyCategory.Display, DEFAULT_COLOR_1));
		addProperty(new ColorProperty(PROP_COLOR_2, "Color 2",
				WidgetPropertyCategory.Display, DEFAULT_COLOR_2));
		addProperty(new ColorProperty(PROP_COLOR_3, "Color 3",
				WidgetPropertyCategory.Display, DEFAULT_COLOR_3));
		addProperty(new ComboProperty(PROP_BINARY_LABEL_POS, "Label Position", 
				WidgetPropertyCategory.Display, BinaryLabelPosition.stringValues(), 0));
		
		addProperty(new BooleanProperty(PROP_EFFECT3D, "3D Effect", 
				WidgetPropertyCategory.Display, true));
		
		addProperty(new BooleanProperty(PROP_BINARY_AUTO, "Automatic colors (script)", 
				WidgetPropertyCategory.Display, false));
		//setPropertyVisible(PROP_BINARY_LABEL_POS, false);
	}
	/**
	 * The ID of this widget model.
	 */
	public static final String ID = "org.csstudio.pch.widgets.Binary"; //$NON-NLS-1$	
	
	@Override
	public String getTypeID() {
		return ID;
	}

	/**
	 * @return the off label
	 */
	public String getLabel0() {
		return (String) getProperty(PROP_LABEL_0).getPropertyValue();
	}
	
	/**
	 * @return the on label
	 */
	public String getLabel1() {
		return (String) getProperty(PROP_LABEL_1).getPropertyValue();
	}
	
	/**
	 * @return the diff label
	 */
	public String getLabel2() {
		return (String) getProperty(PROP_LABEL_2).getPropertyValue();
	}
	
	/**
	 * @return the fail label
	 */
	public String getLabel3() {
		return (String) getProperty(PROP_LABEL_3).getPropertyValue();
	}

	/**
	 * @return the off color
	 */
	public Color getColor0() {
		return getSWTColorFromColorProperty(PROP_COLOR_0);
	}	
	/**
	 * @return the on color
	 */
	public Color getColor1() {
		return getSWTColorFromColorProperty(PROP_COLOR_1);
	}	
	/**
	 * @return the on color
	 */
	public Color getColor2() {
		return getSWTColorFromColorProperty(PROP_COLOR_2);
	}	
	/**
	 * @return the on color
	 */
	public Color getColor3() {
		return getSWTColorFromColorProperty(PROP_COLOR_3);
	}	
	
	/**
	 * @return true if the boolean label should be shown, false otherwise
	 */
	public boolean isShowBinLabel() {
		return (Boolean) getProperty(PROP_BINARY_SHOW_LABEL).getPropertyValue();
	}
	
	public BinaryLabelPosition getBinLabelPosition(){
		return BinaryLabelPosition.values()
				[(Integer)getPropertyValue(PROP_BINARY_LABEL_POS)];
	}
	
	/**
	 * @return true if the widget would be painted with 3D effect, false otherwise
	 */
	public boolean isEffect3D() {
		return (Boolean) getProperty(PROP_EFFECT3D).getPropertyValue();
	}
	
	/**
	 * @return true if the Binary is automatic, false otherwise
	 */
	public boolean isBinaryAuto() {
		return (Boolean) getProperty(PROP_BINARY_AUTO).getPropertyValue();
	}
}
