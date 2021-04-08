package top.leeti.util;

import java.util.UUID;

public class UuidUtil {

    public static String acquireUuid(){
        UUID uuid = UUID.randomUUID();
        return uuid.toString().replaceAll("-", "");
    }

}
