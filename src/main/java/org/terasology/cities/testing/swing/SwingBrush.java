/*
 * Copyright 2013 MovingBlocks
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.terasology.cities.testing.swing;

import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.cities.BlockTypes;
import org.terasology.cities.raster.Brush;
import org.terasology.math.TeraMath;

import com.google.common.base.Function;

/**
 * Converts model elements into blocks of of a chunk
 * @author Martin Steiger
 */
public class SwingBrush extends Brush {
    
    private static final Logger logger = LoggerFactory.getLogger(SwingBrush.class);
    
    private final Function<BlockTypes, Color> blockColor;
    private final Rectangle affectedArea;

    private final BufferedImage image;
    private final short[][] heightMap;      // [x][z] 

    private final int wz;
    private final int wx;
    
    /**
     * @param wx the world block x of the top-left corner
     * @param wz the world block z of the top-left corner
     * @param image the image to draw onto
     * @param blockColor a mapping String type -> block
     */
    public SwingBrush(int wx, int wz, BufferedImage image, Function<BlockTypes, Color> blockColor) {
        this.blockColor = blockColor;
        this.image = image;
        this.wx = wx;
        this.wz = wz;

        this.heightMap = new short[image.getWidth()][image.getHeight()];
        
        this.affectedArea = new Rectangle(wx, wz, image.getWidth(), image.getHeight());
    }

    @Override
    public Rectangle getAffectedArea() {
        return affectedArea;
    }

    @Override
    public int getMaxHeight() {
        return 64;
    }
    
    @Override
    public int getMinHeight() {
        return 0;
    } 
    
    /**
     * @param x x in world coords
     * @param y y in world coords
     * @param z z in world coords
     * @param type the block type 
     */
    @Override
    public void setBlock(int x, int y, int z, BlockTypes type) {
        setBlock(x, y, z, blockColor.apply(type));
    }

    /**
     * @param x x in world coords
     * @param y y in world coords
     * @param z z in world coords
     * @param color the actual block color
     */
    protected void setBlock(int x, int y, int z, Color color) {
        
        int lx = x - wx;
        int lz = z - wz;

        // TODO: remove
        final boolean debugging = true;
        final boolean warnOnly = true;
        if (debugging) {
            boolean xOk = lx >= 0 && lx < image.getWidth();
            boolean yOk = lx >= getMinHeight() && lx < getMaxHeight();
            boolean zOk = lz >= 0 && lz < image.getHeight();
            
            if (warnOnly) {
                if (!xOk) {
                    logger.warn("X value of {} not in range [{}..{}]", x, wx, wx + image.getWidth() - 1);
                    return;
                }
                
                if (!yOk) {
                    logger.warn("Y value of {} not in range [{}..{}]", y, getMinHeight(), getMaxHeight() - 1);
                    return;
                }
                
                if (!zOk) {
                    logger.warn("Z value of {} not in range [{}..{}]", z, wz, wz + image.getHeight() - 1);
                    return;
                }
            } 
        }
        
        if (color.getAlpha() == 0) {
            return;
        }
            
        if (heightMap[lx][lz] < y) {
            heightMap[lx][lz] = (short) y;
            float[] hsb = new float[3];
            Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), hsb);
            hsb[2] = 0.5f + 0.5f * (float) TeraMath.clamp(y / 16f);
            image.setRGB(lx, lz, Color.HSBtoRGB(hsb[0], hsb[1], hsb[2]));
        }
    }
}