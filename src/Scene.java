
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.List;

/**
 * Created by Oleksandr on 13.06.2016.
 */
class Scene extends JFrame {

    private Calculations calculations;
    private Saver saver;
    private final List<Image> pictures;
    private int index; //Beaufort index

    public void setCalculations(Calculations calculations) {
        this.calculations = calculations;
    }

    //X & Y axis
    private final int xAxisStart = 20;
    private final int xAxisEnd = 220;
    private final int xAxisMiddle = 126;
    private final int yAxisStart = 22;
    private final int yAxisEnd = 224;
    private final int yAxisMiddle = 130;

    private double shipSpeedDouble;
    private double shipCourseDouble;
    private double trueWindSpeedDouble;
    private double trueWindDirectionDouble;
    private double relativeWindDirectionDouble;
    private double relativeWindSpeedDouble;
    private AffineTransform transformed;
    private Polygon headMark;
    private Polygon trueMark;
    private Polygon relativeMark;
    private int currentArrowX;
    private int currentArrowY;
    private boolean isShipCourseArrowSelected = false;
    private boolean isRelativeWindArrowSelected = false;
    private double currentArrowAngle;
    private final double headMarkRadius = 110;
    private double xRelativeHeadMark;
    private double yRelativeHeadMark;
    private double xHeadMark;
    private double yHeadMark;
    private double xTrueWindMark;
    private double yTrueWindMark;
    private double xRelativeWindMark;
    private double yRelativeWindMark;

    private JPanel shipData;
    private JPanel windData;
    private JLabel shipSpeed;
    private JLabel shipCourse;
    private JLabel relativeWindSpeed;
    private JLabel relativeWindDirection;
    private JRadioButton sidePort;
    private JRadioButton sideStbd;
    private JLabel trueWindSpeed;
    private JLabel trueWindDirection;
    private JTextField ship_Speed;
    private JTextField ship_Course;
    private JTextField relWindSpeed;
    private JTextField relWindDirection;
    private JLabel waveBeaufort;
    private JTextField true_Wind_Speed;
    private JTextField rhumb_Direction;
    private JLabel windBeaufort;
    private JTextField wind_Beaufort;
    private JTextField wind_Description;
    private JTextField true_Wind_Direction;
    private JLabel waveHeight;
    private JLabel windSpeedUnit;
    private JTextField wave_Height;
    private JTextField wave_Description;
    private JTextField wave_Beaufort;
    private JButton exitButton;
    private JPanel panel;
    private JRadioButton speedKnots;
    private JRadioButton speedMs;
    private JPanel seaScheme;
    private JTextArea wave_Text;
    private JButton aboutButton;
    public JLabel loadLabel;
    private JTextField abcdTextField;

    private String shipSpeedString;
    private String shipCourseString;
    private String relativeWindSpeedString;
    private String relativeWindDirectionString;
    private String trueWindSpeedString;
    private String trueWindSpeedStringMs;
    private String trueWindDirectionString;
    private double speedUnitFactor;

    public void setShip_Speed(String shipSpeed) {
        ship_Speed.setText(shipSpeed);
    }

    public void setShip_Course(String shipCourse) {
        ship_Course.setText(shipCourse);
    }

    public void setRelWindSpeed(String relativeWindSpeed) {
        relWindSpeed.setText(relativeWindSpeed);
    }

    public void setRelWindDirection(String relativeWindDirection) {
        relWindDirection.setText(relativeWindDirection);
    }

    public void setSideStbd(String sideStbdIsSelected) {
        if (sideStbdIsSelected.equals("true")) {
            sideStbd.setSelected(true);
        } else {
            sidePort.setSelected(true);
        }
    }

    public void setSpeedKnots(String speedKnotsIsSelected) {
        if (speedKnotsIsSelected.equals("true")) {
            speedKnots.setSelected(true);
        } else {
            speedMs.setSelected(true);
        }
    }

