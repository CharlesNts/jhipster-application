package fr.it_akademy.jhipsterapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class AgentTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));
    private static final AtomicInteger intCount = new AtomicInteger(random.nextInt() + (2 * Short.MAX_VALUE));

    public static Agent getAgentSample1() {
        return new Agent().id(1L).lastname("lastname1").firstname("firstname1").age(1);
    }

    public static Agent getAgentSample2() {
        return new Agent().id(2L).lastname("lastname2").firstname("firstname2").age(2);
    }

    public static Agent getAgentRandomSampleGenerator() {
        return new Agent()
            .id(longCount.incrementAndGet())
            .lastname(UUID.randomUUID().toString())
            .firstname(UUID.randomUUID().toString())
            .age(intCount.incrementAndGet());
    }
}
