import kon.lib.coord.*;
import kon.lib.debug.*;
import mains.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.File;
/**
 * メインのプログラムです。
 * @author kon
 * @version 2.0
 */
 public class Main extends JFrame implements ActionListener{
     public static String message;

     /**
      * エントリーポイント
      * @param args
      */
     public static void main(String[] args){
         Main frame = new Main("kon_tool_v2");
         frame.setVisible(true);
     }

     /**
      * コンストラクタ
      * @param title ウィンドウのタイトル
      */
     public Main(String title){
         setTitle(title);
         setBounds(100,100,600,400);
         setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
         JPanel p = new JPanel();
         JPanel debug = new JPanel();
         ArrayList<JButton> btns = new ArrayList<>();
         ArrayList<JButton> btns_debug = new ArrayList<>();

         btns.add(new JButton("周辺地物の有無"));
         for(JButton btn:btns){
             btn.addActionListener(this);
             p.add(btn);
         }

         JPanel p2 = new JPanel();
         p2.add(new JButton("test2"));
         getContentPane().add(p, BorderLayout.CENTER);
         getContentPane().add(p2, BorderLayout.SOUTH);
     }

     @Override
     public void actionPerformed(ActionEvent e){
         String cmdName = e.getActionCommand();
         String[] params = new String[1];
         JLabel label;
         JFileChooser filechooser = new JFileChooser();

         switch(cmdName){
             case "周辺地物の有無":
                 filechooser.setDialogTitle("事故座標データを選択してください。");
                 int selected = filechooser.showOpenDialog(this);
                 File jiko_file = filechooser.getSelectedFile();
                 filechooser.setDialogTitle("対照地物のデータを選択してください");
                 selected = filechooser.showOpenDialog(this);
                 File point_file= filechooser.getSelectedFile();
                 int[] col_num = {4,5};
                 CoordController.searchFeature(jiko_file.getPath(), point_file.getPath(), "data/result/searchFeature/result.txt", 10000d, col_num, col_num);
                 label = new JLabel(point_file.getPath());
                 break;


            default:
                 label = new JLabel("ボタンが設定されていません。\nボタン名: "+cmdName);
         }
         JOptionPane.showMessageDialog(this, label);
     }
 }
