import java.io.*;
import java.util.Properties;

/**
 * Created by Oleksandr on 19.06.2016.
 */
class Saver {

    public void saveLastInstance(String[] dataToSave) {
        try {
            FileWriter writer = new FileWriter("init.properties");
            String shipSpeed = dataToSave[0];
            String shipCourse = dataToSave[1];
            String relativeWindSpeed = dataToSave[2];
            String relativeWindDirection = dataToSave[3];
            String sideStbdIsSelected = dataToSave[4];
            String speedKnotIsSelected = dataToSave[5];

            writer.write("shipSpeed:" + shipSpeed + "\n");
            writer.write("shipCourse:" + shipCourse + "\n");
            writer.write("relativeWindSpeed:" + relativeWindSpeed + "\n");
            writer.write("relativeWindDirection:" + relativeWindDirection + "\n");
            writer.write("sideStbdIsSelected:" + sideStbdIsSelected + "\n");
            writer.write("speedKnotIsSelected:" + speedKnotIsSelected + "\n");

            writer.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String[] loadLastInstance() {
        String[] lastInstance = new String[6];

        try (FileInputStream fileInputStream = new FileInputStream("init.properties")) {
          //try(InputStream inputStream = getClass().getClassLoader().getResourceAsStream("init.properties")){
            Properties properties = new Properties();
            properties.load(fileInputStream);
            //properties.load(inputStream);

            String shipSpeed = properties.getProperty("shipSpeed");
            String shipCourse = properties.getProperty("shipCourse");
            String relativeWindSpeed = properties.getProperty("relativeWindSpeed");
            String relativeWindDirection = properties.getProperty("relativeWindDirection");
            String sideStbdIsSelected = properties.getProperty("sideStbdIsSelected");
            String speedKnotIsSelected = properties.getProperty("speedKnotIsSelected");

            lastInstance[0]=shipSpeed;
            lastInstance[1]=shipCourse;
            lastInstance[2]=relativeWindSpeed;
            lastInstance[3]=relativeWindDirection;
            lastInstance[4]=sideStbdIsSelected;
            lastInstance[5]=speedKnotIsSelected;

        }catch(IOException e) {
            lastInstance[0] = "0";
            lastInstance[1] = "0";
            lastInstance[2] = "0";
            lastInstance[3] = "0";
            lastInstance[4] = "0";
            lastInstance[5] = "0";
        }
        return lastInstance;
    }
}
