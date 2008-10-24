/*
 * Copyright (c) 2008 Wayne Meissner. All rights reserved.
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

package com.kenai.jaffl;

/**
 * Represents a C long.
 * <p>
 * In C, a <tt>long</tt> can be either 32 bits or 64bits, depending on the platform.
 * </p>
 * Replace any function parameters which are <tt>long</tt> in the C definition with 
 * a NativeLong.
 * </p>
 */
public class NativeLong extends Number implements Comparable<NativeLong> {
    public static final int SIZE = Platform.getPlatform().longSize();
    public static final int SHIFT = SIZE == 32 ? 2 : 3;
    public static final long MASK = SIZE == 32 ? 0xffffffffL : 0xffffffffffffffffL;
    private static final NativeLong ZERO = new NativeLong(0);
    private static final NativeLong ONE = new NativeLong(1);
    private static final NativeLong MINUS_ONE = new NativeLong(-1);

    private final long value;
    
    /**
     * Creates a new <tt>NativeLong</tt> instance with the supplied value.
     * 
     * @param value a long or integer.
     */
    public NativeLong(long value) {
        this.value = value;
    }
    
    /**
     * Returns an integer representation of this <tt>NativeLong</tt>.
     * 
     * @return an integer value for this <tt>NativeLong</tt>.
     */
    @Override
    public final int intValue() {
        return (int) value;
    }
    
    /**
     * Returns an {@code long} representation of this <tt>NativeLong</tt>.
     * 
     * @return an {@code long} value for this <tt>NativeLong</tt>.
     */
    @Override
    public final long longValue() {
        return value;
    }
    
    /**
     * Returns an {@code float} representation of this <tt>NativeLong</tt>.
     * 
     * @return an {@code float} value for this <tt>NativeLong</tt>.
     */
    @Override
    public final float floatValue() {
        return (float) value;
    }
    
    /**
     * Returns an {@code double} representation of this <tt>NativeLong</tt>.
     * 
     * @return an {@code double} value for this <tt>NativeLong</tt>.
     */
    @Override
    public final double doubleValue() {
        return (double) value;
    }
    
    /**
     * Gets a hash code for this {@code NativeLong}.
     * 
     * @return a hash code for this {@code NativeLong}.
     */
    @Override
    public final int hashCode() {
         return (int)(value ^ (value >>> 32));
    }

    /**
     * Compares this <tt>NativeLong</tt> to another <tt>NativeLong</tt>.
     * 
     * @param obj the other <tt>NativeLong</tt> to compare to.
     * @return {@code true} if this <tt>NativeLong</tt> is equal to the other 
     * <tt>NativeLong</tt>, else false.
     */
    @Override
    public final boolean equals(Object obj) {
        return ((obj instanceof NativeLong) && value == ((NativeLong) obj).value);
    }
    
    /**
     * Returns a string representation of this <tt>NativeLong</tt>.
     *
     * @return a string representation of this <tt>NativeLong</tt>.
     */
    @Override
    public String toString() {
        return String.valueOf(value);
    }
    
    /**
     * Compares two {@code NativeLong} instances numerically.
     * 
     * @param other the other NativeLong to compare to.
     * 
     * @return {@code 0} if {@code other} is equal to this instance, -1 if this
     * instance is numerically less than {@code other} or 1 if this instance is
     * numerically greater than {@code other}.
     */
    public final int compareTo(NativeLong other) {
        return value < other.value ? -1 : value > other.value ? 1 : 0;
    }

    /**
     * Converts this <tt>NativeLong</tt> to its native representation.
     * 
     * @param marshaller the marshaller to use to convert to native.
     * @param context marshalling context.
     * @return a marshalling session.
     */
//    public final Session marshal(Marshaller marshaller, MarshalContext context) {
//        if (SIZE == 32) {
//            marshaller.addInt32((int) value);
//        } else {
//            marshaller.addInt64(value);
//        }
//        return Marshaller.EMPTY_SESSION;
//    }
    private static final class Cache {
        private final NativeLong[] cache = new NativeLong[256];
        private Cache() {
            for (int i = 0; i < cache.length; ++i) {
                cache[i] = new NativeLong(Long.valueOf(i - 128));
            }
            cache[128 + 0] = ZERO;
            cache[128 + 1] = ONE;
            cache[128 - 1] = MINUS_ONE;
        }
        public static final NativeLong get(long value) {
            return INSTANCE.cache[128 + (int) value];
        }
        private static final Cache INSTANCE = new Cache();
    }
    private static final NativeLong _valueOf(final long value) {
        if (value >= -128 && value <= 127) {
            return Cache.get(value);
        }
        return new NativeLong(value);
    }
    public static final NativeLong valueOf(final long value) {
        return value == 0 ? ZERO : value == 1 ? ONE : value == -1 ? MINUS_ONE : _valueOf(value);
    }
}