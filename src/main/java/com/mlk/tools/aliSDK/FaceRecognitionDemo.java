package com.mlk.tools.aliSDK;


import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import sun.misc.BASE64Encoder;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 人脸识别  人证比对-人脸比对核验-人脸身份证认证-人脸识别-人脸身份证校验
 */
public class FaceRecognitionDemo {
    public static void main(String[] args) {
        String host = "https://rlsfzdb.market.alicloudapi.com";
        String path = "/face_id/check";
        String method = "POST";
        String appcode = "4ecc0411069a4986b2a2333fb53d75a1";
        Map<String, String> headers = new HashMap<String, String>();
        //最后在header中的格式(中间是英文空格)为Authorization:APPCODE 83359fd73fe94948385f570e3c139105
        headers.put("Authorization", "APPCODE " + appcode);
        //根据API的要求，定义相对应的Content-Type
        headers.put("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
        Map<String, String> querys = new HashMap<String, String>();
        Map<String, String> bodys = new HashMap<String, String>();
        String imgPath = "C:\\Users\\Administrator\\Pictures\\微信图片_20200616121432.jpg";
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
        System.out.println("本地图片转换Base64:" + encoder.encode(Objects.requireNonNull(data)));

        bodys.put("idcard", "610322199510250333");
        bodys.put("image", encoder.encode(Objects.requireNonNull(data)));
        bodys.put("name", "马利康");


        try {
            /**
             * 重要提示如下:
             * HttpUtils请从
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/src/main/java/com/aliyun/api/gateway/demo/util/HttpUtils.java
             * 下载
             *
             * 相应的依赖请参照
             * https://github.com/aliyun/api-gateway-demo-sign-java/blob/master/pom.xml
             */
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            StatusLine statusLine = response.getStatusLine();
            System.out.println(statusLine.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
