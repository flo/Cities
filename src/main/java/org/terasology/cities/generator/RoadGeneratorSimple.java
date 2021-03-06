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

import org.terasology.math.Vector2i;

import org.terasology.cities.model.Junction;
import org.terasology.cities.model.Road;
import org.terasology.cities.model.Site;
import org.terasology.commonworld.UnorderedPair;
import org.terasology.commonworld.geom.Vector2iUtils;

import com.google.common.base.Function;

/**
 * Creates a simple, straight, road with no segments for a connection
 */
public class RoadGeneratorSimple implements Function<UnorderedPair<Site>, Road> {
    
    private Function<Vector2i, Junction> junctions;
    private double avgSegmentLength = 40;

    /**
     * @param junctions gives junctions based on location
     */
    public RoadGeneratorSimple(Function<Vector2i, Junction> junctions) {
        this.junctions = junctions;
    }
    
    @Override
    public Road apply(UnorderedPair<Site> pair) {

        Site a = pair.getA();
        Site b = pair.getB();
        
        Vector2i posA = a.getPos();
        Vector2i posB = b.getPos();

        Junction junA = junctions.apply(posA);
        Junction junB = junctions.apply(posB);
        Road road = new Road(junA, junB);

        addSegments(road, avgSegmentLength);

        // here we define width as the log of the smaller site's size
        double avgSize = Math.min(a.getRadius(), b.getRadius());
        float width = (float) Math.max(1.0, Math.log(avgSize));
        width = (float) Math.floor(width * 0.5);
        
        // TODO: check and remove
        road.setWidth(5.0f);

        return road;
    }

    /**
     * @param road the road
     * @param avgDist average length of a segment measured in sectors
     * @return a road with segments
     */
    protected Road addSegments(Road road, double avgDist) {
        Vector2i coordsA = road.getStart().getCoords();
        Vector2i coordsB = road.getEnd().getCoords();
        
        double dist = Vector2iUtils.distance(coordsA, coordsB);

        int segments = (int) (dist / avgDist + 0.5);

        for (int i = 1; i < segments; i++) {
            Vector2i p = Vector2iUtils.interpolate(coordsA, coordsB, i / (double) segments);

            road.add(p);
        }

        return road;
    }
}
