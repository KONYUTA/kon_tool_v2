/**
 * CoordDouble
 * 座標データ等諸々を管理するデータ用のクラス
 *
 */
//
package kon.lib.coord;
//
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
public class CoordDouble extends Coord{
    public ArrayList<double[]> coords;
    public CoordDouble(String file_name, int[] col_nums){
        super(file_name, col_nums);
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
            this.coords = new ArrayList<double[]>();

            while((data = bufferedReader.readLine()) != null){
                String[] nums = data.split(" ");
                double[] coord = new double[2];
                for(int i=0; i<coord.length; i++){ 
                    try{
                        coord[i] = Double.parseDouble(nums[col_nums[i]]);
                    }catch(NumberFormatException e){System.out.println("数値以外はスキップされます");}
                }
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
        for(double[] coord:this.coords){
            System.out.println(coord[0]+","+coord[1]);
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
                for(double[] xy:this.coords){
                    printer.println(xy[0]+" "+xy[1]);
                }
            }
            else{
                for(double[] xy:this.coords){
                    printer.println(xy[1]+" "+xy[0]);
                }
            }
            printer.close();
        }catch(IOException e){e.printStackTrace();}
    }
}
