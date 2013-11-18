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
import java.util.logging.Level;

import java.util.logging.Logger;
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
public class BinaryFigure extends Figure implements Introspectable {

	Bulb bulb; 
	private final static int OUTLINE_WIDTH = 2;
	private final static int SQURE_BORDER_WIDTH = 1;
	private final static Color DARK_GRAY_COLOR = CustomMediaFactory.getInstance().getColor(
			CustomMediaFactory.COLOR_DARK_GRAY); 
	private final static Color WHITE_COLOR = CustomMediaFactory.getInstance().getColor(
			CustomMediaFactory.COLOR_WHITE); 
	private final static Color BLACK_COLOR = CustomMediaFactory.getInstance().getColor(
			CustomMediaFactory.COLOR_BLACK); 
	private boolean effect3D = true;
	private boolean squareLED = false;
	public BinaryFigure() {
		boolLabel = new Label(offLabel){
			@Override
			public boolean containsPoint(int x, int y) {
				return false;
			}
		};
		boolLabel.setVisible(showBooleanLabel);
		bulb = new Bulb();		
		setLayoutManager(new XYLayout());
		add(bulb);
		add(boolLabel);
		bulb.setBulbColor(booleanValue ? onColor : offColor);		
	}
	
	public enum TotalBits {
		BITS_16,
		BITS_32,
		BITS_64
	}
	
	public enum BinaryLabelPosition{
		
		DEFAULT("Default"),				
		TOP("Top"),	
		LEFT("Left"),
		CENTER("Center"),
		RIGHT("Right"),
		BOTTOM("Bottom"),
		TOP_LEFT("Top Left"),
		TOP_RIGHT("Top Right"),	
		BOTTOM_LEFT("Bottom Left"),
		BOTTOM_RIGHT("Bottom Right");
		
		public static String[] stringValues(){
			String[] result = new String[values().length];
			int i=0;
			for(BinaryLabelPosition h : values()){
				result[i++] = h.toString();
			}
			return result;
		}
		String descripion;
		BinaryLabelPosition(String description){
			this.descripion = description;
		}
		
		@Override
		public String toString() {
			return descripion;
		}
	}
	
	private TotalBits totalBits = TotalBits.BITS_64;

	protected Label boolLabel;

	protected long value = 0;

	protected int bit = -1;

	protected boolean showBooleanLabel = false;

	protected boolean booleanValue = false;

	protected String onLabel = "ON";

	protected String offLabel = "OFF";
	
	protected BinaryLabelPosition boolLabelPosition = BinaryLabelPosition.DEFAULT;

	protected Color onColor = CustomMediaFactory.getInstance().getColor(
			CustomMediaFactory.COLOR_GREEN);

	protected Color offColor = CustomMediaFactory.getInstance().getColor(
			new RGB(0,128,0));

	private Point labelLocation;

	protected void calculateLabelLocation(Point defaultLocation) {
		if(boolLabelPosition == BinaryLabelPosition.DEFAULT){
			labelLocation =  defaultLocation;
			return;
		}
		Rectangle textArea = getClientArea();		
		Dimension textSize = TextUtilities.INSTANCE.getTextExtents(
				boolLabel.getText(), getFont());
			int x=0;
			if(textArea.width > textSize.width){				
				switch (boolLabelPosition) {
				case CENTER:
				case TOP:
				case BOTTOM:
					x = (textArea.width - textSize.width)/2;
					break;
				case RIGHT:
				case TOP_RIGHT:
				case BOTTOM_RIGHT:
					x = textArea.width - textSize.width;
					break;
				default:					
					break;
				}
			}
			
			int y=0;
			if(textArea.height > textSize.height){
				switch (boolLabelPosition) {
				case CENTER:
				case LEFT:
				case RIGHT:
					y = (textArea.height - textSize.height)/2;
					break;
				case BOTTOM:
				case BOTTOM_LEFT:
				case BOTTOM_RIGHT:
					y =textArea.height - textSize.height;
					break;
				default:
					break;
				}
			}
			if(useLocalCoordinates())
				labelLocation = new Point(x, y);
			else
				labelLocation = new Point(x + textArea.x, y + textArea.y);
	}

	
	/**
	 * @return the bit
	 */
	public int getBit() {
		return bit;
	}

