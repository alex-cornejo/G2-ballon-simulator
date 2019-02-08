package com.unosquare.ballonsimulator.model;

/**
 * Observatory graphs of equivalences
 */
public class Observatory {

    public enum ObservatoryEnum {
        AU(0, "AU"), US(1, "US"), FR(2, "FR"), OTHER(3, "Other");

        private final int index;
        private final String code;

        ObservatoryEnum(int index, String code) {
            this.index = index;
            this.code = code;
        }

        public int getIndex() {
            return this.index;
        }

        public String getCode() {
            return this.code;
        }
    }

    @FunctionalInterface
    public interface TempConverter {
        float convert(float t);
    }

    public enum TempsEquivs implements TempConverter {

        C_TO_F {
            @Override
            public float convert(float t) {
                return (t*9/5)+32;
            }
        },
        C_TO_K {
            @Override
            public float convert(float t) {
                return t+273.15f;
            }
        },
        F_TO_C {
            @Override
            public float convert(float t) {
                return (t-32) * 5/9;
            }
        },
        F_TO_K {
            @Override
            public float convert(float t) {
                return (t - 32) * 5/9 + 273.15f;
            }
        },
        K_TO_C {
            @Override
            public float convert(float t) {
                return t - 273.15f;
            }
        },
        K_TO_F {
            @Override
            public float convert(float t) {
                return (t - 273.15f) * 9/5 + 32;
            }
        },
        T_TO_T {
            @Override
            public float convert(float t) {
                return t;
            }
        };
    }

    public static ObservatoryEnum getObservatory(String code) {
        try {
            return ObservatoryEnum.valueOf(code);
        } catch (IllegalArgumentException ex) {
            ex.printStackTrace();
            return ObservatoryEnum.OTHER;
        }
    }

    private static final float[][] DISTANCE_EQUIVALENCE = { 
            { 1f,       0.621371f,    1000f,    1f       },
            { 1.60934f, 1f,           1609.34f, 1.60934f }, 
            { 0.001f,   0.000621371f, 1f,       0.001f   }, 
            { 1f,       0.621371f,    1000f,    1f       } };

    private static final TempConverter[][] TEMPERATURE_EQUIVALENCE = {
            { TempsEquivs.T_TO_T, TempsEquivs.C_TO_F, TempsEquivs.C_TO_K, TempsEquivs.C_TO_K },
            { TempsEquivs.F_TO_C, TempsEquivs.T_TO_T, TempsEquivs.F_TO_K, TempsEquivs.F_TO_K },
            { TempsEquivs.K_TO_C, TempsEquivs.K_TO_F, TempsEquivs.T_TO_T, TempsEquivs.T_TO_T },
            { TempsEquivs.K_TO_C, TempsEquivs.K_TO_F, TempsEquivs.T_TO_T, TempsEquivs.T_TO_T } };

    public static float getDistEquiv(ObservatoryEnum obs1, int amount, ObservatoryEnum obs2) {
        return DISTANCE_EQUIVALENCE[obs1.getIndex()][obs2.getIndex()] * amount;
    }

    public static float getTempEquiv(ObservatoryEnum obs1, float temp, ObservatoryEnum obs2) {
        return TEMPERATURE_EQUIVALENCE[obs1.getIndex()][obs2.getIndex()].convert(temp);
    }

}