    public void update() {
        ship_Speed.selectAll();

        if (speedKnots.isSelected()) {
            speedUnitFactor = 1;
        } else {
            speedUnitFactor = (double) 3600 / 1852;
        }

        try {
            shipSpeedString = ship_Speed.getText();
            shipSpeedDouble = Double.parseDouble(shipSpeedString);
            if (shipSpeedDouble >= 0) {
                ship_Speed.setToolTipText("");
            } else {
                ship_Speed.setText("0");
                shipSpeedDouble = 0;
                ship_Speed.setToolTipText("Speed input should be a positive number!");
            }
        } catch (NumberFormatException e) {
            ship_Speed.setToolTipText("Speed input should be a positive number!");
            ship_Speed.setText("0");
            shipSpeedDouble = 0;
        } finally {
            calculations.setShipSpeed(shipSpeedDouble);
        }
        try {
            shipCourseString = ship_Course.getText();
            shipCourseDouble = Double.parseDouble(shipCourseString);
            if (shipCourseDouble >= 0) {
                ship_Course.setToolTipText("");
            } else {
                ship_Course.setToolTipText("Course input should be a positive number!");
                ship_Course.setText("0");
                shipCourseDouble = 0;
            }
        } catch (NumberFormatException e) {
            ship_Course.setToolTipText("Course input should be a positive number!");
            ship_Course.setText("0");
            shipCourseDouble = 0;
        } finally {
            calculations.setShipCourse(shipCourseDouble);
        }

        try {
            relativeWindSpeedString = relWindSpeed.getText();
            relativeWindSpeedDouble = Double.parseDouble(relativeWindSpeedString) * speedUnitFactor;
            if (relativeWindSpeedDouble >= 0) {
                relWindSpeed.setToolTipText("");
            } else {
                relWindSpeed.setToolTipText("Speed input should be a positive number!");
                relWindSpeed.setText("0");
                relativeWindSpeedDouble = 0;
            }
        } catch (NumberFormatException e) {
            relWindSpeed.setToolTipText("Speed input should be a positive number!");
            relWindSpeed.setText("0");
            relativeWindSpeedDouble = 0;
        } finally {
            calculations.setRelativeWindSpeed(relativeWindSpeedDouble);
        }

        try {
            relativeWindDirectionString = relWindDirection.getText();
            relativeWindDirectionDouble = Double.parseDouble(relativeWindDirectionString);
            if (relativeWindDirectionDouble >= 0 & relativeWindDirectionDouble <= 180) {
                relWindDirection.setToolTipText("");
            } else {
                relWindDirection.setToolTipText("Relative wind direction should be a number from 0 to 180.");
                relWindDirection.setText("0");
                relativeWindDirectionDouble = 0;
            }
        } catch (NumberFormatException e) {
            relWindDirection.setToolTipText("Relative wind direction should be a number from 0 to 180.");
            relWindDirection.setText("0");
            relativeWindDirectionDouble = 0;
        } finally {
            calculations.setRelativeWindDirection(relativeWindDirectionDouble);
        }

        if (sideStbd.isSelected()) {
            calculations.setSide("stbd");
        } else {
            calculations.setSide("port");
        }


        calculations.calculate();

        index = calculations.getIndex(calculations.getSpeed());
        trueWindSpeedDouble = Math.round(calculations.getSpeed());
        trueWindSpeedString = String.valueOf((int) trueWindSpeedDouble);
        trueWindSpeedStringMs = String.valueOf((int) Math.round((calculations.getSpeed()) * 1852 / 3600));
        true_Wind_Speed.setText(trueWindSpeedString + " kn (" + trueWindSpeedStringMs + " m/s)");

        trueWindDirectionDouble = Math.round(calculations.getDirection());
        trueWindDirectionString = String.valueOf((int) trueWindDirectionDouble);
        true_Wind_Direction.setText(trueWindDirectionString);

        rhumb_Direction.setText(calculations.getWindRhumb());

        wind_Description.setText(calculations.getWindDescription());
        wind_Beaufort.setText(calculations.getWindBeaufort());

        wave_Height.setText(calculations.getWaveHeight() + " m");
        wave_Beaufort.setText(calculations.getWaveBeaufort());
        wave_Description.setText(calculations.getWaveDescription());
        wave_Text.setText(calculations.getWaveText());

        seaScheme.repaint();
    }

