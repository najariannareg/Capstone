package raytracing;

import static library.Matrix.*;
import static library.Transformation3D.getTranslationMatrix;

public class Camera {

    private Point position = new Point(0, 0, 0);
    private Point target = new Point(0, 0, -1);

    private float[][] positionMatrix = identityMatrix(4);
    private float[][] directionMatrix = identityMatrix(4);
    private float[][] lookAtMatrix = identityMatrix(4);

    private float theta;
    private float width;
    private float height;

    public Camera(float theta, float width, float height){
        translateCamera(position.getX(), position.getY(), position.getZ());
        directionMatrix = makeDirectionMatrix();
        lookAtMatrix = matrixMultiplication(directionMatrix, positionMatrix);
        this.theta = theta;
        this.width = width;
        this.height = height;
    }

    public void translateCamera(float tx, float ty, float tz){
        float[][] t = getTranslationMatrix(-tx, -ty, -tz);
        positionMatrix = matrixMultiplication(positionMatrix, t);
    }

    public float[][] makeDirectionMatrix(){
        // direction vector
        float[][] d = normalize(getVector(position.getPoints(), target.getPoints()));
        // forward vector
        float[][] f = scalarMultiplication(-1, d);
        // y up vector
        float[][] y = {{0}, {1}, {0}};
        // right vector
        float[][] r = normalize(crossProduct(y, f));
        // up vector
        float[][] u = normalize(crossProduct(f, r));
        return new float[][] {
                {r[0][0], u[0][0], f[0][0], 0},
                {r[1][0], u[1][0], f[1][0], 0},
                {r[2][0], u[2][0], f[2][0], 0},
                {0, 0, 0, 1}
        };
    }

    public Point getPosition(){
        return position;
    }

    public float[][] getLookAtMatrix() {
        return lookAtMatrix;
    }

    public float getTheta() {
        return theta;
    }

    public void setTheta(float theta) {
        this.theta = theta;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

}
