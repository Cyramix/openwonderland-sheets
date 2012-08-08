/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.views.brushes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.text.Collator;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils.ImageAssigner;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils.TokenMeterSection;

/**
 *
 * @author Ryan
 */
public class ScoreMeterBrush {

    
    private static final int FRAME_POSITION_X = 75; //in pixels
    private static final int FRAME_POSITION_Y = 26; //in pixels
    private static final int FRAME_WIDTH = 315; //in pixels
    private static final int FRAME_HEIGHT = 25;
    private static final int ARC_DIAMETER = 5;
    private static final boolean IS_BEVELED = false;
    private static final int NUMBER_POSITION_X = 28;
    private static final int NUMBER_POSITION_Y = 45;
    
    private BufferedImage canvas = null;
    private Graphics2D graphics = null;
    
    private Map<String, TokenMeterSection> sections;
    
    // we set the max score to the width of the meter initially.
    private int MAX_SCORE = 315;
    
    private static final Logger logger = Logger.getLogger(ScoreMeterBrush.class.getName());
    public ScoreMeterBrush(int maxScore) {
//        Comparator<String> c = new SectionComparator();
        sections = new TreeMap<String, TokenMeterSection>(new SectionComparator());
        MAX_SCORE = maxScore;
    }
    
    /**
     * START READING HERE!
     * 
     * This method does all the heavy lifting of getting our background image,
     * drawing the number of tokens we have for this lesson, and drawing the
     * meter.
     * 
     * We will draw in the following order:
     * 
     * 1) Draw the background image
     * 2) Draw the number of current tokens we have for the current lesson (progress)
     * 3) Draw the meter background (so the meter looks like it is framed)
     * 4) Draw the meter on top of the background
     * 5) Draw the 80% marker on top of the meter so it doesn't get overdrawn by the meter
     * 
     * 
     * 
     * @param currentLessonTokens
     * @return 
     */
    public Image paintMeter(int currentLessonTokens) {
        
        //get default URL, we want the default image (white) because we aren't
        //currently letting users have account-specific images.
        String url = ImageAssigner.getImageNameFor(Color.BLACK);

        //get the image from memory.
        Image backgroundImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource(url));
        
        initializeImage(backgroundImage);
        
        //create the big long list of bytes [called a buffer] that we are going
        //to write data (our meter) to
        canvas = new BufferedImage(backgroundImage.getWidth(null),
                                   backgroundImage.getHeight(null),
                                   BufferedImage.TYPE_4BYTE_ABGR);
        
        //create the object we will use to write image data with
        graphics = canvas.createGraphics();
        
        //paint the background first
        paintBackgroundImage(graphics, backgroundImage);
        
        //next paint the number of my tokens for this lesson
        paintNumber(graphics, currentLessonTokens);
        
        //next paint the background of the meter...aka...the FRAME
        paintMeterBackground(graphics);
        
        
        //iterate over all sections and paint them as rectangles
        paintMeterProgress(graphics);
        
        //we paint the marker after progress has been painted so that the marker
        //overlays the progress indicating whether or not progress has reached
        //the desired goal
        paintEightyPercentMarker(graphics);
        
        return canvas;
        
    }
    
    
    public void addSection(TokenMeterSection section) {
        synchronized(sections) {
            sections.put(section.getName(), section);
        }
    }

    private void paintMeterBackground(Graphics2D g) {
        g.setColor(Color.WHITE);
//        g.fillRoundRect(FRAME_POSITION_X,
//                        FRAME_POSITION_Y,
//                        FRAME_WIDTH,
//                        FRAME_HEIGHT,
//                        ARC_DIAMETER,
//                        ARC_DIAMETER);
        g.fillRect(FRAME_POSITION_X,
                     FRAME_POSITION_Y,
                     FRAME_WIDTH,
                     FRAME_HEIGHT);
               
    }
    
    private void paintNumber(Graphics2D g, int number) {
        g.setColor(Color.BLACK);
        g.drawString(String.valueOf(number), NUMBER_POSITION_X, NUMBER_POSITION_Y);
        
        logger.warning("PAINTING NUMBER: "+number);
        
    }

    private void paintBackgroundImage(Graphics2D graphics, Image i) {
        graphics.drawImage(i, 0, 0, null);
    }
    
    private void paintMeterProgress(Graphics2D graphics) {
        //TODO: paint meter progress
        synchronized(sections) {
            
            
            int xIndex = FRAME_POSITION_X;
            int yIndex = FRAME_POSITION_Y;
            
            
            //this will give us the number of pixels that represent 1 unit
            //of score
            double multiplier = Math.floor(FRAME_WIDTH/MAX_SCORE);
            
            for(TokenMeterSection section: sections.values()) {
                Color c = section.getColor();
                int tokenAmount = section.getAmountOfTokens();
                
                //this will give us the width of the section to draw
                double sectionWidth = tokenAmount * multiplier;
                
                graphics.setColor(c);
                graphics.fillRect(xIndex,
                                  yIndex,
                                  new Double(sectionWidth).intValue(),
                                  FRAME_HEIGHT);
                
                //set the current X index to be the end of last section
                xIndex += sectionWidth;
                
            }
            
        }
    }
    
    /**
     * Creates an ImageIcon instance from the given image. Somehow, someway,
     * the ImageIcon constructor loads the image better than we could with 
     * Toolkit. Perhaps something to do with MediaTracker...
     * 
     * @param i the image to be loaded by ImageIcon's constructor
     */
    private void initializeImage(Image i) {
        
        //For now, it's best just to assume that this is needed and works
        //and not to ask questions :)
        ImageIcon icon = new ImageIcon(i);
    }

    private void paintEightyPercentMarker(Graphics2D graphics) {
        graphics.setColor(Color.BLUE);
        
        
        //caclculate the 80% marker by taking the frame width and multiplying
        //by 0.8
        int EIGHTY_PERCENT_X = (int) Math.floor(FRAME_POSITION_X+(FRAME_WIDTH*0.8)); 
        
        //it should be drawn at the same starting point as the frame since it 
        //will be the same height
        int EIGHTY_PERCENT_Y = FRAME_POSITION_Y;
        
        //it is the same height as the frame;
        int EIGHTY_PERCENT_HEIGHT = FRAME_HEIGHT;
        
        //the should be tweaked until we are happy with the thickness of the
        //marker
        int EIGHTY_PERCENT_WIDTH = 4; //in pixels
        graphics.fillRect(EIGHTY_PERCENT_X,
                          EIGHTY_PERCENT_Y,
                          EIGHTY_PERCENT_WIDTH,
                          EIGHTY_PERCENT_HEIGHT);
    }
    
    public int getLengthOfMeter() {
        return FRAME_WIDTH;
    }

    
    private static class SectionComparator implements Comparator<String> {
        
//        public int compare(Object a, Object b) {
//            String one = (String)a;
//            String two = (String)b;
//            
//            return Collator.getInstance().compare(one, two);
//        }

        public int compare(String t, String t1) {
            return Collator.getInstance().compare(t, t1);
        }
    }
    
    
}
