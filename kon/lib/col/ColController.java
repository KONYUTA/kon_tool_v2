package kon.lib.col;
import kon.lib.table.*;
import kon.lib.debug.*;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.lang.StringBuilder;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
/**
 * 列データに対する操作
 * @author kon
 * @version 2.0
 */
public class ColController{
    /**
     * 「列データの結合」ボタンが発火したときに実行される。指定されたディレクトリ内の全ての列データからコンマ区切りテキストファイルを作成。
     * 入力フォルダ内のファイルは全て数字.txtとすること。
     * なお数字が一桁の場合は最初に0をつけないと順番が崩れる。
     * 例：08.txt
     * @param input_dir 入力の列データが保存されたフォルダのパス
     * @param output_file 結果を保存するファイルのパス*/
    public static boolean makeCSV_from_dir(String input_dir, String output_file){
        File in_dir = new File(input_dir);
        File[] input_files = in_dir.listFiles();
        ArrayList<Col> cols = new ArrayList<Col>();
        Arrays.sort(input_files);

        for(File f:input_files){
            if(f.getName().equals(".DS_Store")){continue;}
            Col col = new Col(input_dir+"/"+f.getName(), 0);
            col.readData(" ");
            cols.add(col);
        }

        if(!makeCSV(cols, output_file)){
            return false;
        }

        return true;
    }
    /**
     * 複数の列データからコンマ区切りテキストデータを作るプログラム。
     * @param cols 列データのArrayList
     * @param result_file 出力ファイルのパス
     */
    public static boolean makeCSV(ArrayList<Col> cols, String result_file){
        try{
            FileWriter out = new FileWriter(result_file);
            BufferedWriter out_buffer = new BufferedWriter(out);
            PrintWriter out_print = new PrintWriter(out_buffer);
            for(int i=0; i<cols.get(0).coords.size(); i++){
                StringBuilder row = new StringBuilder();
                for(Col col:cols){
                    row.append(col.coords.get(i)+",");
                }
                row.setLength(row.length()-1);
                out_print.println(row);
            }
            out_print.close();
        }catch(IOException e){
            System.out.println("I/Oのエラーだよ(´･ω･`)");
            e.printStackTrace();
        }
        return true;
    }
    /**
     * 「対応表による変換(1列)」ボタンが発火した時に実行される。指定されたファイルの指定された列に対して、対応表による変換を行う
     * @param input_file 対象のコンテクスト表のファイル
     * @param correspendence_file 対応表のファイル
     * @param col_number コンテクスト表の列番号
     * @param result_file 出力先のパス
     */
    public static boolean translate(String input_file, String correspendence_file, int col_number, String result_file){
        Col col = new Col(input_file, col_number);
        Table table = new Table(correspendence_file);
        col.readData(",");
        table.readData("\t");
        Col result = correspendence(col, table);
        result.write(result_file, false);
        return true;
    }
    /**
     * 対応づけする
     * @param col コンテクスト表の列データ
     * @param table 対応表データ
     * @@return 変換後の列データ
     * */
    static Col correspendence(Col col, Table table){
        Col result = new Col("", 0);
        for(String i:col.coords){
            for(HashMap.Entry<String, String> map:table.coords.entrySet()){
                if(i.equals(map.getKey())){
                    result.coords.add(map.getValue());
                }
            }
        }
        return result;
    }

    public static boolean route2linear(String input_file, String linear_path, String output_file){
        Col col = new Col(input_file, 0);
        Col linear = new Col(linear_path, 0);
        Col result = new Col("", 0);
        col.readData(",");
        linear.readData(",");
        String route;
        for(String i:col.coords){
            System.out.println("test");
            System.out.println(i);
            route = "-1";
            if(i.equals("0")){
                result.coords.add("0");
            } else{
                try{
                    result.coords.add(linear.coords.get(Integer.parseInt(i)));
                }catch(NumberFormatException e){
                    result.coords.add("-1");
                }
            }
        }
        result.write(output_file, false);
        return true;
    }
} 
