import java.awt.*;
import java.awt.event.ActionEvent;
import javax.swing.*;
import java.awt.event.ActionListener;
import java.sql.*;
public class Gui implements ActionListener {
    private JButton go=new JButton("GO!");
    private JButton clear=new JButton("CLEAR");
    static private JTextArea inputField = new JTextArea(10,20);
    private JFrame frame = new JFrame("Interface");
    private JPanel pane1 = new JPanel();
    private JPanel pane2 = new JPanel();
    static private JTextArea result=new JTextArea(10,10);
    JScrollPane text2=new JScrollPane(result);
    public class getconn {
        Connection getConn() {
            String driver = "com.mysql.cj.jdbc.Driver";
            String url = "jdbc:mysql://localhost:3306/coursework"+"?serverTimezone=GMT%2B8";
            String username = "root";
            String password = "fzx19980510";
            Connection conn = null;
            try {
                Class.forName(driver); 
                conn = (Connection) DriverManager.getConnection(url, username, password);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return conn;
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == go) {
            if (inputField.getText().equals("")) {
                return;
            }
            getconn c = new getconn();
            Connection conn = c.getConn();
            String sql = inputField.getText();
            PreparedStatement pstmt;
            try {
                pstmt = (PreparedStatement) conn.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery();
                ResultSetMetaData rsmd = rs.getMetaData();
                int col = rsmd.getColumnCount();
                /*for (int i = 1; i <= col; i++){
                    result.setText(result.getText()+rsmd.getColumnName(i)+"  ");
                    if(i==col)
                    result.setText(result.getText()+"\n");
                }*/
                while (rs.next()) {
                    for (int i = 1; i <= col; i++) {
                            result.setText(result.getText() + rsmd.getColumnName(i)+" = "+rs.getString(i) + " | ");
                            if (i == col)
                                result.setText(result.getText() + "\n"+"\n");
                    }
                }
            } catch (SQLException E) {
                E.printStackTrace();
                result.setText(E.getLocalizedMessage());
            }
        }
        if(e.getSource() ==clear){
            inputField.setText("");
            result.setText("");
        }
    }
    public void startFrame(){
        go.addActionListener(this);
        clear.addActionListener(this);
        frame.setLayout(new GridLayout(1,2));
        pane1.setLayout(new GridLayout(2,1));
        pane2.setLayout(new GridLayout(1,2));
        frame.add(text2);
        pane1.add(inputField);
        pane2.add(go);
        pane2.add(clear);
        pane1.add(pane2);
        frame.add(pane1);
        frame.setSize(600, 400);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
    public static void main(String[] args) {
        inputField.setLineWrap(true);
        inputField.setWrapStyleWord(true);
        result.setLineWrap(true);
        result.setWrapStyleWord(true);
        result.setBackground(new Color(0,0,0));
        result.setForeground(Color.WHITE);
        Gui a=new Gui();
        a.startFrame();
    }
}
