package root;

public class Simulation {
    //init variables
    private int amountOfPeople;
    private double radius;
    private double speed;

    //screen dimensions
    private int screenWidth;
    private int screenHeight;

    //simulation variables
    private int[][] locations; // two-dimentional array containing locations and speed for all particles
    private boolean[] infections; // array containing information on whether particles are infected
    private int infectionCount;

    //window
    private Window w;

    public Simulation(int amountOfPeople, double radius, double speed, int width, int height, Window w) {
        this.amountOfPeople = amountOfPeople;
        this.radius = radius;
        this.speed = speed;

        this.screenWidth = width;
        this.screenHeight = height;

        this.w = w;

        initializeSimulation();
    }

    private void initializeSimulation(){
        locations = new int[this.amountOfPeople][4];

        for(int i = 0; i < this.amountOfPeople; i++){
            locations[i][0] = (int) (Math.random() * screenWidth); // posX
            locations[i][1] = (int) (Math.random() * screenHeight); // posY
            locations[i][2] = (int) (Math.random() * screenWidth) - (screenWidth/2); // speedX / second
            locations[i][3] = (int) (Math.random() * screenHeight) - (screenHeight/2); // speedY / second
        }

        this.infections = new boolean[amountOfPeople];
        infections[0] = true;
        for(int i = 1; i < this.amountOfPeople; i++){
            infections[i] = false;
        }

        this.infectionCount = 1;
    }

    public void updateAmountOfPeople(int amountOfPeople) {
        this.amountOfPeople = amountOfPeople;
        initializeSimulation();
        w.initializeSimulationElements();
    }

    public void updateRadius(double radius) {
        this.radius = radius;
        w.updateSimulationElements();
    }

    public void updateSpeed(double speed) {
        this.speed = speed;
    }

    public void updateSimulation(double millis){
        for(int i = 0; i < this.amountOfPeople; i++) {
            locations[i][0] += (locations[i][2] * (millis/1000)) * speed;
            locations[i][1] += (locations[i][3] * (millis/1000)) * speed;

            if(locations[i][0] < -this.radius){
                locations[i][0] += screenWidth + (2 * this.radius);
            } if(locations[i][1] < -this.radius){
                locations[i][1] += screenHeight + (2 * this.radius);
            } if(locations[i][0] > screenWidth + this.radius){
                locations[i][0] -= (screenWidth + (2 * this.radius));
            } if(locations[i][1] > screenHeight + this.radius){
                locations[i][1] -= (screenHeight + (2 * this.radius));
            }

            System.out.println("(" + locations[i][0] + ", " + locations[i][1] + ")");
        }

        for(int i = 0; i < amountOfPeople; i++) {
            if(infections[i] == false) {
                for (int j = 0; j < amountOfPeople; j++) {
                    if(infections[j] == true && inRange(i, j)) {
                        infections[i] = true;
                        infectionCount++;
                        w.infect(i);
                        break;
                    }
                }
            }
        }

        System.out.println("Amount of infections: " + infectionCount);
    }

    private boolean inRange(int i, int j) {
        int dx = (locations[i][0] - locations[j][0]);
        int dy = (locations[i][1] - locations[j][1]);
        double distance = Math.sqrt(Math.pow((dx), 2) + Math.pow((dy), 2));

        return (distance < this.radius);
    }

    public int[][] getLocations(){
        return this.locations;
    }

    public boolean[] getInfected(){
        return this.infections;
    }

    public int getInfectionCount(){
        return this.infectionCount;
    }

    public int getAmountOfPeople(){
        return this.amountOfPeople;
    }

    public double getRadius(){
        return this.radius;
    }
}