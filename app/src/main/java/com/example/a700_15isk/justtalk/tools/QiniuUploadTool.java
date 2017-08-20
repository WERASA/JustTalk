package com.example.a700_15isk.justtalk.tools;

import android.content.Context;
import android.util.Log;

import com.example.a700_15isk.justtalk.bean.User;
import com.qiniu.android.http.ResponseInfo;
import com.qiniu.android.storage.UpCompletionHandler;
import com.qiniu.android.storage.UploadManager;
import com.qiniu.util.Auth;
import com.qiniu.util.Base64;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

/**
 * Created by 700-15isk on 2017/8/20.
 */

public class QiniuUploadTool {
        public static  String getKey(String scope ) throws NoSuchAlgorithmException {
            JSONObject jsonObject=new JSONObject();
            int putFlags=1;
            try {
                jsonObject.put("scope",scope);
                jsonObject.put("deadine",1514736000);
                JSONObject returnBody=new JSONObject();
                returnBody.put("name","(fname)");
                returnBody.put("size","(fsize)");
                returnBody.put("w","(imageInfo.width)");
                returnBody.put("h","(imageInfo.width)");
                returnBody.put("hash","(etag)");
                jsonObject.put("returnbody",returnBody);
                byte[] putData=jsonObject.toString().getBytes();
                String encodedPutPolicy= Base64.encodeToString(putData, Base64.URL_SAFE);
                Log.d("js",jsonObject.toString());
                Log.d("d",encodedPutPolicy);
                String secretkey="MY_SECRET_KEY";
                String accesskey=" EkgHnK6hay4eCw73StmWB6Dz-r9-l8bHGG7df7b8";
                String type = "HMAC-SHA1";
                SecretKeySpec secretKeySpec=new SecretKeySpec(secretkey.getBytes(),type);
                Mac mac= Mac.getInstance(type);
                mac.init(secretKeySpec);
                byte[]dygest=mac.doFinal(encodedPutPolicy.getBytes());
                String  encodedSign = Base64.encodeToString(dygest,Base64.URL_SAFE);
                Log.d("encode",encodedSign);
                String uploadKey=accesskey+":"+encodedSign+":"+encodedPutPolicy;
                Log.d("key",uploadKey);
                return  uploadKey;

            } catch (JSONException e) {
                e.printStackTrace();
            } catch (InvalidKeyException e) {
                e.printStackTrace();
            }
            return  null;
        }
        public static void upload(final String path, String key, final User user, final Context context){
            UploadManager uploadManager=new UploadManager();

            Auth auth=Auth.create("EkgHnK6hay4eCw73StmWB6Dz-r9-l8bHGG7df7b8","l7hicsICYNt7Q3foskLmhSBnAtLICb_rwa8uE3bI");
            String token=auth.uploadToken("zhihu");
            uploadManager.put(path, key, token, new UpCompletionHandler() {
                @Override
                public void complete(String key, ResponseInfo info, JSONObject response) {
                         if (info.isOK()){
                             String mAvatar="http://olekc4jwu.bkt.clouddn.com/"+key;
                             user.setAvatar(mAvatar);
                             UserTool.getInstance().upDate(user,context);
                         }
                }
            },null);


        }

}
