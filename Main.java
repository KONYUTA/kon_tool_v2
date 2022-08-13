import kon.lib.coord.*;
import kon.lib.col.*;
import kon.lib.row.*;
import kon.lib.debug.*;
import mains.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
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
         ArrayList<JButton> btns2 = new ArrayList<>();

         btns.add(new JButton("周辺地物の有無"));
         btns.add(new JButton("列データの結合"));
         btns.add(new JButton("属性の比較"));
         for(JButton btn:btns){
             btn.addActionListener(this);
             p.add(btn);
         }

         JPanel p2 = new JPanel();
         btns2.add(new JButton("マニュアル"));
         for(JButton btn:btns2){
             btn.addActionListener(this);
             p2.add(btn);
         }
         getContentPane().add(p, BorderLayout.CENTER);
         getContentPane().add(p2, BorderLayout.SOUTH);
     }

     @Override
     public void actionPerformed(ActionEvent e){
         String cmdName = e.getActionCommand();
         String[] params = new String[1];
         String write_file_path;
         JLabel label;
         JFileChooser filechooser = new JFileChooser();
         int selected;

         switch(cmdName){
             case "周辺地物の有無":
                 filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                 filechooser.setDialogTitle("事故座標データを選択してください。");
                 selected = filechooser.showOpenDialog(this);
                 File jiko_file = filechooser.getSelectedFile();
                 filechooser.setDialogTitle("対照地物のデータを選択してください");
                 selected = filechooser.showOpenDialog(this);
                 File point_file= filechooser.getSelectedFile();
                 int[] col_num = {4,5};
                 write_file_path="data/result/searchFeature/result.txt";
                 CoordController.searchFeature(jiko_file.getPath(), point_file.getPath(), write_file_path, 1000d, col_num, col_num);
                 label = new JLabel("下記ファイルに保存しました(´･ω･`)\n"+write_file_path);
                 break;

            case "列データの結合":
                 write_file_path = "data/result/makeCSV/result.txt";
                 filechooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                 filechooser.setDialogTitle("対象列データ群が保存されたフォルダを選択してください。(´･ω･`)");
                 selected = filechooser.showOpenDialog(this);
                 File col_dir = filechooser.getSelectedFile();
                 ColController.makeCSV_from_dir(col_dir.getPath(), write_file_path);
                 label = new JLabel("下記ファイルに保存しましたよ(´･ω･`)\n"+write_file_path);
                 break;
            case "属性の比較":
                 write_file_path = "data/result/others/attributeDiff";
                 filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                 filechooser.setDialogTitle("一つ目のコンテクスト表を選択してください");
                 selected = filechooser.showOpenDialog(this);
                 File context1= filechooser.getSelectedFile();
                 filechooser.setDialogTitle("二つ目のコンテクスト表を選択してください");
                 selected = filechooser.showOpenDialog(this);
                 File context2= filechooser.getSelectedFile();
                 RowController.headerDiff(context1.getPath(), context2.getPath(), write_file_path);
                 label = new JLabel("下記ファイルに保存しました(´･ω･`)\n"+write_file_path);
                 break;

            case "マニュアル":
                 try{
                     ProcessBuilder psbuilder = new ProcessBuilder("open", "./readme.md");
                     Process ps = psbuilder.start();
                     label = new JLabel("マニュアルを開くよ(´･ω･`)");
                 }catch(IOException e2){
                     label = new JLabel("マニュアルの取得に失敗しました(´･ω･`)");
                     e2.printStackTrace();
                 }
                 break;
            default:
                 label = new JLabel("ボタンが設定されていません。\nボタン名: "+cmdName);
         }
         JOptionPane.showMessageDialog(this, label);
     }
 }
