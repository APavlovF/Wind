import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by Oleksandr on 21.06.2016.
 */
class About extends JFrame {
    private JPanel panel1;
    private JButton button1;

    public About() {
        setContentPane(panel1);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        pack();
        setLocation(520, 200);
        setVisible(true);

        button1.addActionListener(e -> dispose());
    }

}
