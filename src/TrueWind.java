/**
 * Created by Oleksandr on 12.06.2016.
 */
class TrueWind {

    private double trueWindSpeed;

    public void setTrueWindSpeed(double trueWindSpeed) {
        this.trueWindSpeed = trueWindSpeed;
    }

    public double trueWindSpeed(double shipSpeed, double relativeWindSpeed, double relativeWindDirection) {
        relativeWindDirection = Math.toRadians(relativeWindDirection);
        return Math.sqrt(Math.pow(shipSpeed, 2) + Math.pow(relativeWindSpeed, 2) - 2 * shipSpeed * relativeWindSpeed * Math.cos(relativeWindDirection));
    }

    public double trueWindDirection(String side, double shipSpeed, double relativeWindSpeed, double shipCourse, double relativeWindDirection) {
        double trueWindDirection;
        double cos;
        double beta;

        if (shipSpeed == 0) {
            if (side == "stbd") {
                trueWindDirection = shipCourse + relativeWindDirection;
            } else {
                trueWindDirection = shipCourse - relativeWindDirection;
            }
        } else {
            if (relativeWindSpeed == 0) {
                trueWindDirection = shipCourse + 180;
            } else {
                if ((relativeWindDirection == 0 & relativeWindSpeed < shipSpeed) | relativeWindDirection == 180) {
                    trueWindDirection = shipCourse + 180;
                } else {
                    if (relativeWindDirection == 0 & relativeWindSpeed > shipSpeed) {
                        trueWindDirection = shipCourse;
                    } else {
                        cos = (Math.pow(relativeWindSpeed, 2) + Math.pow(trueWindSpeed, 2) - Math.pow(shipSpeed, 2)) / (2 * relativeWindSpeed * trueWindSpeed);
                        beta = Math.toDegrees(Math.acos(cos));
                        if (side == "stbd") {
                            trueWindDirection = shipCourse + relativeWindDirection + beta;
                        } else {
                            trueWindDirection = shipCourse + 360 - relativeWindDirection - beta;
                        }
                    }
                }
            }
        }
        trueWindDirection = trueWindDirection - Math.floor((trueWindDirection) / 360) * 360;
        if (Math.round(trueWindDirection) == 360) {
            trueWindDirection = 0;
        }
        return trueWindDirection;
    }
}