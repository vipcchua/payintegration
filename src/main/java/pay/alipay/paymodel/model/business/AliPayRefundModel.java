package pay.alipay.paymodel.model.business;

import java.math.BigDecimal;

public class AliPayRefundModel {


    private Long out_trade_no;

    private Long trade_no;

    private BigDecimal refund_amount;

    private String refund_reason;

    private String out_request_no;

    private String operator_id;

    private String store_id;

    private String terminal_id;

    public Long getOut_trade_no() {
        return out_trade_no;
    }

    public void setOut_trade_no(Long out_trade_no) {
        this.out_trade_no = out_trade_no;
    }

    public Long getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(Long trade_no) {
        this.trade_no = trade_no;
    }

    public BigDecimal getRefund_amount() {
        return refund_amount;
    }

    public void setRefund_amount(BigDecimal refund_amount) {
        this.refund_amount = refund_amount;
    }

    public String getRefund_reason() {
        return refund_reason;
    }

    public void setRefund_reason(String refund_reason) {
        this.refund_reason = refund_reason;
    }

    public String getOut_request_no() {
        return out_request_no;
    }

    public void setOut_request_no(String out_request_no) {
        this.out_request_no = out_request_no;
    }

    public String getOperator_id() {
        return operator_id;
    }

    public void setOperator_id(String operator_id) {
        this.operator_id = operator_id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getTerminal_id() {
        return terminal_id;
    }

    public void setTerminal_id(String terminal_id) {
        this.terminal_id = terminal_id;
    }
}