package kon.lib.row;
//
import kon.lib.debug.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.HashMap;
import java.util.Map;
import java.lang.StringBuilder;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
/**
 * 行データを扱うためのクラス
 * @author kon
 * @version 2.0
 */
public class RowController{
    /**
     * 「属性の比較」ボタンが発火した時に実行される。二つの表の属性の比較結果をファイル出力する。
     * @param csv_path1 一つ目のコンマ区切りデータのファイルパス
     * @param csv_path2 二つ目のコンマ区切りデータのファイルパス/
     * @param result_path 結果を保存するファイルのパス
     */
    public static boolean headerDiff(String csv_path1, String csv_path2, String result_path){
        LinkedHashMap<String, Integer> diff = colomnDiff(csv_path1, csv_path2);
        StringBuilder sb1 = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        StringBuilder sb3 = new StringBuilder();
        for(HashMap.Entry<String, Integer> entry : diff.entrySet()){
            switch(entry.getValue()){
                case 1:
                    sb1.append(entry.getKey()+",");
                    break;
                case 2:
                    sb2.append(entry.getKey()+",");
                    break;
                case 3:
                    sb3.append(entry.getKey()+",");
                    break;
                default:
                    System.out.println("不正な値になったので止めます。(´･ω･`)");
                    return false;
            }
        }
        try{
            FileWriter fwriter = new FileWriter(result_path);
            BufferedWriter bwriter = new BufferedWriter(fwriter);
            PrintWriter pwriter = new PrintWriter(bwriter);
            pwriter.println(csv_path1+"のみ保有している属性");
            pwriter.println(sb1);
            pwriter.print("\n");
            pwriter.println(csv_path2+"のみ保有している属性");
            pwriter.println(sb2);
            pwriter.print("\n");
            pwriter.println("どちらも保有している属性");
            pwriter.println(sb3);
            pwriter.close();
        }catch(IOException e){
            System.out.println("ファイル書き込みに失敗しました。(´･ω･`)");
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 二つのコンマ区切りデータのカラムの差分を取得する。
     * @param csv_path1 一つ目のコンマ区切りデータのファイルパス
     * @param csv_path2 二つ目のコンマ区切りデータのファイルパス
     * @return HashMapで返す。カラム名をキーとし、バリューが1なら一つ目の表にしかない。2なら二つ目の表にしかない。3ならどちらにも存在する
     */
    public static LinkedHashMap<String, Integer> colomnDiff(String csv_path1, String csv_path2){
        LinkedHashMap<String, Integer> result = new LinkedHashMap<>();
        int result_num;
        Row row1 = new Row(csv_path1, 0);
        Row row2 = new Row(csv_path2, 0);
        row1.readData(",");
        row2.readData(",");
        for(String header:row1.coords){
            result_num = 1;
            for(String i:row2.coords){
                if(header.equals(i)){
                    result_num = 3;
                    break;
                }
            }
            result.put(header, result_num);
        }
        for(String header:row2.coords){
            result_num = 2;
            for(String i:row1.coords){
                if(header.equals(i)){
                    result_num = 0;
                    break;
                }
            }
            if(result_num == 2){
                result.put(header, result_num);
            }
        }
        return result;
    }
}
