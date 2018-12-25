package pay.integration.service;

import pay.alipay.configure.AliPayConfigure;
import pay.alipay.paymodel.model.business.AliPayTradePrecreatePayModel;
import pay.alipay.paymodel.model.request.synchronous.AliPayTradePrecreate;
import pay.alipay.service.pay.service.AliPayPaymentService;
import pay.integration.model.PayOrderGenerate;
import pay.integration.model.PaymentInformation;
import pay.integration.utils.AmountUtils;
import pay.wechat.paymodel.WeCahtWebPayModel;
import pay.wechat.service.WeChatPaymentFacility;

public class PaymentInformationService {

    private final String WeCaht = "WeChat";

    private final String AliPay = "AliPay";

    public PaymentInformation information(PayOrderGenerate payOrderGenerate){


        if (payOrderGenerate.getPaymentMethod().equals(AliPay)){

            AliPayConfigure aliPayConfigure = new AliPayConfigure();
            AliPayPaymentService aliPayPaymentService = new AliPayPaymentService(aliPayConfigure);
            AliPayTradePrecreatePayModel paymentifo = new AliPayTradePrecreatePayModel();

            String pycode = null;


            paymentifo.setOut_trade_no(payOrderGenerate.getOutTradeNo().toString());
            paymentifo.setTotal_amount(payOrderGenerate.getTotalAmount());
            paymentifo.setBizContent(payOrderGenerate.getBody());
            paymentifo.setSubject(payOrderGenerate.getSubject());

        AliPayTradePrecreate toaccountTransfer  =    aliPayPaymentService.aliPayTradePrecreate(paymentifo);
            pycode = toaccountTransfer.getAlipay_trade_precreate_response().getCode();

            PaymentInformation paymentInformation = new PaymentInformation();
            paymentInformation.setPaymentMethod(AliPay);
            paymentInformation.setQrCode(pycode);
            paymentInformation.setOutTradeNo(payOrderGenerate.getOutTradeNo().toString());
            paymentInformation.setTotalAmount(payOrderGenerate.getTotalAmount());

            return  paymentInformation;
        }
       else if (payOrderGenerate.getPaymentMethod().equals(WeCaht)){
            WeChatPaymentFacility weChatPaymentFacility = new WeChatPaymentFacility();

//            WeCahtWebPayModel weCahtWebPayModel = new WeCahtWebPayModel();
//            weCahtWebPayModel.setBody("可樂");
//            weCahtWebPayModel.setAttach("商城支付");
//            weCahtWebPayModel.setOutTradeNo("123");
//            weCahtWebPayModel.setSpbillCreateIp("127.9.9.1");
//            weCahtWebPayModel.setTotalFee(123);


            WeCahtWebPayModel weCahtWebPayModel = new WeCahtWebPayModel();
            weCahtWebPayModel.setBody(payOrderGenerate.getBody());
            weCahtWebPayModel.setAttach(payOrderGenerate.getSubject());
            weCahtWebPayModel.setOutTradeNo(payOrderGenerate.getOutTradeNo().toString());
            weCahtWebPayModel.setSpbillCreateIp(payOrderGenerate.getIp());

          Integer fen = AmountUtils.wechatCorrectYuan2Fen(payOrderGenerate.getTotalAmount());
          weCahtWebPayModel.setTotalFee(fen);

            String urlCode =  weChatPaymentFacility.webPayQrCode(weCahtWebPayModel);

            PaymentInformation paymentInformation = new PaymentInformation() ;
            paymentInformation.setPaymentMethod(WeCaht);
            paymentInformation.setQrCode(urlCode);
            paymentInformation.setOutTradeNo(String.valueOf(payOrderGenerate.getOutTradeNo()));
            paymentInformation.setTotalAmount(payOrderGenerate.getTotalAmount());


            return  paymentInformation;
        }

return null;

    }

}
