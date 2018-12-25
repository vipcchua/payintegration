package pay.alipay.service.pay.service;

import com.alibaba.fastjson.JSONObject;
import pay.alipay.configure.AliPayConfigure;
import pay.alipay.service.pay.facility.AliPayPaymentFacility;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：支付宝支付工具类
 *
 * @author Cchua
 * @date 2018年12月21日
 */
public class AliPayNotifyService {


    public AliPayNotifyService() {
    }

    /**
     * 统一订单-线下当面付
     *
     * @param request 支付宝监听信息
     */
    public static Map<String, String> notifyUrl(HttpServletRequest request) {


        // System.out.println("支付宝支付结果通知" + request.getParameterMap().toString());
        //获取支付宝POST过来反馈信息
        Map<String, String> params = new HashMap<>();
        // Map requestParams = request.getParameterMap();
        //  System.out.println(request.getParameterMap());
        //  String paramsJson = JSONObject.toJSONString(params);
        //    logger.info("支付宝回调，{}", paramsJson);

       /* for (Object o : requestParams.keySet()) {
            String name = (String) o;
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values
                        : valueStr + values + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。
        //    valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            System.out.println(name + "=" + valueStr);
            params.put(name, valueStr);
        }
        */
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String parameterName = parameterNames.nextElement();
            params.put(parameterName, request.getParameter(parameterName));
        }

        //String a =  JSONObject.toJSONString(params);
        // AlipayNotifyParam param = buildAlipayNotifyParam(params);
        //  System.out.println( param.getTradeStatus());


        AliPayPaymentFacility facility = new AliPayPaymentFacility();
        AliPayConfigure aliPayConfigure = new AliPayConfigure();
        Boolean sta = facility.notifyUrlSignVerified(params, aliPayConfigure);

        if (sta == true) {
            return params;
        } else {
            return null;
        }


    }


    public static <T> T buildAliPayNotifyParam(Map<String, String> params, Class<T> clazz) {
        String json = JSONObject.toJSONString(params);
        return JSONObject.parseObject(json, clazz);

    }


    public static <T> T verifyingSignature(HttpServletRequest request, Class<T> clazz) {

        Map<String, String> params = null;

        if (request != null) {
            params = AliPayNotifyService.notifyUrl(request);
            if (params!=null){
                return buildAliPayNotifyParam(params,clazz);
            }
        }

        return null;
    }


}
