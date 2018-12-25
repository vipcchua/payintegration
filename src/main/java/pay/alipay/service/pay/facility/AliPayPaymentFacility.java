package pay.alipay.service.pay.facility;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;


import com.alipay.api.internal.util.AlipaySignature;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pay.alipay.configure.AliPayConfigure;
import pay.alipay.paymodel.GeneratePayment;
import pay.alipay.paymodel.model.request.synchronous.AliPayTradePrecreate;
//import pay.alipay.service.db.impl.AccountAlipayBusinessServerImpl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;


@Service
@Scope("prototype")
public class AliPayPaymentFacility {


    public static String AliPayCharset = "utf-8";


    public AlipayClient AliPayClientConfig(AliPayConfigure aliPayConfigure) {


        AlipayClient alipayClient = new DefaultAlipayClient(
                aliPayConfigure.getURL(),
                aliPayConfigure.getAppId(),
                aliPayConfigure.getAppPrivateKey(),
                aliPayConfigure.getFormat(),
                aliPayConfigure.getCharset(),
                aliPayConfigure.getAlipayPublicKey(),
                aliPayConfigure.getSignType()); // 获得初始化的AlipayClient
        return alipayClient;
    }


    // AliPayConfigure aliPayConfigure = new AliPayConfigure();
    // AlipayClient alipayClient =AliPayClientConfig(aliPayConfigure);


    public static final String Response = "response";
    public static final String Request = "request";


