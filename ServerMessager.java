package za.ac.servermessager;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.BevelBorder;
import za.ac.clientmessager.ClientMessager;

public class ServerMessager {

    private static Box verticalBox;

    private JScrollPane TextAreascrollpane;
    private JScrollPane TextFieldscrollpane;
    private JButton sendButton;
    private JTextArea textarea;
    private static JTextArea textField;

    private JPanel mainPanel;
    private static JPanel TextAreaPanel;
    private JPanel headingPanel;

    private JPanel TextField_ButtonPanel;

    private JLabel headingLabel;
    private static BufferedReader bfr;
    private static BufferedWriter bfw;
    private static PrintWriter prtw;

    private static Socket socket;
    private static ServerSocket serversocket;
    private static JFrame frame;
    private static DataInputStream dataInput;
    private static DataOutputStream dataOutput;
    public ServerMessager() {

        frame = new JFrame();
        verticalBox = Box.createVerticalBox();

        textField = new JTextArea(3, 30);
        TextFieldscrollpane = new JScrollPane(textField);
        TextField_ButtonPanel = new JPanel();
        TextField_ButtonPanel.setLayout(new FlowLayout(FlowLayout.LEADING));

        sendButton = new JButton("Send");

        sendButton.addActionListener(new sendMessage());

        sendButton.setFocusable(false);
        sendButton.setBackground(new Color(100, 250, 100));
        sendButton.setBounds(50, 120, 50, 40);
        sendButton.setPreferredSize(new Dimension(130, 50));

        TextField_ButtonPanel.add(TextFieldscrollpane);
        TextField_ButtonPanel.add(sendButton);
        TextField_ButtonPanel.setPreferredSize(new Dimension(70, 70));

        headingLabel = new JLabel("Walker Active");
        headingLabel.setPreferredSize(new Dimension(80, 80));
        headingPanel = new JPanel();
        headingPanel.setBorder(BorderFactory.createBevelBorder(BevelBorder.RAISED));
        headingPanel.setBackground(new Color(150, 250, 150));
        headingPanel.add(headingLabel);

        textarea = new JTextArea(31, 43);
        TextAreascrollpane = new JScrollPane(textarea);
        TextAreaPanel = new JPanel();
        TextAreaPanel.setLayout(new BorderLayout());

        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());
        mainPanel.add(headingPanel, BorderLayout.NORTH);
        mainPanel.add(TextAreaPanel, BorderLayout.CENTER);
        mainPanel.add(TextField_ButtonPanel, BorderLayout.SOUTH);

        frame.setSize(500, 700);
        frame.setLocation(500, 100);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setTitle("ServerMessager");

        frame.add(mainPanel);
        frame.setVisible(true);
    }

    private class sendMessage implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            
            try {
                String themessage = textField.getText();
                
                JLabel output = new JLabel(themessage);
                
                output.setBackground(new Color(50,150,250));
                output.setOpaque(true);
                output.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
                
                JPanel p1 = new JPanel();
                p1.add(output);
                
                JPanel rightPanel = new JPanel();
                rightPanel.setLayout(new BorderLayout());
                rightPanel.add(p1, BorderLayout.LINE_END);
                
                verticalBox.add(rightPanel);
                verticalBox.add(Box.createVerticalStrut(4));
                
                TextAreaPanel.add(verticalBox, BorderLayout.PAGE_START);
                
                dataOutput.writeUTF(themessage);
                textField.setText("");
                
                frame.repaint();
                frame.invalidate();
                frame.validate();
            } catch (IOException ex) {
                Logger.getLogger(ServerMessager.class.getName()).log(Level.SEVERE, null, ex);
            }
            
           
        }

    }

    public static void main(String[] args) {
        new ServerMessager();

String themessage = textField.getText();
        
            try{

                serversocket = new ServerSocket(1527);
                socket = serversocket.accept();

               
            dataInput=new DataInputStream(socket.getInputStream());
            dataOutput=new DataOutputStream(socket.getOutputStream());
             
               String clientrmessage="";
                while (!clientrmessage.equalsIgnoreCase("Bye")) {
      
                     clientrmessage = dataInput.readUTF();

                    JLabel output1 = new JLabel(clientrmessage);

                    output1.setBackground(Color.WHITE);
                    output1.setOpaque(true);
                    output1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

                    JPanel p2 = new JPanel();
                    p2.add(output1);

                    JPanel leftPanel = new JPanel();
                    leftPanel.setLayout(new BorderLayout());
                    leftPanel.add(p2, BorderLayout.LINE_START);

                    verticalBox.add(leftPanel);
                    verticalBox.add(Box.createVerticalStrut(4));

                    TextAreaPanel.add(verticalBox, BorderLayout.PAGE_START);


            frame.repaint();
            frame.invalidate();
            frame.validate();

                }

            } catch (IOException ex) {
                System.err.println("Error " + ex.getMessage());
            }

        }

   

}
