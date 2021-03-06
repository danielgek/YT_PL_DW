/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 * 38: MP4  - HighRes (everything bigger than 1080p)
 85: MP4  - HD1080 - Stereo 3D
 37: MP4  - HD1080
 84: MP4  - HD720  - Stereo 3D
 22: MP4  - HD720
 83: MP4  - Large  - Stereo 3D
 82: MP4  - Medium - Stereo 3D
 18: MP4  - Medium
 102: WebM - HD720  - Stereo 3D
 45: WebM - HD720
 101: WebM - Large  - Stereo 3D
 44: WebM - Large
 100: WebM - Medium - Stereo 3D
 43: WebM - Medium
 35: FLV  - Large
 34: FLV  - Medium
 5: FLV  - Small
 36: 3GP  - Small
 */
package yt_pl_dw;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.client.utils.URIUtils;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.pushingpixels.substance.api.skin.SkinInfo;
import org.pushingpixels.substance.api.*;
import org.pushingpixels.substance.api.renderers.*;
import org.pushingpixels.substance.api.skin.*;
import org.htmlparser.Parser;



import org.htmlparser.tags.LinkTag;

import org.htmlparser.filters.NodeClassFilter;
import org.htmlparser.util.NodeList;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.*;
import java.util.*;
import sun.net.ProgressListener;

/**
 *
 * @author Adriana
 */
public class GUI extends javax.swing.JFrame implements ActionListener, PropertyChangeListener {

    private static final String scheme = "http";
    private static final String host = "www.youtube.com";
    private static final Pattern commaPattern = Pattern.compile(",");
    private static final Pattern pipePattern = Pattern.compile("\\|");
    private static final char[] ILLEGAL_FILENAME_CHARACTERS = {'/', '\n', '\r', '\t', '\0', '\f', '`', '?', '*', '\\', '<', '>', '|', '\"', ':'};
    private ArrayList<String> url_list = new ArrayList<String>();
    private static String g_userAgeng, g_downloadUrl;
    private static File g_outputFile;
    private single_download single_download;
    private playlist_downloader playlist_downloader;
    private int max, prog;
    private ArrayList<video> play_list = new ArrayList<video>();
    DefaultListModel<video> modelo_lista = new DefaultListModel<video>();
    private String[] skinss = {"SubstanceAutumnLookAndFeel", "SubstanceBusinessBlackSteelLookAndFeel", "SubstanceBusinessBlueSteelLookAndFeel", "SubstanceBusinessLookAndFeel", "SubstanceChallengerDeepLookAndFeel", "SubstanceCremeCoffeeLookAndFeel", "SubstanceCremeLookAndFeel", "SubstanceDustCoffeeLookAndFeel",
        "SubstanceDustLookAndFeel", "SubstanceEmeraldDuskLookAndFeel", "SubstanceGeminiLookAndFeel", "SubstanceGraphiteAquaLookAndFeel", "SubstanceGraphiteGlassLookAndFeel", "SubstanceGraphiteLookAndFeel", "SubstanceMagellanLookAndFeel",
        "SubstanceMarinerLookAndFeel", "SubstanceMistAquaLookAndFeel", "SubstanceMistSilverLookAndFeel", "SubstanceModerateLookAndFeel", "SubstanceNebulaBrickWallLookAndFeel", "SubstanceNebulaLookAndFeel",
        "SubstanceOfficeBlack2007LookAndFeel", "SubstanceOfficeBlue2007LookAndFeel", "SubstanceOfficeSilver2007LookAndFeel", "SubstanceRavenLookAndFeel", "SubstanceSaharaLookAndFeel", "SubstanceTwilightLookAndFeel"};

    class playlist_downloader extends SwingWorker<Void, Void> {

        private String userAgent;
        private String downloadUrl;
        private File outputfile;
        /*
         * Main task. Executed in background thread.
         */

