package kon.lib.col;
import kon.lib.debug.*;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.lang.StringBuilder;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.util.ArrayList;
import java.util.Arrays;
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
} 