    public Scene() {

        setContentPane(panel);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        pack();

        setLocation(300, 100);
        setVisible(true);

        setTitle("Wind 2.0");

        panel.setIgnoreRepaint(true);
        windData.setIgnoreRepaint(true);
        shipData.setIgnoreRepaint(true);

        loadLabel.setText("Loading pictures...");
        pictures = new ArrayList<>();
        /*pictures.add(0, new ImageIcon("pics\\w_0.png").getImage());
        pictures.add(1, new ImageIcon("pics\\w_1.png").getImage());
        pictures.add(2, new ImageIcon("pics\\w_2.png").getImage());
        pictures.add(3, new ImageIcon("pics\\w_3.png").getImage());
        pictures.add(4, new ImageIcon("pics\\w_4.png").getImage());
        pictures.add(5, new ImageIcon("pics\\w_5.png").getImage());
        pictures.add(6, new ImageIcon("pics\\w_6.png").getImage());
        pictures.add(7, new ImageIcon("pics\\w_7.png").getImage());
        pictures.add(8, new ImageIcon("pics\\w_8.png").getImage());
        pictures.add(9, new ImageIcon("pics\\w_9.png").getImage());
        pictures.add(10, new ImageIcon("pics\\w_10.png").getImage());
        pictures.add(11, new ImageIcon("pics\\w_11.png").getImage());
        pictures.add(12, new ImageIcon("pics\\w_12.png").getImage());
        pictures.add(13, new ImageIcon("pics\\back.gif").getImage());
        */
        loadLabel.setText("");

        String internalImagePath = "back.gif";
        Image myImage = Toolkit.getDefaultToolkit().createImage(Main.class.getResource(internalImagePath));
        setIconImage(myImage);
        //setIconImage(pictures.get(13));
        internalImagePath = "w_0.png";
        Image myImage0 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource(internalImagePath));
        pictures.add(0, myImage0);
        internalImagePath = "w_1.png";
        Image myImage1 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource(internalImagePath));
        pictures.add(1, myImage1);
        internalImagePath = "w_2.png";
        Image myImage2 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource(internalImagePath));
        pictures.add(2, myImage2);
        internalImagePath = "w_3.png";
        Image myImage3 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource(internalImagePath));
        pictures.add(3, myImage3);
        internalImagePath = "w_4.png";
        Image myImage4 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource(internalImagePath));
        pictures.add(4, myImage4);
        internalImagePath = "w_5.png";
        Image myImage5 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource(internalImagePath));
        pictures.add(5, myImage5);
        internalImagePath = "w_6.png";
        Image myImage6 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource(internalImagePath));
        pictures.add(6, myImage6);
        internalImagePath = "w_7.png";
        Image myImage7 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource(internalImagePath));
        pictures.add(7, myImage7);
        internalImagePath = "w_8.png";
        Image myImage8 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource(internalImagePath));
        pictures.add(8, myImage8);
        internalImagePath = "w_9.png";
        Image myImage9 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource(internalImagePath));
        pictures.add(9, myImage9);
        internalImagePath = "w_10.png";
        Image myImage10 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource(internalImagePath));
        pictures.add(10, myImage10);
        internalImagePath = "w_11.png";
        Image myImage11 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource(internalImagePath));
        pictures.add(11, myImage11);
        internalImagePath = "w_12.png";
        Image myImage12 = Toolkit.getDefaultToolkit().createImage(Main.class.getResource(internalImagePath));
        pictures.add(12, myImage12);

        ship_Speed.selectAll();
        ship_Speed.grabFocus();
        sideStbd.setSelected(true);
        speedKnots.setSelected(true);

        exitButton.addActionListener(e -> {
            saver = new Saver();
            String[] dataToSave = new String[6];
            dataToSave[0] = ship_Speed.getText();
            dataToSave[1] = ship_Course.getText();
            dataToSave[2] = relWindSpeed.getText();
            dataToSave[3] = relWindDirection.getText();
            dataToSave[4] = String.valueOf(sideStbd.isSelected());
            dataToSave[5] = String.valueOf(speedKnots.isSelected());

            saver.saveLastInstance(dataToSave);

            System.exit(1);
        });

        ship_Speed.addActionListener(e -> {
            update();
            ship_Course.grabFocus();
        });

        ship_Speed.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                ship_Speed.selectAll();
            }
        });

        ship_Course.addActionListener(e -> {
            update();
            relWindSpeed.grabFocus();
        });
        ship_Course.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                ship_Course.selectAll();
            }
        });
        ship_Course.addPropertyChangeListener(evt -> update());
        relWindSpeed.addActionListener(e -> {
            update();
            relWindDirection.grabFocus();
        });
        relWindSpeed.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                relWindSpeed.selectAll();
            }
        });
        relWindDirection.addActionListener(e -> {
            update();
            ship_Speed.grabFocus();
        });
        relWindDirection.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                relWindDirection.selectAll();
            }
        });
        sidePort.addActionListener(e -> update());
        sideStbd.addActionListener(e -> update());
        speedKnots.addActionListener(e -> update());
        speedMs.addActionListener(e -> update());

        seaScheme.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (Math.abs(Math.round(xHeadMark - e.getX())) < 5 & Math.abs(Math.round(yHeadMark - e.getY())) < 5) {
                    isShipCourseArrowSelected = true;
                }
                if (Math.abs(Math.round(xRelativeWindMark - e.getX())) < 5 & Math.abs(Math.round(yRelativeWindMark - e.getY())) < 5) {
                    isRelativeWindArrowSelected = true;
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                isShipCourseArrowSelected = Math.abs(Math.round(xHeadMark - e.getX())) < 5 & Math.abs(Math.round(yHeadMark - e.getY())) < 5;
                isRelativeWindArrowSelected = Math.abs(Math.round(xRelativeWindMark - e.getX())) < 5 & Math.abs(Math.round(yRelativeWindMark - e.getY())) < 5;
                update();
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                super.mouseEntered(e);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                super.mouseExited(e);
            }

            @Override
            public void mouseDragged(MouseEvent e) {
            }
        });
        seaScheme.addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                currentArrowX = e.getX();
                currentArrowY = e.getY();
                int xAux = currentArrowX - xAxisMiddle;
                int yAux = -currentArrowY + yAxisMiddle;
                currentArrowAngle = Math.round(Math.toDegrees(Math.atan((xAux / headMarkRadius) / (yAux / headMarkRadius))));
                if (yAux < 0) {
                    currentArrowAngle = currentArrowAngle + 180;
                } else if (xAux < 0) {
                    currentArrowAngle = currentArrowAngle + 360;
                }
                if (isShipCourseArrowSelected & !isRelativeWindArrowSelected) {
                    ship_Course.setText(Double.toString(currentArrowAngle));
                    update();
                }
                if (isRelativeWindArrowSelected & !isShipCourseArrowSelected) {
                    double diff = currentArrowAngle + 360 - shipCourseDouble;
                    diff = diff - Math.floor(diff / 360) * 360;
                    if (diff <= 180) {
                        currentArrowAngle = diff;
                        sideStbd.setSelected(true);
                    } else {
                        currentArrowAngle = 360 - diff;
                        sidePort.setSelected(true);
                    }
                    relWindDirection.setText(Double.toString(currentArrowAngle));
                    update();
                }
            }
        });
        seaScheme.addMouseWheelListener(e -> {
            if (isShipCourseArrowSelected) {
                shipSpeedDouble = Double.parseDouble(ship_Speed.getText());
                double step = 0.2;
                if (e.getWheelRotation() < 0) {
                    step = -step;
                }
                shipSpeedDouble = (double) Math.round((shipSpeedDouble + step) * 10) / 10;
                if (shipSpeedDouble >= 0) {
                    ship_Speed.setText(String.valueOf(shipSpeedDouble));
                }
                update();
            }
            if (isRelativeWindArrowSelected) {
                relativeWindSpeedDouble = Double.parseDouble(relWindSpeed.getText());
                relativeWindSpeedDouble = relativeWindSpeedDouble + e.getWheelRotation();
                if (relativeWindSpeedDouble >= 0) {
                    relWindSpeed.setText(String.valueOf(relativeWindSpeedDouble));
                }
                update();
            }
        });
        aboutButton.addActionListener(e -> {
            About about = new About();
        });
    }

    private void createUIComponents() {
        seaScheme = new Scheme();
        // TODO: place custom component creation code here
    }

    public class Scheme extends JPanel {
        final BasicStroke basicStroke0 = new BasicStroke(2, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        final BasicStroke basicStroke1 = new BasicStroke(4, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        final BasicStroke basicStroke2 = new BasicStroke(6, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        final BasicStroke basicStroke3 = new BasicStroke(8, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        final BasicStroke basicStroke4 = new BasicStroke(10, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND);
        final float[] dash = {5, 5, 5, 5};
        BasicStroke basicStroke5 = new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 10, dash, 0);
        //vectors
        double xShip;
        double yShip;
        double xTrueWind;
        double yTrueWind;
        double xRelativeWind;
        double yRelativeWind;
        double vectorSizeFactor;

        public void paintComponent(Graphics graphics) {

            Graphics2D g = (Graphics2D) graphics;

            g.setColor(Color.lightGray);
            g.fillRect(0, 0, this.getWidth(), this.getHeight());

            try {
                g.drawImage(pictures.get(index), 0, 0, this);
            } catch (IndexOutOfBoundsException e) {

            }

            g.setColor(Color.black);
            g.drawLine(xAxisStart, yAxisMiddle, xAxisEnd, yAxisMiddle);
            g.drawLine(xAxisMiddle, yAxisStart, xAxisMiddle, yAxisEnd);
            g.setStroke(new BasicStroke(3));
            g.drawOval(28, 32, 197, 197);
            g.setColor(Color.blue);

            Font font = new Font("Serif", Font.BOLD, 10);
            g.setFont(font);
            g.drawString("N", xAxisMiddle - 3, 16);

            double angle = 0;
            double x;
            double y;
            double deltaX1, deltaX2;
            double deltaY1, deltaY2;

            g.setColor(Color.black);
            while (angle < 360) {
                deltaX1 = Math.round((headMarkRadius - 4) * Math.sin(Math.toRadians(angle)));
                deltaY1 = Math.round((headMarkRadius - 4) * Math.cos(Math.toRadians(angle)));
                deltaX2 = Math.round((headMarkRadius + 3) * Math.sin(Math.toRadians(angle)));
                deltaY2 = Math.round((headMarkRadius + 3) * Math.cos(Math.toRadians(angle)));
                x = xAxisMiddle + deltaX1;
                y = yAxisMiddle - deltaY1;
                g.drawLine((int) x, (int) y, (int) x, (int) y);
                if (angle % 45 == 0) {
                    g.setStroke(basicStroke2);
                    if (angle > 0 & angle < 180) {
                        g.drawString(String.valueOf((int) angle), xAxisMiddle + (int) deltaX2, yAxisMiddle - (int) deltaY2);
                    } else if (angle == 180) {
                        g.drawString(String.valueOf((int) angle), xAxisMiddle + (int) deltaX2 + 4, yAxisMiddle - (int) deltaY2);
                    } else if (angle > 180) {
                        g.drawString(String.valueOf((int) angle), xAxisMiddle + (int) deltaX2 - 12, yAxisMiddle - (int) deltaY2 + 2);
                    }
                } else {

                    g.setStroke(basicStroke3);
                }
                angle = angle + 22.5;
            }

            vectorSizeFactor = Math.max(shipSpeedDouble, relativeWindSpeedDouble);
            vectorSizeFactor = Math.max(vectorSizeFactor, trueWindSpeedDouble);

            drawHeadMark(g);
            drawRelativeWindMark(g);
            drawTrueWindMark(g);
            //drawCompass(g);
            draw(g, shipCourseDouble);
        }

        public void drawCompass(Graphics gr) {
            Graphics2D g = (Graphics2D) gr;
            double angle = 0;
            double deltaX1;
            double deltaY1;
            List<GlyphVector> glyphVectors = new ArrayList<>();
            FontRenderContext fontRenderContext = g.getFontRenderContext();
            Font font = new Font("Serif", Font.BOLD, 30);
            glyphVectors.add(0, font.createGlyphVector(fontRenderContext, "N"));
            glyphVectors.add(1, font.createGlyphVector(fontRenderContext, "E"));
            glyphVectors.add(2, font.createGlyphVector(fontRenderContext, "S"));
            glyphVectors.add(3, font.createGlyphVector(fontRenderContext, "W"));

            for (GlyphVector glyphVector : glyphVectors) {
                deltaX1 = Math.round((headMarkRadius - 4) * Math.sin(Math.toRadians(angle)));
                deltaY1 = Math.round((headMarkRadius - 4) * Math.cos(Math.toRadians(angle)));
                Point2D.Double p = new Point2D.Double(deltaX1, -deltaY1);
                glyphVector.setGlyphPosition(0, p);
                glyphVector.setGlyphTransform(0, AffineTransform.getRotateInstance(Math.toRadians(angle)));
                g.drawGlyphVector(glyphVector, xAxisMiddle, yAxisMiddle);
                angle = angle + 90;
            }

        }

        public void draw(Graphics2D gShip, double shipCourse) {
            //ship
            int shipLength = 80;
            int shipWidth = 25;
            Polygon polygon = new Polygon();
            polygon.addPoint(xAxisMiddle, yAxisMiddle - shipLength / 2);
            polygon.addPoint(xAxisMiddle + shipWidth / 2, yAxisMiddle - shipLength / 4);
            polygon.addPoint(xAxisMiddle + shipWidth / 2, yAxisMiddle + shipLength / 2);
            polygon.addPoint(xAxisMiddle - shipWidth / 2, yAxisMiddle + shipLength / 2);
            polygon.addPoint(xAxisMiddle - shipWidth / 2, yAxisMiddle - shipLength / 4);
            gShip.setStroke(basicStroke0);
            gShip.setColor(Color.yellow);
            xShip = Math.sin(Math.toRadians(shipCourseDouble)) * shipSpeedDouble * (headMarkRadius - 10) / vectorSizeFactor;
            yShip = Math.cos(Math.toRadians(shipCourseDouble)) * shipSpeedDouble * (headMarkRadius - 10) / vectorSizeFactor;
            gShip.drawLine(xAxisMiddle, yAxisMiddle, xAxisMiddle + (int) xShip, yAxisMiddle - (int) yShip);

            transformed = AffineTransform.getRotateInstance(Math.toRadians(shipCourseDouble), xAxisMiddle, yAxisMiddle);
            gShip.transform(transformed);
            //gShip.fillPolygon(polygon);
            gShip.setColor(Color.black);
            //gShip.drawPolygon(polygon);
            gShip.setStroke(basicStroke1);
            gShip.drawArc(xAxisMiddle - shipWidth * 6 / 3, yAxisMiddle - shipWidth * 6 / 3, shipWidth * 4, shipWidth * 4, -70, -40);
            gShip.drawArc(xAxisMiddle - shipWidth * 9 / 3, yAxisMiddle - shipWidth * 6 / 3, shipWidth * 4, shipWidth * 4, 32, 29);
            gShip.drawArc(xAxisMiddle - shipWidth * 3 / 3, yAxisMiddle - shipWidth * 6 / 3, shipWidth * 4, shipWidth * 4, 122, 29);
            gShip.drawLine(143, 176, 143, 104);
            gShip.drawLine(107, 176, 107, 104);
        }

        public void drawHeadMark(Graphics2D gP) {
            xRelativeHeadMark = (headMarkRadius - 20) * Math.sin(Math.toRadians(shipCourseDouble));
            yRelativeHeadMark = (headMarkRadius - 20) * Math.cos(Math.toRadians(shipCourseDouble));

            xHeadMark = Math.round(xAxisMiddle + xRelativeHeadMark);
            yHeadMark = Math.round(yAxisMiddle - yRelativeHeadMark);
            gP.setColor(Color.yellow);
            gP.setStroke(basicStroke4);
            headMark = new Polygon();
            headMark.addPoint((int) xHeadMark, (int) yHeadMark);
            gP.drawPolygon(headMark);
            if (isShipCourseArrowSelected) {
                gP.setStroke(basicStroke1);
                gP.setColor(Color.blue);
                gP.drawOval((int) xHeadMark - 6, (int) yHeadMark - 6, 12, 12);
            }
        }

        public void drawTrueWindMark(Graphics2D gT) {
            gT.setStroke(basicStroke4);
            xTrueWindMark = Math.round(xAxisMiddle + (headMarkRadius - 10) * Math.sin(Math.toRadians(Math.round(trueWindDirectionDouble))));
            yTrueWindMark = Math.round(yAxisMiddle - (headMarkRadius - 10) * Math.cos(Math.toRadians(Math.round(trueWindDirectionDouble))));
            trueMark = new Polygon();
            trueMark.addPoint((int) xTrueWindMark, (int) yTrueWindMark);
            gT.setColor(Color.cyan);
            gT.drawPolygon(trueMark);
            gT.setStroke(basicStroke0);
            xTrueWind = Math.sin(Math.toRadians(trueWindDirectionDouble)) * trueWindSpeedDouble * (headMarkRadius - 10) / vectorSizeFactor;
            yTrueWind = Math.cos(Math.toRadians(trueWindDirectionDouble)) * trueWindSpeedDouble * (headMarkRadius - 10) / vectorSizeFactor;
            gT.drawLine(xAxisMiddle, yAxisMiddle, xAxisMiddle + (int) xTrueWind, yAxisMiddle - (int) yTrueWind);
        }

        public void drawRelativeWindMark(Graphics2D gR) {
            gR.setStroke(basicStroke4);
            double relativeWindNorth;
            if (sideStbd.isSelected()) {
                relativeWindNorth = shipCourseDouble + relativeWindDirectionDouble;
            } else {
                relativeWindNorth = shipCourseDouble - relativeWindDirectionDouble;
            }
            xRelativeWindMark = Math.round(xAxisMiddle + headMarkRadius * Math.sin(Math.toRadians(Math.round(relativeWindNorth))));
            yRelativeWindMark = Math.round(yAxisMiddle - headMarkRadius * Math.cos(Math.toRadians(Math.round(relativeWindNorth))));
            relativeMark = new Polygon();
            relativeMark.addPoint((int) xRelativeWindMark, (int) yRelativeWindMark);
            gR.setColor(Color.red);
            gR.drawPolygon(relativeMark);
            if (isRelativeWindArrowSelected) {
                gR.setStroke(basicStroke1);
                gR.setColor(Color.blue);
                gR.drawOval((int) xRelativeWindMark - 6, (int) yRelativeWindMark - 6, 12, 12);
            }
            gR.setColor(Color.red);
            gR.setStroke(basicStroke0);
            xRelativeWind = Math.round(xAxisMiddle + relativeWindSpeedDouble * (headMarkRadius - 10) / vectorSizeFactor * Math.sin(Math.toRadians(Math.round(relativeWindNorth))));
            yRelativeWind = Math.round(yAxisMiddle - relativeWindSpeedDouble * (headMarkRadius - 10) / vectorSizeFactor * Math.cos(Math.toRadians(Math.round(relativeWindNorth))));
            gR.drawLine(xAxisMiddle, yAxisMiddle, (int) xRelativeWind, (int) yRelativeWind);
            /*check true wind
            gR.setColor(Color.cyan);
            gR.setStroke(basicStroke5);
            xShip = Math.sin(Math.toRadians(shipCourseDouble)) * shipSpeedDouble * 2;
            yShip = Math.cos(Math.toRadians(shipCourseDouble)) * shipSpeedDouble * 2;
            gR.drawLine((int) xRelativeWind, (int) yRelativeWind, xAxisMiddle + (int) xShip, yAxisMiddle - (int) yShip);
            */
        }
    }
}
