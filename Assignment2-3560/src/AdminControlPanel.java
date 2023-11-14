import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class AdminControlPanel {

    private JPanel rootPanel;
    private JButton helloButton;
    private JLabel twitterLabel;
    private static AdminControlPanel initializedObject=null;
    private JFrame frame;


    //Utilizes Singleton's pattern scheme by only initializing when called and only getting one instance
    public AdminControlPanel() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize(){
        frame = new JFrame("twitter");
        frame.setContentPane(rootPanel);
        frame.setPreferredSize(new Dimension(500,500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500,500);

        ImageIcon icon = new ImageIcon("src/twitterLogo.png");
        frame.setIconImage(icon.getImage());

        helloButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                twitterLabel.setText("Its twitter");
            }
        });
        frame.pack();
    }

    //Only way to get instance using Singleton's
    public static AdminControlPanel getInstance(){
        if (initializedObject==null)
        {
            initializedObject=new AdminControlPanel();
        }
        return initializedObject;
    }

}
