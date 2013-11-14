/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.pch.widgets;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import org.csstudio.opibuilder.model.AbstractWidgetModel;
import org.csstudio.opibuilder.properties.IWidgetPropertyChangeHandler;
import org.csstudio.opibuilder.widgets.editparts.AbstractBoolEditPart;
import org.csstudio.pch.widgets.BinaryFigure;
import org.csstudio.pch.widgets.BinaryModel;
import org.eclipse.draw2d.IFigure;

/**
 * Binary EditPart
 * @author Xihui Chen
 *
 */
public class BinaryEditPart extends AbstractBoolEditPart{

	@Override
	protected IFigure doCreateFigure() {
		final BinaryModel model = getWidgetModel();

		BinaryFigure Binary = new BinaryFigure();
		
		initializeCommonFigureProperties(Binary, model);			
		Binary.setEffect3D(model.isEffect3D());
		Binary.setSquareLED(model.isSquareLED());
		return Binary;
		
		
	}

	@Override
	public BinaryModel getWidgetModel() {
		return (BinaryModel)getModel();
	}
	
	@Override
	protected void registerPropertyChangeHandlers() {
		registerCommonPropertyChangeHandlers();
		
		//effect 3D
		IWidgetPropertyChangeHandler handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				BinaryFigure Binary = (BinaryFigure) refreshableFigure;
				Binary.setEffect3D((Boolean) newValue);
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_EFFECT3D, handler);	
		
		//Sqaure Binary
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				BinaryFigure Binary = (BinaryFigure) refreshableFigure;
				Binary.setSquareLED((Boolean) newValue);
				if(!(Boolean)newValue){
					int width = Math.min(getWidgetModel().getWidth(), getWidgetModel().getHeight());
					getWidgetModel().setSize(width, width);
				}
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_SQUARE_LED, handler);	
		
		//force square size
		final IWidgetPropertyChangeHandler sizeHandler = new IWidgetPropertyChangeHandler() {
			
			public boolean handleChange(Object oldValue, Object newValue, IFigure figure) {
				if(getWidgetModel().isSquareLED())
					return false;
				if(((Integer)newValue) < BinaryModel.MINIMUM_SIZE)
					newValue = BinaryModel.MINIMUM_SIZE;			
				getWidgetModel().setSize((Integer)newValue, (Integer)newValue);
				return false;
			}
		};		
		PropertyChangeListener sizeListener = new PropertyChangeListener() {
		
			public void propertyChange(PropertyChangeEvent evt) {
				sizeHandler.handleChange(evt.getOldValue(), evt.getNewValue(), getFigure());
			}
		};
		getWidgetModel().getProperty(AbstractWidgetModel.PROP_WIDTH).
			addPropertyChangeListener(sizeListener);
		getWidgetModel().getProperty(AbstractWidgetModel.PROP_HEIGHT).
			addPropertyChangeListener(sizeListener);
		
	}

}
