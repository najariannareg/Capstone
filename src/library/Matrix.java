package library;

public class Matrix {

    // Matrices
    // --------
    public static float[][] matrixMultiplication(float[][] m1, float[][] m2) {
        if(m1[0].length != m2.length) throw new AssertionError("matrix size mismatch");
        int row = m1.length;
        int col = m2[0].length;
        float[][] m = new float[row][col];
        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                float dot = 0;
                for(int k=0; k<m2.length; k++){
                    dot += m1[i][k] * m2[k][j];
                }
                m[i][j] = dot;
            }
        }
        return m;
    }

    public static float[][] matrixTranspose(float[][] m){
        int row = m.length;
        int col = m[0].length;
        float[][] t = new float[col][row];
        for(int i=0; i<row; i++){
            for(int j=0; j<col; j++){
                t[j][i] = m[i][j];
            }
        }
        return t;
    }

    public static float[][] identityMatrix(int n){
        float[][] m = new float[n][n];
        for(int i=0; i<n; i++){
            for(int j=0; j<n; j++){
                m[i][j] = i==j? 1: 0;
            }
        }
        return m;
    }

    public static void printMatrix(float[][] m){
        System.out.print("{");
        for(int i=0; i<m.length; i++){
            System.out.print("{");
            for(int j=0; j<m[i].length; j++){
                System.out.print(m[i][j]);
                System.out.print(j<m[i].length-1? ", ": "}");
            }
            System.out.println(i<m.length-1? ",": "}");
        }
        System.out.println();
    }



    // Vectors
    // -------
    public static float dotProduct(float[][] v1, float[][] v2){
        if(v1.length != v2.length) throw new AssertionError("vector size mismatch");
        if(v1[0].length != 1) throw new AssertionError("v1 not a column vector");
        if(v2[0].length != 1) throw new AssertionError("v2 not a column vector");
        float[][] vT = matrixTranspose(v1);
        float dot = 0;
        for(int k=0; k<v2.length; k++){
            dot += vT[0][k] * v2[k][0];
        }
        return dot;
    }

    public static float magnitude(float[][] v){
        return (float)Math.sqrt(dotProduct(v, v));
    }


    // vec3
    // ----
    public static float[][] crossProduct(float[][] v1, float[][] v2){
        if(v1.length != v2.length) throw new AssertionError("vector size mismatch");
        if(v1[0].length != 1) throw new AssertionError("v1 not a column vector");
        if(v2[0].length != 1) throw new AssertionError("v2 not a column vector");
        return new float[][] {
                {v1[1][0] * v2[2][0] - v1[2][0] * v2[1][0]},
                {v1[2][0] * v2[0][0] - v1[0][0] * v2[2][0]},
                {v1[0][0] * v2[1][0] - v1[1][0] * v2[0][0]}
        };
    }

    public static float[][] vec3To4(float[][] v){
        return new float[][] {
                {v[0][0]},
                {v[1][0]},
                {v[2][0]},
                {1}
        };
    }

    public static float[][] vec4To3(float[][] v){
        return new float[][] {
                {v[0][0]},
                {v[1][0]},
                {v[2][0]}
        };
    }

    public static float[][] getVector(float[][] p1, float[][] p2){
        return new float[][] {
                {p2[0][0] - p1[0][0]},
                {p2[1][0] - p1[1][0]},
                {p2[2][0] - p1[2][0]}
        };
    }

    public static float[][] addition(float[][] v1, float[][] v2){
        return new float[][] {
                {v1[0][0] + v2[0][0]},
                {v1[1][0] + v2[1][0]},
                {v1[2][0] + v2[2][0]}
        };
    }

    public static float[][] subtraction(float[][] v1, float[][] v2){
        return new float[][] {
                {v1[0][0] - v2[0][0]},
                {v1[1][0] - v2[1][0]},
                {v1[2][0] - v2[2][0]}
        };
    }

    public static float[][] scalarMultiplication(float t, float[][] V){
        return new float[][] {
                {t * V[0][0]},
                {t * V[1][0]},
                {t * V[2][0]}
        };
    }

    public static float[][] normalize(float[][] n){
        float m = magnitude(n);
        return new float[][] {
                {n[0][0] / m},
                {n[1][0] / m},
                {n[2][0] / m}
        };
    }

    public static float[][] vec3(float x, float y, float z){
        return new float[][] {{x}, {y}, {z}};
    }

    public static float[][] vec4(float x, float y, float z, float w){
        return new float[][] {{x}, {y}, {z}, {w}};
    }

}
