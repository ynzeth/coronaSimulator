package root;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

public class Window {
    // External
    private Simulation sim;

    // Visual (hierarchical)
    // lvl 0
    private Scene s;
    // lvl 1
    private Group g;
    private Pane p;
    // lvl 2
    private HBox h;
    // lvl 3
    private Slider radiusSlider;
    private Slider chanceSlider;
    private Slider populationSlider;
    private Button startButton;
    private Button stopButton;

    // Animation loop
    private AnimationTimer timer;
    private Long lastTimeStamp;

    public Window() {
        this.sim = new Simulation(10, 250, 0.1);

        this.p = new Pane();
        this.g = new Group(p);
        this.s = new Scene(g, 800,  800);

        initializeScrollers();
        initializeAnimation();
    }

    private void initializeScrollers() {
        this.radiusSlider = new Slider();
        this.populationSlider = new Slider();
        this.chanceSlider = new Slider();

        populationSlider.setMin(2);
        populationSlider.setMax(50);
        populationSlider.setValue(10);
        radiusSlider.setMin(1);
        radiusSlider.setMax(500);
        radiusSlider.setValue(250);
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
        h.setBackground(new Background(new BackgroundFill(Color.GREY, new CornerRadii(0), new Insets(0,0,0,0))));
        h.setPrefWidth(s.getWidth());
        g.getChildren().add(h);
    }

    private void initializeAnimation(){
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(lastTimeStamp != null) {
                    long milliSecsPast = (long) ((l - lastTimeStamp) / Math.pow(10, 6));
                    sim.updateSimulation(milliSecsPast);
                }

                lastTimeStamp = l;
            }
        };
    }

    private void startAnimation() {
        timer.start();
        h.getChildren().remove(6);
        h.getChildren().add(stopButton);
    }

    private void stopAnimation() {
        timer.stop();
        h.getChildren().remove(6);
        h.getChildren().add(startButton);
    }

    public Scene getScene() {
        return this.s;
    }
}
