package root;

public class Simulation {
    //init variables
    private int amountOfPeople;
    private double radius;
    private double chance;

    //simulation variables
    private int[][] locations; // two-dimentional array containing locations and speed for all particles
    private boolean[] infections; // array containing information on whether particles are infected
    private int infectionCount;

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
        locations = new int[this.amountOfPeople][4];

        for(int i = 0; i < this.amountOfPeople; i++){
            locations[i][0] = (int) (Math.random() * 100); // posX
            locations[i][1] = (int) (Math.random() * 100); // posY
            locations[i][2] = (int) (Math.random() * 20) - 10; // speedX / second
            locations[i][3] = (int) (Math.random() * 20) - 10; // speedY / second
        }

        this.infectionCount = 1;
        this.infections = new boolean[amountOfPeople];

        infections[0] = true;
        for(int i = 1; i < this.amountOfPeople; i++){
            infections[i] = false;
        }
    }

    public void updateSimulation(double millis){
        for(int i = 0; i < this.amountOfPeople; i++) {
            locations[i][0] += (locations[i][2] * (millis/1000));
            locations[i][1] += (locations[i][3] * (millis/1000));

            if(locations[i][0] < 0 ){
                locations[i][0] += 100;
            } if(locations[i][1] < 0 ){
                locations[i][1] += 100;
            } if(locations[i][0] > 100 ){
                locations[i][0] -= 100;
            } if(locations[i][1] > 100 ){
                locations[i][1] -= 100;
            }

            System.out.println("(" + locations[i][0] + ", " + locations[i][1] + ")");
        }

        for(int i = 0; i < amountOfPeople; i++) {
            if(infections[i] == false) {
                for (int j = 0; j < amountOfPeople; j++) {
                    if(infections[j] == true && inRange(i, j)) {
                        infections[i] = true;
                        infectionCount++;
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
}
