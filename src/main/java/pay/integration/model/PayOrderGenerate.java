package pay.integration.model;

import java.math.BigDecimal;

public class PayOrderGenerate {

    public final String WeCaht = "WeChat";

    public final String AliPay = "AliPay";

    private String body;//支付标题

    private String subject ; //支付信息

    private Long outTradeNo;//内部支付单号

    private String paymentMethod;//支付方式

    private String ip;//支付方式

    private BigDecimal totalAmount;//支付金額

    private boolean state ;


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public Long getOutTradeNo() {
        return outTradeNo;
    }

    public void setOutTradeNo(Long outTradeNo) {
        this.outTradeNo = outTradeNo;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
    }
}
