/* 
 * Copyright (C) 2008 Wayne Meissner
 * 
 * This file is part of jffi.
 *
 * This code is free software: you can redistribute it and/or modify it under 
 * the terms of the GNU Lesser General Public License version 3 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT 
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU Lesser General Public License 
 * version 3 for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * version 3 along with this work.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.kenai.jaffl.byref;

import java.nio.ByteBuffer;

/**
 *
 */
public class FloatByReference extends AbstractPrimitiveReference<Float> {
    
    /**
     * Creates a new reference to a float value
     * 
     * @param value the initial native value
     */
    public FloatByReference(Float value) {
        super(value);
    }
    
    /**
     * Copies the float value to native memory
     * 
     * @param buffer the native memory buffer
     */
    public void marshal(ByteBuffer buffer) {
        buffer.putFloat(0, value);
    }

    /**
     * Copies the float value from native memory
     * 
     * @param buffer the native memory buffer.
     */
    public void unmarshal(ByteBuffer buffer) {
        value = buffer.getFloat(0);
    }
    
    /**
     * Gets the native size of type of reference in bytes.
     * 
     * @return the size of a byte in bytes
     */
    public final int nativeSize() {
        return Float.SIZE / 8;
    }
}