    public String getAliPayTradePagePay(Map<String, Object> bizContent, AliPayConfigure aliPayConfigure, String callMode) throws IOException {


        AlipayClient alipayClient = AliPayClientConfig(aliPayConfigure);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(aliPayConfigure.getReturnUrl());
        alipayRequest.setNotifyUrl(aliPayConfigure.getNotifyUrl());// 在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent(JSONObject.toJSON(bizContent).toString());

        String form = "";
        if (callMode == Response) {
            try {
                AlipayTradePagePayResponse response = alipayClient.pageExecute(alipayRequest, "get");
                form = response.getBody();
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        } else if (callMode == Request) {
            try {
                form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }

        return form;

    }








    /*------------------电脑----*/


    public String getAliPayTradePagePayRequest(HttpServletRequest httpRequest, HttpServletResponse httpResponse, AliPayConfigure aliPayConfigure, GeneratePayment paymentifo) throws IOException {


        AlipayClient alipayClient = AliPayClientConfig(aliPayConfigure);
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

        alipayRequest.setReturnUrl(aliPayConfigure.getReturnUrl());
        alipayRequest.setNotifyUrl(aliPayConfigure.getNotifyUrl());// 在公共参数中设置回跳和通知地址

        String passbackParams = java.net.URLEncoder.encode(paymentifo.getPassbackParams(), "UTF-8");


        Map<String, Object> bizContent = new HashMap<String, Object>();

        bizContent.put("out_trade_no", paymentifo.getOutTradeNo());
        bizContent.put("total_amount", paymentifo.getTotalAmount());
        bizContent.put("subject", paymentifo.getSubject());
        bizContent.put("body", paymentifo.getBody());
        bizContent.put("passback_params", passbackParams);
        bizContent.put("product_code", paymentifo.getProductCode());

        alipayRequest.setBizContent(JSONObject.toJSON(bizContent).toString());


        /*


        alipayRequest.setBizContent("{" +
                " \"out_trade_no\":\"" + paymentifo.getOutTradeNo() + "\"," +
                " \"total_amount\":\"" + paymentifo.getTotalAmount() + "\"," +
                " \"subject\":\"" + paymentifo.getSubject() + "\"," +
                " \"body\":\"" + paymentifo.getBody() + "\"," +
                " \"passback_params\":\"" + passbackParams + "\"," +
                " \"product_code\":\"" + paymentifo.getProductCode() + "\"" +
                " }");
*/



/*
        alipayRequest.setBizContent("{" +
                "    \"out_trade_no\":\"20150320010101001\"," +
                "    \"product_code\":\"FAST_INSTANT_TRADE_PAY\"," +
                "    \"total_amount\":88.88," +
                "    \"subject\":\"Iphone6 16G\"," +
                "    \"body\":\"Iphone6 16G\"," +
                "    \"passback_params\":\"merchantBizType%3d3C%26merchantBizNo%3d2016010101111\"," +
                "    \"extend_params\":{" +
                "    \"sys_service_provider_id\":\"2088511833207846\"" +
                "    }"+
                "  }");//填充业务参数

        */

        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单


            System.out.println(form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + AliPayCharset);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();


        return form;

    }


    public String getAliPayTradePagePayResponse(HttpServletRequest httpRequest, HttpServletResponse httpResponse, AliPayConfigure aliPayConfigure, GeneratePayment paymentifo) throws IOException {

        AlipayClient alipayClient = AliPayClientConfig(aliPayConfigure);

        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();
        alipayRequest.setReturnUrl(aliPayConfigure.getReturnUrl());
        alipayRequest.setNotifyUrl(aliPayConfigure.getNotifyUrl());// 在公共参数中设置回跳和通知地址

        String passbackParams = java.net.URLEncoder.encode(paymentifo.getPassbackParams(), "UTF-8");


        alipayRequest.setBizContent("{" +
                " \"out_trade_no\":\"" + paymentifo.getOutTradeNo() + "\"," +
                " \"total_amount\":\"" + paymentifo.getTotalAmount() + "\"," +
                " \"subject\":\"" + paymentifo.getSubject() + "\"," +
                " \"body\":\"" + paymentifo.getBody() + "\"," +
                " \"passback_params\":\"" + passbackParams + "\"," +
                " \"product_code\":\"" + paymentifo.getProductCode() + "\"" +
                " }");


        try {
            AlipayTradePagePayResponse response = alipayClient.pageExecute(alipayRequest, "get");
            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return "";

    }

    /*------------------电脑----*/


    /*------------------手機----*/
    public String phonepay(HttpServletRequest httpRequest, HttpServletResponse httpResponse, AliPayConfigure aliPayConfigure, GeneratePayment paymentifo) throws IOException {

        AlipayClient alipayClient = AliPayClientConfig(aliPayConfigure);

        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();// 创建API对应的request
        alipayRequest.setReturnUrl(aliPayConfigure.getReturnUrl());
        alipayRequest.setNotifyUrl(aliPayConfigure.getNotifyUrl());// 在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{"
                + " \"out_trade_no\":\"20150320010101002\","
                + " \"total_amount\":\"0.01\","
                + " \"subject\":\"Iphone6 16G\","
                + " \"product_code\":\"QUICK_WAP_PAY\"" + " }");// 填充业务参数
        String form = "";
        try {
            form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
            System.out.println(form);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        httpResponse.setContentType("text/html;charset=" + AliPayCharset);
        httpResponse.getWriter().write(form);// 直接将完整的表单html输出到页面
        httpResponse.getWriter().flush();
        httpResponse.getWriter().close();

        /*
         * ResponseEntity<PaginationVo> data = new
         * ResponseEntity<PaginationVo>(form, HttpStatus.OK);
         */

        return form;

    }


    public String phonepayreq(HttpServletRequest httpRequest, HttpServletResponse httpResponse, AliPayConfigure aliPayConfigure, GeneratePayment paymentifo) throws IOException {

        AlipayClient alipayClient = AliPayClientConfig(aliPayConfigure);
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();// 创建API对应的request
        alipayRequest.setReturnUrl(aliPayConfigure.getReturnUrl() + "/callback/returnurl");/*支付完返回的頁面*/
        alipayRequest.setNotifyUrl(aliPayConfigure.getReturnUrl() + "/callback/notifyurl");// 在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent("{"
                + " \"out_trade_no\":\"20150320010101002\","
                + " \"total_amount\":\"0.01\","
                + " \"subject\":\"Iphone6 16G\","
                + " \"product_code\":\"QUICK_WAP_PAY\"" + " }");// 填充业务参数
        String form = "";


        try {
            AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest, "get");
            System.out.println(response.getBody().toString());
            //   return "{'html':'"+response.getBody().toString()+"'}";

            // String a=  HttpUtil.doGetAlipay(response.getBody(),new HashMap<String, Object>());


            return response.getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }

        return "";

        /*
         * ResponseEntity<PaginationVo> data = new
         * ResponseEntity<PaginationVo>(form, HttpStatus.OK);
         */

    }


    public String getAliPayTradeWapPay(Map<String, Object> bizContent, AliPayConfigure aliPayConfigure, String callMode) throws IOException {


        AlipayClient alipayClient = AliPayClientConfig(aliPayConfigure);
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();// 创建API对应的request
        alipayRequest.setReturnUrl(aliPayConfigure.getReturnUrl());
        alipayRequest.setNotifyUrl(aliPayConfigure.getNotifyUrl());// 在公共参数中设置回跳和通知地址
        alipayRequest.setBizContent(JSONObject.toJSON(bizContent).toString());

        String form = "";
        if (callMode == Response) {
            try {

                AlipayTradeWapPayResponse response = alipayClient.pageExecute(alipayRequest, "get");
                form = response.getBody();
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        } else if (callMode == Request) {
            try {
                form = alipayClient.pageExecute(alipayRequest).getBody(); // 调用SDK生成表单
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }

        return form;

    }





    /*------------------手機----*/

    public AliPayTradePrecreate getAliPayTradePrecreate(Map<String, Object> bizContent, AliPayConfigure aliPayConfigure) throws IOException {

        AlipayClient alipayClient = AliPayClientConfig(aliPayConfigure);

        AlipayTradePrecreateRequest alipayRequest = new AlipayTradePrecreateRequest();//创建API对应的request类

        alipayRequest.setBizContent(JSONObject.toJSON(bizContent).toString());
        alipayRequest.setReturnUrl(aliPayConfigure.getReturnUrl());
        alipayRequest.setNotifyUrl(aliPayConfigure.getNotifyUrl());// 在公共参数中设置回跳和通知地址777

        try {

            AlipayTradePrecreateResponse response = alipayClient.execute(alipayRequest);

            if (response.getCode().equals("10000")) {
                System.out.println(response.getMsg());

                System.out.println("二维码串 = " + response.getQrCode());
                AliPayTradePrecreate transfer = JSONObject.parseObject(response.getBody(), AliPayTradePrecreate.class);





                return transfer;
            } else {
                return null;
            }


        } catch (AlipayApiException e) {

            e.printStackTrace();

        }
        return null;
    }


    public String toaccount(HttpServletRequest httpRequest, HttpServletResponse httpResponse, AliPayConfigure aliPayConfigure, GeneratePayment paymentifo) throws IOException {


        AlipayClient alipayClient = AliPayClientConfig(aliPayConfigure);

        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();
        request.setBizContent("{" +
                "    \"out_biz_no\":\"3142321423432\"," +
                "    \"payee_type\":\"ALIPAY_LOGONID\"," +
                "    \"payee_account\":\"vipcchua@qq.com\"," +
                "    \"amount\":\"12.23\"," +
                "    \"payer_show_name\":\"上海交通卡退款\"," +
                "    \"payee_real_name\":\"Cc\"," +
                "    \"remark\":\"转账备注\"," +
                "  }");
        AlipayFundTransToaccountTransferResponse response = null;
        try {
            response = alipayClient.execute(request);
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        if (response.isSuccess()) {
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }


        return null;
    }



    public Boolean notifyUrlSignVerified(Map<String, String> params, AliPayConfigure aliPayConfigure) {
        try {
            boolean signVerified = AlipaySignature.rsaCheckV1(params, aliPayConfigure.getAlipayPublicKey(), aliPayConfigure.charset, aliPayConfigure.getSignType());
            return signVerified;
        } catch (AlipayApiException e) {
            e.printStackTrace();
            return false;
        }

    }
/*
    public HttpServletResponse create(HttpServletRequest httpRequest, HttpServletResponse httpResponse,AliPayConfigure aliPayConfigure, GeneratePayment paymentifo) throws AlipayApiException {



        AlipayClient alipayClient =AliPayClientConfig(aliPayConfigure);


        AlipayTradePrecreateRequest request = new AlipayTradePrecreateRequest();
        AlipayTradePrecreateModel paymodel = new AlipayTradePrecreateModel();
        request.setBizModel(paymodel);
        paymodel.setOutTradeNo("123");
        paymodel.setTotalAmount("88.88");
        paymodel.setSubject("Iphone6 16G");
        AlipayTradePrecreateResponse response = alipayClient.execute(request);
        System.out.print(response.getBody());
        System.out.print(response.getQrCode());
        return (HttpServletResponse) response;

    }*/


    private static Map<String, String> convertRequestParamsToMap(HttpServletRequest request) {


        Map<String, String> params = new HashMap<String, String>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            //乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            //valueStr = new String(valueStr.getBytes("ISO-8859-1"), "gbk");
            params.put(name, valueStr);
        }

  /*


        Map<String, String> params = new HashMap<>();
        Map requestParams = request.getParameterMap();
        for (Iterator iter = requestParams.keySet().iterator(); iter.hasNext(); ) {
            String name = (String) iter.next();
            String[] values = (String[]) requestParams.get(name);
            String valueStr = "";
            for (int i = 0; i < values.length; i++) {
                valueStr = (i == values.length - 1) ? valueStr + values[i]
                        : valueStr + values[i] + ",";
            }
            // 乱码解决，这段代码在出现乱码时使用。如果mysign和sign不相等也可以使用这段代码转化
            try {
                valueStr = new String(valueStr.getBytes("ISO-8859-1"), "utf-8");
            } catch (UnsupportedEncodingException e) {
//                logger.info("parse alipay callback data encoding error", e);
//                throw new PyRuntimeError("解析结果错误" , "解析结果错误 : " + valueStr);
            }
            params.put(name, valueStr);
        }

*/


//        Map<String, String> retMap = new HashMap<String, String>();
//
//        Set<Map.Entry<String, String[]>> entrySet = request.getParameterMap().entrySet();
//
//        for (Map.Entry<String, String[]> entry : entrySet) {
//            String name = entry.getKey();
//            String[] values = entry.getValue();
//            int valLen = values.length;
//
//            if (valLen == 1) {
//                retMap.put(name, values[0]);
//            } else if (valLen > 1) {
//                StringBuilder sb = new StringBuilder();
//                for (String val : values) {
//                    sb.append(",").append(val);
//                }
//                retMap.put(name, sb.toString().substring(1));
//            } else {
//                retMap.put(name, "");
//            }
//        }

        return params;
    }









    /*--------------- -----<----*删除*---->--- ----------------------*/

}
