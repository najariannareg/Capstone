package raytracing;


public class Ray {

    private Point source;
    private Vec3 direction;
    private RayType type = RayType.DIRECT;

    public Ray(Point source, Point target){
        this.source = source;
        direction = target.diff(source).normalise();
    }

    public Ray(Point source, Vec3 direction){
        this.source = source;
        this.direction = direction.normalise();
    }

    public Ray(Point source, Vec3 direction, RayType type){
        this.source = source;
        this.direction = direction.normalise();
        this.type = type;
    }

    public Point getSource() { return source; }
    public Vec3 getDir() { return direction; }
    public RayType getType() { return type; }

}
