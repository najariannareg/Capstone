package raytracing;

import java.util.ArrayList;

public class Scene {

    private ArrayList<Shape> shapes = new ArrayList<>();
    private ArrayList<LightDirect> lights = new ArrayList<>();
    private LightIndirect indirect = new LightIndirect();

    public Scene(){}

    public void addShape(Shape shape){
        shapes.add(shape);
    }

    public void addLight(LightDirect light) {
        lights.add(light);
    }

    public ArrayList<Shape> getShapes(){
        return shapes;
    }

    public ArrayList<LightDirect> getLights() {
        return lights;
    }

    public LightIndirect getIndirect(){
        return indirect;
    }

}
