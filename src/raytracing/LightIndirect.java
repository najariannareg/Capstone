package raytracing;

import java.util.Random;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static raytracing.RayTrace.traceRay;

public class LightIndirect {

    private Color black = new Color(0, 0, 0);

    // step 1: shaded point local coordinate system
    private Vec3[] createCoordinateSystem(Vec3 N){
        Vec3 Nt, Nb;
        if(Math.abs(N.getX()) > Math.abs(N.getY()))
            Nt = new Vec3(N.getZ(), 0, -N.getX());
        else
            Nt = new Vec3(0, -N.getZ(), N.getY());
        Nt.normalize();
        Nb = N.cross(Nt);
        Nb.normalize();
        return new Vec3[] {N, Nt, Nb};
    }

    // step 2: sample in world space
    private Vec3 uniformSampleHemisphere(float r1, float r2){
        // cos(theta) = r1 = y
        // cos^2(theta) + sin^2(theta) = 1 -> sin(theta) = sqrt(1 - cos^2(theta))
        float sinTheta = (float)Math.sqrt(1 - r1 * r1);
        float phi = (float)(2 * Math.PI * r2);
        float x = (float)(sinTheta * Math.cos(phi));
        float z = (float)(sinTheta * Math.sin(phi));
        return new Vec3(x, r1, z);
    }

    // step 3: transform world to local
    private Ray sampleRay(Intersection hit, Vec3[] coord, Vec3 sample){
        Vec3 N  = coord[0];
        Vec3 Nt = coord[1];
        Vec3 Nb = coord[2];
        Vec3 sampleWorld = new Vec3(
                sample.getX() * Nb.getX() + sample.getY() * N.getX() + sample.getZ() * Nt.getX(),
                sample.getX() * Nb.getY() + sample.getY() * N.getY() + sample.getZ() * Nt.getY(),
                sample.getX() * Nb.getZ() + sample.getY() * N.getZ() + sample.getZ() * Nt.getZ()
        );
        return new Ray(hit.getUnbiased(), sampleWorld, RayType.INDIRECT);
    }

    // steps 1 -> 7 indirect Illumination
    public Color monteCarlo(Intersection hit, Scene scene, int num, int depth){
        Color global, indirect = black;
        Random rand = new Random();
        float r1, r2;
        // step 1: local coordinate system
        Vec3[] coord = createCoordinateSystem(hit.getN());
        // step 6: repeat from 2 to 5
        for(int i = 0; i < num; i++){
            r1 = rand.nextFloat();
            r2 = rand.nextFloat();
            // step 2: sample direction
            Vec3 sample = uniformSampleHemisphere(r1, r2);
            // step 3: transformation model space -> world space
            Ray sampleRay = sampleRay(hit, coord, sample);
            // step 4: cast ray
            global = traceRay(sampleRay, scene, depth + 1);
            // step 5: compute indirect light
            indirect = indirect.sum(global.prod(r1));
        }
        // step 7: average, step 8: pdf, step 9: color albedo
        Material mat = hit.getShape().getMaterial();
        return indirect.prod(2f / num).prod(mat.getColor());
    }

    // parallelism
    public Color monteCarlo1(Intersection hit, Scene scene, int num, int depth){
        final Color[] indirect = {black};
        //Color global, indirect = black;
        Random rand = new Random();
        // step 1: local coordinate system
        Vec3[] coord = createCoordinateSystem(hit.getN());
        // step 6: repeat from 2 to 5
        List<Object> collect = IntStream.range(0, num).parallel().mapToObj(index -> {
            float r1, r2;
            r1 = rand.nextFloat();
            r2 = rand.nextFloat();
            // step 2: sample direction
            Vec3 sample = uniformSampleHemisphere(r1, r2);
            // step 3: transformation model space -> world space
            Ray sampleRay = sampleRay(hit, coord, sample);
            // step 4: cast ray
            Color global = traceRay(sampleRay, scene, depth + 1);
            // step 5: compute indirect light
            indirect[0] = indirect[0].sum(global.prod(r1));
            return 0;
        }).collect(Collectors.toList());
        // step 7: average, step 8: pdf, step 9: color albedo
        Material mat = hit.getShape().getMaterial();
        return indirect[0].prod(2f / num).prod(mat.getColor());
    }

}
