/**
 * iSocial Project http://isocial.missouri.edu
 *
 * Copyright (c) 2011, University of Missouri iSocial Project, All Rights
 * Reserved
 *
 * Redistributions in source code form must reproduce the above copyright and
 * this condition.
 *
 * The contents of this file are subject to the GNU General Public License,
 * Version 2 (the "License"); you may not use this file except in compliance
 * with the License. A copy of the License is available at
 * http://www.opensource.org/licenses/gpl-license.php.
 *
 * The iSocial project designates this particular file as subject to the
 * "Classpath" exception as provided by the iSocial project in the License file
 * that accompanied this code.
 */
package org.jdesktop.wonderland.modules.isocial.tokensheet.client.legacy;

import java.awt.*;
import java.awt.event.PaintEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Collection;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import org.jdesktop.wonderland.modules.isocial.client.ISocialManager;
import org.jdesktop.wonderland.modules.isocial.common.model.Result;
import org.jdesktop.wonderland.modules.isocial.common.model.Role;
import org.jdesktop.wonderland.modules.isocial.common.model.Sheet;
import org.jdesktop.wonderland.modules.isocial.tokensheet.client.utils.ImageAssigner;
import org.jdesktop.wonderland.modules.isocial.tokensheet.common.TokenResult;

/**
 * Creates the tokens panel for token and behavior system. The panel contains a
 * label with an image to show the student's current number of token and its
 * color graph.
 *
 * @author Kaustubh
 */
public class TokenStudentPanel extends ImageIcon {

    private ImageIcon imageIcon;
    private TokenResult tokenDetails;
    private Image tokenFrameImage;
    private Graphics2D graphics2D;
    private int startMeterX, startMeterY, end;
    private String userName;
    private int imageWidth, imageHeight;
    private int startTextX, startTextY;
    private int thresholdOffset, classTokens;
    private Color meterColor;
    private final ISocialManager manager;
    private int totalTokens, maxLimit;
    private Sheet sheet;
    private static final Logger logger = Logger.getLogger(TokenStudentPanel.class.getName());

    public TokenStudentPanel(ISocialManager manager, Sheet sheet) {
        //super(url);
        this.userName = manager.getUsername();
        this.manager = manager;
        this.sheet = sheet;

        retrieveImage(manager);

        BufferedImage bufferedImage = new BufferedImage(tokenFrameImage.getWidth(component),
                tokenFrameImage.getHeight(component), BufferedImage.TYPE_4BYTE_ABGR);
        graphics2D = bufferedImage.createGraphics();
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.drawImage(tokenFrameImage, 0, 0, component);

        this.imageIcon = new ImageIcon(bufferedImage);
        imageWidth = tokenFrameImage.getWidth(component);
        imageHeight = tokenFrameImage.getHeight(component);
        startMeterX = 18 * imageWidth / 100;
        thresholdOffset = 18 * imageWidth / 100;
        startTextX = startMeterX / 2 - 8;
        startMeterY = (int) (imageHeight / 3.5);
        startTextY = imageHeight / 2 + 8;
    }

    private void retrieveImage(ISocialManager manager) {
//        try {
        meterColor = Color.decode("#FFFFFF");
//            if (manager.getCurrentRole() == Role.STUDENT) {
//                //meterColor = ColorManager.getInstance().getColorFor(manager.getCurrentInstance().getCohortId(), userName);
//            } else {
//                meterColor = Color.decode("#FFFFFF");
//            }


        String url = ImageAssigner.getImageNameFor(meterColor);
        tokenFrameImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource(url));
//        } catch (IOException ex) {
//            Logger.getLogger(TokenStudentPanel.class.getName()).log(Level.SEVERE, null, ex);
//        }



        //NEEDED - 7/17/2012
        tracker.addImage(tokenFrameImage, 0);

        try {
            
            tracker.waitForID(0);
        } catch (InterruptedException ex) {
            Logger.getLogger(TokenStudentPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        //TODO: DOES THIS NEED TO HAPPEN?
//        while (tracker.statusAll(true) != MediaTracker.COMPLETE) {
//            try {
//                logger.warning("Running on thread: " + Thread.currentThread().getName());
//                wait(500);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(TokenStudentPanel.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
    }

    public ImageIcon getImageIcon() {
        return this.imageIcon;
    }

    public void drawNumberOfTokens(Graphics graphics, String tokenNumberAsString, int x, int y) {
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));

