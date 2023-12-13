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
    private JButton validateUsrsBtn;
    private JButton lastUpdateButton;
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

        validateUsrsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                validateIds();
            }
        });
        lastUpdateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (users.size() == 0) {
                    System.out.println("No Users created!");
                }
                else {
                    getLastUpdated();
                }
            }
        });

        frame.pack();
    }
    private void getLastUpdated(){
        User lastUpdated=users.get(0);
        for(int i=0;i<users.size();i++){
            if(users.get(i).getLastUpdate()>lastUpdated.getLastUpdate()){
                lastUpdated=users.get(i);
            }
        }
        System.out.println("Last user updated: "+lastUpdated.getUID());
        System.out.println("At: "+lastUpdated.getLastUpdate());
    }

    private void validateIds(){
        List<User> usersNotValid=new ArrayList<User>();
        List<User> groupsNotValid=new ArrayList<User>();
        for(int i=0;i< users.size();i++){
            String uid=users.get(i).getUID();
            for(int j=0;j< users.size();j++){
                if(j==i){
                    continue;
                }
                if(users.get(j).getUID().equals(uid)){
                    usersNotValid.add(users.get(j));
                }
            }
            for (char c : uid.toCharArray()) {
                if (Character.isWhitespace(c)) {
                    usersNotValid.add(users.get(i));
                }
            }
        }
        for(int i=0;i< groups.size();i++){
            String uid=groups.get(i).getUID();
            for(int j=0;j< groups.size();j++){
                if(j==i){
                    continue;
                }
                if(groups.get(j).getUID().equals(uid)){
                    groupsNotValid.add(groups.get(j));
                }
            }
            for (char c : uid.toCharArray()) {
                if (Character.isWhitespace(c)) {
                    groupsNotValid.add(groups.get(i));
                }
            }
        }
        if(usersNotValid.size()==0){
            System.out.println("All user ids valid!");
        }
        else{
            System.out.println("User ids not valid:");
            for(int i=0;i<usersNotValid.size();i++){
                System.out.println(usersNotValid.get(i).getUID());
            }
        }
        if(groupsNotValid.size()==0){
            System.out.println("All group ids valid!");
        }
        else{
            System.out.println("Group ids not valid:");
            for(int i=0;i<groupsNotValid.size();i++){
                System.out.println(groupsNotValid.get(i).getUID());
            }
        }
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
