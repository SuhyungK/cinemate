package movie.cinemate.cinemate.utils;

public class UUIDUtil {
    public static String createUUID(String uuid) {
        return uuid.replaceAll("-", "");
    }
}