        int width = graphics.getFontMetrics().stringWidth(tokenNumberAsString);

        graphics.drawString(tokenNumberAsString, x - width / 4, y);

    }

    @Override
    public synchronized void paintIcon(Component cmpnt, Graphics graphics, int x, int y) {
        try {
            int meterWidth = tokenFrameImage.getWidth(component) - thresholdOffset;
            int meterHeight = tokenFrameImage.getHeight(component) / 2 - 5;
            int tokens = tokenDetails.getStudentResult().getTokensValue();

            if (manager.getCurrentRole() != Role.STUDENT) {
                int tokenAmount = totalTokens;
//                grphcs.setColor(Color.BLACK);
//                grphcs.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
//                int width = grphcs.getFontMetrics().stringWidth(String.valueOf(tokenAmount));
//                grphcs.drawString(String.valueOf(tokenAmount), x - width / 4, y);
                drawNumberOfTokens(graphics, String.valueOf(tokenAmount), x, y);
            } else if (userName.equals(tokenDetails.getStudentResult().getName())) {
//                grphcs.setColor(Color.BLACK);
//                grphcs.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 24));
//                int width = grphcs.getFontMetrics().stringWidth(String.valueOf(tokens));
                if (totalTokens == 0) {
//                    grphcs.drawString(String.valueOf(tokens), x - width / 4, y);
                    drawNumberOfTokens(graphics, String.valueOf(tokens), x, y);
                } else {
//                    grphcs.drawString(String.valueOf(totalTokens), x - width / 4, y);
                    drawNumberOfTokens(graphics, String.valueOf(totalTokens), x, y);
                }
            }
            graphics.setColor(meterColor);
            if (maxLimit == 0) {
                maxLimit = 100;
            }
            float myTokens = tokens * 100 / maxLimit;
            int colorWidth = (int) ((myTokens * meterWidth) / 100);
            graphics.fillRect(startMeterX, startMeterY, colorWidth, meterHeight);
            startMeterX = startMeterX + colorWidth;
//            grphcs.setColor(Color.BLACK);
//              grphcs.fillRect(startMeterX - 1, startMeterY, 1, meterHeight);
            graphics.setColor(Color.BLUE);
            int temp = 80 * meterWidth;
            temp = temp / 100;
            graphics.fillRect(temp + thresholdOffset, startMeterY, 2, meterHeight);
        } catch (IOException ex) {
            Logger.getLogger(TokenStudentPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void updateTokensInternal(TokenResult tokenResult) {
        this.tokenDetails = tokenResult;
        paintIcon(component, graphics2D, startTextX, startTextY);
        component.repaint();
        PaintEvent e = new PaintEvent(component, PaintEvent.UPDATE,
                new Rectangle(component.getX(), component.getY(), component.getWidth(),
                component.getHeight()));
        Toolkit.getDefaultToolkit().getSystemEventQueue().postEvent(e);
    }

    public void updateTokens(Collection<Result> results, int maxLimit, boolean calcTotal) {
        totalTokens = 0;
        this.maxLimit = maxLimit;
        if (calcTotal) {
            calculateTotalTokens(results);
        }

        for (Result result : results) {
            updateTokensInternal((TokenResult) result.getDetails());
        }
    }

    public synchronized void resetImage() {
        graphics2D.drawImage(tokenFrameImage, 0, 0, component);
        startMeterX = 18 * imageWidth / 100;
        try {
            if (manager.getCurrentRole() == Role.STUDENT) {
                //meterColor = ColorManager.getInstance().getColorFor(manager.getCurrentInstance().getCohortId(), userName);
            } else {
                meterColor = Color.decode("#FFFFFF");
            }
        } catch (IOException ex) {
            Logger.getLogger(TokenStudentPanel.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void calculateTotalTokens(Collection<Result> results) {
        for (Result result : results) {
            totalTokens += ((TokenResult) result.getDetails()).getStudentResult().getTokensValue();
        }
    }
}
