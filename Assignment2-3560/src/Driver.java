import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
/**Use composition when making the UI (this part actually sucks),
 * use observer for followers, use visitor for anything involving use/group stats,
 * and use Singleton for admin panel and any other "manager" classes.
 * You don't have to make the oop stuff from scratch either.
 * If you copy paste from some w3 tutorials type shit then you just need to make a
 * few classes implement the interfaces they give you
 */

public class Driver {
    public static void main(String[] args) {
        AdminControlPanel.getInstance();
    }
}
