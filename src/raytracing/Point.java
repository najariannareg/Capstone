package raytracing;


public class Point{

    private float[][] points = new float[3][1];

    public Point(float x, float y, float z){
        points[0][0] = x;
        points[1][0] = y;
        points[2][0] = z;
    }

    public Point(float[][] p){
        if(p.length != 3 && p[0].length != 1) throw new AssertionError("not a vec3");
        this.points = p;
    }


    public float[][] getPoints(){
        return points;
    }

    public void setPoints(float[][] points){
        this.points = points;
    }

    public float getX(){
        return points[0][0];
    }

    public void setX(float x){
        points[0][0] = x;
    }

    public float getY(){
        return points[1][0];
    }

    public void setY(float y){
        points[1][0] = y;
    }

    public float getZ(){
        return points[2][0];
    }

    public void setZ(float z){
        points[2][0] = z;
    }

    public float getI(int i){
        return points[i][0];
    }

    public void setI(int i, float num){
        points[i][0] = num;
    }


    public void move(Vec3 v){
        points[0][0] += v.getX();
        points[1][0] += v.getY();
        points[2][0] += v.getZ();
    }

    public Point shift(Vec3 v){
        return new Point(
            points[0][0] + v.getX(),
            points[1][0] + v.getY(),
            points[2][0] + v.getZ()
        );
    }

    public Vec3 diff(Point p){
        return new Vec3(
            points[0][0] - p.getX(),
            points[1][0] - p.getY(),
            points[2][0] - p.getZ()
        );
    }

    public Vec3 diff(){
        return new Vec3(
            points[0][0],
            points[1][0],
            points[2][0]
        );
    }

    public float dist(Point p){
        return p.diff(this).magnitude();
    }

    @Override
    public String toString(){
        return "{" + getX() + ", " + getY() + ", " + getZ() + "}";
    }

}