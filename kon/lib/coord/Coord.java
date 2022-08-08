/**
 * 座標データ等諸々を管理するデータ用のクラスの基になるクラス。
 * @author kon
 * @version 2.0
 */
//
package kon.lib.coord;
//
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//
abstract class Coord{
    public String file_name;
    public int col_num;
    public int[] col_nums;

    /**
     * コンストラクタその１。n行1列のデータ用。
     * @param file_name 読み込むデータのファイルパス
     * @param col_num 読み込むデータの行番号
     */
    public Coord(String file_name, int col_num){
        this.file_name = file_name;
        this.col_num = col_num;
    }

    /**
     * コンストラクタその2。n行m列のデータ用
     * @param file_name 読み込むデータのファイルパス
     * @param col_nums 読む虚無データの行番号の配列
     */
    public Coord(String file_name, int[] col_nums){
        this.file_name = file_name;
        this.col_nums = col_nums;
    }

    /**
     * データ読み込み
     * @param delimiter 区切り文字
     */
    abstract public void readData(String delimiter);

    /**
     * データの表示
     */
    abstract public void show();

    /**
     * データの書き込み
     * @param write_file_name 書き出すファイルのパス
     * @param reverse データを逆向きにするか否か。普通は0にしとけばおk
     */
    abstract public void write(String write_file_name, boolean reverse);

}
