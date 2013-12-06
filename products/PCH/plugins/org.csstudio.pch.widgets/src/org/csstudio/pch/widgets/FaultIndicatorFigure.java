/*******************************************************************************
 * Copyright (c) 2010 Oak Ridge National Laboratory.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 ******************************************************************************/
package org.csstudio.pch.widgets;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
//import java.util.logging.Level;

import org.csstudio.swt.widgets.introspection.DefaultWidgetIntrospector;
import org.csstudio.swt.widgets.introspection.Introspectable;
import org.csstudio.swt.widgets.util.GraphicsUtil;
import org.csstudio.ui.util.CustomMediaFactory;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.Graphics;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.TextUtilities;
import org.eclipse.draw2d.XYLayout;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.Pattern;
import org.eclipse.swt.graphics.RGB;
import org.eclipse.swt.widgets.Display;

/**
 * LED figure
 * @author Xihui Chen
 *
 */
public class FaultIndicatorFigure extends Figure implements Introspectable {

	//Bulb bulb; 
//	private final static int OUTLINE_WIDTH = 2;
	private final static int SQURE_BORDER_WIDTH = 1;
	//private final static Color DARK_GRAY_COLOR = CustomMediaFactory.getInstance().getColor(
	//		CustomMediaFactory.COLOR_DARK_GRAY); 
	private final static Color WHITE_COLOR = CustomMediaFactory.getInstance().getColor(
			CustomMediaFactory.COLOR_WHITE); 
	private final static Color BLACK_COLOR = CustomMediaFactory.getInstance().getColor(
			CustomMediaFactory.COLOR_BLACK); 
	private boolean effect3D = true;
	private boolean FaultIndicatorAuto = false;
	public FaultIndicatorFigure() {
		binLabel = new Label(Label){
			@Override
			public boolean containsPoint(int x, int y) {
				return false;
			}
		};
		binLabel.setVisible(showFaultIndicatorLabel);
		setLayoutManager(new XYLayout());
		add(binLabel);
	}
	
	public enum FaultIndicatorLabelPosition{
		
		DEFAULT("Default"),				
		LEFT("Left"),
		RIGHT("Right");
		
		public static String[] stringValues(){
			String[] result = new String[values().length];
			int i=0;
			for(FaultIndicatorLabelPosition h : values()){
				result[i++] = h.toString();
			}
			return result;
		}
		String descripion;
		FaultIndicatorLabelPosition(String description){
			this.descripion = description;
		}
		
		@Override
		public String toString() {
			return descripion;
		}
	}
	
	protected Label binLabel;

	protected long value = 0;

	protected boolean showFaultIndicatorLabel = false;

	protected int intValue = 0;

	protected String Label = "OFF";
	
	protected FaultIndicatorLabelPosition binLabelPosition = FaultIndicatorLabelPosition.DEFAULT;

	protected Color Color0 = CustomMediaFactory.getInstance().getColor(
			new RGB(0,128,0));

	protected Color Color1 = CustomMediaFactory.getInstance().getColor(
			CustomMediaFactory.COLOR_GREEN);

	private Point labelLocation;

	protected void calculateLabelLocation(Point defaultLocation) {
		if(binLabelPosition == FaultIndicatorLabelPosition.DEFAULT){
			labelLocation =  defaultLocation;
			return;
		}
		Rectangle textArea = getClientArea();		
		Dimension textSize = TextUtilities.INSTANCE.getTextExtents(
				binLabel.getText(), getFont());
			int x=textArea.height;
			if(textArea.width > textSize.width + textArea.height){				
				switch (binLabelPosition) {
				case LEFT:
					x = (textArea.width - textSize.width)/2;
					break;
				case RIGHT:
					x = textArea.width - textSize.width;
					break;
				default:					
					break;
				}
			}
			
			int y=0;
			y = (textArea.height - textSize.height)/2;

			if(useLocalCoordinates())
				labelLocation = new Point(x, y);
			else
				labelLocation = new Point(x + textArea.x, y + textArea.y);
	}

	
	/**
	 * @return the intValue
	 */
	public int getIntValue() {
		return intValue;
	}
	
	protected Point getLabelLocation(final int x, final int y){
		return getLabelLocation(new Point(x, y));
	}
	
	/**
	 * @param defaultLocation The default location.
	 * @return the location of the boolean label
	 */
	protected Point getLabelLocation(Point defaultLocation){
		if(labelLocation == null)
			calculateLabelLocation(defaultLocation);
		return labelLocation;
	}
	
	public FaultIndicatorLabelPosition getBinLabelPosition() {
		return binLabelPosition;
	}

	/**
	 * @return the offColor
	 */
	public Color getColor0() {
		return Color0;
	}

	/**
	 * @return the offLabel
	 */
	public String getLabel0() {
		return Label;
	}

	/**
	 * @return the onColor
	 */
	public Color getColor1() {
		return Color1;
	}

