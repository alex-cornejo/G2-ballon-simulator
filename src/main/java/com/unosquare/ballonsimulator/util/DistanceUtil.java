package com.unosquare.ballonsimulator.util;

/**
 * DistanceUtil
 */
public class DistanceUtil {

    /**
     * Computes the euclidean distance for n dimensions for two vectors
     * @param p1 vector 1
     * @param p2 vector 2
     * @return euclidean distance
     */
    public static float computeEucDist(int[] p1, int[] p2) {
        float dist = 0;
        for (int i = 0; i < p1.length; i++) {
            dist += Math.pow(p1[i] - p2[i], 2);
        }
        dist = (float) Math.sqrt(dist);
        return dist;
    }

    /**
     * Computes the euclidean distance for n dimensions for two vectors
     * @param p1 vector 1
     * @param p2 vector 2
     * @return euclidean distance
     */
    public static float computeEucDist(float[] p1, float[] p2) {
        float dist = 0;
        for (int i = 0; i < p1.length; i++) {
            dist += Math.pow(p1[i] - p2[i], 2);
        }
        dist = (float) Math.sqrt(dist);
        return dist;
    }
}