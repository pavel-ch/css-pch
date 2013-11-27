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

import org.csstudio.opibuilder.editparts.AbstractPVWidgetEditPart;
import org.csstudio.opibuilder.model.AbstractPVWidgetModel;
import org.csstudio.opibuilder.model.AbstractWidgetModel;
import org.csstudio.opibuilder.properties.IWidgetPropertyChangeHandler;
import org.csstudio.opibuilder.util.OPIColor;
import org.csstudio.pch.widgets.BinaryFigure;
import org.csstudio.pch.widgets.BinaryModel;
import org.csstudio.simplepv.VTypeHelper;
import org.csstudio.pch.widgets.BinaryFigure.BinaryLabelPosition;
import org.csstudio.ui.util.CustomMediaFactory;
import org.eclipse.draw2d.IFigure;
import org.epics.vtype.VType;

/**
 * Binary EditPart
 * @author Xihui Chen
 *
 */
public class BinaryEditPart extends AbstractPVWidgetEditPart{

	@Override
	protected IFigure doCreateFigure() {
		final BinaryModel model = getWidgetModel();

		BinaryFigure Binary = new BinaryFigure();
		
		Binary.setShowBinLabel(model.isShowBinLabel());
		Binary.setLabel0(model.getLabel0());
		Binary.setLabel1(model.getLabel1());
		Binary.setLabel2(model.getLabel2());
		Binary.setLabel3(model.getLabel3());
		Binary.setColor0(model.getColor0());
		Binary.setColor1(model.getColor1());
		Binary.setColor2(model.getColor2());
		Binary.setColor3(model.getColor3());
		Binary.setFont(CustomMediaFactory.getInstance().getFont(
				model.getFont().getFontData()));
		Binary.setBinLabelPosition(model.getBinLabelPosition());
		Binary.setEffect3D(model.isEffect3D());
		Binary.setBinaryAuto(model.isBinaryAuto());
		return Binary;
		
		
	}

	@Override
	public BinaryModel getWidgetModel() {
		return (BinaryModel)getModel();
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
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				
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
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setShowBinLabel((Boolean) newValue);
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_BINARY_SHOW_LABEL, handler);

		//  bool label position
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue, final IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setBinLabelPosition(BinaryLabelPosition.values()[(Integer)newValue]);
				return false;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_BINARY_LABEL_POS, handler);
	
		// off label
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setLabel0((String) newValue);
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_LABEL_0, handler);

		// on label
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setLabel1((String) newValue);
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_LABEL_1, handler);

		// dif label
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setLabel2((String) newValue);
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_LABEL_2, handler);

		// fail label
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setLabel3((String) newValue);
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_LABEL_3, handler);

		// off color
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setColor0(((OPIColor) newValue).getSWTColor());
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_COLOR_0, handler);
		
		// on color
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setColor1(((OPIColor) newValue).getSWTColor());
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_COLOR_1, handler);

		// diff color
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setColor2(((OPIColor) newValue).getSWTColor());
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_COLOR_2, handler);

		// fail color
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setColor3(((OPIColor) newValue).getSWTColor());
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_COLOR_3, handler);

		//effect 3D
		handler = new IWidgetPropertyChangeHandler() {
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
				Binary.setBinaryAuto((Boolean) newValue);
				if(!(Boolean)newValue){
					int width = Math.min(getWidgetModel().getWidth(), getWidgetModel().getHeight());
					getWidgetModel().setSize(width, width);
				}
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_BINARY_AUTO, handler);	
		
		//force square size
		final IWidgetPropertyChangeHandler sizeHandler = new IWidgetPropertyChangeHandler() {
			
			public boolean handleChange(Object oldValue, Object newValue, IFigure figure) {
				if(getWidgetModel().isBinaryAuto())
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

	/* (non-Javadoc)
	 * @see org.csstudio.opibuilder.editparts.AbstractPVWidgetEditPart#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		if(value instanceof Number)
			((BinaryFigure)getFigure()).setValue(((Number)value).doubleValue());
		else if (value instanceof Boolean)
			((BinaryFigure)getFigure()).setIntValue((int)value);
		else 
			super.setValue(value);
	}

	@Override
	public Integer getValue() {
		return ((BinaryFigure)getFigure()).getIntValue();
	}
	/**
	 * @param newValue
	 * @param figure
	 */
	private void updateFromValue(final VType newValue,
			BinaryFigure figure) {
		if(newValue == null)
			return;
		figure.setValue(VTypeHelper.getDouble(newValue));
	}
}
