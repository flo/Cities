/*
 * Copyright 2015 MovingBlocks
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.terasology.cities.generator;

import java.awt.Rectangle;
import java.awt.geom.Rectangle2D;
import java.util.Objects;
import java.util.Set;

import javax.vecmath.Point2d;
import org.terasology.math.Vector2i;
import javax.vecmath.Vector2d;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.terasology.cities.AreaInfo;
import org.terasology.cities.model.City;
import org.terasology.cities.model.SimpleLot;
import org.terasology.utilities.random.FastRandom;
import org.terasology.utilities.random.Random;

import com.google.common.collect.Sets;

/**
 * A very simple lot generator. It places square-shaped lots 
 * randomly in a circular area and checks whether it intersects or not.  
 */
public class LotGeneratorRandom {

    private static final Logger logger = LoggerFactory.getLogger(LotGeneratorRandom.class);
    
    private final String seed;

    private final double minSize; 
    private final double maxSize;
    private final int maxTries;
    private final int maxLots;
    
    /**
     * @param seed the random seed
     * @param minSize minimum lot size
     * @param maxSize maximum lot size
     * @param maxLots maximum number of lots
     * @param maxTries maximum number of tries to create lots
     */
    public LotGeneratorRandom(String seed, double minSize, double maxSize, int maxLots, int maxTries) {
        this.seed = seed;
        this.minSize = minSize;
        this.maxSize = maxSize;
        this.maxLots = maxLots;
        this.maxTries = maxTries;
    }
    
    /**
     * @param seed the random seed
     */
    public LotGeneratorRandom(String seed) {
        this.seed = seed;
        this.minSize = 10d;
        this.maxSize = 18d;
        this.maxTries = 100;
        this.maxLots = maxTries;
    }

    /**
     * @param city the city
     * @param si describes the blocked area for a sector
     * @return a set of lots for that city within the city radius
     */
    public Set<SimpleLot> generate(City city, AreaInfo si) {
        Random rand = new FastRandom(Objects.hash(seed, city));
        
        Vector2i center = city.getPos();
        
        Set<SimpleLot> lots = Sets.newLinkedHashSet();  // the order is important for deterministic generation
        double maxLotDiam = maxSize * Math.sqrt(2);
        double minRad = 5 + maxSize * 0.5;
        double maxRad = (city.getDiameter() - maxLotDiam) * 0.5;
        
        if (minRad >= maxRad) {
            return lots;        // which is empty
        }
        
        for (int i = 0; i < maxTries && lots.size() < maxLots;  i++) {
            double ang = rand.nextDouble(0, Math.PI * 2.0);
            double rad = rand.nextDouble(minRad, maxRad);
            double desSizeX = rand.nextDouble(minSize, maxSize);
            double desSizeZ = rand.nextDouble(minSize, maxSize);
            
            double x = center.x + rad * Math.cos(ang);
            double z = center.y + rad * Math.sin(ang);
            
            Point2d pos = new Point2d(x, z);
            Vector2d maxSpace = getMaxSpace(pos, lots);

            int sizeX = (int) Math.min(desSizeX, maxSpace.x);
            int sizeZ = (int) Math.min(desSizeZ, maxSpace.y);
            
            // check if enough space is available
            if (sizeX < minSize || sizeZ < minSize) {
                continue;
            }
            
            Rectangle shape = new Rectangle((int) (pos.x - sizeX * 0.5), (int) (pos.y - sizeZ * 0.5), sizeX, sizeZ);

            // check if lot intersects with blocked area
            if (si.isBlocked(shape)) {
                continue;
            }
            
            si.addBlockedArea(shape);

            // all tests passed -> create and add
            SimpleLot lot = new SimpleLot(shape);
            lots.add(lot);
        }
        
        logger.debug("Generated {} lots for city {}", lots.size(), city);
        
        return lots;
    }

    private Vector2d getMaxSpace(Point2d pos, Set<SimpleLot> lots) {
        double maxX = Double.MAX_VALUE;
        double maxZ = Double.MAX_VALUE;
        
        //      xxxxxxxxxxxxxxxxxxx
        //      x                 x             (p)
        //      x        o------- x--------------|
        //      x                 x
        //      xxxxxxxxxxxxxxxxxxx       dx
        //                         <------------->
        
        for (SimpleLot lot : lots) {
            Rectangle2D bounds = lot.getShape();
            double dx = Math.abs(pos.x - bounds.getCenterX()) - bounds.getWidth() * 0.5;
            double dz = Math.abs(pos.y - bounds.getCenterY()) - bounds.getHeight() * 0.5;
            
            // the point is inside -> abort
            if (dx <= 0 && dz <= 0) {
                return new Vector2d(0, 0);
            }
            
            // the point is diagonally outside -> restrict one of the two only
            if (dx > 0 && dz > 0) {
                // make the larger of the two smaller --> larger shape area
                if (dx > dz) {
                    maxX = Math.min(maxX, dx);
                } else {
                    maxZ = Math.min(maxZ, dz);
                }
            }
            
            // the z-axis is overlapping -> restrict x
            if (dx > 0 && dz <= 0) {
                maxX = Math.min(maxX, dx);
            }
            
            // the x-axis is overlapping -> restrict z
            if (dx <= 0 && dz > 0) {
                maxZ = Math.min(maxZ, dz);
            }
        }
        
        return new Vector2d(2 * maxX, 2 * maxZ);
    }
}
