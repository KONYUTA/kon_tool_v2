/**
 * 対応表のデータを管理するためのクラス
 * @author kon
 * @version 2.0
 *
 */
package kon.lib.table;

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
import java.util.HashMap;
//
public class Table extends kon.lib.coord.Coord{
    public HashMap<String, String> coords;
    public Table(String file_name){
        super(file_name, 0);
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
            String input_line;
            String[] data;
            this.coords = new HashMap<String, String>();

            while((input_line = bufferedReader.readLine())!= null){
                data = input_line.split(delimiter);
                System.out.println(data[0]);
                this.coords.put(data[0], data[1]);
            }
            bufferedReader.close();
        } catch(IOException e){e.printStackTrace();}
    }

    @Override
    /**
     * {@inheritDoc}
     */
    public void show(){
        for(HashMap.Entry<String, String> coord:this.coords.entrySet()){
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
                for(HashMap.Entry<String, String> xy:this.coords.entrySet()){
                    printer.println(xy);
                }
            }
            else{
                for(HashMap.Entry<String, String> xy:this.coords.entrySet()){
                    printer.println(xy);
                }
            }
            printer.close();
        }catch(IOException e){e.printStackTrace();}
    }
}
