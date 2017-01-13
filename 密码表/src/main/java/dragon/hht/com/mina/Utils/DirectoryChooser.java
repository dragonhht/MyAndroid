package dragon.hht.com.mina.Utils;


import android.os.Environment;
import android.util.Log;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.os.Environment.getRootDirectory;

/**
 * Created by 游戏2 on 2017/1/9.
 *
 * 文件夹选择器
 */

public class DirectoryChooser {

    //获取文件夹
    public List<Map<String,String>> getDirectory(String directoryName){


        Log.i("info","手机根目录"+ Environment.getExternalStorageDirectory());

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
                        directoryList.add(map);
                    }
                }
            }
        }

        return directoryList;
    }

}
