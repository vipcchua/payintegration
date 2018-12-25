package pay.alipay.paymodel.service;

import pay.alipay.paymodel.model.business.AliPayPagePayModel;
import pay.alipay.paymodel.model.business.AliPayTradePrecreatePayModel;
import pay.alipay.paymodel.model.business.AliPayWapPayModel;
import pay.alipay.paymodel.model.business.AlipayToaccountTransferModel;

import java.io.UnsupportedEncodingException;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class AliPayPayModelStructure {

    public Map<String, Object> pagePayModel(AliPayPagePayModel aliPayPagePayModel) {
        Map<String, Object> bizContent = new HashMap<String, Object>();
        String passbackParams = null;
        try {
            passbackParams = java.net.URLEncoder.encode(aliPayPagePayModel.getPassback_params(), "UTF-8");

            bizContent.put("out_trade_no", aliPayPagePayModel.getOut_trade_no());
            bizContent.put("total_amount", aliPayPagePayModel.getTotal_amount());
            bizContent.put("subject", aliPayPagePayModel.getSubject());
            bizContent.put("body", aliPayPagePayModel.getBody());
            bizContent.put("passback_params", passbackParams);
            bizContent.put("product_code", aliPayPagePayModel.getProduct_code());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return bizContent;

    }


    public Map<String, Object> wapPayModel(AliPayWapPayModel aliPayWapPayModel) {
        Map<String, Object> bizContent = new HashMap<String, Object>();
        String passbackParams = null;
        try {
            passbackParams = java.net.URLEncoder.encode(aliPayWapPayModel.getPassback_params(), "UTF-8");

            bizContent.put("out_trade_no", aliPayWapPayModel.getOut_trade_no());
            bizContent.put("total_amount", aliPayWapPayModel.getTotal_amount());
            bizContent.put("subject", aliPayWapPayModel.getSubject());
            bizContent.put("body", aliPayWapPayModel.getBody());
            bizContent.put("passback_params", passbackParams);
            bizContent.put("product_code", aliPayWapPayModel.getProduct_code());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        return bizContent;
    }



       public Map<String, Object> tradePrecreatePayModel(AliPayTradePrecreatePayModel aliPayTradePrecreatePayModel) {
        Map<String, Object> bizContent = new HashMap<String, Object>();


        //        passbackParams = java.net.URLEncoder.encode(aliPayWapPayModel.getPassback_params(), "UTF-8");


        bizContent.put("out_trade_no", aliPayTradePrecreatePayModel.getOut_trade_no());
        bizContent.put("total_amount", aliPayTradePrecreatePayModel.getTotal_amount().setScale(2, RoundingMode.HALF_UP).doubleValue());
        bizContent.put("subject", aliPayTradePrecreatePayModel.getSubject());
        bizContent.put("bizContent", aliPayTradePrecreatePayModel.getBizContent());

        bizContent.put("timeout_express", "90m");



        return bizContent;
    }



    public Map<String, Object> getToaccountTransferModel(AlipayToaccountTransferModel model) {
        Map<String, Object> bizContent = new HashMap<String, Object>();
        model.setPayee_type("ALIPAY_LOGONID");


        bizContent.put("out_biz_no", model.getOut_biz_no());
        bizContent.put("payee_type", model.getPayee_type());
        bizContent.put("payee_account", model.getPayee_account());
        bizContent.put("amount", model.getAmount().setScale(2, RoundingMode.HALF_UP).doubleValue());
        bizContent.put("payer_show_name", model.getPayer_show_name());
        bizContent.put("payee_real_name", model.getPayee_real_name());
        bizContent.put("remark", model.getRemark());


//    "    \"out_biz_no\":\"3142321423432\"," +
//            "    \"payee_type\":\"ALIPAY_LOGONID\"," +
//            "    \"payee_account\":\"abc@sina.com\"," +
//            "    \"amount\":\"12.23\"," +
//            "    \"payer_show_name\":\"上海交通卡退款\"," +
//            "    \"payee_real_name\":\"张三\"," +
//            "    \"remark\":\"转账备注\"," +

        return bizContent;
    }




}