        @Override
        public Void doInBackground() {

            int progress = 0;
            // Initialize progress property.
            setProgress(0);



            try {
                //System.out.println("\nentrei no download\n");
                HttpGet httpget2 = new HttpGet(g_downloadUrl);
                httpget2.setHeader("User-Agent", g_userAgeng);

                //System.out.println("\n\n\n\ndownload Executing " + httpget2.getURI());
                HttpClient httpclient2 = new DefaultHttpClient();
                HttpResponse response2 = httpclient2.execute(httpget2);
                HttpEntity entity2 = response2.getEntity();
                if (entity2 != null && response2.getStatusLine().getStatusCode() == 200) {
                    long length = entity2.getContentLength();
                    InputStream instream2 = entity2.getContent();
                    //System.out.println("Writing " + length + " bytes to " + g_outputFile);
                    if (g_outputFile.exists()) {
                        g_outputFile.delete();
                    }

                    FileOutputStream outstream = new FileOutputStream(g_outputFile);

                    max = (int) length;
                    //System.out.println(jProgressBar1.getMaximum());
                    try {
                        byte[] buffer = new byte[2048];
                        int count = -1;
                        int counter = 1;
                        while ((count = instream2.read(buffer)) != -1) {
                            outstream.write(buffer, 0, count);

                            counter = counter + count;
                            //System.out.println(counter);
                            final int counter2 = counter;

                            // jProgressBar1.setValue(counter2);
                            prog = counter;
                            if (progress != 100) {
                                progress += 1;
                            } else {
                                progress = 0;
                            }

                            setProgress(progress);




                            //System.out.println("lenght= "+length+"counter= "+counter+" Progress= "+x+"Float= "+x2);

                        }
                        outstream.flush();
                    } finally {
                        outstream.close();
                        if (jCheckBox1.isSelected()) {
                            try {
                                String line;
                                Process p = null;


                                if (/*
                                         * isWindows()
                                         */true) {
                                    String outmp3 = g_outputFile.getAbsolutePath().replaceAll("mp4", "mp3");
                                    p = Runtime.getRuntime().exec("libs/ffmpeg.exe -i " + g_outputFile.getAbsolutePath() + " -y " + outmp3);

                                    System.out.println("libs/ffmpeg.exe -i " + g_outputFile.getAbsolutePath() + " -y " + outmp3);
                                    System.out.println("comando de conversao:");
                                    //p.waitFor();
                                    BufferedReader input2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));

                                    String line2 = null;

                                    while ((line2 = input2.readLine()) != null) {
                                        System.out.println(line2);
                                    }

                                    int exitVal = p.waitFor();
                                    System.out.println("Exited with error code " + exitVal);

                                    g_outputFile.delete();
                                    //ffmpeg -i filename.mp4 filename.mp3
                                    //logtofile("lib/adb.exe" + reinstall + " install " + op);

                                }/*
                                 * else if (isMac()) {
                                 *
                                 * logtofile("lib/adbMac" + reinstall + "
                                 * install " + op);
                                 *
                                 * p = Runtime.getRuntime().exec("lib/adbMac
                                 * install" + reinstall + " " + op);
                                 *
                                 * } else if (isUnix()) {
                                 *
                                 * //logtofile("This is Unix or Linux"); //p =
                                 * Runtime.getRuntime().exec("./adbl install" +
                                 * reinstall + " " + op);
                                 *
                                 * } else { //logtofile("Your OS is not
                                 * support!!");
                                 *
                                 * }
                                 */

                                BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
                                BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                                while ((line = bri.readLine()) != null) {
                                    System.out.println(line);
                                    //logtofile(line);
                                    //modelolog.addElement(line);
                                }
                                bri.close();
                                while ((line = bre.readLine()) != null) {
                                    System.out.println(line);
                                    //logtofile(line);
                                    //modelolog.addElement(line);
                                }
                                bre.close();
                                p.waitFor();
                                System.out.println("Done.");

                            } catch (Exception err) {
                                System.out.println(err.toString());
                                //modelolog.addElement(err);
                                ///logtofile(err.toString());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("erro na task download" + e.toString() + "\n" + e.getCause());
            }
            // Make random progress.


            return null;
        }
        /*
         * Executed in event dispatching thread
         */

        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            jButton2.setEnabled(true);

        }
    }
    
    class single_download extends SwingWorker<Void, Void> {

        private String userAgent;
        private String downloadUrl;
        private File outputfile;
        /*
         * Main task. Executed in background thread.
         */

