package pay.alipay.service.pay.service;

import com.alibaba.fastjson.JSONObject;
import pay.alipay.configure.AliPayConfigure;
import pay.alipay.paymodel.model.business.AliPayPagePayModel;
import pay.alipay.paymodel.model.business.AliPayTradePrecreatePayModel;
import pay.alipay.paymodel.model.business.AliPayWapPayModel;
import pay.alipay.paymodel.model.request.synchronous.AliPayTradePrecreate;
import pay.alipay.paymodel.service.AliPayPayModelStructure;
import pay.alipay.service.pay.facility.AliPayPaymentFacility;


import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

/**
 * 描述：支付宝支付工具类
 *
 * @author Cchua
 * @date 2018年12月21日
 */
public  class AliPayPaymentService {

    //https://docs.open.alipay.com/277/106748/


//             手机网站支付
//    手机网站支付接口alipay.trade.wap.pay

//    App支付接口alipay.trade.app.pay
//


//                线下当面付
//    统一收单交易支付接口alipay.trade.pay
//    统一收单线下交易预创建接口alipay.trade.precreate


//            电脑网站支付
//    统一收单下单并支付页面接口alipay.trade.page.pay


   // AliPayConfigure aliPayConfigure = new AliPayConfigure();
    AliPayConfigure aliPayConfigure = null;
    public  AliPayPaymentService(AliPayConfigure configure ){
        aliPayConfigure = configure;
    }

    public static final String Response = "response";
    public static final String Request = "request";

    /**
     * 网页支付
     *
     * @param aliPayPagePayModel 订单信息
     * @param callMode           请求模式
     */
    public String aliPayPagePay(AliPayPagePayModel aliPayPagePayModel, String callMode) {


        AliPayPaymentFacility facility = new AliPayPaymentFacility();
        String payInfo = null;
        AliPayPayModelStructure aliPayPayModelStructure = new AliPayPayModelStructure();
        Map<String, Object> bizContent = aliPayPayModelStructure.pagePayModel(aliPayPagePayModel);


        try {
            payInfo = facility.getAliPayTradePagePay(bizContent, aliPayConfigure, callMode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return payInfo;

    }


    public static Map<String, Object> getMap(String json) {
        JSONObject jsonObject = JSONObject.parseObject(json);
        Map<String, Object> valueMap = new HashMap<String, Object>();
        valueMap.putAll(jsonObject);
        return valueMap;
    }


    /**
     * 手机支付支付
     *
     * @param aliPayWapPayModel 订单信息
     * @param callMode          请求模式
     */


    public String aliPayWapPay(AliPayWapPayModel aliPayWapPayModel, String callMode) {


        AliPayPaymentFacility facility = new AliPayPaymentFacility();
        String asd = null;
        AliPayPayModelStructure aliPayPayModelStructure = new AliPayPayModelStructure();
        Map<String, Object> bizContent = aliPayPayModelStructure.wapPayModel(aliPayWapPayModel);


        try {
            asd = facility.getAliPayTradeWapPay(bizContent, aliPayConfigure, callMode);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return asd;

    }

    /**
     * 统一订单-线下当面付
     *
     * @param aliPayTradePrecreatePayModel 订单信息
     */
    public AliPayTradePrecreate aliPayTradePrecreate(AliPayTradePrecreatePayModel aliPayTradePrecreatePayModel) {


        AliPayPaymentFacility facility = new AliPayPaymentFacility();
        AliPayTradePrecreate pycode = null;
        try {

            AliPayPayModelStructure aliPayPayModelStructure = new AliPayPayModelStructure();
            Map<String, Object> bizContent = aliPayPayModelStructure.tradePrecreatePayModel(aliPayTradePrecreatePayModel);
            pycode = facility.getAliPayTradePrecreate(bizContent, aliPayConfigure);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return pycode;

    }

    /**
     * 统一订单-线下当面付
     *
     * @param request        支付宝监听信息
     *
     */


}
