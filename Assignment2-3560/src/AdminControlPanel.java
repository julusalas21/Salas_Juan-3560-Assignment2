import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class AdminControlPanel {

    private JPanel rootPanel;
    private DefaultTreeModel template;
    private JTree tree1;

    private DefaultMutableTreeNode selected;
    private DefaultMutableTreeNode addUser;
    private DefaultMutableTreeNode addGroup;

    private List<User> users;
    private List<User> groups;

    private JTextField newUID;
    private JTextField newGID;
    private JButton openUserViewPanelBtn;
    private JButton showUserTotalButton;
    private JButton showGroupTotalButton;
    private JButton showMessagesTotalButton;
    private JButton showPositivePercentageButton;
    private JButton addUserBtn;
    private JButton addGroupBtn;
    private JLabel errorLabel;
    //private JButton helloButton;
    private JLabel twitterLabel;

    private static AdminControlPanel initializedObject=null;
    private JFrame frame;


    //Utilizes Singleton's pattern scheme by only initializing when called and only getting one instance
    public AdminControlPanel() {
        initialize();
        frame.setVisible(true);
    }

    private void initialize(){
        frame = new JFrame("twitter");
        frame.setContentPane(rootPanel);
        frame.setPreferredSize(new Dimension(600,500));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocation(500,500);

        users=new ArrayList<User>();
        groups=new ArrayList<User>();

        ImageIcon icon = new ImageIcon("src/twitterLogo.png");
        frame.setIconImage(icon.getImage());

        //setting up the tree
        CompositeUserGroup rootgroup=new CompositeUserGroup("root");
        DefaultMutableTreeNode root = new DefaultMutableTreeNode(rootgroup);
        template=new DefaultTreeModel(root);
        tree1.setModel(template);

        //action listeners
        addUserBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });

        addGroupBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addGroup();
            }
        });
        openUserViewPanelBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserViewPanel();
            }
        });
        //error label will have a dual use as messages to the user
        //will show analytics from analyticsVisitor
        showUserTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TotalUsers total = new TotalUsers();
                ConcreteVisitor concreteVisitor= new ConcreteVisitor();
                errorLabel.setText(concreteVisitor.visit(total));
            }
        });
        showGroupTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TotalGroups total = new TotalGroups();
                ConcreteVisitor concreteVisitor= new ConcreteVisitor();
                errorLabel.setText(concreteVisitor.visit(total));
            }
        });
        showMessagesTotalButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TotalMessages total = new TotalMessages();
                ConcreteVisitor concreteVisitor= new ConcreteVisitor();
                errorLabel.setText(concreteVisitor.visit(total));
            }
        });
        showPositivePercentageButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TotalPositive total = new TotalPositive();
                ConcreteVisitor concreteVisitor= new ConcreteVisitor();
                errorLabel.setText(concreteVisitor.visit(total));
            }
        });

        frame.pack();
    }
    private void addUser(){
        template=(DefaultTreeModel) tree1.getModel();
        selected=(DefaultMutableTreeNode) tree1.getLastSelectedPathComponent();
        errorLabel.setText("");
        if(selected==null){
            errorLabel.setText("Error: Select a valid node");
        }
        else {
            String userid=newUID.getText().trim();
            if(!userid.equals("")){
                if (selected.getUserObject() instanceof CompositeUserGroup){
                    for(int i=0;i< users.size();i++){
                        if(users.get(i).getUID().equalsIgnoreCase(userid)){
                            errorLabel.setText("Error: not a unique id");
                            return;
                        }
                    }
                    for(int i=0;i< groups.size();i++){
                        if(groups.get(i).getUID().equalsIgnoreCase(userid)){
                            errorLabel.setText("Error: not a unique id");
                            return;
                        }
                    }
                    users.add(new IsolatedUser(userid));
                    addUser=new DefaultMutableTreeNode(users.get(users.size()-1));
                    template.insertNodeInto(addUser,selected,selected.getChildCount());
                    template.reload();
                    errorLabel.setText("");
                }
                else{
                    errorLabel.setText("Error: can only add users to a Group");
                }
            }
            else
            {errorLabel.setText("Error: Enter a valid UserID");}
        }
    }

    private void addGroup(){
        template=(DefaultTreeModel) tree1.getModel();
        selected=(DefaultMutableTreeNode) tree1.getLastSelectedPathComponent();
        errorLabel.setText("");
        if(selected==null){
            errorLabel.setText("Error: select a valid node");
        }
        else {
            String userid=newGID.getText().trim();
            if(!userid.equals("")){
                if (selected.getUserObject() instanceof CompositeUserGroup){
                    for(int i=0;i< users.size();i++){
                        if(users.get(i).getUID().equalsIgnoreCase(userid)){
                            errorLabel.setText("Error: not a unique id");
                            return;
                        }
                    }
                    for(int i=0;i< groups.size();i++){
                        if(groups.get(i).getUID().equalsIgnoreCase(userid)){
                            errorLabel.setText("Error: not a unique id");
                            return;
                        }
                    }
                    groups.add(new CompositeUserGroup(userid));
                    addGroup=new DefaultMutableTreeNode(groups.get(groups.size()-1));
                    template.insertNodeInto(addGroup,selected,selected.getChildCount());
                    template.reload();
                    errorLabel.setText("");
                }
                else{
                    errorLabel.setText("Error: cannot add Group into user");
                }
            }
            else{
                errorLabel.setText("Error: must enter a GroupID");
            }
        }
    }

    //Only way to get instance using Singleton's
    public static AdminControlPanel getInstance(){
        if (initializedObject==null)
        {
            initializedObject=new AdminControlPanel();
        }
        return initializedObject;
    }

    public List<User> getUsers(){
        return users;
    }
    public List<User> getGroups(){
        return groups;
    }
    public void openUserViewPanel(){
        selected=(DefaultMutableTreeNode) tree1.getLastSelectedPathComponent();
        errorLabel.setText("");
        if(selected.getUserObject() instanceof IsolatedUser){
            User user=(IsolatedUser) selected.getUserObject();
            UserViewPanel.getInstance(user);
        }
        else if(selected.getUserObject() instanceof CompositeUserGroup){
            errorLabel.setText("Error: group is not a valid user");
        }
        else {
            errorLabel.setText("Error: choose a valid user");
        }
    }

}
