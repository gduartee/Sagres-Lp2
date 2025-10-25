package infra;

import java.util.concurrent.atomic.AtomicLong;

/** Gerador simples de IDs */
public class IdGenerator {
    private static final AtomicLong seq = new AtomicLong(System.currentTimeMillis());

    public static long nextId() {
        return seq.incrementAndGet();
    }
}
