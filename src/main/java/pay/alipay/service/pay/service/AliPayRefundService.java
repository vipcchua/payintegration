package pay.alipay.service.pay.service;

import com.alibaba.fastjson.JSONObject;
import pay.alipay.configure.AliPayConfigure;
import pay.alipay.paymodel.model.business.AliPayRefundModel;
import pay.alipay.service.pay.facility.AliPayRefundFacility;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 描述：支付宝支付工具类
 *
 * @author Cchua
 * @date 2018年12月21日
 */
public class AliPayRefundService {

    //https://docs.open.alipay.com/270/105899/


//   统一收单交易退款接口alipay.trade.refund：


    AliPayConfigure aliPayConfigure = new AliPayConfigure();


    public static final String Response = "response";
    public static final String Request = "request";

    /**
     * 网页支付
     *
     * @param aliPayRefundModel 订单信息
     * @param callMode          请求模式
     */

    public String aliPayTradeRefund(AliPayRefundModel aliPayRefundModel, String callMode) {

        AliPayRefundFacility facility = new AliPayRefundFacility();
        String asd = null;
        try {

            //   String passbackParams = java.net.URLEncoder.encode(aliPayRefundModel.getPassbackParams(), "UTF-8");

          //  Map<String, Object> bizContent = new HashMap<String, Object>();
            Map<String, Object> bizContent = getMap( JSONObject.toJSONString(aliPayRefundModel));
         //   bizContent.put("out_trade_no", aliPayRefundModel.getOutTradeNo());

            asd = facility.getAlipayTradeRefund(bizContent, aliPayConfigure, callMode);
            return asd;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return asd;

    }

    public static Map<String, Object> getMap(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.putAll(jsonObject);
        return valueMap;
    }

}
