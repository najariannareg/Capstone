package raytracing;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static library.Matrix.*;

public class RayTrace {

    private static Color background = new Color(0.53f, 0.81f, 0.92f); // sky blue
    private static Color black = new Color(0, 0, 0);
    private static Color white = new Color(1, 1, 1);
    private static Color greyDefault = new Color(0.18f, 0.18f, 0.18f);
    private static Color grey = new Color(0.5f, 0.5f, 0.5f);


    private static float maxDepthDI = 4;
    private static float maxDepthGI = 1;
    private static int num = 4;

    private static Intersection findIntersection(Ray ray, Scene scene){
        float minT = Float.MAX_VALUE;
        Shape minPrimitive = null;
        for(Shape primitive: scene.getShapes()){
            float t = primitive.intersect(ray);
            if(t < minT){
                minPrimitive = primitive;
                minT = t;
            }
        }
        return new Intersection(ray, minPrimitive, minT);
    }

    private static boolean findLight(Ray ray, Scene scene, LightDirect light){
        float minT = light.dist(ray.getSource());
        for(Shape primitive: scene.getShapes()){
            float t = primitive.intersect(ray);
            if(t < minT) return false;
        }
        return true;
    }

    private static Ray constructRayThroughPixel(Camera camera, int i, int j, float r1, float r2){
        float theta = camera.getTheta();
        float width = camera.getWidth();
        float height = camera.getHeight();
        float aspectRatio = width / height;
        Point source = camera.getPosition();
        // Raster Space
        float pixelX = (i + r1) / width;
        float pixelY = (j + r2) / height;
        // Raster Space -> Screen Space -> NDC Space -> Camera Space
        pixelX = (2 * pixelX - 1) * (float)Math.tan(Math.toRadians(theta/2)) * aspectRatio;
        pixelY = (1 - 2 * pixelY) * (float)Math.tan(Math.toRadians(theta/2));
        // Camera Space -> World Space
        float[][] targetCS = vec4(pixelX, pixelY, -1, 1);
        float[][] targetWS = matrixMultiplication(camera.getLookAtMatrix(), targetCS);
        targetWS = vec4To3(targetWS);
        Point target = new Point(targetWS);
        // return new ray
        return new Ray(source, target);
    }

    public static Color traceRay(Ray ray, Scene scene, int depth){
        if(depth > maxDepthDI && ray.getType() == RayType.DIRECT)
            return grey;
        if(depth > maxDepthGI && ray.getType() == RayType.INDIRECT)
            return white;
        Intersection hit = findIntersection(ray, scene);
        if(hit.getShape() == null && ray.getType() == RayType.DIRECT)
            return background;
        if(hit.getShape() == null && ray.getType() == RayType.INDIRECT)
            return white;

        Color hitColor = black;
        switch (hit.getShape().getMaterial().getType()){
            case LAMBERTIAN: {
                // direct illumination
                Color phong, direct = black, indirect;
                for (LightDirect light : scene.getLights()) {
                    Ray shadow = new Ray(hit.getUnbiased(), light.getL(hit.getP()));
                    if (!findLight(shadow, scene, light))
                        phong = light.getAmbient(hit);
                    else
                        phong = light.getBlinnPhong(hit);
//                        phong = light.getCartoon(hit, 6);
                    direct = direct.sum(phong);
                }
                hitColor = hitColor.sum(direct);
                // indirect illumination
                indirect = scene.getIndirect().monteCarlo(hit, scene, num, depth);
                hitColor = hitColor.sum(indirect);
                break;
            }
            case REFLECTIVE: {
                Color reflective;
                Ray R = reflect(hit);
                reflective = traceRay(R, scene, depth + 1).prod(0.8f);
                hitColor = hitColor.sum(reflective);
                break;
            }
            case REFRACTIVE: {
                Color reflective, refractive = black;
                Ray Re = reflect(hit);
                reflective = traceRay(Re, scene, depth + 1);
                float kr = fresnel(hit);
                if (kr < 1) {
                    Ray Ra = refract(hit);
                    refractive = traceRay(Ra, scene, depth + 1);
                }
                Color fresnel = reflective.prod(kr).sum(refractive.prod(1 - kr));
                hitColor = hitColor.sum(fresnel);
                break;
            }
        }

        return hitColor;
    }

