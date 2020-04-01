package root;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Window {
    private Slider radiusSlider;
    private Slider chanceSlider;
    private Slider populationSlider;

    private Scene s;
    private Group g;
    private Pane p;

    private Simulation sim;

    public Window() {
        this.g = new Group();
        this.s = new Scene(g, 800,  800);

        initializeScrollers();
        this.p = new Pane();
        this.g.getChildren().add(p);
        this.sim = new Simulation(10, 5, 0.1);
    }

    private void initializeScrollers() {
        this.radiusSlider = new Slider();
        this.populationSlider = new Slider();
        this.chanceSlider = new Slider();

        radiusSlider.setMin(1);
        radiusSlider.setMax(10);
        radiusSlider.setValue(5);
        populationSlider.setMin(2);
        populationSlider.setMax(50);
        populationSlider.setValue(10);
        chanceSlider.setMin(0);
        chanceSlider.setMax(1);
        chanceSlider.setValue(0.1);

        radiusSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Radius: " + newVal);
            this.sim.updateRadius((double) newVal);
        });
        populationSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Population: " + newVal);
            this.sim.updateAmountOfPeople(newVal.intValue());
        });
        chanceSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Chance of infection: " + newVal);
            //this.sim.updateChance((double) newVal);
            sim.updateSimulation(500);
        });

        Text radiusText = new Text(" Radius: ");
        Text populationText = new Text(" Population: ");
        Text speedText = new Text(" Chance of infection: ");

        g.getChildren().add(new HBox(radiusText, radiusSlider, populationText, populationSlider, speedText, chanceSlider));
    }

    public Scene getScene() {
        return this.s;
    }
}
