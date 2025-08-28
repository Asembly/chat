package asembly.app.util;

import java.util.UUID;

public class GeneratorId {
    public static String generateShortUuid() {
        UUID uuid = UUID.randomUUID();
        String shortId = uuid.toString().substring(0, 8);
        return shortId;
    }
}
