//package com.mlk.tools.aliSDK;
//
//import okhttp3.FormBody;
//import okhttp3.OkHttpClient;
//import okhttp3.Request;
//import okhttp3.Response;
//import sun.misc.BASE64Encoder;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.io.InputStream;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.Map;
//import java.util.Objects;
//
///**
// *功能描述 【天眼数聚】人证比对-人脸比对-活体检测-人脸身份证比对-人像比对-人脸验证-人脸识别-刷脸认证-人脸认证-公安库权威人脸比对
// *@date 2020/6/16
// */
//public class FaceRecognitionDemo2 {
//    public static void main(String[] args) throws IOException {
//        String url = "https://faceidcardb.shumaidata.com/getfaceidb";
//        String appCode = "4ecc0411069a4986b2a2333fb53d75a1";
//
//        Map<String, String> params = new HashMap<>();
//        params.put("idcard", "610502199404084824");
//        params.put("name", "王晓妮");
//
//        String imgPath = "C:\\Users\\Administrator\\Pictures\\微信图片_20200616120842.jpg";
//        byte[] data = null;
//        // 读取图片字节数组
//        try {
//            InputStream in = new FileInputStream(imgPath);
//            data = new byte[in.available()];
//            in.read(data);
//            in.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        // 对字节数组Base64编码
//        BASE64Encoder encoder = new BASE64Encoder();
//        // 返回Base64编码过的字节数组字符串
//        System.out.println("本地图片转换Base64:" + encoder.encode(Objects.requireNonNull(data)));
//        params.put("image", "data:image/jpeg;base64,"+ encoder.encode(Objects.requireNonNull(data)));
//        String result = postForm(appCode, url, params);
//        System.out.println(result);
//    }
//
//    /**
//     * 用到的HTTP工具包：okhttp 3.13.1
//     * <dependency>
//     * <groupId>com.squareup.okhttp3</groupId>
//     * <artifactId>okhttp</artifactId>
//     * <version>3.13.1</version>
//     * </dependency>
//     */
//    public static String postForm(String appCode, String url, Map<String, String> params) throws IOException {
//        OkHttpClient client = new OkHttpClient.Builder().build();
//        FormBody.Builder formbuilder = new FormBody.Builder();
//        Iterator<String> it = params.keySet().iterator();
//        while (it.hasNext()) {
//            String key = it.next();
//            formbuilder.add(key, params.get(key));
//        }
//        FormBody body = formbuilder.build();
//        Request request = new Request.Builder().url(url).addHeader("Authorization", "APPCODE " + appCode).post(body).build();
//        Response response = client.newCall(request).execute();
//        System.out.println("返回状态码" + response.code() + ",message:" + response.message());
//        String result = response.body().string();
//        return result;
//    }
//}