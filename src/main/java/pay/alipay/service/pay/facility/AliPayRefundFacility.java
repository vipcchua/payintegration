package pay.alipay.service.pay.facility;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.*;
import com.alipay.api.response.*;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pay.alipay.configure.AliPayConfigure;

import java.io.IOException;
import java.util.Map;


@Service
@Scope("prototype")
public class AliPayRefundFacility {


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


    public String getAlipayTradeRefund(Map<String, Object> bizContent, AliPayConfigure aliPayConfigure, String callMode) throws IOException {


        AlipayClient alipayClient = AliPayClientConfig(aliPayConfigure);

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();

        request.setReturnUrl(aliPayConfigure.getReturnUrl());
        request.setNotifyUrl(aliPayConfigure.getNotifyUrl());// 在公共参数中设置回跳和通知地址
        request.setBizContent(JSONObject.toJSON(bizContent).toString());

        String form = "";
        if (callMode == Response) {
            try {
                AlipayTradeRefundResponse response = alipayClient.pageExecute(request, "get");
                form = response.getBody();
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        } else if (callMode == Request) {
            try {
                form = alipayClient.pageExecute(request).getBody(); // 调用SDK生成表单
            } catch (AlipayApiException e) {
                e.printStackTrace();
            }
        }

        return form;

    }










    /*--------------- -----<----*删除*---->--- ----------------------*/

}
