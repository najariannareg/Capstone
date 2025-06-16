package raytracing;

public class LightPoint extends LightDirect{

    private Point pos;

    public LightPoint(Point pos, float La, float Ld, float Ls, Color color){
        this.pos = pos;
        this.La = La;
        this.Ld = Ld;
        this.Ls = Ls;
        this.color = color;
    }

    public Point getPos() { return pos; }

    @Override
    public Vec3 getL(Point p) {
        return pos.diff(p);
    }

    @Override
    public float att(Point p) {
        return pos.dist(p);
    }

    @Override
    public float dist(Point p) {
        return pos.dist(p);
    }

}