	/**
	 * @return the boolValue
	 */
	public boolean getBooleanValue() {
		return booleanValue;
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
	
	public BinaryLabelPosition getBoolLabelPosition() {
		return boolLabelPosition;
	}

	/**
	 * @return the offColor
	 */
	public Color getOffColor() {
		return offColor;
	}



	/**
	 * @return the offLabel
	 */
	public String getOffLabel() {
		return offLabel;
	}

	/**
	 * @return the onColor
	 */
	public Color getOnColor() {
		return onColor;
	}

	/**
	 * @return the onLabel
	 */
	public String getOnLabel() {
		return onLabel;
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
	public boolean isShowBooleanLabel() {
		return showBooleanLabel;
	}

	/**
	 * @param bit the bit to set
	 */
	public void setBit(int bit) {
		if(this.bit == bit)
			return;
		this.bit = bit;
		updateBoolValue();
	}


	public void setBooleanValue(boolean value){
		if(this.booleanValue == value)
			return;
		this.booleanValue = value;
		updateValue();
	}

	@Override
	public void setEnabled(boolean value) {
		super.setEnabled(value);
		repaint();
	}
	
	@Override
	public void setFont(Font f) {
		super.setFont(f);
		boolLabel.setFont(f);
		revalidate();
	}
	
	public void setBoolLabelPosition(BinaryLabelPosition labelPosition) {
		this.boolLabelPosition = labelPosition;
		labelPosition = null;
		revalidate();
		repaint();
	}

	/**
	 * @param offLabel the offLabel to set
	 */
	public void setOffLabel(String offLabel) {
		if(this.offLabel != null && this.offLabel.equals(offLabel))
			return;
		this.offLabel = offLabel;
		if(!booleanValue)
			boolLabel.setText(offLabel);
		
	}

	/**
	 * @param onLabel the onLabel to set
	 */
	public void setOnLabel(String onLabel) {
		if(this.onLabel != null && this.onLabel.equals(onLabel))
			return;
		this.onLabel = onLabel;
		if(booleanValue)
			boolLabel.setText(onLabel);
	}

	/**
	 * @param showBooleanLabel the showBooleanLabel to set
	 */
	public void setShowBooleanLabel(boolean showBooleanLabel) {
		if(this.showBooleanLabel == showBooleanLabel)
			return;
		this.showBooleanLabel = showBooleanLabel;
		boolLabel.setVisible(showBooleanLabel);
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
		updateBoolValue();
		revalidate();
		repaint();
	}

	/**
	 * update the value from boolValue
	 */
	@SuppressWarnings("nls")
    protected void updateValue(){
		//get boolValue
		if(bit < 0)
			setValue(booleanValue ? 1 : 0);
		else if(bit >=0) {
			if(bit >= 64) {
			    // Log with exception to obtain call stack
				Activator.getLogger(Activator.PLUGIN_ID).log(Level.WARNING, "Bit " + bit + "can not exceed 63.", new Exception());
			}
			else {
				switch (totalBits) {
				case BITS_16:
					setValue(booleanValue? value | ((short)1<<bit) : value & ~((short)1<<bit));
				break;				
				case BITS_32:
					setValue(booleanValue? value | ((int)1<<bit) : value & ~((int)1<<bit));
				break;
				default:				
					setValue(booleanValue? value | (1L<<bit) : value & ~(1L<<bit));
					break;
				}			
			}
		}
	}
	
	public TotalBits getTotalBits() {
		return totalBits;
	}

	/**
	 * @param totalBits number of total bits
	 */
	public void setTotalBits(TotalBits totalBits) {
		this.totalBits = totalBits;
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
	 * @return the squareLED
	 */
	public boolean isSquareLED() {
		return squareLED;
	}
	
	@Override
	protected void layout() {	
		Rectangle bulbBounds = getClientArea().getCopy();
		if(bulb.isVisible() && !squareLED){			
			bulbBounds.shrink(OUTLINE_WIDTH, OUTLINE_WIDTH);
			bulb.setBounds(bulbBounds);
		}		
		if(boolLabel.isVisible()){
			Dimension labelSize = boolLabel.getPreferredSize();				
			boolLabel.setBounds(new Rectangle(bulbBounds.x + bulbBounds.width/2 - labelSize.width/2,
					bulbBounds.y + bulbBounds.height/2 - labelSize.height/2,
					labelSize.width, labelSize.height));
		}
		super.layout();
	}
	
	@Override
	protected void paintClientArea(Graphics graphics) {	
		graphics.pushState();
		graphics.setAntialias(SWT.ON);		
		Rectangle clientArea = getClientArea().getCopy();
		boolean support3D = GraphicsUtil.testPatternSupported(graphics);
		if(squareLED){
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
				Color fillColor = booleanValue?onColor:offColor;
		        graphics.setBackgroundColor(fillColor);
		        graphics.fillRectangle(clientArea);
				pattern = GraphicsUtil.createScaledPattern(graphics, Display.getCurrent(), clientArea.x,	clientArea.y,
		        		clientArea.x + clientArea.width, clientArea.y + clientArea.height,
		        		WHITE_COLOR, 200, fillColor, 0);
		        graphics.setBackgroundPattern(pattern);
		       	graphics.fillRectangle(clientArea);		
		       	pattern.dispose();
				
			}else { //if not 3D
				clientArea.shrink(SQURE_BORDER_WIDTH/2, SQURE_BORDER_WIDTH/2);
//				graphics.setForegroundColor(DARK_GRAY_COLOR);
				graphics.setForegroundColor(BLACK_COLOR);
				graphics.setLineWidth(SQURE_BORDER_WIDTH);
				graphics.drawRectangle(clientArea);
				
				clientArea.shrink(SQURE_BORDER_WIDTH/2, SQURE_BORDER_WIDTH/2);
				Color fillColor = booleanValue?onColor:offColor;
		        graphics.setBackgroundColor(fillColor);
		        graphics.fillRectangle(clientArea);
			}
			
		}else { // if round LED
			int width = Math.min(clientArea.width, clientArea.height);
			Rectangle outRect = new Rectangle(getClientArea().x, getClientArea().y, 
				width, width);
			if(effect3D && support3D){
				graphics.setBackgroundColor(WHITE_COLOR);
				graphics.fillOval(outRect);
				Pattern pattern = GraphicsUtil.createScaledPattern(graphics, Display.getCurrent(), outRect.x, outRect.y,
					outRect.x+width, outRect.y+width, DARK_GRAY_COLOR, 255, DARK_GRAY_COLOR, 0);
				graphics.setBackgroundPattern(pattern);
				graphics.fillOval(outRect);
				pattern.dispose();
			}else {
				graphics.setBackgroundColor(DARK_GRAY_COLOR);
				graphics.fillOval(outRect);
			}
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
		bulb.setEffect3D(effect3D);
	}
	
	public void setOffColor(Color offColor) {
		if(this.offColor != null && this.offColor.equals(offColor))
			return;
		this.offColor = offColor;
		repaint();
		if(!booleanValue  && bulb.isVisible())
			bulb.setBulbColor(offColor);
	}

	public void setOnColor(Color onColor) {
		if(this.onColor != null && this.onColor.equals(onColor))
			return;
		this.onColor = onColor;
		repaint();
		if(booleanValue && bulb.isVisible())
			bulb.setBulbColor(onColor);
	}

	/**
	 * @param squareLED the squareLED to set
	 */
	public void setSquareLED(boolean squareLED) {
		if(this.squareLED == squareLED)
			return;
		this.squareLED = squareLED;
		bulb.setVisible(!squareLED);
	}

	protected void updateBoolValue() {
		//get boolValue
		if(bit < 0)
			booleanValue = (this.value != 0);
		else if(bit >=0) {
			booleanValue = ((value>>bit)&1L) >0;
		}
		//change boolLabel text
		if(booleanValue)
			boolLabel.setText(onLabel);
		else
			boolLabel.setText(offLabel);
		bulb.setBulbColor(booleanValue ? onColor : offColor);
		
	}
}
