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
package org.terasology.cities.events;

import org.terasology.cities.model.NamedArea;
import org.terasology.entitySystem.event.Event;

public class OnEnterAreaEvent implements Event {
    private final NamedArea area;

    /**
     * @param area the area that was entered
     */
    public OnEnterAreaEvent(NamedArea area) {
        this.area = area;
    }

    /**
     * @return the area that was entered
     */
    public NamedArea getArea() {
        return area;
    }
}