        @Override
        public Void doInBackground() {

            int progress = 0;
            // Initialize progress property.
            setProgress(0);



            try {
                //System.out.println("\nentrei no download\n");
                HttpGet httpget2 = new HttpGet(g_downloadUrl);
                httpget2.setHeader("User-Agent", g_userAgeng);

                //System.out.println("\n\n\n\ndownload Executing " + httpget2.getURI());
                HttpClient httpclient2 = new DefaultHttpClient();
                HttpResponse response2 = httpclient2.execute(httpget2);
                HttpEntity entity2 = response2.getEntity();
                if (entity2 != null && response2.getStatusLine().getStatusCode() == 200) {
                    long length = entity2.getContentLength();
                    InputStream instream2 = entity2.getContent();
                    //System.out.println("Writing " + length + " bytes to " + g_outputFile);
                    if (g_outputFile.exists()) {
                        g_outputFile.delete();
                    }

                    FileOutputStream outstream = new FileOutputStream(g_outputFile);

                    max = (int) length;
                    //System.out.println(jProgressBar1.getMaximum());
                    try {
                        byte[] buffer = new byte[2048];
                        int count = -1;
                        int counter = 1;
                        while ((count = instream2.read(buffer)) != -1) {
                            outstream.write(buffer, 0, count);

                            counter = counter + count;
                            //System.out.println(counter);
                            final int counter2 = counter;

                            // jProgressBar1.setValue(counter2);
                            prog = counter;
                            if (progress != 100) {
                                progress += 1;
                            } else {
                                progress = 0;
                            }

                            setProgress(progress);




                            //System.out.println("lenght= "+length+"counter= "+counter+" Progress= "+x+"Float= "+x2);

                        }
                        outstream.flush();
                    } finally {
                        outstream.close();
                        if (jCheckBox1.isSelected()) {
                            try {
                                String line;
                                Process p = null;


                                if (/*
                                         * isWindows()
                                         */true) {
                                    String outmp3 = g_outputFile.getAbsolutePath().replaceAll("mp4", "mp3");
                                    p = Runtime.getRuntime().exec("libs/ffmpeg.exe -i " + g_outputFile.getAbsolutePath() + " -y " + outmp3);

                                    System.out.println("libs/ffmpeg.exe -i " + g_outputFile.getAbsolutePath() + " -y " + outmp3);
                                    System.out.println("comando de conversao:");
                                    //p.waitFor();
                                    BufferedReader input2 = new BufferedReader(new InputStreamReader(p.getErrorStream()));

                                    String line2 = null;

                                    while ((line2 = input2.readLine()) != null) {
                                        System.out.println(line2);
                                    }

                                    int exitVal = p.waitFor();
                                    System.out.println("Exited with error code " + exitVal);

                                    g_outputFile.delete();
                                    //ffmpeg -i filename.mp4 filename.mp3
                                    //logtofile("lib/adb.exe" + reinstall + " install " + op);

                                }/*
                                 * else if (isMac()) {
                                 *
                                 * logtofile("lib/adbMac" + reinstall + "
                                 * install " + op);
                                 *
                                 * p = Runtime.getRuntime().exec("lib/adbMac
                                 * install" + reinstall + " " + op);
                                 *
                                 * } else if (isUnix()) {
                                 *
                                 * //logtofile("This is Unix or Linux"); //p =
                                 * Runtime.getRuntime().exec("./adbl install" +
                                 * reinstall + " " + op);
                                 *
                                 * } else { //logtofile("Your OS is not
                                 * support!!");
                                 *
                                 * }
                                 */

                                BufferedReader bri = new BufferedReader(new InputStreamReader(p.getInputStream()));
                                BufferedReader bre = new BufferedReader(new InputStreamReader(p.getErrorStream()));
                                while ((line = bri.readLine()) != null) {
                                    System.out.println(line);
                                    //logtofile(line);
                                    //modelolog.addElement(line);
                                }
                                bri.close();
                                while ((line = bre.readLine()) != null) {
                                    System.out.println(line);
                                    //logtofile(line);
                                    //modelolog.addElement(line);
                                }
                                bre.close();
                                p.waitFor();
                                System.out.println("Done.");

                            } catch (Exception err) {
                                System.out.println(err.toString());
                                //modelolog.addElement(err);
                                ///logtofile(err.toString());
                            }
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("erro na task download" + e.toString() + "\n" + e.getCause());
            }
            // Make random progress.


            return null;
        }
        /*
         * Executed in event dispatching thread
         */

        @Override
        public void done() {
            Toolkit.getDefaultToolkit().beep();
            jButton2.setEnabled(true);

        }
    }

    /**
     * Creates new form GUI
     */
    public GUI() {
        initComponents();
        jPanel1.setVisible(false);
        jPanel2.setVisible(false);
        for (String sk : skinss) {
            jComboBox2.addItem(sk);
        }
        jComboBox2.setSelectedIndex(1);
        //jTextField1.setText("http://www.youtube.com/watch?v=ObKk-0AVlcI");
        jTextField2.setText("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop");
        jTextField3.setText("C:\\Users\\" + System.getProperty("user.name") + "\\Desktop");
        jProgressBar1.setStringPainted(true);
        jList1.setModel(modelo_lista);
        this.setLocationRelativeTo(getRootPane());


    }

    public Icon scanImage(String fn) {
        try {
            BufferedImage img = null;
            URL url = new URL(fn);
            img = ImageIO.read(url);
            Image resizedimage2 = (Image) img;
            ImageIcon resizedimage3 = new ImageIcon(resizedimage2);
            jLabel9.setIcon(resizedimage3);
            return resizedimage3;
        } catch (Exception ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jButton4 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jTextField2 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        jTextField1 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jProgressBar1 = new javax.swing.JProgressBar();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        jCheckBox1 = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jTextField4 = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jButton8 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jList1 = new javax.swing.JList();
        jProgressBar2 = new javax.swing.JProgressBar();
        jCheckBox2 = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        jToolBar1 = new javax.swing.JToolBar();
        jLabel10 = new javax.swing.JLabel();
        jComboBox2 = new javax.swing.JComboBox(new Vector<SkinInfo>(
            SubstanceLookAndFeel.getAllSkins().values()));
    filler1 = new javax.swing.Box.Filler(new java.awt.Dimension(0, 0), new java.awt.Dimension(0, 0), new java.awt.Dimension(32767, 0));
    jButton9 = new javax.swing.JButton();

    jButton4.setText("jButton4");

    setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

    jTextField2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField2ActionPerformed(evt);
        }
    });

    jLabel2.setText("Location:");

    jButton2.setText("Download");
    jButton2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton2ActionPerformed(evt);
        }
    });

