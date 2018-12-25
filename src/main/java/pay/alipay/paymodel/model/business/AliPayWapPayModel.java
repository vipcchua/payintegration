package pay.alipay.paymodel.model.business;

import java.math.BigDecimal;

public class AliPayWapPayModel {


    private Long out_trade_no;

    private BigDecimal total_amount;

    private String subject;

    private String body;

    private String product_code = "FAST_INSTANT_TRADE_PAY";

    private String passback_params;

    public Long getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(Long out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public BigDecimal getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(BigDecimal total_amount) {
        this.total_amount = total_amount;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getProduct_code() {
        return product_code;
    }

    public void setProduct_code(String product_code) {
        this.product_code = product_code;
    }

    public String getPassback_params() {
        return passback_params;
    }

    public void setPassback_params(String passback_params) {
        this.passback_params = passback_params;
    }
}