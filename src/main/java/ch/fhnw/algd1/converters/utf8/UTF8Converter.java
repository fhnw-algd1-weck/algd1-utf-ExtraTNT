package ch.fhnw.algd1.converters.utf8;

/*
 * Created on 05.09.2014
 */

/**
 * @author ExtraTNT
 */
public class UTF8Converter {
public static byte[] codePointToUTF(int x) {
    // 0x00000000 - 0x0000007F:
    // 0xxxxxxx

    // 0x00000080 - 0x000007FF:
    // 110xxxxx 10xxxxxx

    // 0x00000800 - 0x0000FFFF:
    // 1110xxxx 10xxxxxx 10xxxxxx

    // 0x00010000 - 0x001FFFFF:
    // 11110xxx 10xxxxxx 10xxxxxx 10xxxxxx


    // not elegant, but fast
    if (x <= 0x7F) {
        // ascii
        return new byte[]{(byte) x};
    }
    if (x <= 0x7FF) {
        return new byte[]{
            (byte) (0xC0 | ((x >> 6) & 0x1F)),
            (byte) (0x80 | (x & 0x3F))};
    }
    if (x <= 0xFFFF) {
        return new byte[]{
            (byte) (0xE0 | ((x >> 12) & 0x0F)),
            (byte) (0x80 | ((x >> 6) & 0x3F)),
            (byte) (0x80 | (x & 0x3F))};
    }

    return new byte[]{
        (byte) (0xF0 | ((x >> 18) & 0x07)),
        (byte) (0x80 | ((x >> 12) & 0x3F)),
        (byte) (0x80 | ((x >> 6) & 0x3F)),
        (byte) (0x80 | (x & 0x3F))};
}

public static int UTFtoCodePoint(byte[] bytes) {
    if (!isValidUTF8(bytes)) return 0;
    
    return switch (bytes.length) {
        case 1 -> 
            bytes[0] & 0x7F;
        case 2 ->
            (bytes[0]  & 0x3F) << 6 | // missing mask, as the first bytes have a strict value
            bytes[1] & 0x3F;
        case 3 -> 
            ((bytes[0] & 0x0F) << 12) | 
            ((bytes[1] & 0x3F) << 6) |
            (bytes[2] & 0x3F);
        case 4 -> 
            ((bytes[0] & 0x07) << 18) | 
            ((bytes[1] & 0x3F) << 12) | 
            ((bytes[2] & 0x3F) << 6) | 
            (bytes[3] & 0x3F);
        default -> 0;
    };
}

private static boolean isValidUTF8(byte[] bytes) {
    if (bytes.length == 1) return (bytes[0] & 0b1000_0000) == 0;
    else if (bytes.length == 2)
        return ((bytes[0] & 0b1110_0000) == 0b1100_0000) && isFollowup(
            bytes[1]);
    else if (bytes.length == 3)
        return ((bytes[0] & 0b1111_0000) == 0b1110_0000) && isFollowup(
            bytes[1]) && isFollowup(bytes[2]);
    else if (bytes.length == 4)
        return ((bytes[0] & 0b1111_1000) == 0b1111_0000) && isFollowup(
            bytes[1]) && isFollowup(bytes[2]) && isFollowup(bytes[3]);
    else return false;
}

private static boolean isFollowup(byte b) {
    return (b & 0b1100_0000) == 0b1000_0000;
}
}
