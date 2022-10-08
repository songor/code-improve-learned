package xyz.csongyu;

import java.util.HashMap;
import java.util.Map;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Mode;

@BenchmarkMode(Mode.Throughput)
@Fork(1)
public class MyBenchmark {
    @Benchmark
    public void measureHashMap() {
        final Map<HashedKey, String> map = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            map.put(new HashedKey(i), "value");
        }
    }

    @Benchmark
    public void measureCollidedHashMap() {
        final Map<CollidedKey, String> map = new HashMap<>();
        for (int i = 0; i < 10000; i++) {
            map.put(new CollidedKey(i), "value");
        }
    }

    private static class HashedKey {
        private final int key;

        private HashedKey(final int key) {
            this.key = key;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final HashedKey other = (HashedKey)o;
            return this.key == other.key;
        }

        @Override
        public int hashCode() {
            return this.key;
        }
    }

    private static class CollidedKey {
        private final int key;

        private CollidedKey(final int key) {
            this.key = key;
        }

        @Override
        public boolean equals(final Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || this.getClass() != o.getClass()) {
                return false;
            }
            final CollidedKey other = (CollidedKey)o;
            return this.key == other.key;
        }

        @Override
        public int hashCode() {
            return this.key % 10;
        }
    }
}
