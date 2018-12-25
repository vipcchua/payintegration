package pay.alipay.service.pay.facility;


import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.request.AlipayFundTransToaccountTransferRequest;
import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import pay.alipay.configure.AliPayClientStructure;
import pay.alipay.configure.AliPayConfigure;
import pay.alipay.paymodel.model.request.synchronous.AlipayFundTransToaccountTransfer;


import java.io.IOException;
import java.util.Map;

//https://docs.open.alipay.com/309/106236/

@Service
@Scope("prototype")
public class AliPayToaccountTransferFacility {


    public static String AliPayCharset = "utf-8";

    public static final String Response = "response";
    public static final String Request = "request";


    public String AliPayToaccountTrans(Map<String, Object> bizContent, AliPayConfigure aliPayConfigure, String callMode) throws IOException {

//Response

        AlipayClient alipayClient = new AliPayClientStructure().aliPayClientConfig(aliPayConfigure);


        AlipayFundTransToaccountTransferRequest request = new AlipayFundTransToaccountTransferRequest();

        request.setReturnUrl(aliPayConfigure.getReturnUrl());
        request.setNotifyUrl(aliPayConfigure.getNotifyUrl());// 在公共参数中设置回跳和通知地址
        request.setBizContent(JSONObject.toJSON(bizContent).toString());

        String form = "";
        if (callMode == Response) {
            try {
                AlipayFundTransToaccountTransferResponse response = alipayClient.execute(request);


                if (response.isSuccess()) {
                    form = response.getBody();

                    AlipayFundTransToaccountTransfer transfer = JSONObject.parseObject(form, AlipayFundTransToaccountTransfer.class);
                    System.out.println(transfer);
                    //    System.out.println("调用成功");
                } else {
                    form = response.getBody();
                    //System.out.println("调用失败");
                }

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
