package kon.lib.coord;
import kon.lib.debug.*;
import java.io.File;
import java.io.IOException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.BufferedWriter;
/**
 * 地点データに対する操作
 * @author kon
 * @version 2.0
 */
public class CoordController{
    /**
     * ボタン「対照地物の有無」が発火した時に実行される
     * 各事故発生地点周辺の地物の存在を調査し、ファイル出力する。
     * @param jiko_file 事故データのファイルパス
     * @param point_file 対照地物のデータのファイルパス
     * @param save_file_path 保存用のファイルのパス
     * @param radius 調査する範囲の半径(単位はメートル)
     * @param col_nums1 事故データの行番号の配列
     * @param col_nums2 事故データの行番号の配列
     */
    public static void searchFeature(String jiko_file, String point_file, String save_file_path, double radius, int[] col_nums1, int[] col_nums2){
        CoordDouble jiko = new CoordDouble(jiko_file, col_nums1);
        CoordDouble point = new CoordDouble(point_file, col_nums2);
        jiko.readData(" ");
        point.readData(" ");
        boolean[] result = CoordController.search(jiko, point, radius);
        try{
            FileWriter filewriter = new FileWriter(save_file_path);
            PrintWriter printwriter = new PrintWriter(new BufferedWriter(filewriter));

            for(boolean i:result){
                if(i){
                    printwriter.println(1);
                }else{
                    printwriter.println(0);
                }
            }
            printwriter.close();
        }catch(IOException e){System.out.println("ファイル書き込みに失敗しました。");}
    }

    /**
     * 事故発生地点周辺に指定の地物が存在するかどうかを調べる
     * @param jiko 事故地点の座標データ単位はメートル)
     * @param point 対象の地物の座標データ(単位はメートル)
     * @param radius 調査する範囲の半径(単位はメートル)
     * @return 各座標周辺の地物の有無を二値で表したboolean配列
     */
    public static boolean[] search(CoordDouble jiko, CoordDouble point, double radius){
        boolean[] result = new boolean[jiko.coords.size()];
        double[] xy;
        for(int i=0; i<result.length; i++){
            xy = jiko.coords.get(i);
            for(double[] xy_in:point.coords){
                if(isInCircle(xy, xy_in, radius)){
                    result[i] = true;
                    break;
                }
            }
        }
        return result;
    }

    /**
     * 二つの入力座標の距離がradius未満ならtrue、そうでなければfalse
     * @param center 事故地点の座標(単位はメートル)
     * @param dot 対象地物の座標(単位はメートル)
     * @param radius 閾値となる距離(単位はメートル)
     * @return 距離の計算結果
     */
    public static boolean isInCircle(double[] center, double[] dot, double radius){
        double derection=(center[0]-dot[0])*(center[0]-dot[0])+(center[1]-dot[1])*(center[1]-dot[1]);
        double diameter = radius*radius;
        if(derection < diameter){
            return true;
        }else{
            return false;
        }
    }
}
