import java.awt.*;
import javax.swing.*;
import java.io.*;
import java.awt.event.*;
import javax.swing.plaf.metal.*;
import javax.swing.text.*;
import java.util.HashMap;
import java.util.Map;

class editor extends JFrame implements ActionListener {
    // Text component
    JTextArea t;
    // Frame
    JFrame f;
    // Menu items for formatting
    JMenuItem miBold, miItalic, miUnderline, miChangeTextColor, miChangeBgColor;

    // Constructor
    editor() {
        // Create a frame
        f = new JFrame("EDITHIT");

        try {
            // Set metal look and feel
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            // Set theme to ocean
            MetalLookAndFeel.setCurrentTheme(new OceanTheme());
        } catch (Exception e) {
        }

        // Text component
        t = new JTextArea();
        t.setFont(new Font("Arial", Font.PLAIN, 14));
        t.setLineWrap(true);
        t.setWrapStyleWord(true);

        // Create a menubar
        JMenuBar mb = new JMenuBar();

        // Create a menu for file operations
        JMenu m1 = new JMenu("File");
        JMenuItem mi1 = new JMenuItem("New");
        JMenuItem mi2 = new JMenuItem("Open");
        JMenuItem mi3 = new JMenuItem("Save");
        JMenuItem mi9 = new JMenuItem("Print");

        // Add action listeners
        mi1.addActionListener(this);
        mi2.addActionListener(this);
        mi3.addActionListener(this);
        mi9.addActionListener(this);

        m1.add(mi1);
        m1.add(mi2);
        m1.add(mi3);
        m1.add(mi9);

        // Create a menu for text editing
        JMenu m2 = new JMenu("Edit");
        JMenuItem mi4 = new JMenuItem("Cut");
        JMenuItem mi5 = new JMenuItem("Copy");
        JMenuItem mi6 = new JMenuItem("Paste");

        mi4.addActionListener(this);
        mi5.addActionListener(this);
        mi6.addActionListener(this);

        m2.add(mi4);
        m2.add(mi5);
        m2.add(mi6);

        // Create a menu for text formatting
        JMenu m3 = new JMenu("Format");
        miBold = new JMenuItem("Bold");
        miItalic = new JMenuItem("Italic");
        miUnderline = new JMenuItem("Underline");

        miBold.addActionListener(this);
        miItalic.addActionListener(this);
        miUnderline.addActionListener(this);

        m3.add(miBold);
        m3.add(miItalic);
        m3.add(miUnderline);

        // Add new color options
        miChangeTextColor = new JMenuItem("Change Text Color");
        miChangeBgColor = new JMenuItem("Change Background Color");

        miChangeTextColor.addActionListener(this);
        miChangeBgColor.addActionListener(this);

        m3.add(miChangeTextColor);
        m3.add(miChangeBgColor);

        JMenuItem mc = new JMenuItem("Close");
        mc.addActionListener(this);

        // Add menus to the menubar
        mb.add(m1);
        mb.add(m2);
        mb.add(m3);
        mb.add(mc);

        f.setJMenuBar(mb);
        f.add(new JScrollPane(t), BorderLayout.CENTER);
        f.setSize(500, 500);
        f.setVisible(true);
    }

    // If a button is pressed
    public void actionPerformed(ActionEvent e) {
        String s = e.getActionCommand();

        if (s.equals("cut")) {
            t.cut();
        } else if (s.equals("copy")) {
            t.copy();
        } else if (s.equals("paste")) {
            t.paste();
        } else if (s.equals("Save")) {
            JFileChooser j = new JFileChooser("f:");
            int r = j.showSaveDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                File fi = new File(j.getSelectedFile().getAbsolutePath());
                try {
                    FileWriter wr = new FileWriter(fi, false);
                    BufferedWriter w = new BufferedWriter(wr);
                    w.write(t.getText());
                    w.flush();
                    w.close();
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(f, "The user cancelled the operation.");
            }
        } else if (s.equals("Print")) {
            try {
                t.print();
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(f, evt.getMessage());
            }
        } else if (s.equals("Open")) {
            JFileChooser j = new JFileChooser("f:");
            int r = j.showOpenDialog(null);
            if (r == JFileChooser.APPROVE_OPTION) {
                File fi = new File(j.getSelectedFile().getAbsolutePath());
                try {
                    String s1 = "", sl = "";
                    FileReader fr = new FileReader(fi);
                    BufferedReader br = new BufferedReader(fr);
                    sl = br.readLine();
                    while ((s1 = br.readLine()) != null) {
                        sl = sl + "\n" + s1;
                    }
                    t.setText(sl);
                } catch (Exception evt) {
                    JOptionPane.showMessageDialog(f, evt.getMessage());
                }
            } else {
                JOptionPane.showMessageDialog(f, "The user cancelled the operation.");
            }
        } else if (s.equals("New")) {
            t.setText("");
        } else if (s.equals("Close")) {
            f.setVisible(false);
        } else if (s.equals("Bold")) {
            toggleTextStyle(Font.BOLD);
        } else if (s.equals("Italic")) {
            toggleTextStyle(Font.ITALIC);
        } else if (s.equals("Underline")) {
            toggleUnderline();
        } else if (s.equals("Change Text Color")) {
            Color color = JColorChooser.showDialog(this, "Choose Text Color", Color.BLACK);
            if (color != null) {
                t.setForeground(color);
            }
        } else if (s.equals("Change Background Color")) {
            Color color = JColorChooser.showDialog(this, "Choose Background Color", Color.WHITE);
            if (color != null) {
                t.setBackground(color);
            }
        }
    }

    // Toggle Bold, Italic, Underline text styles
    private void toggleTextStyle(int style) {
        Font currentFont = t.getFont();
        Font newFont = currentFont.deriveFont(currentFont.getStyle() ^ style);
        t.setFont(newFont);
    }

    private void toggleUnderline() {
        Font currentFont = t.getFont();
        Map<TextAttribute, Object> attributes = new HashMap<>(currentFont.getAttributes());
        if (attributes.get(TextAttribute.UNDERLINE) == TextAttribute.UNDERLINE_ON) {
            attributes.put(TextAttribute.UNDERLINE, -1);
        } else {
            attributes.put(TextAttribute.UNDERLINE, TextAttribute.UNDERLINE_ON);
        }
        t.setFont(currentFont.deriveFont(attributes));
    }

    // Main class
    public static void main(String args[]) {
        new editor();
    }
}
