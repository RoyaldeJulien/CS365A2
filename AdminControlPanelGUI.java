import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.ListSelectionModel;
import javax.swing.JButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class AdminControlPanelGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private static AdminControlPanelGUI instance = null;
    private static Group userTree;

    private AdminControlPanelGUI() {}

    public static AdminControlPanelGUI getInstance() {
        if (instance == null) {
            synchronized(AdminControlPanelGUI.class) {
                if (instance == null) {
                    instance = new AdminControlPanelGUI();
                    userTree = new Group("Root");
                    initFrame();
                }
            }
        }
        return instance;
    }

    //important components of frame
    private static JList<String> treeView;
    private static JTextField userIDEntry;
    private static JTextField groupIDEntry;
    private static JLabel showResults;
    private static JButton addUserButton;
    private static JButton addGroupButton;
    private static JButton userViewButton;
    private static JButton showUserTotalButton;
    private static JButton showGroupTotalButton;
    private static JButton showMessagesTotalButton;
    private static JButton showPositivePercentageButton;
    private static JButton verifyUsersButton;
    private static JButton FindLastUpdatedUserButton;

    private static void addUserButtonPress() {
        if(treeView.getSelectedValue() != null && !userIDEntry.getText().equals("") && !userIDEntry.getText().equals("User ID")) {
            Group selected = new Group(treeView.getSelectedValue().replaceAll("^\\s+", ""));
            User Entry = new User(userIDEntry.getText());
            //if the usertree already contains something with the given ID, do nothing
            ContainsVisitor conV = new ContainsVisitor(Entry);
            userTree.accept(conV);
            if(!conV.found()) {
                userTree.accept(new addToVisitor(Entry, selected));
            }
            //update the treeView
            treeView.setListData(userTree.toStringArray());
            userIDEntry.setText("");
        }
    }

    private static void addGroupButtonPress() {
        if(treeView.getSelectedValue() != null && !groupIDEntry.getText().equals("") && !groupIDEntry.getText().equals("Group ID")) {
            Group selected = new Group(treeView.getSelectedValue().replaceAll("^\\s+", ""));
            Group Entry = new Group(groupIDEntry.getText());
            //if the usertree already contains something with the given ID, do nothing
            ContainsVisitor conV = new ContainsVisitor(Entry);
            userTree.accept(conV);
            if(!conV.found()) {
                userTree.accept(new addToVisitor(Entry, selected));
            }
            //update the treeView
            treeView.setListData(userTree.toStringArray());
            groupIDEntry.setText("");
        }
    }

    private static void addUserViewButtonPress() {
        String selectedName = treeView.getSelectedValue().replaceAll("^\\s+", "");
        GetUserByIDVisitor vis = new GetUserByIDVisitor(selectedName);
        userTree.accept(vis);
        if (vis.getTarget() != null) {
            UserGUI window = new UserGUI(vis.getTarget(), userTree);
            window.setVisible(true);
            vis.getTarget().setWindow(window);
        }
    }

    private static void showUserTotalButtonPress() {
        UserTotalVisitor vis = new UserTotalVisitor();
        userTree.accept(vis);
        showResults.setText("User Total: " + vis.getTotal());
    }

    private static void showGroupTotalButtonPress() {
        GroupTotalVisitor vis = new GroupTotalVisitor();
        userTree.accept(vis);
        showResults.setText("Group Total: " + vis.getTotal());
    }

    private static void showMessagesTotalButtonPress() {
        MessagesTotalVisitor vis = new MessagesTotalVisitor();
        userTree.accept(vis);
        showResults.setText("Message Total: " + vis.getTotal());
    }

    private static void showPositivePercentageButtonPress() {
        PositivePercentageTotalVisitor vis = new PositivePercentageTotalVisitor();
        userTree.accept(vis);
        showResults.setText("Pos. Perc.: " + vis.getPercentage()*100 + "%");
    }

    private static void verifyUsersButtonPress() {
    	VerifyVisitor vis = new VerifyVisitor();
    	userTree.accept(vis);
    	showResults.setText("Bad IDs: { " + vis.getResults() + "}");
    }

    private static void FindLastUpdatedUserButtonPress() {
    	FindLastUpdatedUserVisitor vis = new FindLastUpdatedUserVisitor();
    	userTree.accept(vis);
    	showResults.setText("Last updated: " + vis.getResults().getID());
    }

    //all further methods initialize the frame
    private static void initFrame() {
        instance.setTitle("Mini-Twitter Admin View");
        instance.setSize(600, 400);
        instance.setLocationRelativeTo(null);
        instance.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel framePanel = new JPanel();
        framePanel.setLayout(new GridLayout(0,2, 10, 10));
        framePanel.add(initLeftPanel());
        framePanel.add(initRightPanel());

        instance.add(framePanel);
    }

    private static JPanel initLeftPanel() {
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());

        treeView = new JList<>();
        treeView.setListData(userTree.toStringArray());
        treeView.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
        leftPanel.add(new JLabel("Tree View:"), BorderLayout.NORTH);
        leftPanel.add(new JScrollPane(treeView), BorderLayout.CENTER);

        return leftPanel;
    }

    private static JPanel initRightPanel() {
        JPanel rightPanel = new JPanel();
        rightPanel.setLayout(new GridLayout(3,0,10,10));

        rightPanel.add(initUpperRightPanel());
        rightPanel.add(initCenterRightPanel());
        rightPanel.add(initLowerRightPanel());

        return rightPanel;
    }

    private static JPanel initUpperRightPanel() {
        JPanel upperRightPanel = new JPanel();
        upperRightPanel.setLayout(new GridLayout(2,2, 10, 10));

        userIDEntry = new JTextField("User ID");
        addUserButton = new JButton("Add User");
        addUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                addUserButtonPress();
            }
        });

        groupIDEntry = new JTextField("Group ID");
        addGroupButton = new JButton("Add Group");
        addGroupButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                addGroupButtonPress();
            }
        });

        upperRightPanel.add(userIDEntry);
        upperRightPanel.add(addUserButton);
        upperRightPanel.add(groupIDEntry);
        upperRightPanel.add(addGroupButton);

        return upperRightPanel;
    }

    private static JPanel initCenterRightPanel() {
        JPanel centerRightPanel = new JPanel();
        centerRightPanel.setLayout(new GridLayout(2,1, 5, 5));

        userViewButton = new JButton("Open User View");
        userViewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addUserViewButtonPress();
            }
        });
        showResults = new JLabel("Sample Text");

        centerRightPanel.add(userViewButton);
        centerRightPanel.add(showResults);

        return centerRightPanel;
    }

    private static JPanel initLowerRightPanel() {
        JPanel lowerRightPanel = new JPanel();
        lowerRightPanel.setLayout(new GridLayout(3,2, 10,10));

        showUserTotalButton = new JButton("Show User Total");
        showUserTotalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showUserTotalButtonPress();
            }
        });

        showGroupTotalButton = new JButton("Show Group Total");
        showGroupTotalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showGroupTotalButtonPress();
            }
        });

        showMessagesTotalButton = new JButton("Show Total Messages");
        showMessagesTotalButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showMessagesTotalButtonPress();
            }
        });

        showPositivePercentageButton = new JButton("Show Positive Percentage");
        showPositivePercentageButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showPositivePercentageButtonPress();
            }
        });

        verifyUsersButton = new JButton("Verify Users");
        verifyUsersButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	verifyUsersButtonPress();
            }
        });

        FindLastUpdatedUserButton = new JButton("Find Last Updated User");
        FindLastUpdatedUserButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            	FindLastUpdatedUserButtonPress();
            }
        });


        lowerRightPanel.add(showUserTotalButton);
        lowerRightPanel.add(showGroupTotalButton);
        lowerRightPanel.add(showMessagesTotalButton);
        lowerRightPanel.add(showPositivePercentageButton);
        lowerRightPanel.add(verifyUsersButton);
        lowerRightPanel.add(FindLastUpdatedUserButton);

        return lowerRightPanel;
    }
}
