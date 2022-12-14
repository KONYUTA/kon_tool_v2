package kon.lib.coord;
import kon.lib.debug.*;
import kon.lib.col.*;
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
     * ボタン「同一地点での事故検索」が発火した際に実行される。第一引数のファイル内の各事故の座標と路線をもとに、同じ路線の周辺一定範囲で発生した事故を第二引数のファイル内の事故から検索し、最初にヒットしたものをリストとして返す。
     * @param file1 各事故の座標と路線が記録されたファイルパス
     * @param file2 検索対照の座標と路線が記録されたファイルパス
     * @param save_file_path 保存するファイルのパス
     * @param radius 検索する範囲
     * @param col_nums1 file1内で座標と路線が記録された列番号[x, y, 路線]
     * @param col_nums2 file2内で座標と路線が記録された列番号[x, y, 路線]
     */
    public static void nearbyAccident(String file1, String file2, String save_file_path, double radius, int[] col_nums1, int[] col_nums2){
        int[]col_coord_1 = {col_nums1[0], col_nums1[1]};
        int[]col_coord_2 = {col_nums2[0], col_nums2[1]};
        CoordDouble jiko1 = new CoordDouble(file1, col_coord_1);
        CoordDouble jiko2 = new CoordDouble(file2, col_coord_2);
        jiko1.readData(",");
        jiko2.readData(",");
        Col rosexn1 = new Col(file1, col_nums1[2]);
        Col rosexn2 = new Col(file2, col_nums2[2]);
        rosexn1.readData(",");
        rosexn2.readData(",");
        int[] result = search_with_rosexn(jiko1, jiko2, rosexn1, rosexn2, radius);
        try{
            FileWriter filewriter = new FileWriter(save_file_path);
            PrintWriter printwriter = new PrintWriter(new BufferedWriter(filewriter));
            printwriter.println("同じ路線での周囲の人身事故");
            for(int i:result){
                printwriter.println(i);
            }
            printwriter.close();
        }catch(IOException e){System.out.println("ファイル書き込みに失敗しました");}
    }
    /**
     * ボタン「周辺地物の有無」が発火した時に実行される
     * 各事故現場地点周辺の地物の存在を調査し、ファイル出力する。
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
                printwriter.println(i?1:0);
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
     * 事故発生地点の周辺で同一路線の事故が存在するかどうかを調べる
     * @param jiko 事故地点の座標データ単位はメートル)
     * @param point 対象の地物の座標データ(単位はメートル)
     * @param radius 調査する範囲の半径(単位はメートル)
     * @return 各座標周辺の地物の有無を二値で表したboolean配列
     */
    private static int[] search_with_rosexn(CoordDouble jiko, CoordDouble point, Col rosexn1, Col rosexn2, double radius){
        int[] result = new int[jiko.coords.size()];
        double[] xy;
        double[] xy_in;
        for(int i=0; i<result.length; i++){
            result[i] = 0;
            xy = jiko.coords.get(i);
            for(int j=1; j<rosexn2.coords.size(); j++){
                xy_in = point.coords.get(j);
//                if(isInCircle(xy, xy_in, radius)){
//                    if(rosexn1.coords.get(i).equals(rosexn2.coords.get(j))){
//                        System.out.println("j");
//                    }
//                }
                if(isInCircle(xy, xy_in, radius) && rosexn1.coords.get(i).equals(rosexn2.coords.get(j))){
                    result[i] = j;
                    System.out.println(j);
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
        return derection<diameter?true:false;
    }

    /**
     * 色分けする関数を呼び出すやつ。
     * 凡例に基づいて呼び出す関数を変更する必要あり。
     * @param colorcode_file bgrのカラーコードのファイルのパス(コンマ区切りテキスト)
     * @param save_file_path 結果を保存するファイルのパス
     */
    public static void roadColor(String colorcode_file, String save_file_path){
        int[] col_nums = {0,1,2};
        Coord3 color = new Coord3(colorcode_file, col_nums);
        color.readData(",");
        int[] result = CoordController.colorWidth(color);
        try{
            FileWriter filewriter = new FileWriter(save_file_path);
            PrintWriter printwriter = new PrintWriter(new BufferedWriter(filewriter));
            for(int i:result){
                printwriter.println(i);
            }
            printwriter.close();
        }catch(IOException e){System.out.println("ファイル書き込みに失敗しました。");}
    }
    /**
     * 色分けする関数を呼び出すやつ。
     * 凡例に基づいて呼び出す関数を変更する必要あり。
     * @param colorcode_file bgrのカラーコードのファイルのパス(コンマ区切りテキスト)
     * @param save_file_path 結果を保存するファイルのパス
     */
    public static void crossColor(String colorcode_file, String save_file_path){
        int[] col_nums = {0,1,2};
        Coord3 color = new Coord3(colorcode_file, col_nums);
        color.readData(",");
        int[] result = CoordController.colorCross(color);
        try{
            FileWriter filewriter = new FileWriter(save_file_path);
            PrintWriter printwriter = new PrintWriter(new BufferedWriter(filewriter));
            for(int i:result){
                printwriter.println(i);
            }
            printwriter.close();
        }catch(IOException e){System.out.println("ファイル書き込みに失敗しました。");}
    }
    /**
     * 色分けする関数を呼び出すやつ。
     * 凡例に基づいて呼び出す関数を変更する必要あり。
     * @param colorcode_file bgrのカラーコードのファイルのパス(コンマ区切りテキスト)
     * @param save_file_path 結果を保存するファイルのパス
     */
    public static void bunritaiColor(String colorcode_file, String save_file_path){
        int[] col_nums = {0,1,2};
        Coord3 color = new Coord3(colorcode_file, col_nums);
        color.readData(",");
        int[] result = CoordController.colorBunritai(color);
        try{
            FileWriter filewriter = new FileWriter(save_file_path);
            PrintWriter printwriter = new PrintWriter(new BufferedWriter(filewriter));
            for(int i:result){
                printwriter.println(i);
            }
            printwriter.close();
        }catch(IOException e){System.out.println("ファイル書き込みに失敗しました。");}
    }
    /**
     * 色分けする関数を呼び出すやつ。
     * 凡例に基づいて呼び出す関数を変更する必要あり。
     * @param colorcode_file bgrのカラーコードのファイルのパス(コンマ区切りテキスト)
     * @param save_file_path 結果を保存するファイルのパス
     */
    public static void hodouColor(String colorcode_file, String save_file_path){
        int[] col_nums = {0,1,2};
        Coord3 color = new Coord3(colorcode_file, col_nums);
        color.readData(",");
        int[] result = colorHodou(color);
        try{
            FileWriter filewriter = new FileWriter(save_file_path);
            PrintWriter printwriter = new PrintWriter(new BufferedWriter(filewriter));
            for(int i:result){
                printwriter.println(i);
            }
            printwriter.close();
        }catch(IOException e){System.out.println("ファイル書き込みに失敗しました。");}
    }

    /**
     * 色分けする関数。
     * 凡例に基づいて条件式を変更する必要あり。
     * @param color bgrのデータ
     */
    public static int[] colorWidth(Coord3 color){
        int[] result = new int[color.coords.size()];
        double blue, green, red;
        /*
            color |(width)  |県警  |条件
            ------|---------|------|-----------------------
            white0|(---)    |(0,1) |b>200 && g>200 && r>200
            black4|(19.5~)  |(6)   |b<100 && g<100 && r<100
            green2|(5.5~13) |(3,4) |g > (b+r)*0.6
            red  1|(~5.5)   |(1,2) |b<150 && g<150 && r>200
            blue 3|(13~19.5)|(5)   |b>200 && g<150 && r<150
         */
        /*
         * 3,4を13.5m以上とする。(県警データでいうと5,6)
         * 2を5.5m-13とする。(県警データでいうと5,6)
         *
         */
        for(int i=0; i<result.length; i++){
            double[] bgr = color.coords.get(i);
            blue = bgr[0];
            green = bgr[1];
            red = bgr[2];
            if(blue<10 && green<10 && red<10){
                result[i] = 4;
            //}else if(green>(blue+red)*0.6){
            }else if(green>blue*1.2 && green>red*1.2){
                result[i] = 2;
            }else if(blue<100 && green<100 && red>150){
                result[i] = 1;
            }else if(blue>200 && green<150 && red<150){
                result[i] = 3;
            //}else if(blue>red && blue>green){
            //    result[i] = 3;
            //}else if(green>red && green>blue){
            //    result[i] = 2;
            //}else if(red>blue && red>green){
            //    result[i] = 1;
            }else{
                result[i] = 0;
            }
        }
        return result;
    }
    /**
     * 色分けする関数。
     * @param color bgrのデータ
     */
    public static int[] colorCross(Coord3 color){
        int[] result = new int[color.coords.size()];
        double blue, green, red;
        for(int i=0; i<result.length; i++){
            double[] bgr = color.coords.get(i);
            blue = bgr[0];
            green = bgr[1];
            red = bgr[2];
            if(red>blue*1.1 && red>green*1.1){
                result[i] = 1;
            }else{
                result[i] = 0;
            }
        }
        return result;
    }
    /**
     * 色分けする関数。
     * @param color bgrのデータ
     */
    public static int[] colorBunritai(Coord3 color){
        int[] result = new int[color.coords.size()];
        double blue, green, red;
        for(int i=0; i<result.length; i++){
            double[] bgr = color.coords.get(i);
            blue = bgr[0];
            green = bgr[1];
            red = bgr[2];
            if(blue>red*3 && blue>green*3){
                result[i] = 1;
            }else{
                result[i] = 0;
            }
        }
        return result;
    }
    /**
     * 色分けする関数。
     * @param color bgrのデータ
     */
    private static int[] colorHodou(Coord3 color){
        int[] result = new int[color.coords.size()];
        double blue, green, red;
        for(int i=0; i<result.length; i++){
            double[] bgr = color.coords.get(i);
            blue = bgr[0];
            green = bgr[1];
            red = bgr[2];
            if(blue<=254 && green<=254 && red<=254){
                result[i] = 1;
            }else{
                result[i] = 0;
            }
        }
        return result;
    }
}
