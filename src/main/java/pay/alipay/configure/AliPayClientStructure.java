package pay.alipay.configure;

import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;

public class AliPayClientStructure {
/*
    public AlipayClient aliPayClientConfig() {
        AliPayConfigure aliPayConfigure = new AliPayConfigure();

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
**/

public AliPayClientStructure(){

}


        public AlipayClient aliPayClientConfig(AliPayConfigure aliPayConfigure) {
               // AliPayConfigure aliPayConfigure = new AliPayConfigure();
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



}
