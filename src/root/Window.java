package root;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class Window {
    private Slider radiusSlider;
    private Slider chanceSlider;
    private Slider populationSlider;

    private Button startButton;
    private Button stopButton;

    private Scene s;
    private Group g;
    private HBox h;
    private Pane p;

    private Simulation sim;

    // Visuals
    private AnimationTimer t;
    private Long lastTimeStamp;

    public Window() {
        this.g = new Group();
        this.s = new Scene(g, 800,  800);

        initializeScrollers();
        initializeAnimation();

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
            stopAnimation();
        });
        populationSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Population: " + newVal);
            this.sim.updateAmountOfPeople(newVal.intValue());
            stopAnimation();
        });
        chanceSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Chance of infection: " + newVal);
            this.sim.updateChance((double) newVal);
            stopAnimation();
        });

        Text radiusText = new Text(" Radius: ");
        Text populationText = new Text(" Population: ");
        Text speedText = new Text(" Chance of infection: ");

        this.startButton = new Button("Start");
        this.stopButton = new Button("Stop");

        startButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                startAnimation();
            }
        });

        stopButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                stopAnimation();
            }
        });

        this.h = new HBox(radiusText, radiusSlider, populationText, populationSlider, speedText, chanceSlider, startButton);
        g.getChildren().add(h);
    }

    private void initializeAnimation(){
        t = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(lastTimeStamp != null) {
                    long milliSecsPast = (long) ((l - lastTimeStamp) / Math.pow(10, 6));
                    System.out.println(milliSecsPast);
                    sim.updateSimulation(milliSecsPast);
                }

                lastTimeStamp = l;
            }
        };
    }

    private void startAnimation() {
        System.out.println("start animation");
        t.start();
        h.getChildren().remove(6);
        h.getChildren().add(stopButton);
    }

    private void stopAnimation() {
        System.out.println("stop animation");
        t.stop();
        h.getChildren().remove(6);
        h.getChildren().add(startButton);
    }

    public Scene getScene() {
        return this.s;
    }
}
