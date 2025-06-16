package raytracing;

public abstract class LightDirect {

    protected float La;
    protected float Ld;
    protected float Ls;
    protected Color color;

    public abstract Vec3 getL(Point p);

    public abstract float att(Point p);

    public abstract float dist(Point p);

    public Color getBlinnPhong(Intersection hit){
        Material mat = hit.getShape().getMaterial();
        // Ambient Reflection
        float Ia = mat.getKa() * La;
        // Diffuse Reflection
        Vec3 L = getL(hit.getP()).normalise();
        float Id = mat.getKd() * Math.max(L.dot(hit.getN()), 0.0f) * Ld;
        // Specular Reflection
        Vec3 H = L.sum(hit.getV()).normalise();
        float Is = mat.getKs() * Math.max((float)Math.pow(H.dot(hit.getN()), mat.getK()), 0.0f) * Ls;
        // Attenuation
        float d = att(hit.getP());
        // Phong Illumination
        Color ambient = color.prod(Ia);
        Color diffuse = color.prod(mat.getColor().prod(Id / d));
        Color specular = color.prod(Is / d);
        return ambient.sum(diffuse).sum(specular);
    }

    public Color getCartoon(Intersection hit, int levels){
        Material mat = hit.getShape().getMaterial();
        // Ambient Reflection
        float Ia = mat.getKa() * La;
        // Diffuse Reflection
        Vec3 L = getL(hit.getP()).normalise();
        float cosine = Math.max(L.dot(hit.getN()), 0.0f);
        float Id = mat.getKd() * (float)Math.floor(cosine * levels) / levels * Ld;
        // Specular Reflection
        Vec3 H = L.sum(hit.getV()).normalise();
        float cosine1 = Math.max((float)Math.pow(H.dot(hit.getN()), mat.getK()), 0.0f);
        float Is = mat.getKs() * (float)Math.floor(cosine1 * levels) / levels * Ls;
        // Attenuation
        float d = att(hit.getP());
        // Cartoon Illumination
        Color ambient = color.prod(Ia);
        Color diffuse = color.prod(mat.getColor().prod(Id / d));
        Color specular = color.prod(Is / d);
        return ambient.sum(diffuse).sum(specular);
    }

    public Color getAmbient(Intersection hit){
        Material mat = hit.getShape().getMaterial();
        float Ia = mat.getKa() * La;
        return color.prod(Ia);
    }

    public Color getDiffuse(Intersection hit){
        Material mat = hit.getShape().getMaterial();
        Vec3 L = getL(hit.getP()).normalise();
        float Id = mat.getKd() * Math.max(L.dot(hit.getN()), 0.0f) * Ld;
        float d = att(hit.getP());
        return color.prod(mat.getColor().prod(Id / d));
    }

}
