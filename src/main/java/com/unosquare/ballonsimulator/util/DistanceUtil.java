package com.unosquare.ballonsimulator.util;

/**
 * DistanceUtil
 */
public class DistanceUtil {

    public static float computeEucDist(int[] p1, int[] p2) {
        float dist = 0;
        for (int i = 0; i < p1.length; i++) {
            dist += Math.pow(p1[i] - p2[i], 2);
        }
        dist = (float) Math.sqrt(dist);
        return dist;
    }
}