    public static void render(Camera camera, Scene scene, int width, int height) throws IOException {
        // window setup
        final BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Random rand = new Random();
        final int[] count = {0};

        List<Object> collect = IntStream.range(0, width * height).parallel().mapToObj(index -> {
            if(count[0] % width == 0) System.out.println(count[0] / width);
            count[0]++;
            float r1, r2;
            int i = index / width;
            int j = index % width;
            Color hitColor, pixel = black;
            for(int k = 0; k < num; k++) {
                r1 = rand.nextFloat();
                r2 = rand.nextFloat();
                Ray primary = constructRayThroughPixel(camera, i, j, r1, r2);
                hitColor = traceRay(primary, scene, 0);
                pixel = pixel.sum(hitColor);
            }
            pixel = pixel.prod(1f / num);
            image.setRGB(i, j, pixel.intColor());
            return 0;
        }).collect(Collectors.toList());

//        List<Object> collect = IntStream.range(0, width * height).parallel().mapToObj(index -> {
//            if(count[0] % width == 0) System.out.println(count[0] / width);
//            count[0]++;
//
//            int i = index / width;
//            int j = index % width;
//            final Color[] pixel = {black};
//            List<Object> k = IntStream.range(0, num).parallel().mapToObj(ind -> {
//                float r1, r2;
//                r1 = rand.nextFloat();
//                r2 = rand.nextFloat();
//                Ray primary = constructRayThroughPixel(camera, i, j, r1, r2);
//                Color hitColor = traceRay(primary, scene, 0);
//                pixel[0] = pixel[0].sum(hitColor);
//                return 0;
//            }).collect(Collectors.toList());
//            pixel[0] = pixel[0].prod(1f / num);
//            image.setRGB(i, j, pixel[0].intColor());
//            return 0;
//        }).collect(Collectors.toList());

        File file = new File("RayTrace.jpg");
        ImageIO.write(image, "jpg", file);
    }




    // reflect, refract, fresnel
    private static float clamp(float low, float high, float v){
        return Math.max(low, Math.min(high, v));
    }

    private static Ray reflect(Intersection hit){
        Vec3 I = hit.getV().prod(-1);
        Vec3 N = hit.getN();
        Vec3 R = I.diff(N.prod(2 * I.dot(N))).normalise();
        return new Ray(hit.getUnbiased(), R);
    }

    private static Ray refract(Intersection hit){
        Vec3 I = hit.getV().prod(-1);
        Vec3 N = hit.getN();
        float ior = hit.getShape().getMaterial().getIOR();
        float cosi = clamp(-1, 1, I.dot(N));
        float etai = 1, etat = ior;
        Vec3 n = N;
        if(cosi < 0) cosi = -cosi;
        else {
            etai = ior;
            etat = 1;
            n = N.prod(-1);
        }
        float eta = etai / etat;
        float k = 1 - eta * eta * (1 - cosi * cosi);
        Vec3 R =  k < 0 ? new Vec3(0, 0, 0) :
                I.prod(eta).sum(n.prod(eta * cosi - (float)Math.sqrt(k))).normalise();
        return new Ray(hit.getUnbiased(), R);
    }

    private static float fresnel(Intersection hit){
        Vec3 I = hit.getV().prod(-1);
        Vec3 N = hit.getN();
        float ior = hit.getShape().getMaterial().getIOR();
        float kr;
        float cosi = clamp(-1, 1, I.dot(N));
        float etai = 1, etat = ior;
        if(cosi > 0){
            etai = ior;
            etat = 1;
        }
        float sint = etai / etat * (float)Math.sqrt(Math.max(0, 1 - cosi * cosi));
        if(sint >= 1) kr = 1;
        else {
            float cost = (float)Math.sqrt(Math.max(0, 1 - sint * sint));
            cosi = Math.abs(cosi);
            float Rs = ((etat * cosi) - (etai * cost)) / ((etat * cosi) + (etai * cost));
            float Rp = ((etai * cosi) - (etat * cost)) / ((etai * cosi) + (etat * cost));
            kr = (Rs * Rs + Rp * Rp) / 2;
        }
        return kr;
    }

}
