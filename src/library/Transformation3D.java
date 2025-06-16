package library;

public class Transformation3D {

    public static float[][] getTranslationMatrix(float tx, float ty, float tz){
        return new float[][]{
                {1, 0, 0, tx},
                {0, 1, 0, ty},
                {0, 0, 1, tz},
                {0, 0, 0, 1}
        };
    }

    public static float[][] getRotationMatrixX(float angle){
        return new float[][] {
                {1, 0, 0, 0},
                {0, (float)Math.cos(Math.toRadians(angle)), (float)-Math.sin(Math.toRadians(angle)), 0},
                {0, (float)Math.sin(Math.toRadians(angle)), (float)Math.cos(Math.toRadians(angle)), 0},
                {0, 0, 0, 1}
        };
    }

    public static float[][] getRotationMatrixY(float angle){
        return new float[][] {
                {(float)Math.cos(Math.toRadians(angle)), 0, (float)Math.sin(Math.toRadians(angle)), 0},
                {0, 1, 0, 0},
                {(float)-Math.sin(Math.toRadians(angle)), 0, (float)Math.cos(Math.toRadians(angle)), 0},
                {0, 0, 0, 1}
        };
    }

    public static float[][] getRotationMatrixZ(float angle){
        return new float[][] {
                {(float)Math.cos(Math.toRadians(angle)), (float)-Math.sin(Math.toRadians(angle)), 0, 0},
                {(float)Math.sin(Math.toRadians(angle)), (float)Math.cos(Math.toRadians(angle)), 0, 0},
                {0, 0, 1, 0},
                {0, 0, 0, 1}
        };
    }

    public static float[][] getScalingMatrix(float sx, float sy, float sz){
        return new float[][] {
                {sx, 0, 0, 0},
                {0, sy, 0, 0},
                {0, 0, sz, 0},
                {0, 0, 0, 1}
        };
    }

}
