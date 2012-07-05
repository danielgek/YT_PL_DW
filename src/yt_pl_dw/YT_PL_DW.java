/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package yt_pl_dw;

import java.awt.Image;
import java.awt.Toolkit;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.UIManager;
import javax.swing.plaf.MenuBarUI;

/**
 *
 * @author Adriana
 */
public class YT_PL_DW {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
       JDialog.setDefaultLookAndFeelDecorated(true);
        JFrame.setDefaultLookAndFeelDecorated(true);


        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                try {

                    UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin.SubstanceRavenLookAndFeel");
                } catch (Exception e) {
                    System.out.println("Falha ao realizar a mudanca de estilo" + e);
                }//RZ6GRZ7HG3BK
                JFrame frame = new GUI();
                JMenuBar menubar = frame.getJMenuBar(); // assuming you've set the menu bar already
                String os = System.getProperty("os.name");

                if (os.equals("Mac OS X")) {
                    try {
                        System.setProperty("apple.laf.useScreenMenuBar", "true");
                        System.setProperty("com.apple.mrj.application.apple.menu.about.name", "Youtube");

                        menubar.setUI((MenuBarUI) Class.forName("com.apple.laf.AquaMenuBarUI").newInstance());

                    } catch (Exception ex) {
                        // log...
                    }
                }
                try {
                    Image icon = Toolkit.getDefaultToolkit().getImage("logo.png");
                frame.setIconImage(icon);
                } catch (Exception e) {
                    System.out.println(e.getStackTrace());
                }
                
                frame.setVisible(true);
            }
        });
    
        
        
    }
}
