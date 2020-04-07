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
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class Window {
    // Visual (hierarchical)

    /// lvl 0
    private Scene s;

    /// lvl 1
    private Group g;
    private Pane p;

    /// lvl 2
    private HBox h;
    private Circle[] people;
    private Circle[] radii;

    /// lvl 3
    private Slider radiusSlider;
    private Slider speedSlider;
    private Slider populationSlider;
    private Button startButton;
    private Button stopButton;
    private Button resetButton;

    // External
    private Simulation sim;

    // Animation loop
    private AnimationTimer timer;
    private Long lastTimeStamp;

    //screen dimensions
    private final static int screenWidth = 1500;
    private final static int screenHeight = 800;

    public Window() {
        this.p = new Pane();
        this.g = new Group(p);
        this.s = new Scene(g, screenWidth,  screenHeight);

        this.sim = new Simulation(10, screenWidth/20, 0.5, screenWidth, screenHeight, this);

        initializeHUD();
        initializeAnimation();
        initializeSimulationElements();
    }

    private void initializeHUD() {
        this.radiusSlider = new Slider();
        this.populationSlider = new Slider();
        this.speedSlider = new Slider();

        populationSlider.setMin(2);
        populationSlider.setMax(50);
        populationSlider.setValue(10);
        radiusSlider.setMin(20);
        radiusSlider.setMax(screenWidth/4);
        radiusSlider.setValue(screenWidth/20);
        speedSlider.setMin(0);
        speedSlider.setMax(1);
        speedSlider.setValue(0.5);

        radiusSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Radius: " + newVal);
            this.sim.updateRadius((double) newVal);
        });
        populationSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Population: " + newVal);
            this.sim.updateAmountOfPeople(newVal.intValue());
            stopAnimation();
        });
        speedSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            System.out.println("Speed of particles: " + newVal);
            this.sim.updateSpeed((double) newVal);
        });

        Text radiusText = new Text(" Radius: "); radiusText.setFill(Color.WHITE);
        Text populationText = new Text(" Population: "); populationText.setFill(Color.WHITE);
        Text speedText = new Text(" Speed: "); speedText.setFill(Color.WHITE);

        this.startButton = new Button("Start");
        this.stopButton = new Button("Stop");
        this.resetButton = new Button("Reset");

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
        resetButton.addEventHandler(ActionEvent.ACTION, new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                resetSimulation();
            }
        });

        this.h = new HBox(radiusText, radiusSlider, populationText, populationSlider, speedText, speedSlider, resetButton, startButton);
        h.setBackground(new Background(new BackgroundFill(Color.BLACK, new CornerRadii(0), new Insets(0,0,0,0))));
        h.setPrefWidth(s.getWidth());
        g.getChildren().add(h);
    }

    public void initializeSimulationElements(){
        this.people = new Circle[sim.getAmountOfPeople()];
        this.radii = new Circle[sim.getAmountOfPeople()];

        p.getChildren().removeAll(p.getChildren());

        people[0] = new Circle(2, Color.RED);
        radii[0] = new Circle(sim.getRadius()/2, new Color(1, 0, 0, 0.5));
        for(int i = 1; i < sim.getAmountOfPeople(); i++){
            people[i] = new Circle(2, Color.GREEN);
            radii[i] = new Circle(sim.getRadius()/2, new Color(0, 1, 0, 0.5));
        }

        p.getChildren().addAll(people);
        p.getChildren().addAll(radii);
    }

    public void updateSimulationElements() {
        for(int i = 0; i < sim.getAmountOfPeople(); i++) {
            ((Circle) p.getChildren().get(sim.getAmountOfPeople() + i)).setRadius(sim.getRadius()/2);
        }
    }

    private void initializeAnimation(){
        timer = new AnimationTimer() {
            @Override
            public void handle(long l) {
                if(lastTimeStamp == null) { lastTimeStamp = l; }

                long milliSecsPast = (long) ((l - lastTimeStamp) / Math.pow(10, 6)); // Convert to milliseconds
                sim.updateSimulation(milliSecsPast);

                for(int i = 0; i < people.length; i++) {
                    people[i].setCenterX(sim.getLocations()[i][0]);
                    people[i].setCenterY(sim.getLocations()[i][1]);

                    radii[i].setCenterX(sim.getLocations()[i][0]);
                    radii[i].setCenterY(sim.getLocations()[i][1]);
                }

                lastTimeStamp = l;
            }
        };
    }

    public void infect(int i) {
        radii[i].setFill(new Color(1,0,0,0.5));
    }

    private void startAnimation() {
        timer.start();
        h.getChildren().remove(7);
        h.getChildren().add(stopButton);
    }

    private void stopAnimation() {
        timer.stop();
        h.getChildren().remove(7);
        h.getChildren().add(startButton);
        this.lastTimeStamp = null;
    }

    private void resetSimulation() {
        this.sim.resetSimulation();
        initializeSimulationElements();
    }

    public Scene getScene() {
        return this.s;
    }
}