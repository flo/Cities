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

package org.terasology.cities.model.bldg;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;

/**
 * Defines a town wall consisting of {@link Tower}s 
 * and {@link WallSegment}s.
 */
public class TownWall {

    private final List<WallSegment> walls = Lists.newArrayList();
    private final Set<Tower> towers = Sets.newHashSet();

    /**
     * @param wallSegment the wall segment to add
     */
    public void addWall(WallSegment wallSegment) {
        walls.add(wallSegment);
    }

    /**
     * @return an unmodifiable view on the walls
     */
    public List<WallSegment> getWalls() {
        return Collections.unmodifiableList(walls);
    }

    /**
     * @param tower the tower to add
     */
    public void addTower(Tower tower) {
        towers.add(tower);
    }

    /**
     * @return an unmodifiable view on the towers
     */
    public Set<Tower> getTowers() {
        return Collections.unmodifiableSet(towers);
    }
}
