package tech.threekilogram.droidvr;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * @author Liujin 2019/2/11:12:51:17
 */
public class Test {

      public static void main ( String[] args ) {

            File file = new File( "F:\\迅雷下载\\小说\\骸骨骑士大人异世界冒险中.txt" );
            System.out.println( file.exists() );
            try {
                  FileInputStream stream = new FileInputStream( file );
            } catch(FileNotFoundException e) {
                  e.printStackTrace();
            }
      }
}
