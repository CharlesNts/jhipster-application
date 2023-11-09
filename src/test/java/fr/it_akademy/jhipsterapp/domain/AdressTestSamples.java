package fr.it_akademy.jhipsterapp.domain;

import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public class AdressTestSamples {

    private static final Random random = new Random();
    private static final AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    public static Adress getAdressSample1() {
        return new Adress().id(1L).streetNumb("streetNumb1").streetName("streetName1");
    }

    public static Adress getAdressSample2() {
        return new Adress().id(2L).streetNumb("streetNumb2").streetName("streetName2");
    }

    public static Adress getAdressRandomSampleGenerator() {
        return new Adress()
            .id(longCount.incrementAndGet())
            .streetNumb(UUID.randomUUID().toString())
            .streetName(UUID.randomUUID().toString());
    }
}
