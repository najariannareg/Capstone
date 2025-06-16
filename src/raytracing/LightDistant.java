package raytracing;

public class LightDistant extends LightDirect{

    private Vec3 dir;

    public LightDistant(Vec3 dir, float La, float Ld, float Ls, Color color){
        this.dir = dir.normalise();
        this.La = La;
        this.Ld = Ld;
        this.Ls = Ls;
        this.color = color;
    }

    public Vec3 getDir() {
        return dir;
    }

    @Override
    public Vec3 getL(Point p){
        return dir.prod(-1);
    }

    @Override
    public float att(Point p) {
        return (Ld + Ls) / 2;
    }

    @Override
    public float dist(Point p){
        return Float.MAX_VALUE;
    }

}