    jButton1.setText("Browse");
    jButton1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton1ActionPerformed(evt);
        }
    });

    jTextField1.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField1ActionPerformed(evt);
        }
    });
    jTextField1.addInputMethodListener(new java.awt.event.InputMethodListener() {
        public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            jTextField1InputMethodTextChanged(evt);
        }
        public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
        }
    });

    jLabel1.setText("Link:");

    jLabel5.setText("Quality:");

    jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

    jLabel6.setText("Name:");

    jLabel8.setText("Preview:");

    jLabel9.setText(" ");

    jButton3.setText("Get Info");
    jButton3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton3ActionPerformed(evt);
        }
    });

    jLabel11.setText("To MP3:");

    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
    jPanel1.setLayout(jPanel1Layout);
    jPanel1Layout.setHorizontalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jProgressBar1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addContainerGap()
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jLabel11)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jCheckBox1))
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8)))
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(0, 454, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jComboBox1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 99, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextField1, javax.swing.GroupLayout.DEFAULT_SIZE, 468, Short.MAX_VALUE)
                                    .addComponent(jTextField2))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jButton1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap()))))
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton2)
                    .addGap(31, 31, 31))))
    );
    jPanel1Layout.setVerticalGroup(
        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel1Layout.createSequentialGroup()
            .addGap(42, 42, 42)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jLabel1)
                .addComponent(jButton3))
            .addGap(18, 18, 18)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel2)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton1))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel5)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel6)
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGap(18, 18, 18)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel8)
                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 89, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 11, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jCheckBox1)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jProgressBar1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
    );

    jTabbedPane1.addTab("Video", jPanel1);

    jTextField3.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField3ActionPerformed(evt);
        }
    });

    jLabel3.setText("Location:");

    jButton5.setText("Download");
    jButton5.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton5ActionPerformed(evt);
        }
    });

    jButton6.setText("Browse");
    jButton6.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton6ActionPerformed(evt);
        }
    });

    jTextField4.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jTextField4ActionPerformed(evt);
        }
    });
    jTextField4.addInputMethodListener(new java.awt.event.InputMethodListener() {
        public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            jTextField4InputMethodTextChanged(evt);
        }
        public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
        }
    });

    jLabel4.setText("Link:");

    jButton8.setText("Get Info");
    jButton8.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton8ActionPerformed(evt);
        }
    });

    jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);

    jList1.setModel(modelo_lista);
    jList1.setDoubleBuffered(true);
    jList1.setCellRenderer(new BookCellRenderer());
    jScrollPane1.setViewportView(jList1);

    jLabel12.setText("To MP3:");

    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
    jPanel2.setLayout(jPanel2Layout);
    jPanel2Layout.setHorizontalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                    .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jButton5))
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 626, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jTextField3)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(jButton6, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jTextField4))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jButton8)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jLabel12)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(jCheckBox2)))))))
            .addContainerGap())
    );
    jPanel2Layout.setVerticalGroup(
        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(jPanel2Layout.createSequentialGroup()
            .addGap(22, 22, 22)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(jLabel4)
                .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(jButton8))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jButton6))
                    .addComponent(jCheckBox2)))
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jProgressBar2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addContainerGap())
    );

    jTabbedPane1.addTab("PlayList", jPanel2);

    jToolBar1.setFloatable(false);
    jToolBar1.setRollover(true);
    jToolBar1.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));

    jLabel10.setText("Skin:");
    jToolBar1.add(jLabel10);

    jComboBox2.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jComboBox2ActionPerformed(evt);
        }
    });
    jToolBar1.add(jComboBox2);
    jToolBar1.add(filler1);

    jButton9.setText("About");
    jButton9.setFocusable(false);
    jButton9.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
    jButton9.setVerticalTextPosition(javax.swing.SwingConstants.BOTTOM);
    jButton9.addActionListener(new java.awt.event.ActionListener() {
        public void actionPerformed(java.awt.event.ActionEvent evt) {
            jButton9ActionPerformed(evt);
        }
    });
    jToolBar1.add(jButton9);

    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
    getContentPane().setLayout(layout);
    layout.setHorizontalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addComponent(jToolBar1, javax.swing.GroupLayout.DEFAULT_SIZE, 651, Short.MAX_VALUE)
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1))
    );
    layout.setVerticalGroup(
        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
        .addGroup(layout.createSequentialGroup()
            .addComponent(jToolBar1, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addGap(0, 367, Short.MAX_VALUE))
        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(0, 29, Short.MAX_VALUE)
                .addComponent(jTabbedPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
    );

    pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField2ActionPerformed

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        try {
            jButton2.setEnabled(false);
            //task.addPropertyChangeListener(GUI.this);
            //task.execute();
            actionPerformed(evt);
        } catch (Throwable ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }


    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //JOptionPane.showMessageDialog(this, "altero o texto da cixa");
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        //
        // disable the "All files" option.
        //
        chooser.setAcceptAllFileFilterUsed(false);
        //   
        if (chooser.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            System.out.println("getCurrentDirectory(): "
                    + chooser.getCurrentDirectory());
            System.out.println("getSelectedFile() : "
                    + chooser.getSelectedFile());
        } else {
            System.out.println("No Selection ");
        }
        jTextField2.setText(chooser.getSelectedFile().toString());
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jTextField1InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextField1InputMethodTextChanged
        JOptionPane.showMessageDialog(this, "altero o texto da cixa");
    }//GEN-LAST:event_jTextField1InputMethodTextChanged

    private void jTextField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField3ActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jTextField4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField4ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4ActionPerformed

    private void jTextField4InputMethodTextChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_jTextField4InputMethodTextChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField4InputMethodTextChanged

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        final String[] url = jTextField1.getText().split("=");
        final String[] url2 = url[1].split("&");

        try {


            int format = 18; // http://en.wikipedia.org/wiki/YouTube#Quality_and_codecs
            String encoding = "UTF-8";
            String userAgent = "Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/20100101 Firefox/12.0";
            File outputDir = new File(jTextField2.getText());
            String extension = getExtension(format);

            play(url2[0], format, encoding, userAgent, outputDir, extension);








        } catch (Throwable t) {
            t.printStackTrace();
        }


    }//GEN-LAST:event_jButton3ActionPerformed

    private void jComboBox2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox2ActionPerformed
        try {

            UIManager.setLookAndFeel("org.pushingpixels.substance.api.skin." + jComboBox2.getSelectedItem().toString());
            SwingUtilities.updateComponentTreeUI(this);
        } catch (Exception e) {
            System.out.println("Falha ao realizar a mudanca de estilo" + e);
        }
    }//GEN-LAST:event_jComboBox2ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        setCursor(Cursor.WAIT_CURSOR);
        String htmlContents = "";
        try {
            URL url = new URL("http://www.youtube.com/playlist?list=PL2C9A419902CCA1EA");
            System.out.println("Connecting to http://www.youtube.com/playlist?list=PL2C9A419902CCA1EA");
            URLConnection ucon = url.openConnection();
            htmlContents = getResponseData(ucon);
            //System.out.println(htmlContents);
        } catch (Exception e) {
            e.printStackTrace();
        }

        ArrayList<String> links = new ArrayList<String>();


        try {


            Parser parser = new Parser();

            parser.setInputHTML(htmlContents);
            NodeList list = parser.extractAllNodesThatMatch(new NodeClassFilter(LinkTag.class));

            for (int i = 0; i < list.size(); i++) {
                LinkTag extracted = (LinkTag) list.elementAt(i);
                String extractedLink = extracted.getLink();
                links.add(extractedLink);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
        int x = 0;
        for (String v : links) {

            if (v.startsWith("/watch?v=") && v.contains("index")) {
                String spliter[] = v.split("v=");
                String spliter2[] = spliter[1].split("&amp;");
                //System.out.print(spliter2[0] + "\n ");
                int format = 18;
                String encoding = "UTF-8";
                String userAgent = "Mozilla/5.0 (Windows NT 6.1; rv:12.0) Gecko/20100101 Firefox/12.0";
                File outputDir = new File(jTextField2.getText());
                String extension = getExtension(format);
                video vd = null;

                try {
                    vd = get_info(spliter2[0], format, encoding, userAgent, outputDir, extension);
                } catch (Exception ex) {
                    Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
                }

                if (vd.status.equals("ok")) {
                    play_list.add(vd);

                    modelo_lista.addElement(vd);
                } else {
                    x = x + 1;

                }

                //get_info(spliter2[0],format,encoding ,userAgent, outputDir, extension);
            }


        }
        if (x != 0) {
            JOptionPane.showMessageDialog(this, "It was not possible to get all videos!! Sorry");
        }
        setCursor(Cursor.DEFAULT_CURSOR);

    }//GEN-LAST:event_jButton8ActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        new about().setVisible(true);
    }//GEN-LAST:event_jButton9ActionPerformed
    private static String getResponseData(URLConnection conn) throws Exception {
        StringBuffer sb = new StringBuffer();
        String data = "";
        InputStream is = conn.getInputStream();
        int ch;
        while ((ch = is.read()) != -1) {
            sb.append((char) ch);
        }

        data = sb.toString();
        is.close();
        is = null;
        sb = null;
        System.gc();
        return data;
    }

    private static String getExtension(int format) {
        // TODO
        return "mp4";
    }

    private static String cleanFilename(String filename) {
        for (char c : ILLEGAL_FILENAME_CHARACTERS) {
            filename = filename.replace(c, '_');
        }
        return filename;
    }

    private static URI getUri(String path, List<NameValuePair> qparams) throws URISyntaxException {
        URI uri = URIUtils.createURI(scheme, host, -1, "/" + path, URLEncodedUtils.format(qparams, "UTF-8"), null);
        return uri;
    }

    private static String getStringFromInputStream(String encoding, InputStream instream) throws UnsupportedEncodingException, IOException {
        Writer writer = new StringWriter();

        char[] buffer = new char[1024];
        try {
            Reader reader = new BufferedReader(new InputStreamReader(instream, encoding));
            int n;
            while ((n = reader.read(buffer)) != -1) {
                writer.write(buffer, 0, n);
            }
        } finally {
            instream.close();
        }
        String result = writer.toString();
        return result;
    }

    public void play(final String videoId, int format, String encoding, String userAgent, File outputdir, String extension) throws Exception {

        System.out.println("Retrieving " + videoId);
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        qparams.add(new BasicNameValuePair("video_id", videoId));
        qparams.add(new BasicNameValuePair("fmt", "" + format));
        URI uri = getUri("get_video_info", qparams);

        CookieStore cookieStore = new BasicCookieStore();
        HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(uri);
        httpget.setHeader("User-Agent", userAgent);

        //System.out.println("Executing " + uri);
        HttpResponse response = httpclient.execute(httpget, localContext);
        HttpEntity entity = response.getEntity();
        if (entity != null && response.getStatusLine().getStatusCode() == 200) {
            InputStream instream = entity.getContent();
            String videoInfo = getStringFromInputStream(encoding, instream);
            if (videoInfo != null && videoInfo.length() > 0) {
                List<NameValuePair> infoMap = new ArrayList<NameValuePair>();
                URLEncodedUtils.parse(infoMap, new Scanner(videoInfo), encoding);
                String token = null;
                String downloadUrl = null;
                String filename = videoId;

                for (NameValuePair pair : infoMap) {
                    String key = pair.getName();
                    String val = pair.getValue();
                    System.out.println(key + "=" + val);
                    if (key.equals("token")) {
                        token = val;
                    } else if (key.equals("title")) {
                        jLabel7.setText(val);
                        filename = val;
                    } else if (key.equals("thumbnail_url")) {
                        scanImage(val);
                    } else if (key.equals("url_encoded_fmt_stream_map")) {



                        String[] formats = commaPattern.split(val);

                        for (String fmt : formats) {
                            //System.out.println(URLDecoder.decode(URLDecoder.decode(fmt)));
                            String url = URLDecoder.decode(URLDecoder.decode(fmt));
                            String split_url[] = url.split("rl=");
                            for (int i = 0; i < split_url.length; i++) {//System.out.println("Split url n"+i+" = "+split_url[i]);
                            }
                            String split_quality[] = split_url[1].split("&quality");
                            for (int i = 0; i < split_url.length; i++) {
                                //System.out.println("Split quality n " + i + " = " + split_url[i]);
                            }
                            downloadUrl = split_quality[0];
                            //System.out.println("down_url= " + split_quality[0]);
                            /*
                             * String[] fmtPieces = pipePattern.split(fmt);
                             *
                             * if (fmtPieces.length == 2) { // in the end,
                             * download somethin! downloadUrl = fmtPieces[1];
                             * int pieceFormat = Integer.parseInt(fmtPieces[0]);
                             * if (pieceFormat == format) { // found what we *
                             * downloadUrl = fmtPieces[1]; break; } }
                             */
                            //System.out.println("\n\n\n");
                        }
                    }
                }

                filename = cleanFilename(filename);
                if (filename.length() == 0) {
                    filename = videoId;
                } else {
                    filename += "_" + videoId;
                }
                filename += "." + extension;
                filename = filename.replaceAll(" ", "-");
                File outputfile = new File(outputdir, filename);
                //System.out.println("download url=" + downloadUrl);
                if (downloadUrl != null) {
                    //System.out.println("\npassei aki\n");
                    //downloadWithHttpClient(userAgent, downloadUrl, outputfile);
                    single_download = new single_download();
                    single_download.addPropertyChangeListener(this);
                    single_download.downloadUrl = g_downloadUrl;
                    single_download.outputfile = g_outputFile;
                    single_download.userAgent = g_userAgeng;
                    g_userAgeng = userAgent;
                    g_downloadUrl = downloadUrl;
                    g_outputFile = outputfile;
                }
            }
        }


    }

    public video get_info(final String videoId, int format, String encoding, String userAgent, File outputdir, String extension) throws Exception {
        video v;
        System.out.println("Retrieving " + videoId);
        List<NameValuePair> qparams = new ArrayList<NameValuePair>();
        qparams.add(new BasicNameValuePair("video_id", videoId));
        qparams.add(new BasicNameValuePair("fmt", "" + format));
        URI uri = getUri("get_video_info", qparams);

        CookieStore cookieStore = new BasicCookieStore();
        HttpContext localContext = new BasicHttpContext();
        localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);

        HttpClient httpclient = new DefaultHttpClient();
        HttpGet httpget = new HttpGet(uri);
        httpget.setHeader("User-Agent", userAgent);
        String token = null;
        String downloadUrl = null;
        String filename = videoId;
        String title = null, image = null, status = null, author = null;
        System.out.println("Executing " + uri);
        HttpResponse response = httpclient.execute(httpget, localContext);
        HttpEntity entity = response.getEntity();
        if (entity != null && response.getStatusLine().getStatusCode() == 200) {
            InputStream instream = entity.getContent();
            String videoInfo = getStringFromInputStream(encoding, instream);
            if (videoInfo != null && videoInfo.length() > 0) {
                List<NameValuePair> infoMap = new ArrayList<NameValuePair>();
                URLEncodedUtils.parse(infoMap, new Scanner(videoInfo), encoding);
                token = null;
                downloadUrl = null;
                filename = videoId;

                for (NameValuePair pair : infoMap) {
                    String key = pair.getName();
                    String val = pair.getValue();
                    System.out.println(key + "=" + val);//<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
                    if (key.equals("token")) {
                        token = val;
                    } else if (key.equals("title")) {
                        title = val;
                        filename = val;
                    } else if (key.equals("thumbnail_url")) {
                        //scanImage(val);
                        image = val;
                    } else if (key.equals("status")) {
                        //scanImage(val);
                        status = val;
                    } else if (key.equals("author")) {
                        //scanImage(val);
                        author = val;
                    } else if (key.equals("url_encoded_fmt_stream_map")) {



                        String[] formats = commaPattern.split(val);

                        for (String fmt : formats) {
                            //System.out.println(URLDecoder.decode(URLDecoder.decode(fmt)));
                            String url = URLDecoder.decode(URLDecoder.decode(fmt));
                            String split_url[] = url.split("rl=");
                            for (int i = 0; i < split_url.length; i++) {//System.out.println("Split url n"+i+" = "+split_url[i]);
                            }
                            String split_quality[] = split_url[1].split("&quality");
                            for (int i = 0; i < split_url.length; i++) {
                                //System.out.println("Split quality n " + i + " = " + split_url[i]);
                            }
                            downloadUrl = split_quality[0];
                            //System.out.println("down_url= " + split_quality[0]);
                            /*
                             * String[] fmtPieces = pipePattern.split(fmt);
                             *
                             * if (fmtPieces.length == 2) { // in the end,
                             * download somethin! downloadUrl = fmtPieces[1];
                             * int pieceFormat = Integer.parseInt(fmtPieces[0]);
                             * if (pieceFormat == format) { // found what we *
                             * downloadUrl = fmtPieces[1]; break; } }
                             */
                            //System.out.println("\n\n\n");
                        }
                    }
                }

                filename = cleanFilename(filename);
                if (filename.length() == 0) {
                    filename = videoId;
                } else {
                    filename += "_" + videoId;
                }
                filename += "." + extension;
                File outputfile = new File(outputdir, filename);
                //System.out.println("download url=" + downloadUrl);
                if (downloadUrl != null) {
                    //System.out.println("\npassei aki\n");
                    //downloadWithHttpClient(userAgent, downloadUrl, outputfile);
                    single_download = new single_download();
                    single_download.addPropertyChangeListener(this);
                    single_download.downloadUrl = g_downloadUrl;
                    single_download.outputfile = g_outputFile;
                    single_download.userAgent = g_userAgeng;
                    g_userAgeng = userAgent;
                    g_downloadUrl = downloadUrl;
                    g_outputFile = outputfile;
                }
            }
        }
        v = new video(token, title, image, downloadUrl, videoId, status, author, outputdir);
        //System.out.println(v.toString() + "\n\n\n\n\n\n\n");
        return v;

    }

    private void downloadWithHttpClient(String userAgent, String downloadUrl, File outputfile) throws Exception {
        //System.out.println("\nentrei no download\n");
        HttpGet httpget2 = new HttpGet(downloadUrl);
        httpget2.setHeader("User-Agent", userAgent);

        //System.out.println("\n\n\n\ndownload Executing " + httpget2.getURI());
        HttpClient httpclient2 = new DefaultHttpClient();
        HttpResponse response2 = httpclient2.execute(httpget2);
        HttpEntity entity2 = response2.getEntity();
        if (entity2 != null && response2.getStatusLine().getStatusCode() == 200) {
            long length = entity2.getContentLength();
            InputStream instream2 = entity2.getContent();
            System.out.println("Writing " + length + " bytes to " + outputfile);
            if (outputfile.exists()) {
                outputfile.delete();
            }
            FileOutputStream outstream = new FileOutputStream(outputfile);

            jProgressBar1.setMaximum((int) length);
            System.out.println(jProgressBar1.getMaximum());
            try {
                byte[] buffer = new byte[2048];
                int count = -1;
                int counter = 1;
                while ((count = instream2.read(buffer)) != -1) {
                    outstream.write(buffer, 0, count);
                    if (count == 2048) {
                        counter = counter + count;
                        System.out.println(counter);
                        final int counter2 = counter;
                        Runnable updateProgress = new Runnable() {

                            public void run() {
                                jProgressBar1.setValue(counter2);
                            }
                        };
                        updateProgress.run();




                    }


                    //System.out.println("lenght= "+length+"counter= "+counter+" Progress= "+x+"Float= "+x2);

                }
                outstream.flush();
            } finally {
                outstream.close();
            }
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /*
         * Set the Nimbus look and feel
         */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /*
         * If Nimbus (introduced in Java SE 6) is not available, stay with the
         * default look and feel. For details see
         * http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(GUI.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /*
         * Create and display the form
         */
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new GUI().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.Box.Filler filler1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JComboBox jComboBox2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JList jList1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JProgressBar jProgressBar1;
    private javax.swing.JProgressBar jProgressBar2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JToolBar jToolBar1;
    // End of variables declaration//GEN-END:variables

    @Override
    public void actionPerformed(ActionEvent e) {
        single_download = new single_download();
        single_download.addPropertyChangeListener(this);
        single_download.execute();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if ("progress" == evt.getPropertyName()) {
            if (jProgressBar1.getMaximum() != max);
            jProgressBar1.setMaximum(max);

            jProgressBar1.setValue(prog);
            //System.out.println("max= " + max + "prog= " + prog);

        }
    }

    class BookCellRenderer extends JLabel implements ListCellRenderer {

        public Component getListCellRendererComponent(JList list, Object value,
                int index, boolean isSelected, boolean cellHasFocus) {
            video video = (video) value; // Using value we are getting the object in JList
            //System.out.println(video.toString());
            setSize(new Dimension(300, 20));
            setText("<html><body>Title: " + video.getTitle() + "<br>Author: " + video.getAuthor() + "<html><body>"); // Setting the text
            try {
                //java.awt.Image image = java.awt.Toolkit.getDefaultToolkit().getDefaultToolkit().createImage(video.getThumbnail_url());
                java.net.URL where = new URL(video.getThumbnail_url());
                setIcon(new ImageIcon(where)); // Setting the Image Icon
            } catch (Exception ex) {
                System.out.println("1sadnbgusbfalsjkdfhajbfaliudfjfsdf" + ex);
            }

            return this;
        }
    }
}
