package kon.lib;
import kon.lib.coord.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//
public class ToInFile{
    /**
     * 「座標データの60進数への変換」が発火した時に実行される
     * 10進数座標データを60進数度分秒に変換してファイル出力する。
     * @param input_file 10進数座標データのファイルパス
     * @param output_file 出力するファイルのパス
     */
    public static void translate(String input_file, String output_file){
        try{
            int[] col_nums = {0,1};
            CoordDouble jiko = new CoordDouble(input_file, col_nums);
            jiko.readData(",");
            jiko.show();
            FileWriter jiko_out = new FileWriter(output_file);
            PrintWriter jiko_out_write = new PrintWriter(new BufferedWriter(jiko_out));
            for(double[] xy:jiko.coords){
                jiko_out_write.println(toDMS(xy[0])+" "+toDMS(xy[1]));
            }
            jiko_out_write.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static double toDMS(double input_f){
        double deg = input_f;
        double degree = (int)deg;
        double minute = (int)((deg-degree)*60);
        double second = ((deg-degree)*60-minute)*60;
        double dms = degree*10000 + minute*100 + second;
        return dms;
    }
}
