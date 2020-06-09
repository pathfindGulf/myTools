package com.mlk.tools;

import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 功能描述: 短信服务
 * @author: mlk
 * @date: 2020/6/9
 */
@Slf4j
public class SMSUtil {

    /*
     * 帐号：SY0416 密码：13709226207 查询平台地址：www.106818.com
     */

    private static String CorpID="HX0060";
    private static String pwd="606060HX";
    /**
     * 发送消息（电话号，发送内容，发送时间(空字符串为即时)）
     *
     * @param Mobile
     * @param
     * @param send_time
     * @return
     * @throws MalformedURLException
     * @throws UnsupportedEncodingException
     * @author
     */
    public static int sendSMSGet(String Mobile, String content, String send_time)
            throws MalformedURLException, UnsupportedEncodingException {


        Logger log = LoggerFactory.getLogger(SMSUtil.class);
        URL url = null;
        String send_content = URLEncoder.encode(content, "GBK");// 发送内容
        url = new URL("http://106818.com/WS/Send.aspx?CorpID=" + CorpID + "&Pwd=" + pwd + "&Mobile=" + Mobile
                + "&Content=" + send_content + "&Cell=&SendTime=" + send_time);
        BufferedReader in = null;
        int inputLine = 0;
        try {
            log.info("sendSMS===>url:" + url);
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            inputLine = new Integer(in.readLine()).intValue();
            log.info("sendSMS===>return:" + inputLine);
        } catch (Exception e) {
            inputLine = -9;
        }
        return inputLine;
    }

    /**
     * 以POST形式发送
     *
     // * @param CorpID
     //* @param pwd
     * @param Mobile
     * @param content
     * @param send_time
     * @return
     */
    public static int sendSMS(String Mobile, String content, String send_time) {
        BufferedReader in = null;
        try {
            log.info("sendSMS===>content:" + content);
            String send_content = URLEncoder.encode(content, "UTF-8");
            Date currentTime = new Date();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
            String s8 = formatter.format(currentTime);
            String jsonStr = "action=send" +"&userid=" + "113" + "&timestamp=" + s8
                    + "&sign=" + getMd5Value(CorpID+pwd+s8) + "&mobile=" + Mobile + "&content=" + send_content+ "&sendTime=" + send_time+"&extno=" ;
            log.info("sendSMS===>param:" + jsonStr);
            String urlStr = "http://47.93.131.195:8888/v2sms.aspx";
            URL urls = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) urls
                    .openConnection();
            byte[] requestStringBytes = jsonStr.getBytes();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            //设置请求头里面的数据，以下设置用于解决http请求code415的问题
            connection.setRequestProperty("Content-Type",
                    "application/x-www-form-urlencoded");
            connection.setRequestProperty("Content-length", "" + requestStringBytes.length);
            connection.connect();
            //POST请求
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            out.write(requestStringBytes);
            out.flush();
            out.close();
            // 定义BufferedReader输入流来读取URL的响应
            log.info("sendSMS===>url:" + urls);
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));


            int result=0;
            while((result=reader.read())!=-1)
            {
                //因为读取到的是int类型的，所以要强制类型转换
                log.info("sendSMS===>return:"+reader.readLine());
            }
            reader.close();

            //  while(in.n)
            // log.info("sendSMS===>return:" + in.readLine());
            return 0;
        } catch (Exception e) {
            log.error("SMSUtil sendSMS exception:==>" + e);
            return -1;
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 获取短信内容
     *
     * @param map
     * @return
     */
    public static Map<String,String> getMessage(Map<String, String> map) {
        String type = map.get("Type");
        String message = "";
        switch (type) {
            case "0": //推送办理地址
                message = "尊敬的企业您好,您设立的"+map.get("companyName")+"已由工商审核通过，请及时领取或自助打印。高新政府提供免费刻章服务，如需申请请点击"+map.get("pushUrl");
                break;
            case "1": //工商审核通过用户
                message = "尊敬的"+map.get("companyName")+"，您好,您申请的免费刻章服务已预审通过，正在刻制公章，请您通过短信提醒了解刻章进度。领取请携带二维码（勿删）："+map.get("imageUrl");
                break;
            case "2": //工商审核通过经办商户
                message = "尊敬的商户您好,您有"+map.get("companyName")+"的新订单，请尽快刻章。归还大厅验证码："+map.get("verificationCode");
                break;
            case "3": //快递发送
                message = "尊敬的"+map.get("companyName")+"，您好，您的公章已经由"+map.get("expressCompany")+"揽收，快递编号为："+map.get("expressNumber")+"，请注意查收";
                break;
            case "4": //通知用户领取
                message = "尊敬的"+map.get("companyName")+"，您好，您的公章已经刻制完成，请及时到"+map.get("hallName")+"大厅领取,领取请携带二维码（勿删）："+map.get("imageUrl");
                break;
            case "5": //评价
                message = "尊敬的"+map.get("companyName")+"，您好，感谢您对高新管委会工作的支持！请对本次服务进行评价，谢谢!"+map.get("url");
                break;
            case "6": //驳回
                message = "尊敬的"+map.get("companyName")+"，您好,您申请的免费刻章服务被驳回，驳回原因"+map.get("reason");
                break;
            case "7": //领取办理
                message = "尊敬的"+map.get("companyName")+"，您好,您申请的免费刻章已由"+map.get("merchantName")+"领取办理";
                break;
            default:
                break;
        }
        // int code =  SMSUtil.sendSMS("SY0784", "13772405681",map.get("phone"),message,"");
        Map<String,String>  resultMap = new HashMap<>();
        resultMap.put("phone",map.get("phone"));
        resultMap.put("message",message);
        resultMap.put("code",String.valueOf("ww"));
        System.out.println("短信内容："+message+"发送结果："+resultMap.toString());
        return resultMap;
    }


    public static void main(String[] args) {
        //sendSMS("SY0486", "13709226207", "13772405681", "测试：https://wx.scnner.cn/approveManager1/?hall=a1650cb0aabc4dbaa7740983d8e62d02", "");
        //sendSMS("SY0486", "13709226207", "18629634646", "测试短信发送", "");029-96767
        sendSMS("18829077644","尊敬的客户  您有一个实验项目待您确认  请确认 http://124.115.229.135:8012/?id=489911   ", "");
    }

    public static String getMd5Value(String value){
        try {
            //1. 获得md5加密算法工具类
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //2. 加密的结果为十进制
            byte[] md5Bytes = messageDigest.digest(value.getBytes());
            //3. 将md5加密算法值转化为16进制
            BigInteger bigInteger = new BigInteger(1, md5Bytes);
            return bigInteger.toString(16);

        } catch (Exception e) {
            //如果产生错误则抛出异常
            throw new RuntimeException();
        }
    }


}
