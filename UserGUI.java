import javax.swing.JFrame;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UserGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    private User user;
    private Group allOtherUsers;

    public UserGUI(User user, Group group) {
        this.user = user;
        this.allOtherUsers = group;
        init();
        user.setWindow(this);
    }

    //important components of frame
    private JTextField userIDEntry;
    private JTextField messageEntry;
    private JLabel messageLabel;
    private JTextArea messageView;
    private JTextArea followView;
    private JButton followButton;
    private JButton postButton;

    private void followButtonPress() {
        if(!userIDEntry.getText().equals("") && !userIDEntry.getText().equals("User ID")) {
            GetUserByIDVisitor vis = new GetUserByIDVisitor(userIDEntry.getText());
            allOtherUsers.accept(vis);
            if (vis.getTarget() != null) {
                user.nowFollows(vis.getTarget());
            }
            update();
        }
        userIDEntry.setText("");
    }

    private void postButtonPress() {
        if(!messageEntry.getText().equals("") && !messageEntry.getText().equals("Tweet Message")) {
            user.sendMessage(messageEntry.getText());
        }
        update();
        messageEntry.setText("");
    }

    public void update() {
        messageView.setText(String.join("\n", user.getMessages()));
        StringBuilder result = new StringBuilder();
        for(User U : user.getFollowing()) {
            result.append(U.getID() + "\n");
        }
        followView.setText(result.toString());
        messageLabel.setText("Messages: (Last Updated: " + user.getLastUpdateTime() + ")");
    }

    //all further methods initialize the frame
    private void init() {
        setTitle(user.getID() +"'s Mini-Twitter (Created: " + user.getCreationTime() + ")");
        setSize(500, 400);
        setLocationRelativeTo(null);

        JPanel framePanel = new JPanel();
        framePanel.setLayout(new GridLayout(2,0, 5, 5));

        framePanel.add(initTopPanel());
        framePanel.add(initBottomPanel());

        add(framePanel);
        update();
    }

    private JPanel initTopPanel() {
        JPanel TopPanel = new JPanel();
        TopPanel.setLayout(new BorderLayout());

        JPanel TopBar = new JPanel();
        TopBar.setLayout(new GridLayout(0, 2, 10, 10));
        userIDEntry = new JTextField("User ID");
        followButton = new JButton("Follow User");
        followButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                followButtonPress();
            }
        });

        TopBar.add(userIDEntry);
        TopBar.add(followButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        JLabel followLabel = new JLabel("Following:");
        centerPanel.add(followLabel, BorderLayout.NORTH);
        followView = new JTextArea("");
        followView.setEditable(false);
        centerPanel.add(new JScrollPane(followView), BorderLayout.CENTER);

        TopPanel.add(TopBar, BorderLayout.NORTH);
        TopPanel.add(centerPanel, BorderLayout.CENTER);
        return TopPanel;
    }

    private JPanel initBottomPanel() {
        JPanel BottomPanel = new JPanel();
        BottomPanel.setLayout(new BorderLayout());

        JPanel TopBar = new JPanel();
        TopBar.setLayout(new GridLayout(0, 2, 10, 10));
        messageEntry = new JTextField("Tweet Message");
        postButton = new JButton("Post Tweet");
        postButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                postButtonPress();
            }
        });
        TopBar.add(messageEntry);
        TopBar.add(postButton);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout());
        messageLabel = new JLabel("Messages: (Last Updated: " + user.getLastUpdateTime() + ")");
        centerPanel.add(messageLabel, BorderLayout.NORTH);
        messageView = new JTextArea("");
        messageView.setEditable(false);
        centerPanel.add(new JScrollPane(messageView), BorderLayout.CENTER);

        BottomPanel.add(TopBar, BorderLayout.NORTH);
        BottomPanel.add(centerPanel, BorderLayout.CENTER);
        return BottomPanel;
    }
}