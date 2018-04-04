package com.logging;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.Timer;

public class LogReader {
	
	static String filepath = "lssconsole.log";

    File file = null;
    BufferedReader reader = null;

    private Timer timer = null;
    private JTextArea textArea;
    private JTextField jtfFile;
    private String fileName;
    private JButton browse;
    private JFrame frame;

    public LogReader() {
        textArea = new JTextArea(25, 60);
        frame = new JFrame("Show Log");

        browse = new JButton("Browse");
        browse.addActionListener(new ShowLogListener());

        jtfFile = new JTextField(25);
        jtfFile.addActionListener(new ShowLogListener());

        timer = new Timer(100, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String line;
                try {
                    if ((line = reader.readLine()) != null) {
                        textArea.append(line + "\n");
                    } else {
                        ((Timer) e.getSource()).stop();
                    }
                } catch (IOException ex) {
                    Logger.getLogger(LogReader.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

        JPanel panel = new JPanel();
        panel.add(new JLabel("File: "));
        panel.add(jtfFile);
        panel.add(browse);

        frame.add(panel, BorderLayout.NORTH);
        frame.add(new JScrollPane(textArea), BorderLayout.CENTER);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);
    }

    private class ShowLogListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            JFileChooser chooser = new JFileChooser();
            chooser.setCurrentDirectory(new File(filepath));
            int result = chooser.showOpenDialog(frame);
            if (result == JFileChooser.APPROVE_OPTION) {
                file = chooser.getSelectedFile();
                fileName = file.getName();
                jtfFile.setText(fileName);
                try {
                    reader = new BufferedReader(new FileReader(file));
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(LogReader.class.getName()).log(Level.SEVERE, null, ex);
                }
                timer.start();
            }
        }
    }

    
}