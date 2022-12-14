import kon.lib.*;
import kon.lib.coord.*;
import kon.lib.col.*;
import kon.lib.row.*;
import kon.lib.debug.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.File;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Date;
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
        btns.add(new JButton("対応表による変換(1列)"));
        btns.add(new JButton("座標データの60進数への変換"));
        btns.add(new JButton("道路幅員"));
        btns.add(new JButton("交差点"));
        btns.add(new JButton("中央分離帯"));
        btns.add(new JButton("歩道"));
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
        JLabel input_form;
        JFileChooser filechooser = new JFileChooser();
        int selected;
        int col_num;
        File context;

        switch(cmdName){
            case "周辺地物の有無":
                filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                filechooser.setDialogTitle("事故座標データを選択してください。");
                selected = filechooser.showOpenDialog(this);
                File jiko_file = filechooser.getSelectedFile();
                filechooser.setDialogTitle("対照地物のデータを選択してください");
                selected = filechooser.showOpenDialog(this);
                File point_file= filechooser.getSelectedFile();
                int[] col_nums = {4,5};
                write_file_path="data/result/searchFeature/result.txt";
                CoordController.searchFeature(jiko_file.getPath(), point_file.getPath(), write_file_path, 10, col_nums, col_nums);
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
                write_file_path = "data/result/others/attributeDiff"+new Date().toString();
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
            case "対応表による変換(1列)":
                write_file_path = "data/result/translate/correspendence.txt";
                filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                filechooser.setDialogTitle("元データのファイルを選択してください");
                selected = filechooser.showOpenDialog(this);
                context = filechooser.getSelectedFile();
                col_num = Integer.parseInt(JOptionPane.showInputDialog(this, "列番号(0始まりで半角数字)"));
                filechooser.setDialogTitle("対応表のファイルを選択してください");
                selected = filechooser.showOpenDialog(this);
                File correspendence = filechooser.getSelectedFile();
                if(ColController.translate(context.getPath(), correspendence.getPath(), col_num, write_file_path)){
                    label = new JLabel("下記ファイルに保存しましたよ(´･ω･`)\n"+write_file_path);
                }else{
                    label = new JLabel("以上終了だよ(´･ω･`)");
                }
                break;
            case "座標データの60進数への変換":
                write_file_path = "data/result/60/result.in";
                filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                filechooser.setDialogTitle("10進数座標データのファイルを選択してください。");
                selected = filechooser.showOpenDialog(this);
                context = filechooser.getSelectedFile();
                ToInFile.translate(context.getPath(), write_file_path);
                label = new JLabel("下記ファイルに保存しましたよ(´･ω･`)\n"+write_file_path);
                break;
            case "道路幅員":
                write_file_path = "data/result/color/result.txt";
                filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                filechooser.setDialogTitle("bgrのカラーコードのファイルのパスを選択してください");
                selected = filechooser.showOpenDialog(this);
                context = filechooser.getSelectedFile();
                CoordController.roadColor(context.getPath(), write_file_path);
                label = new JLabel("下記ファイルに保存しましたよ(´･ω･`)\n"+write_file_path);
                break;
            case "交差点":
                write_file_path = "data/result/color/result.txt";
                filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                filechooser.setDialogTitle("bgrのカラーコードのファイルのパスを選択してください");
                selected = filechooser.showOpenDialog(this);
                context = filechooser.getSelectedFile();
                CoordController.crossColor(context.getPath(), write_file_path);
                label = new JLabel("下記ファイルに保存しましたよ(´･ω･`)\n"+write_file_path);
                break;
            case "中央分離帯":
                write_file_path = "data/result/color/result.txt";
                filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                filechooser.setDialogTitle("bgrのカラーコードのファイルのパスを選択してください");
                selected = filechooser.showOpenDialog(this);
                context = filechooser.getSelectedFile();
                CoordController.bunritaiColor(context.getPath(), write_file_path);
                label = new JLabel("下記ファイルに保存しましたよ(´･ω･`)\n"+write_file_path);
                break;
            case "歩道":
                write_file_path = "data/result/color/result.txt";
                filechooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                filechooser.setDialogTitle("bgrのカラーコードのファイルのパスを選択してください");
                selected = filechooser.showOpenDialog(this);
                context = filechooser.getSelectedFile();
                CoordController.hodouColor(context.getPath(), write_file_path);
                label = new JLabel("下記ファイルに保存しましたよ(´･ω･`)\n"+write_file_path);
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
