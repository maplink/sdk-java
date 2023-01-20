package global.maplink.helpers;

public class PolylineHelper {

    public static String encodePolyline(double[][] points, double precision) {
        StringBuilder sb = new StringBuilder(Math.max(20, points.length * 3));
        int prevLat = 0;
        int prevLon = 0;
        for (double[] point : points) {
            int num = (int) Math.floor(point[0] * precision);
            encodeNumber(sb, num - prevLat);
            prevLat = num;
            num = (int) Math.floor(point[1] * precision);
            encodeNumber(sb, num - prevLon);
            prevLon = num;
        }
        return sb.toString();
    }

    private static void encodeNumber(StringBuilder sb, int num) {
        num = num << 1;
        if (num < 0) {
            num = ~num;
        }
        while (num >= 0x20) {
            int nextValue = (0x20 | (num & 0x1f)) + 63;
            sb.append((char) (nextValue));
            num >>= 5;
        }
        num += 63;
        sb.append((char) (num));
    }
}
