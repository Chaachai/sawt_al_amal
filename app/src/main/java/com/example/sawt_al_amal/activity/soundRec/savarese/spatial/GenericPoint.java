/*
 * Copyright 2001-2005 Daniel F. Savarese
 * Copyright 2006-2009 Savarese Software Research Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.savarese.com/software/ApacheLicense-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.sawt_al_amal.activity.soundRec.savarese.spatial;


public class GenericPoint<Coord extends Comparable<? super Coord>>
        implements Point<Coord> {

    private Comparable<? super Coord>[] __coordinates;


    public GenericPoint(int dimensions) {
        assert (dimensions > 0);
        __coordinates = new Comparable[dimensions];
    }


    public GenericPoint(Coord x, Coord y) {
        this(2);
        setCoord(0, x);
        setCoord(1, y);
    }


    public GenericPoint(Coord x, Coord y, Coord z) {
        this(3);
        setCoord(0, x);
        setCoord(1, y);
        setCoord(2, z);
    }


    public GenericPoint(Coord x, Coord y, Coord z, Coord w) {
        this(4);
        setCoord(0, x);
        setCoord(1, y);
        setCoord(2, z);
        setCoord(3, w);
    }


    public void setCoord(int dimension, Coord value)
            throws ArrayIndexOutOfBoundsException {
        __coordinates[dimension] = value;
    }

    public Coord getCoord(int dimension) {
        return (Coord) __coordinates[dimension];
    }


    public int getDimensions() {
        return __coordinates.length;
    }

    public int hashCode() {
        int hash = 0;
        for (Comparable<? super Coord> c : __coordinates) {
            hash += c.hashCode();
        }
        return hash;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof GenericPoint)) {
            return false;
        }

        GenericPoint point = (GenericPoint) obj;

        for (int i = 0; i < __coordinates.length; ++i) {
            if (!__coordinates[i].equals(point.getCoord(i))) {
                return false;
            }
        }

        return true;
    }


    public Object clone() {
        GenericPoint<Coord> point =
                new GenericPoint<Coord>(__coordinates.length);
        for (int i = 0; i < __coordinates.length; ++i) {
            point.setCoord(i, (Coord) __coordinates[i]);
        }
        return point;
    }


    public String toString() {
        StringBuffer buffer = new StringBuffer();

        buffer.append("[ ");
        buffer.append(__coordinates[0].toString());

        for (int i = 1; i < __coordinates.length; ++i) {
            buffer.append(", ");
            buffer.append(__coordinates[i].toString());
        }

        buffer.append(" ]");

        return buffer.toString();
    }
}
