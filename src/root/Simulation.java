package root;

public class Simulation {
    //init variables
    private int amountOfPeople;
    private double radius;
    private double chance;

    //simulation variables
    private int[][] locations; // two-dimentional array containing locations and speed for all particles
    private boolean[] infections; // array containing information on whether particles have been infected

    public Simulation(int amountOfPeople, double radius, double chance) {
        this.amountOfPeople = amountOfPeople;
        this.radius = radius;
        this.chance = chance;

        startSimulation();
    }

    public void updateAmountOfPeople(int amountOfPeople) {
        this.amountOfPeople = amountOfPeople;
        startSimulation();
    }

    public void updateRadius(double radius) {
        this.radius = radius;
        startSimulation();
    }

    public void updateChance(double chance) {
        this.chance = chance;
        startSimulation();
    }

    private void startSimulation(){
        locations = new int[this.amountOfPeople][3];

        for(int i = 0; i < this.amountOfPeople; i++){
            locations[i][0] = (int) (Math.random() * 100);
            locations[i][1] = (int) (Math.random() * 100);
            locations[i][2] = (int) (Math.random() * 5);
        }

        this.infections = new boolean[this.amountOfPeople];

        for(int i = 0; i < this.amountOfPeople; i++){
            infections[i] = false;
        }
    }
}
