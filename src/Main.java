/**
 * Created by Oleksandr on 12.06.2016.
 */
class Main {

    public static void main(String[] args) {
        RelativeVector ship = new RelativeVector();
        RelativeVector relativeWind = new RelativeVector();
        TrueWind wind = new TrueWind();
        Description description = new Description();
        Calculations calculations = new Calculations(ship, relativeWind, wind, description);

        Scene scene = new Scene();

        Saver init = new Saver();
        String[] savedData = init.loadLastInstance();

        scene.loadLabel.setText("Loading last saved data...");
        scene.setShip_Speed(savedData[0]);
        scene.setShip_Course(savedData[1]);
        scene.setRelWindSpeed(savedData[2]);
        scene.setRelWindDirection(savedData[3]);
        scene.setSideStbd(savedData[4]);
        scene.setSpeedKnots(savedData[5]);
        scene.setCalculations(calculations);
        scene.loadLabel.setText("");
        scene.update();
    }
}
