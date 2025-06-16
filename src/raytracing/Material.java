package raytracing;

public class Material {

    private MaterialType type; // DIFFUSE, REFLECTIVE, REFRACTIVE
    private Color color; // Material color
    private float ka = 0.2f; // Ambient Reflection Coefficient
    private float kd = 1 / (float)Math.PI; // Diffuse Reflection Coefficient
    private float ks = 0.2f; // Specular Reflection Coefficient
    private int k; // Shininess Factor
    private float ior = 1; // Index of Refraction

    public Material(MaterialType type, Color color, int k){
        this.type = type;
        this.color = color;
        this.k = k;
    }

    public Material(MaterialType type, Color color, int k, float ior){
        this.type = type;
        this.color = color;
        this.k = k;
        this.ior = ior;
    }

    public MaterialType getType() {
        return type;
    }

    public Color getColor(){
        return color;
    }

    public float getKa() {
        return ka;
    }

    public float getKd() {
        return kd;
    }

    public float getKs() {
        return ks;
    }

    public float getK(){
        return k;
    }

    public float getIOR(){
        return ior;
    }

}
