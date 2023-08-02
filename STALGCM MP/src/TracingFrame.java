import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
public class TracingFrame extends JFrame
{
    private JPanel transitionsPanel;
    private ArrayList<TransitionPanel> transitionsList = new ArrayList<TransitionPanel>();
    public TracingFrame()
    {
        super();
        //Basic JFrame attributes
        setName("2-Stack PDA Tracing");
        setLayout(new BorderLayout());
        setSize(360, 720);
        setResizable(false);
        
        setBackground(new Color(0, 127, 127));

        JPanel topButtonPanel = new JPanel();
        topButtonPanel.setSize(360, 60);
        topButtonPanel.setLayout(new FlowLayout());
        JLabel iterationsLabel = new JLabel("Iterations: 69");
        JLabel expandedNodesLabel = new JLabel("Expanded Nodes: 420");

        topButtonPanel.add(iterationsLabel);
        topButtonPanel.add(expandedNodesLabel);

        add(topButtonPanel, BorderLayout.NORTH);

        transitionsPanel = new JPanel();
        transitionsPanel.setSize(360, 600);
        transitionsPanel.setLayout(new FlowLayout());
        
        add(transitionsPanel, BorderLayout.CENTER);

        JPanel bottomButtonPanel = new JPanel();
        bottomButtonPanel.setSize(360, 60);
        bottomButtonPanel.setLayout(new FlowLayout());
        JButton previousStatesButton = new JButton("Previous Iteration");
        JButton nextStatesButton = new JButton("Next Iteration");

        previousStatesButton.addActionListener(e -> 
        {
            if(TreeLayers.size() != 0)
            {
                //Go back a layer
                showResults(currLayer - 1);
            }
        });

        nextStatesButton.addActionListener(e -> 
        {
            if(TreeLayers.size() != 0)
            {
                //Go up a layer
                showResults(currLayer + 1);
            }
        });
        
        bottomButtonPanel.add(previousStatesButton);
        bottomButtonPanel.add(nextStatesButton);

        add(bottomButtonPanel, BorderLayout.SOUTH);
    }

    private Machine currMachine;
    private boolean currResult;
    private int currLayer;
    private int numLayers;
    private ArrayList<ArrayList<TreeDS>> TreeLayers = new ArrayList<ArrayList<TreeDS>>();
    private ArrayList<Character> inputList = new ArrayList<Character>();
    private String initialStateName;
    public void passResults(Machine machine, boolean result, ArrayList<Character> inputList, String initialStateName)
    {
        this.inputList = (ArrayList<Character>) inputList.clone();
        this.initialStateName = initialStateName;

        currMachine = machine;
        currResult = result;

        //Go through the child nodes
        TreeDS root = machine.getTree();
        ArrayList<TreeDS> treeLayer = new ArrayList<TreeDS>();
        treeLayer.add(root);
        TreeLayers.add(treeLayer);

        int currlayerindex = 0;
        boolean hasChildren = false;
        do
        {
            System.out.println("=======================");
            hasChildren = false;
            //Go through the current layers in tree layer
            ArrayList<TreeDS> currLayer = TreeLayers.get(currlayerindex);

            ArrayList<TreeDS> nextLayer = new ArrayList<TreeDS>();
            for(TreeDS currTree : currLayer)
            {
                //Expand the tree, and then proceed to place it inside a new arraylist
                for(TreeDS child : currTree.getChildren())
                {
                    nextLayer.add(child);
                    hasChildren = true;
                    System.out.println(child.getValue().showTransition());
                }
            }

            if(!hasChildren)
                break;
            TreeLayers.add(nextLayer);
            currlayerindex++;
        } while(hasChildren);

        System.out.println(TreeLayers);

        numLayers = TreeLayers.size();
        showResults(0);
    }

    public void showResults(int treeLayer)
    {
        if(treeLayer < 0)
            currLayer = 0;
        else if (treeLayer > numLayers - 1)
            currLayer = numLayers - 1;
        else
            currLayer = treeLayer;

        //Remove the previous transitions
        transitionsPanel.removeAll();
        transitionsList.clear();

        if(currLayer == 0)
        {
            //Special case at the start.
            JPanel startPanel = new JPanel();

            JLabel startText = new JLabel("Starting State Here! Click Next Iteration to explore states by step!");
            startPanel.add(startText);
            
            transitionsPanel.add(startPanel);
        }
        else
        {
            //Generate String from current Step
            String currString = "";
            for(int i = 0; i < treeLayer; i++)
                currString += inputList.get(i);

            //Get the children in the chosen layer
            ArrayList<TreeDS> currLayerTrees = TreeLayers.get(currLayer);
            
            //Go to the specified layer in the tree, if possible
            for(TreeDS currTree : currLayerTrees)
            {
                TransitionPanel newPanel;
                if(currLayer == 1)
                    newPanel = new TransitionPanel(currTree, currString, true, initialStateName);
                else
                    newPanel = new TransitionPanel(currTree, currString, false, initialStateName);
                
                transitionsList.add(newPanel);
            }

            //Display the transitions
            for(TransitionPanel currPanel : transitionsList)
                transitionsPanel.add(currPanel);
        }
        
        transitionsPanel.revalidate();
        transitionsPanel.repaint();
        setVisible(true);
    }
}
