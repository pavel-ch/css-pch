/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.pch.widgets;

//import java.beans.PropertyChangeEvent;
//import java.beans.PropertyChangeListener;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.csstudio.opibuilder.editparts.AbstractPVWidgetEditPart;
import org.csstudio.opibuilder.model.AbstractPVWidgetModel;
//import org.csstudio.opibuilder.model.AbstractWidgetModel;
import org.csstudio.opibuilder.properties.IWidgetPropertyChangeHandler;
import org.csstudio.opibuilder.util.OPIColor;
import org.csstudio.pch.widgets.FaultIndicatorFigure;
import org.csstudio.pch.widgets.FaultIndicatorModel;
import org.csstudio.simplepv.VTypeHelper;
import org.csstudio.pch.widgets.FaultIndicatorFigure.FaultIndicatorLabelPosition;
import org.csstudio.ui.util.CustomMediaFactory;
import org.eclipse.draw2d.IFigure;
import org.epics.vtype.VType;

/**
 * FaultIndicator EditPart
 * @author Xihui Chen
 *
 */
public class FaultIndicatorEditPart extends AbstractPVWidgetEditPart{

	@Override
	protected IFigure doCreateFigure() {
		final FaultIndicatorModel model = getWidgetModel();

		FaultIndicatorFigure FaultIndicator = new FaultIndicatorFigure();
		
		FaultIndicator.setShowBinLabel(model.isShowBinLabel());
		FaultIndicator.setLabel(model.getLabel0());
		FaultIndicator.setColor0(model.getColor0());
		FaultIndicator.setColor1(model.getColor1());
		FaultIndicator.setFont(CustomMediaFactory.getInstance().getFont(
				model.getFont().getFontData()));
		FaultIndicator.setBinLabelPosition(model.getBinLabelPosition());
		FaultIndicator.setEffect3D(model.isEffect3D());
		model.setPropVisibilityAuto(model.isFaultIndicatorAuto());
		FaultIndicator.setFaultIndicatorAuto(model.isFaultIndicatorAuto());
		return FaultIndicator;
		
	}

	@Override
	public FaultIndicatorModel getWidgetModel() {
		return (FaultIndicatorModel)getModel();
	}
	
	@Override
	protected void registerPropertyChangeHandlers() {
		// value
		IWidgetPropertyChangeHandler handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				if(newValue == null || !(newValue instanceof VType))
					return false;
				FaultIndicatorFigure figure = (FaultIndicatorFigure) refreshableFigure;
				
				updateFromValue((VType) newValue, figure);
				return true;
			}


		};
		setPropertyChangeHandler(AbstractPVWidgetModel.PROP_PVVALUE, handler);

		// show bool label
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				FaultIndicatorFigure figure = (FaultIndicatorFigure) refreshableFigure;
				figure.setShowBinLabel((Boolean) newValue);
				return true;
			}
		};
		setPropertyChangeHandler(FaultIndicatorModel.PROP_FAULTINDICATOR_SHOW_LABEL, handler);

		//  bool label position
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue, final IFigure refreshableFigure) {
				FaultIndicatorFigure figure = (FaultIndicatorFigure) refreshableFigure;
				figure.setBinLabelPosition(FaultIndicatorLabelPosition.values()[(Integer)newValue]);
				return false;
			}
		};
		setPropertyChangeHandler(FaultIndicatorModel.PROP_FAULTINDICATOR_LABEL_POS, handler);
	
		// label
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				FaultIndicatorFigure figure = (FaultIndicatorFigure) refreshableFigure;
				figure.setLabel((String) newValue);
				return true;
			}
		};
		setPropertyChangeHandler(FaultIndicatorModel.PROP_LABEL, handler);

		// off color
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				FaultIndicatorFigure figure = (FaultIndicatorFigure) refreshableFigure;
				figure.setColor0(((OPIColor) newValue).getSWTColor());
				return true;
			}
		};
		setPropertyChangeHandler(FaultIndicatorModel.PROP_COLOR_0, handler);

		// on color
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				FaultIndicatorFigure figure = (FaultIndicatorFigure) refreshableFigure;
				figure.setColor1(((OPIColor) newValue).getSWTColor());
				return true;
			}
		};
		setPropertyChangeHandler(FaultIndicatorModel.PROP_COLOR_1, handler);
		
		//effect 3D
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				FaultIndicatorFigure FaultIndicator = (FaultIndicatorFigure) refreshableFigure;
				FaultIndicator.setEffect3D((Boolean) newValue);
				return true;
			}
		};
		setPropertyChangeHandler(FaultIndicatorModel.PROP_EFFECT3D, handler);	
		
		// FaultIndicator Auto
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				FaultIndicatorFigure FaultIndicator = (FaultIndicatorFigure) refreshableFigure;
				FaultIndicator.setFaultIndicatorAuto((Boolean) newValue);
				/*if(!(Boolean)newValue){
					int width = Math.min(getWidgetModel().getWidth(), getWidgetModel().getHeight());
					getWidgetModel().setSize(width, width);
				}*/
				return true;
			}
		};
		setPropertyChangeHandler(FaultIndicatorModel.PROP_FAULTINDICATOR_AUTO, handler);	
		
		// FaultIndicator Auto
		getWidgetModel().getProperty(FaultIndicatorModel.PROP_FAULTINDICATOR_AUTO).addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				getWidgetModel().setPropVisibilityAuto((Boolean) evt.getNewValue());
			}
		});

	}

	/* (non-Javadoc)
	 * @see org.csstudio.opibuilder.editparts.AbstractPVWidgetEditPart#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		if(value instanceof Number)
			((FaultIndicatorFigure)getFigure()).setValue(((Number)value).doubleValue());
		else if (value instanceof Boolean)
			((FaultIndicatorFigure)getFigure()).setIntValue((int)value);
		else 
			super.setValue(value);
	}

	@Override
	public Integer getValue() {
		return ((FaultIndicatorFigure)getFigure()).getIntValue();
	}
	/**
	 * @param newValue
	 * @param figure
	 */
	private void updateFromValue(final VType newValue,
			FaultIndicatorFigure figure) {
		if(newValue == null)
			return;
		figure.setValue(VTypeHelper.getDouble(newValue));
	}
}
