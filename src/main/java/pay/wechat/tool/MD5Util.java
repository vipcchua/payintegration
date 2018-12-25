package pay.wechat.tool;


import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;


public class MD5Util {
    public final static String MD5(String s) {
        char hexDigits[]={'0','1','2','3','4','5','6','7','8','9','A','B','C','D','E','F'};
        try {
            byte[] btInput = s.getBytes("utf-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public static String MD5Encode(String origin, String charsetname) {
        String resultString = null;
        try {
            resultString = new String(origin);
            MessageDigest md = MessageDigest.getInstance("MD5");
            if (charsetname == null || "".equals(charsetname))
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes()));
            else
                resultString = byteArrayToHexString(md.digest(resultString
                        .getBytes(charsetname)));
        } catch (Exception exception) {
        }
        return resultString;
    }

    private static String byteArrayToHexString(byte b[]) {
        StringBuffer resultSb = new StringBuffer();
        for (int i = 0; i < b.length; i++)
            resultSb.append(byteToHexString(b[i]));

        return resultSb.toString();
    }

    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0)
            n += 256;
        int d1 = n / 16;
        int d2 = n % 16;
        return hexDigits[d1] + hexDigits[d2];
    }
    private static final String hexDigits[] = { "0", "1", "2", "3", "4", "5",
            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f" };

    public static String getWXPayMD5(SortedMap<Object, Object> packageParams, String apiKey) {
        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            if (null != v && !"".equals(v) && !"sign".equals(k)
                    && !"key".equals(k)) {
                sb.append(k + "=" + v + "&");
            }
        }
        sb.append("key=" + apiKey);

        return MD5(sb.toString()).toUpperCase();
    }

    public static String getNormalParams(SortedMap<Object, Object> packageParams, String apiKey) throws UnsupportedEncodingException {
        String sign = getWXPayMD5(packageParams,apiKey);

        StringBuffer sb = new StringBuffer();
        Set es = packageParams.entrySet();
        Iterator it = es.iterator();
        while (it.hasNext()) {
            Map.Entry entry = (Map.Entry) it.next();
            String k = (String) entry.getKey();
            String v = (String) entry.getValue();
            sb.append(k + "=" + UrlEncode(v) + "&");
        }

        // 去掉最后一个&
        String packageValue = sb.append("sign=" + sign).toString();

        return packageValue;
    }

    // 特殊字符处理
    public static String UrlEncode(String src) throws UnsupportedEncodingException {
        return URLEncoder.encode(src, "UTF-8").replace("+", "%20");
    }

    public static void main(String[] args) {
        //System.out.println(MD5Util.MD5("appid=wxa32e500f1533077a&attach=申冀特-13662685556&body=50M流量包充值&device_info=WEB&mch_id=1310353301&nonce_str=201603204vfs&notify_url=/llms/api/user-Wechat-paidNotify.do&openid=ogjKTt3Nzi5y8OJ0rGjnRBJbBBy0&out_trade_no=wxa32e500f1533077a1458456409&spbill_create_ip=0:0:0:0:0:0:0:1&total_fee=1&trade_type=JSAPI&key=shenjite123456789012345678901234"));
        //System.out.println(MD5Util.MD5("account=ceshi&mobile=13662685556&package=30&key=879aee1721a740e4a1cb0e8b07c3969b").toLowerCase());
        //System.out.println(MD5Util.MD5("api_key=P5Ki8zlOTRsyceo552MAuuQAgp4YwwSG&prodid=1001&submitorderid=13662685556001&phone=13662685556&num=100").toLowerCase());
        String exprieStr="7200";
        Date curDate=new Date();
        System.out.println(curDate.getTime());
        System.out.println(new Date(curDate.getTime()+Long.valueOf(exprieStr)*1000));

    }

}