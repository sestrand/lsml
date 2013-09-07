package lisong_mechlab.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import lisong_mechlab.model.chassi.Chassi;
import lisong_mechlab.model.chassi.ChassiDB;
import lisong_mechlab.model.item.Item;
import lisong_mechlab.model.item.ItemDB;

/**
 * This class shows a splash screen while the program is loading.
 * 
 * @author Li Song
 */
public class SplashScreen extends JFrame{
   private static final long serialVersionUID   = -2877785947094537320L;
   private static final long MIN_SPLASH_TIME_MS = 20;

   private class BackgroundImage extends JComponent{
      private static final long serialVersionUID = 2294812231919303690L;
      private Image             image;

      public BackgroundImage(Image anImage){
         image = anImage;
      }

      @Override
      protected void paintComponent(Graphics g){
         g.drawImage(image, 0, 0, this);
      }
   }

   SplashScreen(){
      SwingUtilities.invokeLater(new Runnable(){
         @Override
         public void run(){
            Image splash = Toolkit.getDefaultToolkit().getImage(getClass().getResource("/resources/splash.png"));
            setContentPane(new BackgroundImage(splash));
            Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();

            setResizable(false);
            setUndecorated(true);
            setTitle("loading...");
            setSize(500, 300);
            setLocation(dim.width / 2 - getSize().width / 2, dim.height / 2 - getSize().height / 2);
            setVisible(true);
            getRootPane().setBorder(BorderFactory.createLineBorder(Color.BLACK));
            getRootPane().putClientProperty("Window.shadow", Boolean.TRUE);
         }
      });
   }

   public void waitUntilDone(){
      long startTimeMs = new Date().getTime();

      @SuppressWarnings("unused")
      // Causes static initialization to be ran.
      Item bap = ItemDB.BAP;
      @SuppressWarnings("unused")
      // Causes static initialization to be ran.
      Chassi chassi = ChassiDB.lookup("JR7-D");
      long endTimeMs = new Date().getTime();
      long sleepTimeMs = Math.max(0, MIN_SPLASH_TIME_MS - (endTimeMs - startTimeMs));
      try{
         Thread.sleep(sleepTimeMs);
      }
      catch( Exception e ){
         // No-Op
      }
      dispose();
   }
}