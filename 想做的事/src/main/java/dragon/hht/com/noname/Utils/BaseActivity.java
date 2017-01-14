package dragon.hht.com.noname.Utils;


import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 游戏2 on 2017/1/4.
 */

public class BaseActivity extends AppCompatActivity {

    private int i=0x000;

    private List<Map<String,Object>> permissionList=new ArrayList<Map<String,Object>>();

    //检查是否有权限
    public boolean isGranted(String permission){
        boolean ok=true;
        Map<String,Object> map;
        if (ContextCompat.checkSelfPermission(this,permission)!=PackageManager.PERMISSION_GRANTED){
            i++;
            map=new HashMap<>();
            map.put("permission",permission);
            map.put("requestCode",i);
            permissionList.add(map);
            ok=false;
        }
        map=new HashMap<>();
        map.put("permission",permission);
        map.put("requestCode",i);
        permissionList.add(map);
        return ok;
    }

    //默认的运行方法
    public void startRun(){

    }

    //提示方法
    public void tipUser(){

    }

    //申请权限
    public void getGrant(String permission){
        if (!isGranted(permission)){
            //申请权限
            ActivityCompat.requestPermissions(this,new String[]{permission},i);
        }else {
            startRun();
        }
    }

    //处理权限是否被授予
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);


        for (String permission : permissions){
            if (grantResults[0]==PackageManager.PERMISSION_GRANTED){
                startRun();
            }else {
                tipUser();
            }
        }

    }

    public List<Map<String, Object>> getPermissionList() {
        return permissionList;
    }

}
