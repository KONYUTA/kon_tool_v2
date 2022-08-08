package kon.lib.debug;
import java.util.ArrayList;
import javax.swing.text.StyledEditorKit.BoldAction;
/**
 * デバッグ用の関数をまとめたやつ
 * @author kon
 * @version 2.0
 */
public class Debug{

    /**
     * カウンタ。
     * リセットするにはDebug.resetCounter()を使う
     */
    private static int counter = 0;
    /**
     * 入力値を出力して一時停止する。エンターキーを押せば次に進む。
     * @param x ターミナル出力する変数。
     */
    public static void show(int x){
//        System.out.print(new Object(){}.getClass().getEnclosingClass().getName());
        System.out.print("(デバッグ): ");
        System.out.println(x);
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(float x){
        System.out.print("(Debug): ");
        System.out.println(x);
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(double x){
        System.out.print(new Object(){}.getClass().getEnclosingClass().getName());
        System.out.print("(デバッグ): ");
        System.out.println(x);
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(String x){
        System.out.print("(Debug): ");
        System.out.println(x);
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(boolean x){
        System.out.print("(Debug): ");
        System.out.println(x);
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(byte x){
        System.out.print("(Debug): ");
        System.out.println(x);
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(char x){
        System.out.print("(Debug): ");
        System.out.println(x);
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(long x){
        System.out.print("(Debug): ");
        System.out.println(x);
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(short x){
        System.out.print("(Debug): ");
        System.out.println(x);
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(Object x){
        System.out.print("(Debug): ");
        System.out.println(x.toString());
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(int[] x){
        System.out.println("(Debug): ");
        for(int i:x){System.out.print(i+", ");}
        System.out.print("\n");
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(float[] x){
        System.out.print(new Object(){}.getClass().getEnclosingClass().getName());
        System.out.print("(デバッグ): ");
        for(float i:x){System.out.print(i+", ");}
        System.out.print("\n");
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(long[] x){
        System.out.println("(Debug): ");
        for(long i:x){System.out.print(i+", ");}
        System.out.print("\n");
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(short[] x){
        System.out.println("(Debug): ");
        for(short i:x){System.out.print(i+", ");}
        System.out.print("\n");
        String stop = new java.util.Scanner(System.in).nextLine();
    }

    public static void show(double[] x){
        System.out.print(new Object(){}.getClass().getEnclosingClass().getName());
        System.out.print("(デバッグ): ");
        for(double i:x){System.out.print(i+", ");}
        System.out.print("\n");
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(byte[] x){
        System.out.println("(Debug): ");
        for(byte i:x){System.out.print(i+", ");}
        System.out.print("\n");
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(String[] x){
        System.out.println("(Debug): ");
        for(String i:x){System.out.print(i+", ");}
        System.out.print("\n");
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(char[] x){
        System.out.println("(Debug): ");
        for(char i:x){System.out.print(i+", ");}
        System.out.print("\n");
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(boolean[] x){
        System.out.println("(Debug): ");
        for(boolean i:x){System.out.print(i+", ");}
        System.out.print("\n");
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(Object[] x){
        System.out.println("(Debug): ");
        for(Object i:x){System.out.print(i.toString()+", ");}
        System.out.print("\n");
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(int[][] x){
        System.out.println("(Debug): ");
        for(int[] row:x){
            for(int col:row){
                System.out.print(col+",");
            }
            System.out.print("\n");
        }
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(short[][] x){
        System.out.println("(Debug): ");
        for(short[] row:x){
            for(short col:row){
                System.out.print(col+",");
            }
            System.out.print("\n");
        }
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(long[][] x){
        System.out.println("(Debug): ");
        for(long[] row:x){
            for(long col:row){
                System.out.print(col+",");
            }
            System.out.print("\n");
        }
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(byte[][] x){
        System.out.println("(Debug): ");
        for(byte[] row:x){
            for(byte col:row){
                System.out.print(col+",");
            }
            System.out.print("\n");
        }
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(boolean[][] x){
        System.out.println("(Debug): ");
        for(boolean[] row:x){
            for(boolean col:row){
                System.out.print(col+",");
            }
            System.out.print("\n");
        }
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(float[][] x){
        System.out.println("(Debug): ");
        for(float[] row:x){
            for(float col:row){
                System.out.print(col+",");
            }
            System.out.print("\n");
        }
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(double[][] x){
        System.out.println("(Debug): ");
        for(double[] row:x){
            for(double col:row){
                System.out.print(col+",");
            }
            System.out.print("\n");
        }
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(String[][] x){
        System.out.println("(Debug): ");
        for(String[] row:x){
            for(String col:row){
                System.out.print(col+",");
            }
            System.out.print("\n");
        }
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(char[][] x){
        System.out.println("(Debug): ");
        for(char[] row:x){
            for(char col:row){
                System.out.print(col+",");
            }
            System.out.print("\n");
        }
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(Object[][] x){
        System.out.println("(Debug): ");
        for(Object[] row:x){
            for(Object col:row){
                System.out.print(col+",");
            }
            System.out.print("\n");
        }
        String stop = new java.util.Scanner(System.in).nextLine();
    }
    public static void show(ArrayList<Integer> x){
        System.out.println("(Debug): ");
        for(int i:x){System.out.print(i+", ");}
        System.out.print("\n");
        String stop = new java.util.Scanner(System.in).nextLine();
    }


    /**
     * カウンタ変数を表示。ループなどのデバッグに使う。
     */
    public static void countRange(){
        Debug.counter++;
        System.out.println("(Debug): ループ回数: "+Debug.counter);
        String stop = new java.util.Scanner(System.in).nextLine();
    }

    /**
     * カウンタ変数をリセット
     */
    public static void counterReset(){
        Debug.counter=0;
    }
}
