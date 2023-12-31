import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainFrame extends JFrame{

    //Converts a string into an arraylist of characters
    public ArrayList<Character> stringToArrayList(String oldString)
    {
        ArrayList<Character> newStringList = new ArrayList<Character>();

        if(oldString.equals(""))
        {
            newStringList.add('&');
        }
        else
        {
            //Loop through the characters in the string
            for(int i = 0; i < oldString.length(); i++)
                newStringList.add(oldString.charAt(i));
        }
        
        
        return newStringList;
    }

    public boolean isValidInput()
    {
        if(currInputList.get(0) == '&') return true; 

        for(int i = 0; i < currInputList.size(); i++)
            if(!currMachine.E.contains(currInputList.get(i)))
                return false;
        return true;
    }
    public ArrayList<Character> currInputList;

    private JButton machineFileButton;
    private JLabel startStack1Label;
    private JLabel startStack2Label;
    private JLabel startStateLabel;
    private JLabel statesLabel;
    private JLabel inputAlphabetLabel;
    private JLabel stackAlphabetLabel;
    private JTable transitionTable;
    private DefaultTableModel transitionTableModel;
    private JLabel transitionLabel;
    private JLabel finalStateLabel;
    private JLabel errorLabel;
    private Machine currMachine;
    private String filename;
    private ReadGrammar readGrammar;

    private boolean currResult = false;
    private TracingFrame childFrame;
    public MainFrame()
    {
        super();
        //Basic JFrame attributes
        setName("2-Stack Pushdown Automata");
        setLayout(new BorderLayout());
        setSize(720, 540);
        setResizable(false);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        childFrame = new TracingFrame();

        JPanel topPanel = new JPanel();
        topPanel.setOpaque(false);

        JLabel titleLabel = new JLabel("2-Stack Pushdown Automata Reader");
        machineFileButton = new JButton("Input Machine File");
        
        machineFileButton.addActionListener(e -> 
        {
            JFileChooser fileChooser = new JFileChooser(System.getProperty("user.dir")+"/STALGCM MP/src/machines");

            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                try {
                    generateMachine(fileChooser.getSelectedFile().getPath());
                } catch (IOException e1) {
                    // TODO Auto-generated catch block
                    e1.printStackTrace();
                }
            } else if (result == JFileChooser.CANCEL_OPTION) {
                System.out.println("Cancel was selected");
            }
        });


        JButton linkButton = new JButton("Github Link!");
        linkButton.addActionListener(e -> 
        {
            try {
                Desktop.getDesktop().browse(new URL("https://github.com/AaronRdm/STALGCM_MP").toURI());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        });
        
        topPanel.add(linkButton);
        topPanel.add(machineFileButton);
        topPanel.add(titleLabel);
        
        JPanel centerPanel = new JPanel();
        centerPanel.setBackground(new Color(255, 0, 0)); 
        centerPanel.setLayout(new BorderLayout());

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        startStateLabel = new JLabel("Start State:", SwingConstants.CENTER);
        startStack1Label = new JLabel("Start Stack 1:", SwingConstants.CENTER);
        startStack2Label = new JLabel("Start Stack 2:", SwingConstants.CENTER);
        statesLabel = new JLabel("States: {}", SwingConstants.CENTER);
        inputAlphabetLabel = new JLabel("Input Alphabet: {}", SwingConstants.CENTER);
        stackAlphabetLabel = new JLabel("Stack Alphabet: {}", SwingConstants.CENTER);
        transitionLabel = new JLabel("Transition Table:", SwingConstants.CENTER);
        finalStateLabel = new JLabel("Final State/s: {}", SwingConstants.CENTER);

        startStateLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 14));
        startStack1Label.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 14));
        startStack2Label.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 14));
        statesLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 14));
        inputAlphabetLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 14));
        stackAlphabetLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 14));
        transitionLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 14));
        finalStateLabel.setFont(new Font(Font.SANS_SERIF,  Font.BOLD, 14));

        transitionTableModel = new DefaultTableModel();

        transitionTable = new JTable(transitionTableModel);
        transitionTable.setRowHeight(20);

        transitionTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        startStateLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        startStack1Label.setBorder(BorderFactory.createLineBorder(Color.black));
        startStack2Label.setBorder(BorderFactory.createLineBorder(Color.black));
        statesLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        inputAlphabetLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        stackAlphabetLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        transitionLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        transitionTable.setBorder(BorderFactory.createLineBorder(Color.black));
        finalStateLabel.setBorder(BorderFactory.createLineBorder(Color.black));
        
        contentPanel.add(startStateLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(startStack1Label);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(startStack2Label);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(statesLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(inputAlphabetLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(stackAlphabetLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(finalStateLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(transitionLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        contentPanel.add(transitionTable);
        
        
        JScrollPane scrollableArea = new JScrollPane(contentPanel);  
  
        scrollableArea.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);  
        scrollableArea.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS); 

        centerPanel.add(scrollableArea);

        JPanel bottomButtonPanel = new JPanel();
        bottomButtonPanel.setOpaque(false);

        //This one will get the input string
        JTextField inputStringTextField = new JTextField();
        inputStringTextField.setColumns(16);

        //This one will submit the input string for reading
        JButton inputStringSubmitButton = new JButton("Submit");
        inputStringSubmitButton.addActionListener(e ->
        {
            if(currMachine != null)
            {
                String input = inputStringTextField.getText();
               currInputList = stringToArrayList(input);
                if(isValidInput())
                {
                    currResult = currMachine.run(currInputList);
                    errorLabel.setText(""); 
                    inputStringTextField.setText("");
                    childFrame.passResults(currMachine, currResult, currMachine.Q.get(0).getName());
                } else errorLabel.setText("Input has invalid characters not part of input alphabet!");  
            } else errorLabel.setText("No Machine has been Selected!"); 
        });

        //This one will submit the input string for reading
        JButton clearButton = new JButton("Clear");
        clearButton.addActionListener(e ->
        {
            startStateLabel.setText("Start State:");
            startStack1Label.setText("Start Stack 1:");
            startStack2Label.setText("Start Stack 2:");
            statesLabel.setText("States: {}");
            inputAlphabetLabel.setText("Input Alphabet: {}");
            stackAlphabetLabel.setText("Stack Alphabet: {}");
            transitionLabel.setText("Transition Table:");
            finalStateLabel.setText("Final State/s: {}");

            currMachine = null;
            filename = null;
            readGrammar = null;
            // Set transition function for transitionTable;
            while(true)
            {
                try {
                    transitionTableModel.removeRow(1);
                } catch (Exception tableError) {
                    // TODO: handle exception
                    break;
                }
            }
        });


        errorLabel = new JLabel("");
        bottomButtonPanel.add(errorLabel, BorderLayout.SOUTH);
        bottomButtonPanel.add(inputStringTextField, BorderLayout.SOUTH);
        bottomButtonPanel.add(inputStringSubmitButton, BorderLayout.SOUTH);
        bottomButtonPanel.add(clearButton, BorderLayout.SOUTH);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomButtonPanel, BorderLayout.SOUTH);

        setVisible(true);

        int scrollMidValue = scrollableArea.getHorizontalScrollBar().getMaximum() / 3;
        scrollableArea.getHorizontalScrollBar().setValue(scrollMidValue);

        
        String data[] = {"Transition Functions"};
        transitionTableModel.addColumn("Transition Functions", data);
    }

    
    private void generateMachine(String filepath) throws IOException
    {
        filename = filepath;
        System.out.println(filename);

        readGrammar = new ReadGrammar(filename);
        try
        {
            currMachine = readGrammar.loadGrammar();
            errorLabel.setText(""); 
        } catch(IOException ioException)
        {
            System.out.println("Invalid File Format!");
            errorLabel.setText("Invalid File Format!"); 
        }
        

        updateMachineInfo();
    }

    private void updateMachineInfo()
    {
        //Show Info of states
        startStateLabel.setText("Start State: " + currMachine.Q.get(0).getName());
        startStack1Label.setText("Start Stack 1: " + currMachine.Z1);
        startStack2Label.setText("Start Stack 2: " + currMachine.Z2);
        //List of States
        String statesText = "States: {";
        for(int i = 0; i < currMachine.Q.size(); i++)
        {
            statesText += currMachine.Q.get(i).getName();

            if(i < currMachine.Q.size() - 1)
                statesText += ", ";
            else
                statesText += "}";
        }
        statesLabel.setText(statesText);

        //Input Alphabet
        String inputAlphabetText = "Input Alphabet: {";
        for(int i = 0; i < currMachine.E.size(); i++)
        {
            inputAlphabetText += currMachine.E.get(i);

            if(i < currMachine.E.size() - 1)
                inputAlphabetText += ", ";
            else
                inputAlphabetText += "}";
        }
        inputAlphabetLabel.setText(inputAlphabetText);

        //Stack Alphabet
        String stackAlphabetText = "Stack Alphabet: {";
        for(int i = 0; i < currMachine.Gamma.size(); i++)
        {
            stackAlphabetText += currMachine.Gamma.get(i);

            if(i < currMachine.Gamma.size() - 1)
                stackAlphabetText += ", ";
            else
                stackAlphabetText += "}";
        }
        stackAlphabetLabel.setText(stackAlphabetText);

        //Final States
        String finalStatesText = "Final State/s: {";
        for(int i = 0; i < currMachine.F.size(); i++)
        {
            finalStatesText += currMachine.F.get(i).getName();

            if(i < currMachine.F.size() - 1)
                finalStatesText += ", ";
            else
                finalStatesText += "}";
        }
        finalStateLabel.setText(finalStatesText);

        // Set transition function for transitionTable;
        while(true)
        {
            try {
                transitionTableModel.removeRow(1);
            } catch (Exception e) {
                // TODO: handle exception
                break;
            }
        }

        for(int i = 0; i < currMachine.Delta.size(); i++)
        {
            String rowData[] = {currMachine.Delta.get(i).showTransition()};
            transitionTableModel.insertRow(1 + i, rowData);
        }
    }
}
