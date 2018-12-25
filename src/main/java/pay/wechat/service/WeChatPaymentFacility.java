package pay.wechat.service;

import pay.wechat.configure.WechatConfigure;
import pay.wechat.paymodel.OrderRequest;
import pay.wechat.paymodel.WeCahtWebPayModel;
import pay.wechat.tool.HttpUtil;
import pay.wechat.tool.MD5Util;
import pay.wechat.tool.PayToolUtil;
import pay.wechat.tool.XmlUtil;

import javax.xml.parsers.ParserConfigurationException;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;

public class WeChatPaymentFacility {

    public String webPayQrCode(WeCahtWebPayModel weCahtWebPayModel) {


        OrderRequest json = new OrderRequest();
        WechatConfigure wechatConfigure = new WechatConfigure();


        json.setAppid(wechatConfigure.getAppId());
        json.setNotifyUrl(wechatConfigure.getNotifyUrl());
        json.setMchId(wechatConfigure.getMchId());

       // WeCahtWebPayModel weCahtWebPayModel = new WeCahtWebPayModel();



        json.setBody(weCahtWebPayModel.getBody());
        json.setAttach(weCahtWebPayModel.getAttach());
        json.setOutTradeNo(weCahtWebPayModel.getOutTradeNo());

        json.setDeviceInfo("WEB");/* 自定义参数，可以为终端设备号(门店号或收银设备ID)，PC网页或公众号内支付可以传"WEB" */

        json.setNonceStr(UUID.randomUUID().toString());
        //  json.setOpenid(orderInfo.getOpenid());

        json.setSpbillCreateIp(weCahtWebPayModel.getSpbillCreateIp());
        json.setTotalFee(weCahtWebPayModel.getTotalFee());
        json.setTradeType("NATIVE");


        String s = UUID.randomUUID().toString();

        String uuid = s.replace("-", "");
        json.setNonceStr(uuid);


        SortedMap<Object, Object> parameters = new TreeMap<Object, Object>();

        json.setDeviceInfo("WEB");

        parameters.put("appid", json.getAppid());
        parameters.put("mch_id", wechatConfigure.getMchId());
        // parameters.put("attach", json.getAttach());//附加数据

        parameters.put("device_info", json.getDeviceInfo());
        parameters.put("nonce_str", json.getNonceStr());//随机字符串
        parameters.put("body", json.getBody());
        parameters.put("out_trade_no", json.getOutTradeNo());// 商户系统内部订单号

        parameters.put("total_fee", String.valueOf(json.getTotalFee()));// 标价金额（分）*/
        parameters.put("spbill_create_ip", json.getSpbillCreateIp());// 电脑ip地址
        parameters.put("notify_url", wechatConfigure.getNotifyUrl());
        parameters.put("trade_type", json.getTradeType());

        String KeysignStr = MD5Util.getWXPayMD5(parameters, wechatConfigure.getApiKey());
        parameters.put("sign", KeysignStr);

        // String sign = PayToolUtil.createSign("UTF-8", parameters,wechatConfigure.getApiKey());
        json.setSign(KeysignStr);
        String requestXML = PayToolUtil.getRequestXml(parameters);

        System.out.println(requestXML);

        String result = HttpUtil.postData("https://api.mch.weixin.qq.com/pay/unifiedorder", requestXML);


        XmlUtil xmlUtil = new XmlUtil();
        Map<String, String> Xmldata = null;
        try {
            Xmldata = xmlUtil.XmlAnalysis(result);
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }

        String urlCode = (String) Xmldata.get("code_url");

        return urlCode;

/*
        String preOrderXml = "<xml>" + "<appid>" + json.getAppid() + "</appid>" + "<attach>" + "<![CDATA["
                + json.getAttach() + "]]></attach>" + "<body><![CDATA[" + json.getBody() + "]]></body>"
                + "<device_info>" + json.getDeviceInfo() + "</device_info>" + "<mch_id>" + json.getMchId() + "</mch_id>"
                + "<nonce_str>" + json.getNonceStr() + "</nonce_str>" + "<notify_url>" + json.getNotifyUrl()
                + "</notify_url>" + "<openid>" + json.getOpenid() + "</openid>" + "<out_trade_no>"
                + json.getOutTradeNo() + "</out_trade_no>" + "<spbill_create_ip>" + json.getSpbillCreateIp()
                + "</spbill_create_ip>" + "<total_fee>" + json.getTotalFee() + "</total_fee>" + "<trade_type>"
                + json.getTradeType() + "</trade_type>" + "<sign>" + json.getSign() + "</sign>" + "</xml>";
*

        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());
        HttpEntity<String> formEntity = new HttpEntity<String>(preOrderXml, headers);
        String  url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
        String result = HttpUtil.doPostSSL(url, preOrderXml);

*/


    }

}
