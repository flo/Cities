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

package org.terasology.cities;

/**
 * A set of constants used for city building
 */
public enum BlockTypes {

    /**
     * Air (maybe not required)
     */
    AIR,

    /**
     * Surface of a road (asphalt)
     */
    ROAD_SURFACE,
    
    /**
     * Empty space in a lot
     */
    LOT_EMPTY,

    /**
     * A simple building's floor
     */
    BUILDING_FLOOR,

    /**
     * A simple building's foundation
     */
    BUILDING_FOUNDATION,

    /**
     * A simple building wall
     */
    BUILDING_WALL,

    /**
     * Flat roof
     */
    ROOF_FLAT,

    /**
     * Hip roof
     */
    ROOF_HIP,

    /**
     * Dome roof
     */
    ROOF_DOME,

    /**
     * The roof gable for saddle roofs
     */
    ROOF_GABLE,

    /**
     * Saddle roof
     */
    ROOF_SADDLE,

    /**
     * Tower stone
     */
    TOWER_WALL,

    /**
     * Fence
     */
    FENCE,

    /**
     * Fence gate
     */
    FENCE_GATE,

}
