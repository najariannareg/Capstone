package raytracing;

import java.io.IOException;

public class Render3 {

    public static void main(String[] args) throws IOException {

        final int imageWidth = 512;
        final int imageHeight = 512;

        // Render start
        // ------------
        Scene scene = new Scene();

        Color red = new Color(0.75f, 0.25f, 0.25f);
        Color green = new Color(0.25f, 0.75f, 0.25f);
        Color blue = new Color(0, 0, 1);
        Color cyan = new Color(0, 1, 1);
        Color magenta = new Color(1, 0, 1);
        Color yellow = new Color(1, 1, 0);
        Color white = new Color(1, 1, 1);
        Color black = new Color(0, 0, 0);
        Color grey = new Color(0.5f, 0.5f, 0.5f);
        Color greyLight = new Color(0.75f, 0.75f, 0.75f);
        Color greyDark = new Color(0.25f, 0.25f, 0.25f);
        Color greyDefault = new Color(0.18f, 0.18f, 0.18f);

        Material walls = new Material(MaterialType.LAMBERTIAN, white, 30);
        Material floorMat = new Material(MaterialType.LAMBERTIAN, white, 30);
        Material sphereMat1 = new Material(MaterialType.LAMBERTIAN, greyLight,30);
        Material sphereMat2 = new Material(MaterialType.LAMBERTIAN, greyLight,30);
        Material mirror = new Material(MaterialType.REFLECTIVE, white, 30);
        Material glass = new Material(MaterialType.REFRACTIVE, white, 30, 1.5f);


        // bottom
        Triangle t1 = new Triangle(
                new Point(-40.0f, -5.0f, -60.0f),
                new Point(-40.0f, -5.0f, 10.0f),
                new Point( 40.0f, -5.0f, 10.0f),
                floorMat
        );

        Triangle t2 = new Triangle(
                new Point( 40.0f, -5.0f, 10.0f),
                new Point( 40.0f, -5.0f, -60.0f),
                new Point(-40.0f, -5.0f, -60.0f),
                floorMat
        );

        // top (clockwise)
        Triangle t3 = new Triangle(
                new Point(-40.0f, 35.0f, -60.0f),
                new Point( 40.0f, 35.0f, -60.0f),
                new Point( 40.0f, 35.0f, 10.0f),
                walls
        );

        Triangle t4 = new Triangle(
                new Point( 40.0f, 35.0f, 10.0f),
                new Point(-40.0f, 35.0f, 10.0f),
                new Point(-40.0f, 35.0f, -60.0f),
                walls
        );

        // back
        Triangle t5 = new Triangle(
                new Point(-40.0f, 35.0f, -60.0f),
                new Point(-40.0f, -5.0f, -60.0f),
                new Point( 40.0f, -5.0f, -60.0f),
                walls
        );

        Triangle t6 = new Triangle(
                new Point( 40.0f, -5.0f, -60.0f),
                new Point( 40.0f, 35.0f, -60.0f),
                new Point(-40.0f, 35.0f, -60.0f),
                walls
        );

        // front (clockwise)
        Triangle t7 = new Triangle(
                new Point(-40.0f, 35.0f, 10.0f),
                new Point( 40.0f, 35.0f, 10.0f),
                new Point( 40.0f, -5.0f, 10.0f),
                walls
        );

        Triangle t8 = new Triangle(
                new Point( 40.0f, -5.0f, 10.0f),
                new Point(-40.0f, -5.0f, 10.0f),
                new Point(-40.0f, 35.0f, 10.0f),
                walls
        );

        // left
        Triangle t9 = new Triangle(
                new Point(-40.0f, 35.0f, 10.0f),
                new Point(-40.0f, -5.0f, 10.0f),
                new Point(-40.0f, -5.0f, -60.0f),
                walls
        );

        Triangle t10 = new Triangle(
                new Point(-40.0f, -5.0f, -60.0f),
                new Point(-40.0f, 35.0f, -60.0f),
                new Point(-40.0f, 35.0f, 10.0f),
                walls
        );

        // right (clockwise)
        Triangle t11 = new Triangle(
                new Point(40.0f, 35.0f, 10.0f),
                new Point(40.0f, 35.0f, -60.0f),
                new Point(40.0f, -5.0f, -60.0f),
                walls
        );

        Triangle t12 = new Triangle(
                new Point(40.0f, -5.0f, -60.0f),
                new Point(40.0f, -5.0f, 10.0f),
                new Point(40.0f, 35.0f, 10.0f),
                walls
        );

        // bottom
        scene.addShape(t1);
        scene.addShape(t2);
        // top
        scene.addShape(t3);
        scene.addShape(t4);
        // back
        scene.addShape(t5);
        scene.addShape(t6);
        // front
        scene.addShape(t7);
        scene.addShape(t8);
        // left
        scene.addShape(t9);
        scene.addShape(t10);
        // right
        scene.addShape(t11);
        scene.addShape(t12);


        Sphere s1 = new Sphere(
                new Point(7.0f, 5.0f, -30.0f),
                9.0f,
                sphereMat1
        );

        Sphere s2 = new Sphere(
                new Point(-7.0f, 0.0f, -20.0f),
                4.0f,
                mirror
        );

        Sphere s3 = new Sphere(
                new Point(0.0f, 0.0f, -10.0f),
                2.0f,
                glass
        );

        scene.addShape(s1);
        scene.addShape(s2);
        scene.addShape(s3);


        LightPoint light1 = new LightPoint(
                new Point(-10.0f, 34.0f, -10.0f),
                0.5f, 30f, 30f,
                cyan
        );

        LightPoint light2 = new LightPoint(
                new Point(10.0f, 34.0f, -20.0f),
                0.5f, 30f, 30f,
                magenta
        );

        scene.addLight(light1);
        scene.addLight(light2);


        Camera camera = new Camera(90, imageWidth, imageHeight);

        RayTrace.render(camera, scene, imageWidth, imageHeight);
        // ----------
        // Render end

    }

}
