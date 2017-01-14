package dragon.hht.com.noname.Utils;

import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 游戏2 on 2017/1/13.
 */

public class FileChooer {

    //获取文件夹及txt文本
    public static List<Map<String,String>> getFiles(String directoryName){

        List<Map<String,String>> directoryList=new ArrayList<>();
        File rootFile=new File(directoryName);
        File[] files=rootFile.listFiles();
        if (files!=null){
            for (File file:files){
                if (file.isDirectory()){
                    //判断是否有权限
                    if (file!=null) {
                        Map<String, String> map = new HashMap<>();
                        map.put("path", file.getAbsolutePath());
                        map.put("name", file.getName());
                        map.put("flag","0");
                        directoryList.add(map);
                    }
                }else {
                    String filename=file.getName();
                    String houzhui=null;
                    try {
                        houzhui = filename.substring(filename.lastIndexOf(".")+1, filename.length());
                        if (houzhui.equals("txt")){
                            Map<String, String> map = new HashMap<>();
                            map.put("path", file.getAbsolutePath());
                            map.put("name", file.getName());
                            map.put("flag","1");
                            directoryList.add(map);
                        }
                    }catch (Exception e){

                    }


                }
            }
        }

        return directoryList;
    }
}
