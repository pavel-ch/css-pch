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
public class BinaryFigure extends Figure implements Introspectable {

	//Bulb bulb; 
//	private final static int OUTLINE_WIDTH = 2;
	private final static int SQURE_BORDER_WIDTH = 1;
	private final static Color DARK_GRAY_COLOR = CustomMediaFactory.getInstance().getColor(
			CustomMediaFactory.COLOR_DARK_GRAY); 
	private final static Color WHITE_COLOR = CustomMediaFactory.getInstance().getColor(
			CustomMediaFactory.COLOR_WHITE); 
	private final static Color BLACK_COLOR = CustomMediaFactory.getInstance().getColor(
			CustomMediaFactory.COLOR_BLACK); 
	private boolean effect3D = true;
	private boolean binaryAuto = false;
	public BinaryFigure() {
		binLabel = new Label(Label0){
			@Override
			public boolean containsPoint(int x, int y) {
				return false;
			}
		};
		binLabel.setVisible(showBinaryLabel);
		//bulb = new Bulb();		
		setLayoutManager(new XYLayout());
		//add(bulb);
		add(binLabel);
		//bulb.setBulbColor(booleanValue ? onColor : offColor);		
	}
	
/*	public enum TotalBits {
		BITS_16,
		BITS_32,
		BITS_64
	}*/
	
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
	
	protected Label binLabel;

	protected long value = 0;

	protected boolean showBinaryLabel = false;

	protected int intValue = 0;

	protected String Label0 = "OFF";
	
	protected String Label1 = "ON";

	protected String Label2 = "DIFF";

	protected String Label3 = "FAIL";

	protected BinaryLabelPosition binLabelPosition = BinaryLabelPosition.DEFAULT;

	protected Color Color0 = CustomMediaFactory.getInstance().getColor(
			new RGB(0,128,0));

	protected Color Color1 = CustomMediaFactory.getInstance().getColor(
			CustomMediaFactory.COLOR_GREEN);

	protected Color Color2 = CustomMediaFactory.getInstance().getColor(
			CustomMediaFactory.COLOR_YELLOW);

	protected Color Color3 = CustomMediaFactory.getInstance().getColor(
			CustomMediaFactory.COLOR_RED);

	private Point labelLocation;

	protected void calculateLabelLocation(Point defaultLocation) {
		if(binLabelPosition == BinaryLabelPosition.DEFAULT){
			labelLocation =  defaultLocation;
			return;
		}
		Rectangle textArea = getClientArea();		
		Dimension textSize = TextUtilities.INSTANCE.getTextExtents(
				binLabel.getText(), getFont());
			int x=0;
			if(textArea.width > textSize.width){				
				switch (binLabelPosition) {
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
				switch (binLabelPosition) {
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
	
	public BinaryLabelPosition getBinLabelPosition() {
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
		return Label0;
	}

	/**
	 * @return the onColor
	 */
	public Color getColor1() {
		return Color1;
	}

	/**
	 * @return the onLabel
	 */
	public String getLabel1() {
		return Label1;
	}

	/**
	 * @return the diffColor
	 */
	public Color getColor2() {
		return Color2;
	}

	/**
	 * @return the diffLabel
	 */
	public String getLabel2() {
		return Label2;
	}

	/**
	 * @return the failColor
	 */
	public Color getColor3() {
		return Color3;
	}

	/**
	 * @return the failLabel
	 */
	public String getLabel3() {
		return Label3;
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
	public boolean isShowBinaryLabel() {
		return showBinaryLabel;
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
	
	public void setBinLabelPosition(BinaryLabelPosition labelPosition) {
		this.binLabelPosition = labelPosition;
		labelPosition = null;
		revalidate();
		repaint();
	}

	/**
	 * @param Label the offLabel to set
	 */
	public void setLabel0(String Label) {
		if(this.Label0 != null && this.Label0.equals(Label))
			return;
		this.Label0 = Label;
		if(intValue == 0)
			binLabel.setText(Label);
		
	}

	/**
	 * @param Label the onLabel to set
	 */
	public void setLabel1(String Label) {
		if(this.Label1 != null && this.Label1.equals(Label))
			return;
		this.Label1 = Label;
		if(intValue == 1)
			binLabel.setText(Label);
	}

	/**
	 * @param Label the diffLabel to set
	 */
	public void setLabel2(String Label) {
		if(this.Label2 != null && this.Label2.equals(Label))
			return;
		this.Label2 = Label;
		if(intValue == 2)
			binLabel.setText(Label);
	}

	/**
	 * @param Label the failLabel to set
	 */
	public void setLabel3(String Label) {
		if(this.Label3 != null && this.Label3.equals(Label))
			return;
		this.Label3 = Label;
		if(intValue >= 3)
			binLabel.setText(Label);
	}

	/**
	 * @param showBooleanLabel the showBooleanLabel to set
	 */
	public void setShowBinLabel(boolean showBooleanLabel) {
		if(this.showBinaryLabel == showBooleanLabel)
			return;
		this.showBinaryLabel = showBooleanLabel;
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
	 * @return the binaryAuto
	 */
	public boolean isBinaryAuto() {
		return binaryAuto;
	}
	
	@Override
	protected void layout() {	
		Rectangle myBounds = getClientArea().getCopy();
		if(binLabel.isVisible()){
			Dimension labelSize = binLabel.getPreferredSize();				
			binLabel.setBounds(new Rectangle(myBounds.x + myBounds.width/2 - labelSize.width/2,
					myBounds.y + myBounds.height/2 - labelSize.height/2,
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
				Color fillColor;
				if (intValue == 0) fillColor = Color0;
				else if (intValue == 1) fillColor = Color1;
				else if (intValue == 2) fillColor = Color2;
				else fillColor = Color3;
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
				Color fillColor;
				if (intValue == 0) fillColor = Color0;
				else if (intValue == 1) fillColor = Color1;
				else if (intValue == 2) fillColor = Color2;
				else fillColor = Color3;
		        graphics.setBackgroundColor(fillColor);
		        graphics.fillRectangle(clientArea);
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

	public void setColor2(Color Color) {
		if(this.Color2 != null && this.Color2.equals(Color))
			return;
		this.Color2 = Color;
		repaint();
	}

	public void setColor3(Color Color) {
		if(this.Color3 != null && this.Color3.equals(Color))
			return;
		this.Color3 = Color;
		repaint();
	}

	/**
	 * @param binAuto the squareLED to set
	 */
	public void setBinaryAuto(boolean binAuto) {
		if(this.binaryAuto == binAuto)
			return;
		this.binaryAuto = binAuto;
	}

	protected void updateIntValue() {
		//get boolValue
		intValue = (int)this.value;
		//change boolLabel text
		if(intValue == 0) binLabel.setText(Label0);
		else if(intValue == 1) binLabel.setText(Label1);
		else if(intValue == 2) binLabel.setText(Label2);
		else if(intValue == 3) binLabel.setText(Label3);
		else binLabel.setText(Label3);
	}
}
