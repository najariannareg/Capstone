package raytracing;

public class Color {

    private float[] color = new float[3];

    public Color(float r, float g, float b){
        color[0] = r;
        color[1] = g;
        color[2] = b;
    }

    public float getR(){
        return color[0];
    }

    public float getG(){
        return color[1];
    }

    public float getB(){
        return color[2];
    }

    public int intColor(){
        Color c = this.clamp(0, 1).prod(255);
        return ((int)c.getR() << 16 | (int)c.getG() << 8 | (int)c.getB());
    }

    private Color clamp(float low, float high){
        return new Color(
             Math.max(low, Math.min(high, color[0])),
             Math.max(low, Math.min(high, color[1])),
             Math.max(low, Math.min(high, color[2]))
        );
    }


    public void add(Color c){
        color[0] += c.getR();
        color[1] += c.getG();
        color[2] += c.getB();
    }

    public Color sum(Color c){
        return new Color(
            color[0] + c.getR(),
            color[1] + c.getG(),
            color[2] + c.getB()
        );
    }

    public void mult(float c){
        color[0] *= c;
        color[1] *= c;
        color[2] *= c;
    }

    public Color prod(float c){
        return new Color(
            color[0] * c,
            color[1] * c,
            color[2] * c
        );
    }

    public void mult(Color c){
        color[0] *= c.getR();
        color[1] *= c.getG();
        color[2] *= c.getB();
    }

    public Color prod(Color c){
        return new Color(
            color[0] * c.getR(),
            color[1] * c.getG(),
            color[2] * c.getB()
        );
    }

    @Override
    public String toString(){
        return "{" + color[0] + ", " + color[1] + ", " + color[2] + "}";
    }

}
