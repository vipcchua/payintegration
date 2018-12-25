package pay.alipay.service.pay.service;

import pay.alipay.configure.AliPayConfigure;
import pay.alipay.paymodel.model.business.AlipayToaccountTransferModel;
import pay.alipay.paymodel.service.AliPayPayModelStructure;
import pay.alipay.service.pay.facility.AliPayToaccountTransferFacility;

import java.io.IOException;
import java.util.Map;

public class AlipayToaccountTransferService {

    public static final String Response = "response";
    public static final String Request = "request";
    /**
     * 转账
     *
     * @param model 订单信息
     */
    public String toaccountTransResponse(AlipayToaccountTransferModel model,String pattern ) {

        AliPayConfigure aliPayConfigure = new AliPayConfigure();
        AliPayToaccountTransferFacility facility = new AliPayToaccountTransferFacility();
        String pycode = null;
        try {

            AliPayPayModelStructure aliPayPayModelStructure = new AliPayPayModelStructure();
            Map<String, Object> bizContent = aliPayPayModelStructure.getToaccountTransferModel(model);
            pycode = facility.AliPayToaccountTrans(bizContent, aliPayConfigure,pattern);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return pycode;

    }



}
