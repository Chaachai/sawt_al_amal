/*
 * Copyright 2010 Savarese Software Research Corporation
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


public interface Distance<Coord extends Number & Comparable<? super Coord>,
        P extends Point<Coord>> {


    public double distance(P from, P to);


    public double distance2(P from, P to);
}
