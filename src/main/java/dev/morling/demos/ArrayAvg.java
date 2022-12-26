/*
 *  Copyright 2022 The original authors
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package dev.morling.demos;

import java.util.Arrays;

import jdk.incubator.vector.IntVector;
import jdk.incubator.vector.Vector;
import jdk.incubator.vector.VectorSpecies;

public class ArrayAvg {

    private static final VectorSpecies<Integer> SPECIES = IntVector.SPECIES_PREFERRED;

    public static double avgLoop(int[] values) {
        long sum = 0;
        for (int i : values) {
            sum += i;
        }

        return (double) sum / values.length;
    }

    public static double avgStream(int[] values) {
        return Arrays.stream(values).average().orElse(Double.NaN);
    }

    public static double avgVectorized(int[] values) {
        Vector<Integer> iv = SPECIES.zero();
        int i = 0;
        int upperBound = SPECIES.loopBound(values.length);
        for (; i < upperBound; i += SPECIES.length()) {
            var va = IntVector.fromArray(SPECIES, values, i);
            iv = iv.add(va);
        }

        long sum = 0;
        for (int j : iv.toIntArray()) {
            sum += j;
        }
        for (; i < values.length; i++) {
            sum += values[i];
        }

        return (double) sum / values.length;
    }
}
