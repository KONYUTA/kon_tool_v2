package kon.lib.row;
//
import kon.lib.col.*;
import kon.lib.debug.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
/**
 * 行のデータを管理するためのクラス
 * @author kon
 * @version 2.0
 *
 */
public class Row extends kon.lib.col.Col{
    public ArrayList<String> coords;
    public Row(String file_name, int col_num){
        super(file_name, col_num);
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void readData(String delimiter){
        try{
            File file = new File(this.file_name);
            if(!file.exists()){
                System.out.println("ファイルが見つかりません。");
                return;
            }

            FileReader fileReader = new FileReader(file);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String data;
            this.coords = new ArrayList<String>();

            if((data = bufferedReader.readLine()) != null){
                String[] headers = data.split(delimiter);
                for(String coord:headers){
                    this.coords.add(coord);
                }
            }
            bufferedReader.close();
        } catch(IOException e){e.printStackTrace();}
    }
}
