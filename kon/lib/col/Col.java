/**
 * 列のデータを管理するためのクラス
 * @author kon
 * @version 2.0
 *
 */
package kon.lib.col;
//
import kon.lib.coord.*;
import kon.lib.debug.*;
import java.io.File;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
//
public class Col extends kon.lib.coord.Coord{
    public ArrayList<String> coords;
    public Col(String file_name, int col_num){
        super(file_name, col_num);
        this.coords = new ArrayList<String>();
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

            while((data = bufferedReader.readLine()) != null){
                String coord = data.split(delimiter)[col_num];
                this.coords.add(coord);
            }
            bufferedReader.close();
        } catch(IOException e){e.printStackTrace();}
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void show(){
        for(String coord:this.coords){
            System.out.println(coord);
        }
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void write(String write_file_name, boolean reverse){
        try{
            FileWriter writer = new FileWriter(write_file_name);
            PrintWriter printer = new PrintWriter(new BufferedWriter(writer));

            if(reverse){
                for(String xy:this.coords){
                    printer.println(xy);
                }
            }
            else{
                for(String xy:this.coords){
                    printer.println(xy);
                }
            }
            printer.close();
        }catch(IOException e){e.printStackTrace();}
    }
}
