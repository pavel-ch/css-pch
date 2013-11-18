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
//import org.csstudio.opibuilder.widgets.model.BinaryModel;
import org.csstudio.pch.widgets.BinaryFigure;
import org.csstudio.pch.widgets.BinaryModel;
import org.csstudio.simplepv.VTypeHelper;
//import org.csstudio.swt.widgets.figures.BinaryFigure;
import org.csstudio.pch.widgets.BinaryFigure.BinaryLabelPosition;
import org.csstudio.pch.widgets.BinaryFigure.TotalBits;
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
		
		if(model.getDataType() == 0)
			Binary.setBit(model.getBit());
		else
			Binary.setBit(-1);
		updatePropSheet(model.getDataType());
		Binary.setShowBooleanLabel(model.isShowBoolLabel());
		Binary.setOnLabel(model.getOnLabel());
		Binary.setOffLabel(model.getOffLabel());
		Binary.setOnColor(model.getOnColor());
		Binary.setOffColor(model.getOffColor());
		Binary.setFont(CustomMediaFactory.getInstance().getFont(
				model.getFont().getFontData()));
		Binary.setBoolLabelPosition(model.getBoolLabelPosition());
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
		// value
		IWidgetPropertyChangeHandler handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				if(newValue == null || !(newValue instanceof VType))
					return false;
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				
				switch (VTypeHelper.getBasicDataType((VType) newValue)) {
				case SHORT:
					figure.setTotalBits(TotalBits.BITS_16);
					break;
				case INT:
				case ENUM:
					figure.setTotalBits(TotalBits.BITS_32);
					break;
				default:
					break;
				}
				updateFromValue((VType) newValue, figure);
				return true;
			}


		};
		setPropertyChangeHandler(AbstractPVWidgetModel.PROP_PVVALUE, handler);

		// bit
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				if(getWidgetModel().getDataType() != 0)
					return false;
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setBit((Integer) newValue);
				updateFromValue(getPVValue(AbstractPVWidgetModel.PROP_PVNAME), figure);
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_BIT, handler);

		//data type
	    final IWidgetPropertyChangeHandler	dataTypeHandler = new IWidgetPropertyChangeHandler() {

			public boolean handleChange(Object oldValue, Object newValue, IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				if((Integer)newValue == 0)
					figure.setBit(getWidgetModel().getBit());
				else
					figure.setBit(-1);
				updateFromValue(getPVValue(AbstractPVWidgetModel.PROP_PVNAME), figure);
				updatePropSheet((Integer)newValue);
				return true;
			}
		};
		getWidgetModel().getProperty(BinaryModel.PROP_DATA_TYPE).
			addPropertyChangeListener(new PropertyChangeListener() {

				public void propertyChange(PropertyChangeEvent evt) {
					dataTypeHandler.handleChange(evt.getOldValue(), evt.getNewValue(), getFigure());
				}
			});

		//on state
		handler = new IWidgetPropertyChangeHandler() {

			public boolean handleChange(Object oldValue, Object newValue, IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				updateFromValue(getPVValue(AbstractPVWidgetModel.PROP_PVNAME), figure);
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_ON_STATE, handler);


		// show bool label
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setShowBooleanLabel((Boolean) newValue);
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_SHOW_BOOL_LABEL, handler);

		//  bool label position
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue, final IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setBoolLabelPosition(BinaryLabelPosition.values()[(Integer)newValue]);
				return false;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_BOOL_LABEL_POS, handler);
	
		// on label
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setOnLabel((String) newValue);
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_ON_LABEL, handler);

		// off label
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setOffLabel((String) newValue);
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_OFF_LABEL, handler);

		// on color
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setOnColor(((OPIColor) newValue).getSWTColor());
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_ON_COLOR, handler);

		// off color
		handler = new IWidgetPropertyChangeHandler() {
			public boolean handleChange(final Object oldValue,
					final Object newValue,
					final IFigure refreshableFigure) {
				BinaryFigure figure = (BinaryFigure) refreshableFigure;
				figure.setOffColor(((OPIColor) newValue).getSWTColor());
				return true;
			}
		};
		setPropertyChangeHandler(BinaryModel.PROP_OFF_COLOR, handler);
		
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

	/* (non-Javadoc)
	 * @see org.csstudio.opibuilder.editparts.AbstractPVWidgetEditPart#setValue(java.lang.Object)
	 */
	@Override
	public void setValue(Object value) {
		if(value instanceof Number)
			((BinaryFigure)getFigure()).setValue(((Number)value).doubleValue());
		else if (value instanceof Boolean)
			((BinaryFigure)getFigure()).setBooleanValue((Boolean)value);
		else 
			super.setValue(value);
	}

	@Override
	public Boolean getValue() {
		return ((BinaryFigure)getFigure()).getBooleanValue();
	}
	/**
	 * @param newValue
	 * @param figure
	 */
	private void updateFromValue(final VType newValue,
			BinaryFigure figure) {
		if(newValue == null)
			return;
		if(getWidgetModel().getDataType() == 0)
			figure.setValue(VTypeHelper.getDouble(newValue));
		else {
			if(VTypeHelper.getString(newValue).equals(
					getWidgetModel().getOnState()))
				figure.setValue(1);
			else
				figure.setValue(0);
		}
	}

	private void updatePropSheet(final int dataType) {
		getWidgetModel().setPropertyVisible(
				BinaryModel.PROP_BIT, dataType == 0);
		getWidgetModel().setPropertyVisible(
				BinaryModel.PROP_ON_STATE, dataType == 1);
		getWidgetModel().setPropertyVisible(
				BinaryModel.PROP_OFF_STATE, dataType == 1);

	}
}
