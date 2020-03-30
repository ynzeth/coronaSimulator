package root;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;

public class Window {
    private Slider radiusSlider;
    private Slider speedSlider;
    private Slider populationSlider;

    private Group g;
    private Scene s;

    private Simulation sim;

    public Window() {
        this.g = new Group();
        this.s = new Scene(g, 800,  800);

        this.sim = new Simulation();
        initializeScrollers();
    }

    private void initializeScrollers() {
        this.radiusSlider = new Slider();
        this.populationSlider = new Slider();
        this.speedSlider = new Slider();

        radiusSlider.setMin(1);
        radiusSlider.setMax(10);
        populationSlider.setMin(2);
        populationSlider.setMax(50);
        speedSlider.setMin(1);
        speedSlider.setMax(20);

        radiusSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Radius: " + newVal);
        });
        populationSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Population: " + newVal);
        });
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Speed: " + newVal);
        });

        Text radiusText = new Text(" Radius: ");
        Text populationText = new Text(" Population: ");
        Text speedText = new Text(" Speed: ");

        g.getChildren().add(new HBox(radiusText, radiusSlider, populationText, populationSlider, speedText, speedSlider));
    }

    public Scene getScene() {
        return this.s;
    }
}
