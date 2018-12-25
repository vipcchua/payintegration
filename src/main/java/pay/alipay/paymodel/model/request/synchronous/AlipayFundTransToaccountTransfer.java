package pay.alipay.paymodel.model.request.synchronous;


import com.alipay.api.response.AlipayFundTransToaccountTransferResponse;

public class AlipayFundTransToaccountTransfer {

//{
//	"alipay_fund_trans_toaccount_transfer_response": {
//		"code": "10000",
//		"msg": "Success",
//		"order_id": "20181225110070001502050067872622",
//		"out_biz_no": "1545714699772",  自己的
//		"pay_date": "2018-12-25 13:11:46"
//	},
//	"sign": "ASD"
//
//}


    private AlipayFundTransToaccountTransferResponse alipay_fund_trans_toaccount_transfer_response;

    private  String sign;

    public AlipayFundTransToaccountTransferResponse getAlipay_fund_trans_toaccount_transfer_response() {
        return alipay_fund_trans_toaccount_transfer_response;
    }

    public void setAlipay_fund_trans_toaccount_transfer_response(AlipayFundTransToaccountTransferResponse alipay_fund_trans_toaccount_transfer_response) {
        this.alipay_fund_trans_toaccount_transfer_response = alipay_fund_trans_toaccount_transfer_response;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }
}
