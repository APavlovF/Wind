import java.util.ArrayList;
import java.util.List;
/**
 * Created by Oleksandr on 12.06.2016.
 */
class Description {

    final List<String> windDescription = new ArrayList<>();
    final List<String> waveHeight = new ArrayList<>();
    final List<String> waveDescription = new ArrayList<>();
    final List<String> waveText = new ArrayList<>();

    private final double[] speeds = {0, 1, 3, 6, 10, 16, 21, 27, 33, 40, 47, 55, 63};
    private final String[] rhumbs = {"N", "NNE", "NE", "ENE", "E", "ESE", "SE", "SSE", "S", "SSW", "SW", "WSW", "W", "WNW", "NW", "NNW", "N"};
    final String[] windBeaufort = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12"};
    final String[] waveBeaufort = {"0", "1", "2", "2", "3", "4", "5", "6", "7", "7", "8", "9", "9"};

    Description() {
        windDescription.add(0, "Calm");
        windDescription.add(1, "Light air");
        windDescription.add(2, "Light breeze");
        windDescription.add(3, "Gentle breeze");
        windDescription.add(4, "Moderate breeze");
        windDescription.add(5, "Fresh breeze");
        windDescription.add(6, "Strong breeze");
        windDescription.add(7, "Near gale");
        windDescription.add(8, "Gale");
        windDescription.add(9, "Strong gale");
        windDescription.add(10, "Storm");
        windDescription.add(11, "Violent storm");
        windDescription.add(12, "Hurricane");

        waveHeight.add(0, "0");
        waveHeight.add(1, "0.1 (0.1)");
        waveHeight.add(2, "0.2 (0.4)");
        waveHeight.add(3, "0.6 (1.0)");
        waveHeight.add(4, "1.0 (1.5)");
        waveHeight.add(5, "2.0 (2.5)");
        waveHeight.add(6, "3.0 (4.0)");
        waveHeight.add(7, "4.0 (5.5)");
        waveHeight.add(8, "5.5 (7.5)");
        waveHeight.add(9, "7.0 (10.0)");
        waveHeight.add(10, "9.0 (12.5)");
        waveHeight.add(11, "11.5 (16.0)");
        waveHeight.add(12, ">14");

        waveDescription.add(0, "Calm (glassy)");
        waveDescription.add(1, "Calm (rippled)");
        waveDescription.add(2, "Smooth");
        waveDescription.add(3, "Slight");
        waveDescription.add(4, "Slight");
        waveDescription.add(5, "Moderate");
        waveDescription.add(6, "Rough");
        waveDescription.add(7, "Very rough");
        waveDescription.add(8, "Very rough");
        waveDescription.add(9, "High");
        waveDescription.add(10, "Very high");
        waveDescription.add(11, "Very high");
        waveDescription.add(12, "Phenomenal");

        waveText.add(0,"Sea like a mirror.");
        waveText.add(1,"Ripples with the appearance of scales are formed, but without foam crests.");
        waveText.add(2,"Small wavelets, still short but more pronounced - crests have a glassy appearance and do not break.");
        waveText.add(3,"Large wavelets. Crests begin to break. Foam glassy appearance. Perhaps scattered horses.");
        waveText.add(4,"Small waves, becoming longer; fairly frequent white horses.");
        waveText.add(5,"Moderate waves, taking a more pronounced long form; many white horses are formed (chance of some spray).");
        waveText.add(6,"Large waves begin to form; the white foam crests are more extensive everywhere (probably some spray).");
        waveText.add(7,"Sea heaps up and white foam from breaking waves begins to be blown in streaks along the direction of the wind.");
        waveText.add(8,"Moderate high waves of greater length; edges of crests begin to break into the spindrift. The foam is blown in well-marked streaks along the direction of the wind.");
        waveText.add(9,"High waves. Dense streaks of foam along direction of the wind. Crests of waves begin to topple, tumble and roll over. Spray might affect visibility.");
        waveText.add(10,"Very high waves with long overhanging crests. The resulting foam is blown in dense white streaks along the direction of the wind. The tumbling of the sea becomes heavy and shock-like. Visibility affected.");
        waveText.add(11,"Exceptionally high waves. The sea is completely covered with long white patches of foam lying along the direction of the wind. Everywhere the edges of the wave crests are blown into froth. Visibility affected.");
        waveText.add(12,"The air is filled with foam and spray. Sea completely white with driving spray; visibility very seriously affected.");
    }

    public String getRhumb(double direction) {
        if (direction == 0) {
            direction = 360;
        }
        double rhumb = Math.round(direction / 22.5);
        return rhumbs[(int) rhumb];
    }

    public int getIndex(double speed) {
        int index = 12;
        for (int i = 0; i < speeds.length - 1; i++) {
            if (speed >= speeds[i] & speed < speeds[i + 1]) {
                index = i;
            }
        }
        return index;
    }
}
