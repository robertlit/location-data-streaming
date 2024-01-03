package me.robertlit.datastream.calc;

public class Calculations {

    private static final double R = 6371E3;

    /**
     * Calculates the distance between two coordinates using the Haversine algorithm.
     * @param lat1 latitude of one point
     * @param lon1 longitude of one point
     * @param lat2 latitude of the second point
     * @param lon2 longitude of the second point
     * @return the distance in metres
     */
    public static double calculateDistance(double lat1,
                                           double lon1,
                                           double lat2,
                                           double lon2) {
        double phi1 = Math.toRadians(lat1);
        double phi2 = Math.toRadians(lat2);
        double dPhi = phi2 - phi1;
        double dLambda = Math.toRadians(lon2 - lon1);

        double a = Math.sin(dPhi / 2) * Math.sin(dPhi / 2) +
                Math.cos(phi1) * Math.cos(phi2) * Math.sin(dLambda / 2) * Math.sin(dLambda / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c;
    }

    /**
     * Calculates the velocity at a point based on the next and previous points.
     * @param nextLat the latitude of the next point
     * @param nextLon the longitude of the next point
     * @param nextTime the timestamp of the next point
     * @param prevLat the latitude of the previous point
     * @param prevLon the longitude of the previous point
     * @param prevTime the timestamp of the previous point
     * @return the velocity in m/is
     */
    public static double calculateVelocity(double nextLat,
                                           double nextLon,
                                           double nextTime,
                                           double prevLat,
                                           double prevLon,
                                           double prevTime) {
        double distance = Calculations.calculateDistance(
                prevLat,
                prevLon,
                nextLat,
                nextLon);
        double timeChange = (nextTime - prevTime) / 1000;
        return distance / timeChange;
    }

    /**
     * Calculates the acceleration at a point based on the next and previous points' velocities.
     * @param nextVelocity the velocity at the next point
     * @param nextTime the timestamp and the next point
     * @param prevVelocity the velocity at the previous point
     * @param prevTime the timestamp at the previous point
     * @return the acceleration in m/s^2
     */
    public static double calculateAcceleration(double nextVelocity,
                                               double nextTime,
                                               double prevVelocity,
                                               double prevTime) {
        double velocityChange = nextVelocity - prevVelocity;
        double timeChange = (nextTime - prevTime) / 1000;
        return velocityChange / timeChange;
    }
}
