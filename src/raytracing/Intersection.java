package raytracing;


public class Intersection {

    private Vec3 V;
    private Shape shape;
    private Point p;
    private Vec3 N;
    private float bias = 0.0001f;

    public Intersection(Ray ray, Shape primitive, float minT){
        this.V = ray.getDir().prod(-1);
        this.shape = primitive;
        if(shape != null) {
            this.p = ray.getSource().shift(ray.getDir().prod(minT));
            this.N = shape.normal(this.p);
        }
    }

    public Vec3 getV(){
        return V;
    }

    public Shape getShape(){
        return shape;
    }

    public Point getP(){
        return p;
    }

    public Vec3 getN(){
        return N;
    }


    public Point getUnbiased(){
        if(shape.getMaterial().getType() == MaterialType.REFRACTIVE && V.dot(N) >= 0)
            return p.shift(N.prod(-bias));
        return p.shift(N.prod(bias));
    }

}