	/**
	 * @return the value
	 */
	public long getValue() {
		return value;
	}

	@Override
	public void invalidate() {
		labelLocation = null;
		super.invalidate();
	}
	
	@Override
	public boolean isOpaque() {
		return false;
	}

	/**
	 * @return the showBooleanLabel
	 */
	public boolean isShowFaultIndicatorLabel() {
		return showFaultIndicatorLabel;
	}

	public void setIntValue(int value){
		if(this.intValue == value)
			return;
		this.intValue = value;
		setValue(intValue);
	}

	@Override
	public void setEnabled(boolean value) {
		super.setEnabled(value);
		repaint();
	}
	
	@Override
	public void setFont(Font f) {
		super.setFont(f);
		binLabel.setFont(f);
		revalidate();
	}
	
	public void setBinLabelPosition(FaultIndicatorLabelPosition labelPosition) {
		this.binLabelPosition = labelPosition;
		labelPosition = null;
		revalidate();
		repaint();
	}

	/**
	 * @param Label the offLabel to set
	 */
	public void setLabel(String Label) {
		if(this.Label != null && this.Label.equals(Label))
			return;
		this.Label = Label;
		if(intValue == 0)
			binLabel.setText(Label);
		
	}

	/**
	 * @param showBooleanLabel the showBooleanLabel to set
	 */
	public void setShowBinLabel(boolean showBooleanLabel) {
		if(this.showFaultIndicatorLabel == showBooleanLabel)
			return;
		this.showFaultIndicatorLabel = showBooleanLabel;
		binLabel.setVisible(showBooleanLabel);
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		setValue((long)value);
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(long value) {
		if(this.value == value)
			return;
		this.value = value;
		updateIntValue();
		revalidate();
		repaint();
	}

	public BeanInfo getBeanInfo() throws IntrospectionException {
		return new DefaultWidgetIntrospector().getBeanInfo(this.getClass());
	}

	/**
	 * @return the effect3D
	 */
	public boolean isEffect3D() {
		return effect3D;
	}
	
	/**
	 * @return the FaultIndicatorAuto
	 */
	public boolean isFaultIndicatorAuto() {
		return FaultIndicatorAuto;
	}
	
	@Override
	protected void layout() {	
		Rectangle myBounds = getClientArea().getCopy();
		if(binLabel.isVisible()){
			Dimension labelSize = binLabel.getPreferredSize();				
			/*binLabel.setBounds(new Rectangle(myBounds.x + myBounds.width/2 - labelSize.width/2,
					myBounds.y + myBounds.height/2 - labelSize.height/2,
					labelSize.width, labelSize.height));*/
			calculateLabelLocation(new Point (myBounds.x + myBounds.height,
											  myBounds.y + myBounds.height/2 - labelSize.height/2));
			binLabel.setBounds(new Rectangle(labelLocation.x, labelLocation.y,
											 labelSize.width, labelSize.height));
		}
		super.layout();
	}
	
	@Override
	protected void paintClientArea(Graphics graphics) {	
		Color fillColor;
		Color fontColor;

		graphics.pushState();
		graphics.setAntialias(SWT.ON);		
		Rectangle clientArea = getClientArea().getCopy();
		// force square
		clientArea.width = clientArea.height;
		
		fontColor = getForegroundColor();
		if (FaultIndicatorAuto) {
			fillColor = getBackgroundColor();
		}
		else
		{
			if (intValue == 0) { fillColor = Color0; }
			else { fillColor = Color1; }
		}
		
		if(binLabel.isVisible())
		{
			binLabel.setForegroundColor(fontColor);
		}
		
		boolean support3D = GraphicsUtil.testPatternSupported(graphics);
			if(effect3D && support3D){
				//draw up border			
				Pattern pattern = GraphicsUtil.createScaledPattern(graphics, Display.getCurrent(), clientArea.x, clientArea.y, 
					clientArea.x, clientArea.y+SQURE_BORDER_WIDTH, BLACK_COLOR, 20, BLACK_COLOR, 100);			
				graphics.setBackgroundPattern(pattern);
				graphics.fillPolygon(new int[]{clientArea.x, clientArea.y, 
					clientArea.x+SQURE_BORDER_WIDTH,clientArea.y + SQURE_BORDER_WIDTH,
					clientArea.x + clientArea.width - SQURE_BORDER_WIDTH, clientArea.y + SQURE_BORDER_WIDTH,
					clientArea.x + clientArea.width, clientArea.y});
				pattern.dispose();
				
				//draw left border
				pattern = GraphicsUtil.createScaledPattern(graphics, Display.getCurrent(), clientArea.x, clientArea.y, 
					clientArea.x + SQURE_BORDER_WIDTH, clientArea.y, BLACK_COLOR, 20, BLACK_COLOR, 100);			
				graphics.setBackgroundPattern(pattern);
				graphics.fillPolygon(new int[]{clientArea.x, clientArea.y, 
						clientArea.x+SQURE_BORDER_WIDTH,clientArea.y + SQURE_BORDER_WIDTH,
						clientArea.x+SQURE_BORDER_WIDTH, clientArea.y + clientArea.height - SQURE_BORDER_WIDTH,
						clientArea.x, clientArea.y + clientArea.height});
				pattern.dispose();				
				
				//draw bottom border			
				pattern = GraphicsUtil.createScaledPattern(graphics, Display.getCurrent(), clientArea.x, 
					clientArea.y+ clientArea.height - SQURE_BORDER_WIDTH, 
					clientArea.x, clientArea.y+clientArea.height, 
					WHITE_COLOR, 20, WHITE_COLOR, 30);			
				graphics.setBackgroundPattern(pattern);
				graphics.fillPolygon(new int[]{clientArea.x, clientArea.y + clientArea.height, 
					clientArea.x+SQURE_BORDER_WIDTH,clientArea.y +clientArea.height - SQURE_BORDER_WIDTH,
					clientArea.x + clientArea.width - SQURE_BORDER_WIDTH, 
					clientArea.y + clientArea.height - SQURE_BORDER_WIDTH,
					clientArea.x + clientArea.width, clientArea.y + clientArea.height});
				pattern.dispose();
				
				//draw right border			
				pattern = GraphicsUtil.createScaledPattern(graphics, Display.getCurrent(), clientArea.x + clientArea.width - SQURE_BORDER_WIDTH, 
					clientArea.y, 
					clientArea.x + clientArea.width, clientArea.y, 
					WHITE_COLOR, 20, WHITE_COLOR, 30);			
				graphics.setBackgroundPattern(pattern);
				graphics.fillPolygon(new int[]{clientArea.x + clientArea.width, clientArea.y, 
					clientArea.x+ clientArea.width - SQURE_BORDER_WIDTH,clientArea.y + SQURE_BORDER_WIDTH,
					clientArea.x + clientArea.width - SQURE_BORDER_WIDTH, 
					clientArea.y + clientArea.height - SQURE_BORDER_WIDTH,
					clientArea.x + clientArea.width, clientArea.y + clientArea.height});
				pattern.dispose();		
				
				//draw light
				clientArea.shrink(SQURE_BORDER_WIDTH, SQURE_BORDER_WIDTH);
		        graphics.setBackgroundColor(fillColor);
		        graphics.fillRectangle(clientArea);
				pattern = GraphicsUtil.createScaledPattern(graphics, Display.getCurrent(), clientArea.x,	clientArea.y,
		        		clientArea.x + clientArea.width, clientArea.y + clientArea.height,
		        		WHITE_COLOR, 200, fillColor, 0);
		        graphics.setBackgroundPattern(pattern);
		       	graphics.fillRectangle(clientArea);		
		       	pattern.dispose();
				
			}else { //if not 3D
//				clientArea.shrink(SQURE_BORDER_WIDTH/2, SQURE_BORDER_WIDTH/2);
				//clientArea.x+=SQURE_BORDER_WIDTH;
				//clientArea.y+=SQURE_BORDER_WIDTH;
//				graphics.setForegroundColor(DARK_GRAY_COLOR);
				graphics.setForegroundColor(BLACK_COLOR);
				graphics.setLineWidth(SQURE_BORDER_WIDTH);
				graphics.setBackgroundColor(fillColor);
		        graphics.fillRectangle(clientArea);
				clientArea.width-=SQURE_BORDER_WIDTH;
				clientArea.height-=SQURE_BORDER_WIDTH;
				graphics.drawRectangle(clientArea);
				
				//clientArea.shrink(SQURE_BORDER_WIDTH/2, SQURE_BORDER_WIDTH/2);
				/*clientArea.x+=SQURE_BORDER_WIDTH;
				clientArea.y+=SQURE_BORDER_WIDTH;
				clientArea.width-=SQURE_BORDER_WIDTH;
				clientArea.height-=SQURE_BORDER_WIDTH;*/

			}
			
		
		graphics.popState();
		super.paintClientArea(graphics);
	}
	
	/**
	 * @param effect3D the effect3D to set
	 */
	public void setEffect3D(boolean effect3D) {
		if(this.effect3D == effect3D)
			return;
		this.effect3D = effect3D;
		repaint();
	}
	
	public void setColor0(Color offColor) {
		if(this.Color0 != null && this.Color0.equals(offColor))
			return;
		this.Color0 = offColor;
		repaint();
	}

	public void setColor1(Color onColor) {
		if(this.Color1 != null && this.Color1.equals(onColor))
			return;
		this.Color1 = onColor;
		repaint();
	}

	/**
	 * @param binAuto the squareLED to set
	 */
	public void setFaultIndicatorAuto(boolean binAuto) {
		if(this.FaultIndicatorAuto == binAuto)
			return;
		this.FaultIndicatorAuto = binAuto;
		repaint();
	}

	protected void updateIntValue() {
		intValue = (int)this.value;
	}
}
