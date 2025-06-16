package raytracing;


public class Sphere extends Shape {

    private Point center;
    private float radius;

    public Sphere(Point center, float radius){
        this.center = center;
        this.radius = radius;
    }

    public Sphere(Point center, float radius, Material material){
        this.center = center;
        this.radius = radius;
        this.material = material;
    }

    public Point getCenter(){
        return center;
    }

    public float getRadius(){
        return radius;
    }

    private float[] solveQuadratic(float a, float b, float c){
        float discriminant = b * b - 4 * a * c;
        if(discriminant < 0) return null;
        else if(discriminant == 0) {
            float x = (float)(-0.5 * b / a);
            return new float[] {x, x};
        }
        else{
            float q = (b > 0) ? (float)(-0.5 * (b + Math.sqrt(discriminant))) : (float)(-0.5 * (b - Math.sqrt(discriminant)));
            return new float[] {q / a, c / q};
        }
    }

    @Override
    public float intersect(Ray ray) {
        Vec3 L = ray.getSource().diff(center);
        float a = ray.getDir().dot(ray.getDir());
        float b = 2 * L.dot(ray.getDir());
        float c = L.dot(L) - radius * radius;
        float[] roots = solveQuadratic(a, b, c);
        if(roots == null) return Float.MAX_VALUE;
        float t = Math.min(roots[0], roots[1]);
        if(t < 0) t = Math.max(roots[0], roots[1]);
        if(t < 0) t = Float.MAX_VALUE;
        return t;
    }

    @Override
    public Vec3 normal(Point p) {
        return p.diff(center).normalise();
    }

    @Override
    public String toString(){
        return "Sphere :";
    }

}
