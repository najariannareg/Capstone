package raytracing;


public abstract class Shape {

    protected Material material = new Material(MaterialType.LAMBERTIAN, new Color(0.18f, 0.18f, 0.18f), 10);


    public Material getMaterial() {
        return material;
    }

    public abstract float intersect(Ray ray);

    public abstract Vec3 normal(Point p);

    @Override
    public abstract String toString();

}
