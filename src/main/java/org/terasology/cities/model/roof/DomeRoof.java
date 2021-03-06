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

package org.terasology.cities.model.roof;

import java.awt.Rectangle;

/**
 * A dome roof
 */
public class DomeRoof extends RectangularRoof {

    private final int height;
    
    /**
     * @param rc the roof shape
     * @param baseHeight the base height of the roof
     * @param maxHeight the maximum height of the roof
     */
    public DomeRoof(Rectangle rc, int baseHeight, int maxHeight) {
        super(rc, baseHeight);
        
        this.height = maxHeight;
    }

    /**
     * @return the height of the dome
     */
    public int getHeight() {
        return this.height;
    }

    
}
