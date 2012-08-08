/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.views.brushes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils.CustomDimension;

/**
 *
 * @author Ryan
 */
public class PassStrikeBrush {
    //pass coordinates and dimensions
    private static final CustomDimension PASS_1 = new CustomDimension(57, 8, 14, 14);
    private static final CustomDimension PASS_2 = new CustomDimension(62, 31, 14, 14);
    private static final CustomDimension PASS_3 = new CustomDimension(53, 51, 14, 14);
    
    //warning and strike coordinates and dimensions
    private static final CustomDimension WARNING = new CustomDimension(92, 1, 14, 14);
    private static final CustomDimension STRIKE_1 = new CustomDimension(144, 3, 14, 14);
    private static final CustomDimension STRIKE_2 = new CustomDimension(151, 24, 14, 14);
    private static final CustomDimension STRIKE_3 = new CustomDimension(144, 45, 14, 14);
    private static final Color PASS_COLOR = new Color(71, 164, 173);
    private static final Color STRIKE_COLOR = new Color(183, 40, 47);
    private static final String BACKGROUND_IMAGE_URL = "/org/jdesktop/wonderland/modules/isocial/tokensheet/client/resources/NewPassStrike.png";

    private static final int PASS_TEXT_X = 25;
    private static final int PASS_TEXT_Y = 45;
    
    private static final int STRIKE_TEXT_X = 115;
    private static final int STRIKE_TEXT_Y = 45;
    
    private static final String[] PASS_TEXT = { "0", "1", "2", "3"};
    private static final String[] STRIKE_TEXT = {"0", "W", "1", "2", "3"};
    
    private static final Font LABEL_FONT = new Font(Font.SANS_SERIF, Font.BOLD, 24);
    
    private BufferedImage canvas = null;
    private Graphics2D graphics;

    public Image paintScoreBoard(int passes, int strikes) {

        //get background image from memory
        Image backgroundImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource(BACKGROUND_IMAGE_URL));

        initializeImage(backgroundImage);

        //create the big long list of bytes [called a buffer] that we are going
        //to write data (our scoreboard) to
        canvas = new BufferedImage(backgroundImage.getWidth(null),
                backgroundImage.getHeight(null),
                BufferedImage.TYPE_4BYTE_ABGR);

        //create the object we will use to write image data with
        graphics = canvas.createGraphics();

        //paint the background first
        paintBackgroundImage(graphics, backgroundImage);

        //paint pass score
        paintPasses(graphics, passes);

        //paint warning, if needed
        paintWarning(graphics, strikes);

        //paint strikes, if needed
        paintStrikes(graphics, strikes);

        return canvas;
    }
    
    /**
     * Creates an ImageIcon instance from the given image. Somehow, someway,
     * the ImageIcon constructor loads the image better than we could with 
     * Toolkit. Perhaps something to do with MediaTracker...
     * 
     * @param i the image to be loaded by ImageIcon's constructor
     */
    private void initializeImage(Image backgroundImage) {
        //For now, it's best just to assume that this is needed and works
        //and not to ask questions :)
        ImageIcon i = new ImageIcon(backgroundImage);
    }

    private void paintBackgroundImage(Graphics2D graphics, Image backgroundImage) {
        graphics.drawImage(backgroundImage, 0, 0, null);
    }

    private void paintPasses(Graphics2D graphics, int passes) {
        switch (passes) {
            case 3:
                paintPassOval(graphics, PASS_3);
            case 2:
                paintPassOval(graphics, PASS_2);
            case 1:
                paintPassOval(graphics, PASS_1);
                break;
        }
        
        paintText(graphics, PASS_TEXT_X, PASS_TEXT_Y, PASS_TEXT[passes]);
    }

    private void paintPassOval(Graphics2D graphics, CustomDimension dimension) {
        graphics.setBackground(Color.BLACK);
        graphics.drawOval(dimension.getX(),
                dimension.getY(),
                dimension.getWidth(),
                dimension.getHeight());
        graphics.setColor(PASS_COLOR);
        graphics.fillOval(dimension.getX(),
                dimension.getY(),
                dimension.getWidth(),
                dimension.getHeight());

    }

    private void paintWarning(Graphics2D graphics, int strikes) {
        if (strikes >= 1) {
            paintWarningOval(graphics, WARNING);
        }
//        paintText(graphics, STRIKE_TEXT_X, STRIKE_TEXT_Y, STRIKE_TEXT[strikes]);

    }

    private void paintWarningOval(Graphics2D graphics, CustomDimension dimension) {
        graphics.setColor(Color.BLACK);
        graphics.drawOval(dimension.getX(),
                dimension.getY(),
                dimension.getWidth(),
                dimension.getHeight());
        graphics.setColor(STRIKE_COLOR);
        graphics.fillOval(dimension.getX(),
                dimension.getY(),
                dimension.getWidth(),
                dimension.getHeight());

    }

    private void paintStrikeOval(Graphics2D graphics, CustomDimension dimension) {
        paintWarningOval(graphics, dimension);
    }

    private void paintStrikes(Graphics2D graphics, int strikes) {
        switch (strikes) {
            case 4:
                paintStrikeOval(graphics, STRIKE_3);
            case 3:
                paintStrikeOval(graphics, STRIKE_2);
            case 2:
                paintStrikeOval(graphics, STRIKE_1);
                break;
        }
        
        //it's not obvious here, but since both the warning and strike painting is 
        //always called, we handle the painting of the warning text here. This
        //case will happen if strikes is less than 2...otherwise, the point is moot
        //and no warning text needs to be drawn because strike text will be drawn
        //instead
        paintText(graphics, STRIKE_TEXT_X, STRIKE_TEXT_Y, STRIKE_TEXT[strikes]);

    }

    private void paintText(Graphics2D graphics, int x, int y, String string) {
        graphics.setColor(Color.BLACK);
        
        graphics.setFont(LABEL_FONT);
        
        graphics.drawString(string, x, y);
    }
}
