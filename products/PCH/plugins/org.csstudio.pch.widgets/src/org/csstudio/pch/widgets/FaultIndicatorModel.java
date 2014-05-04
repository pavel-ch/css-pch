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
import org.csstudio.pch.widgets.FaultIndicatorFigure.FaultIndicatorLabelPosition;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.RGB;

/**
 * The widget model for LED.
 * @author Pavel Charvat
 *
 */
public class FaultIndicatorModel extends AbstractPVWidgetModel {
	
	/** Label text when boolean widget is off. */
	public static final String PROP_LABEL = "label"; //$NON-NLS-1$
	
	/** Widget color when boolean widget is off. */
	public static final String PROP_COLOR_0 = "color_0"; //$NON-NLS-1$
	
	/** Widget color when boolean widget is on. */
	public static final String PROP_COLOR_1 = "color_1"; //$NON-NLS-1$
	
	/** True if the boolean label should be visible. */
	public static final String PROP_FAULTINDICATOR_SHOW_LABEL = "fault_indicator_show_label"; //$NON-NLS-1$
	
	public static final String PROP_FAULTINDICATOR_LABEL_POS = "fault_indicator_label_position"; //$NON-NLS-1$
	
	/** The default color of the on color property. */
	private static final RGB DEFAULT_COLOR_1 = new RGB(0,255,0);
	/** The default color of the off color property. */
	private static final RGB DEFAULT_COLOR_0 = new RGB(0, 100 ,0);
	
	/** The default string of the off label property. */
	private static final String DEFAULT_LABEL = "Label Text";

	/** The ID of the effect 3D property. */
	public static final String PROP_EFFECT3D = "effect_3d"; //$NON-NLS-1$
	
	/** The ID of the square LED property. */
	public static final String PROP_FAULTINDICATOR_AUTO = "fault_indicator_auto"; //$NON-NLS-1$
	
	
	/** The default value of the height property. */	
	private static final int DEFAULT_HEIGHT = 20;
	
	/** The default value of the width property. */
	private static final int DEFAULT_WIDTH = 80;
	
	public static final int MINIMUM_SIZE = 10;

	
	public FaultIndicatorModel() {
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		setScaleOptions(true, true, true);
	}
	
	@Override
	protected void configureProperties() {
		addProperty(new BooleanProperty(PROP_FAULTINDICATOR_SHOW_LABEL, "Show Label",
				WidgetPropertyCategory.Display, true));		
		addProperty(new StringProperty(PROP_LABEL, "Label",
				WidgetPropertyCategory.Display, DEFAULT_LABEL));	
		addProperty(new ColorProperty(PROP_COLOR_0, "Color Background 0",
				WidgetPropertyCategory.Display, DEFAULT_COLOR_0));		
		addProperty(new ColorProperty(PROP_COLOR_1, "Color Background 1",
				WidgetPropertyCategory.Display, DEFAULT_COLOR_1));
		addProperty(new ComboProperty(PROP_FAULTINDICATOR_LABEL_POS, "Label Position", 
				WidgetPropertyCategory.Display, FaultIndicatorLabelPosition.stringValues(), 0));
		
		addProperty(new BooleanProperty(PROP_EFFECT3D, "3D Effect", 
				WidgetPropertyCategory.Display, true));
		
		addProperty(new BooleanProperty(PROP_FAULTINDICATOR_AUTO, "Automatic colors (script)", 
				WidgetPropertyCategory.Display, false));
		
		setPropertyVisibleAndSavable(PROP_BACKCOLOR_ALARMSENSITIVE, false, false);
		setPropertyVisibleAndSavable(PROP_FORECOLOR_ALARMSENSITIVE, false, false);
		setPropertyVisibleAndSavable(PROP_BORDER_ALARMSENSITIVE, false, false);
		setPropertyValue(PROP_BORDER_ALARMSENSITIVE, false);
	}
	/**
	 * The ID of this widget model.
	 */
	public static final String ID = "org.csstudio.pch.widgets.FaultIndicator"; //$NON-NLS-1$	
	
	@Override
	public String getTypeID() {
		return ID;
	}

	/**
	 * @return the off label
	 */
	public String getLabel0() {
		return (String) getProperty(PROP_LABEL).getPropertyValue();
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
	 * @return true if the boolean label should be shown, false otherwise
	 */
	public boolean isShowBinLabel() {
		return (Boolean) getProperty(PROP_FAULTINDICATOR_SHOW_LABEL).getPropertyValue();
	}
	
	public FaultIndicatorLabelPosition getBinLabelPosition(){
		return FaultIndicatorLabelPosition.values()
				[(Integer)getPropertyValue(PROP_FAULTINDICATOR_LABEL_POS)];
	}
	
	/**
	 * @return true if the widget would be painted with 3D effect, false otherwise
	 */
	public boolean isEffect3D() {
		return (Boolean) getProperty(PROP_EFFECT3D).getPropertyValue();
	}
	
	/**
	 * @return true if the FaultIndicator is automatic, false otherwise
	 */
	public boolean isFaultIndicatorAuto() {
		return (Boolean) getProperty(PROP_FAULTINDICATOR_AUTO).getPropertyValue();
	}
	
	public void setPropVisibilityAuto(boolean Auto)
	{
        //Logger logger = Logger.getLogger(getClass().getName());
        //logger.log(Level.INFO, "Auto = ".concat(String.valueOf(Auto)).concat(" ").concat(getName()));
        //logger.log(Level.INFO, "PROP_LABEL_1 = ".concat(String.valueOf(model.setPropertyVisibleAndSavable(prop_id, visible, isSavable))).concat(" ").concat(model.getName()));

		if (Auto) {
			setPropertyVisible(FaultIndicatorModel.PROP_COLOR_0, false);
			setPropertyVisible(FaultIndicatorModel.PROP_COLOR_1, false);
			setPropertyVisible(FaultIndicatorModel.PROP_COLOR_BACKGROUND, true);
		}
		else {
			setPropertyVisible(FaultIndicatorModel.PROP_COLOR_0, true);
			setPropertyVisible(FaultIndicatorModel.PROP_COLOR_1, true);
			setPropertyVisible(FaultIndicatorModel.PROP_COLOR_BACKGROUND, false);
		}
	}
}
