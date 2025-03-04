package ch.fhnw.algd1.converters.ui;

import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/*
 * Created on 21.03.2014
 */

/**
 * @author Wolfgang Weck
 */
@SuppressWarnings("serial")
public final class Application extends Frame {
/**
 * @param args
 */
public static void main(String[] args) {
    javax.swing.SwingUtilities.invokeLater(
        () -> newFrame(new ConverterPanel()).setVisible(true));
}

private static Frame newFrame(Panel p) {
    Frame f = new Frame();
    f.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    f.add(p);
    f.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
            System.exit(0);
        }
    });
    f.pack();
    f.setResizable(false);
    f.setLocationByPlatform(true);
    return f;
}
}
