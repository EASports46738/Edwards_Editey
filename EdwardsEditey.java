import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class EdwardsEditey extends JFrame implements ActionListener {
    JTextArea textArea;
    JScrollPane scrollPane;
    JLabel fontSizeLabel;
    JSpinner fontSizeSpinner;
    JButton fontColorButton;
    JMenuBar menuBar;
    JMenu fileMenu;
    JMenuItem openItem;
    JMenuItem saveItem;
    JMenuItem exitItem;
    EdwardsEditey(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setTitle("Edward's Editey");
        this.setSize(800, 500);
        this.setLayout(new FlowLayout());
        this.setLocationRelativeTo(null);
        textArea = new JTextArea();
        textArea.setLineWrap(true);
        textArea.setWrapStyleWord(true);
        textArea.setFont(new Font("Courier New", Font.PLAIN,20));
        scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(750,450));
        scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        fontSizeLabel = new JLabel("Font Size: ");
        fontSizeSpinner = new JSpinner();
        fontSizeSpinner.setPreferredSize(new Dimension(50, 25));
        fontSizeSpinner.setValue(20);
        fontSizeSpinner.addChangeListener(new ChangeListener() {
            public void stateChanged(ChangeEvent e) {
                textArea.setFont(new Font(textArea.getFont().getFamily(),Font.PLAIN,(int) fontSizeSpinner.getValue()));
            }
        });
        fontColorButton = new JButton("Color");
        fontColorButton.addActionListener(this);
        // -------------------Menu Bar -----------------
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");
        openItem = new JMenuItem("Open");
        saveItem = new JMenuItem("Save");
        exitItem = new JMenuItem("Exit");
        openItem.addActionListener(this);
        saveItem.addActionListener(this);
        exitItem.addActionListener(this);
        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        // -------------------Menu Bar -----------------
        this.setJMenuBar(menuBar);
        this.add(menuBar);
        this.add(fontSizeLabel);
        this.add(fontSizeSpinner);
        this.add(fontColorButton);
        this.add(scrollPane);
        this.setVisible(true);
    }
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==fontColorButton) {
            JColorChooser colorChooser = new JColorChooser();
            Color color = colorChooser.showDialog(null, "Choose a color", Color.BLACK);
            textArea.setForeground(color);
        }
        if(e.getSource()==openItem) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("C:/"));
            FileNameExtensionFilter filter = new FileNameExtensionFilter("Text Files", "txt");
            fileChooser.setFileFilter(filter);
            int response = fileChooser.showOpenDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                Scanner fileIn = null;
                try {
                    fileIn = new Scanner(file);
                    if (file.isFile()) {
                        while (fileIn.hasNextLine()) {
                            String line = fileIn.nextLine()+"\n";
                            textArea.append(line);
                        }
                    }
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                finally {
                    fileIn.close();
                }
            }
        }
        if(e.getSource()==saveItem) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File("C:/"));
            int response = fileChooser.showSaveDialog(null);
            if (response == JFileChooser.APPROVE_OPTION) {
                File file;
                PrintWriter fileOut = null;
                file = new File(fileChooser.getSelectedFile().getAbsolutePath());
                try {
                    fileOut = new PrintWriter(file);
                    fileOut.println(textArea.getText());
                } catch (FileNotFoundException e1) {
                    e1.printStackTrace();
                }
                finally {
                    fileOut.close();
                }
            }
        }
        if(e.getSource()==exitItem) {
            System.exit(0);
        }
    }
}
