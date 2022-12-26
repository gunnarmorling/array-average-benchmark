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

import java.util.Random;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.infra.Blackhole;

public class ArrayAvgBenchmark {

    private static final int LENGTH = 100_000_000;

    @State(Scope.Benchmark)
    public static class BenchmarkState {

        int[] values;

        @Setup(Level.Trial)
        public void setUp() {
            Random random = new Random();
            values = random.ints(LENGTH, 0, 101).toArray();
        }
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void avgLoop(BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(ArrayAvg.avgLoop(state.values));
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void avgStream(BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(ArrayAvg.avgStream(state.values));
    }

    @Benchmark
    @BenchmarkMode(Mode.Throughput)
    public void avgVectorized(BenchmarkState state, Blackhole blackhole) {
        blackhole.consume(ArrayAvg.avgVectorized(state.values));
    }

    public static void main(String[] args) {
        Random random = new Random();
        int[] values = random.ints(LENGTH, 0, 101).toArray();

        System.out.println("      Loop: " + ArrayAvg.avgLoop(values));
        System.out.println("    Stream: " + ArrayAvg.avgStream(values));
        System.out.println("Vectorized: " + ArrayAvg.avgVectorized(values));
    }
}
