/**
 * Created by Oleksandr on 13.06.2016.
 */
class Calculations {
    private RelativeVector ship;
    private RelativeVector relativeWind;
    private TrueWind wind;
    private Description description;
    private double speed;
    private double direction;
    private String windRhumb;
    private String windDescription;
    private String windBeaufort;
    private String waveHeight;
    private String waveBeaufort;
    private String waveDescription;
    private String waveText;
    private int index;
    private double shipSpeed;
    private double shipCourse;
    private double relativeWindSpeed;
    private double relativeWindDirection;
    private String side;

    public Calculations(RelativeVector ship, RelativeVector relativeWind, TrueWind wind, Description description) {
        this.ship = ship;
        this.relativeWind = relativeWind;
        this.wind = wind;
        this.description = description;
    }

    public void setShipSpeed(double shipSpeed) {
        this.shipSpeed = shipSpeed;
    }

    public void setShipCourse(double shipCourse) {
        this.shipCourse = shipCourse;
    }

    public void setRelativeWindSpeed(double relativeWindSpeed) {
        this.relativeWindSpeed = relativeWindSpeed;
    }

    public void setRelativeWindDirection(double relativeWindDirection) {
        this.relativeWindDirection = relativeWindDirection;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public double getShipCourse() {
        return shipCourse;
    }

    public double getSpeed() {
        return speed;
    }

    public double getDirection() {
        return direction;
    }

    public String getWindRhumb() {
        return windRhumb;
    }

    public String getWindDescription() {
        return windDescription;
    }

    public String getWindBeaufort() {
        return windBeaufort;
    }

    public String getWaveHeight() {
        return waveHeight;
    }

    public String getWaveBeaufort() {
        return waveBeaufort;
    }

    public String getWaveDescription() {
        return waveDescription;
    }

    public String getWaveText() {
        return waveText;
    }

    public int getIndex(double speed) {
        return description.getIndex(speed);
    }

    public void calculate() {

        ship.setSpeed(shipSpeed);
        ship.setDirection(shipCourse);
        relativeWind.setSpeed(relativeWindSpeed);
        relativeWind.setDirection(relativeWindDirection);
        relativeWind.setSide(side);

        speed = wind.trueWindSpeed(ship.getSpeed(), relativeWind.getSpeed(), relativeWind.getDirection());
        wind.setTrueWindSpeed(speed);
        direction = wind.trueWindDirection(relativeWind.getSide(), ship.getSpeed(), relativeWind.getSpeed(), ship.getDirection(), relativeWind.getDirection());
        windRhumb = description.getRhumb(direction);
        index = description.getIndex(speed);
        windDescription = description.windDescription.get(index);
        windBeaufort = description.windBeaufort[index];
        waveHeight = description.waveHeight.get(index);
        waveBeaufort = description.waveBeaufort[index];
        waveDescription = description.waveDescription.get(index);
        waveText = description.waveText.get(index);
    }
}
