package raytracing;


public class Triangle extends Shape {

    private Point[] points = new Point[3];

    public Triangle(Point a, Point b, Point c){
        points[0] = a;
        points[1] = b;
        points[2] = c;
    }

    public Triangle(Point a, Point b, Point c, Material material){
        points[0] = a;
        points[1] = b;
        points[2] = c;
        this.material = material;
    }

    public Point getA(){ return points[0]; }

    public Point getB(){
        return points[1];
    }

    public Point getC(){
        return points[2];
    }

    public void printPoints(){
        System.out.print("{");
        for(int i=0; i<points.length; i++) {
            System.out.println(points[i]);
            System.out.print(i<points.length-1? ", ": "}");
        }
        System.out.println();
    }

    @Override
    public float intersect(Ray ray){
        Point P0 = ray.getSource(); // ray source
        Vec3 I = ray.getDir(); // ray direction
        // I) compute ray-plane intersection
        // 1) calculate plane normal
        Vec3 vAB = getB().diff(getA());
        Vec3 vAC = getC().diff(getA());
        Vec3 N = vAB.cross(vAC).normalise();
        // 2) check if ray and plane are parallel or if plane is behind the ray
        float IN = I.dot(N);
        if(IN > -0.000001) return Float.MAX_VALUE;
        // 3) compute t from the intersection equation
        float d = N.dot(getA().diff()); // plane distance from origin
        float P0N = P0.diff().dot(N);
        float t = ((d - P0N) / IN);
        // 5) compute intersection point by substituting t in ray equation
        Point P = P0.shift(I.prod(t));
        // II) perform inside-outside test with Möller–Trumbore algorithm
        Vec3 C;
        // edge AB
        Vec3 AB = getB().diff(getA());
        Vec3 AP = P.diff(getA());
        C = AB.cross(AP);
        if(N.dot(C) < 0) return Float.MAX_VALUE; // P is on the right side
        // edge BC
        Vec3 BC = getC().diff(getB());
        Vec3 BP = P.diff(getB());
        C = BC.cross(BP);
        if(N.dot(C) < 0) return Float.MAX_VALUE; // P is on the right side
        // edge CA
        Vec3 CA = getA().diff(getC());
        Vec3 CP = P.diff(getC());
        C = CA.cross(CP);
        if(N.dot(C) < 0) return Float.MAX_VALUE; // P is on the right side
        // P is on the left side
        return t;
    }

    public float intersect1(Ray ray){
        Point P0 = ray.getSource(); // ray source
        Vec3 I = ray.getDir(); // ray direction
        // I) compute ray-plane intersection
        // 1) calculate plane normal
        Vec3 vAB = getB().diff(getA());
        Vec3 vAC = getC().diff(getA());
        Vec3 cABC = vAB.cross(vAC);
        Vec3 N = cABC.normalise();
        // 2) check if ray and plane are parallel or if plane is behind the ray
        float IN = I.dot(N);
        if(IN > -0.000001) return Float.MAX_VALUE;
        // 3) compute t from the intersection equation
        float d = N.dot(getA().diff()); // plane distance from origin
        float P0N = P0.diff().dot(N);
        float t = ((d - P0N) / IN);
        // 5) compute intersection point by substituting t in ray equation
        Point P = P0.shift(I.prod(t));
        // perform inside-outside test with Barycentric-Coordinates
        Vec3 vAP =  P.diff(getA());
        Vec3 cABP = vAB.cross(vAP);
        Vec3 cACP = vAC.cross(vAP);
        // calculate triangle areas
        float areaABC = cABC.magnitude() / 2;
        float areaABP = cABP.magnitude() / 2;
        float areaACP = cACP.magnitude() / 2;
        // calculate proportions
        float u = areaABP / areaABC;
        float v = areaACP / areaABC;
        // check if P is inside the triangle ABC
        if(!(u >= 0 && v >= 0 && u+v < 1)) return Float.MAX_VALUE;
        // check if P is on the right side
        if(cABC.dot(cABP) < 0) return Float.MAX_VALUE;
        // check if P is on the left side
        if(cABC.dot(cACP) > 0) return Float.MAX_VALUE;
        // return computed value of t
        return t;
    }

    @Override
    public Vec3 normal(Point p) {
        Vec3 vPB = getB().diff(p);
        Vec3 vPC = getC().diff(p);
        Vec3 cPBC = vPB.cross(vPC);
        Vec3 N = cPBC.normalise();
        return N;
    }

    @Override
    public String toString(){
        return "Triangle:";
    }

}
