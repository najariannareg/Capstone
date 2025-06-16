package raytracing;

import java.util.Arrays;

import static library.Matrix.*;

public class Vec3 {

    private float[][] vec = new float[3][1];

    public Vec3(float x, float y, float z){
        vec[0][0] = x;
        vec[1][0] = y;
        vec[2][0] = z;
    }

    public Vec3(float[][] vec){
        if(vec.length != 3 && vec[0].length != 1) throw new AssertionError("not a vec3");
        this.vec = vec;
    }


    public float[][] getVec(){
        return vec;
    }

    public void setVec(float[][] vec){
        this.vec = vec;
    }

    public float getX(){
        return vec[0][0];
    }

    public void setX(float x){
        vec[0][0] = x;
    }

    public float getY(){
        return vec[1][0];
    }

    public void setY(float y){
        vec[1][0] = y;
    }

    public float getZ(){
        return vec[2][0];
    }

    public void setZ(float z){
        vec[2][0] = z;
    }

    public float getI(int i){
        return vec[i][0];
    }

    public void setI(int i, float num){
        vec[i][0] = num;
    }


    public float dot(Vec3 v){
        float[][] vT = matrixTranspose(vec);
        float dot = 0;
        for(int k = 0; k < vec.length; k++){
            dot += vT[0][k] * v.getI(k);
        }
        return dot;
    }

    public Vec3 cross(Vec3 v){
        return new Vec3(
            vec[1][0] * v.getZ() - vec[2][0] * v.getY(),
            vec[2][0] * v.getX() - vec[0][0] * v.getZ(),
            vec[0][0] * v.getY() - vec[1][0] * v.getX()
        );
    }


    public void add(Vec3 v){
        vec[0][0] += v.getX();
        vec[1][0] += v.getY();
        vec[2][0] += v.getZ();
    }

    public Vec3 sum(Vec3 v){
        return new Vec3(
            vec[0][0] + v.getX(),
            vec[1][0] + v.getY(),
            vec[2][0] + v.getZ()
        );
    }

    public void sub(Vec3 v){
        vec[0][0] -= v.getX();
        vec[1][0] -= v.getY();
        vec[2][0] -= v.getZ();
    }

    public Vec3 diff(Vec3 v){
        return new Vec3(
            vec[0][0] - v.getX(),
            vec[1][0] - v.getY(),
            vec[2][0] - v.getZ()
        );
    }

    public void mult(float t){
        vec[0][0] *= t;
        vec[1][0] *= t;
        vec[2][0] *= t;
    }

    public Vec3 prod(float t){
        return new Vec3(
            vec[0][0] * t,
            vec[1][0] * t,
            vec[2][0] * t
        );
    }


    public float magnitude(){
        return (float)Math.sqrt(dot(this));
    }

    public void normalize(){
        float m = magnitude();
        vec[0][0] /= m;
        vec[1][0] /= m;
        vec[2][0] /= m;
    }

    public Vec3 normalise(){
        float m = magnitude();
        return new Vec3(
            vec[0][0] / m,
            vec[1][0] / m,
            vec[2][0] / m
        );
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec3 vec3 = (Vec3) o;
        return Arrays.equals(vec, vec3.vec);
    }

    @Override
    public String toString(){
        return "{" + getX() + ", " + getY() + ", " + getZ() + "}";
    }

}
