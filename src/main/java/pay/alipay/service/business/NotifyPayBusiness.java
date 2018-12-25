package pay.alipay.service.business;

import ytb.todo.alipay.model.AccountAlipayBusiness;

import pay.alipay.service.pay.service.AliPayNotifyService;

import javax.servlet.http.HttpServletRequest;
import java.util.concurrent.*;

public class NotifyPayBusiness {

    public static int Surplus = 20;
    // private ExecutorService executorService = Executors.newFixedThreadPool(20);


    private ExecutorService executor = Executors.newSingleThreadExecutor();

    void addTask(Runnable runnable) {
        executor.execute(runnable);
    }

    <V> V addTask(Callable<V> callable) {
        Future<V> submit = executor.submit(callable);
        try {
            return submit.get();
        } catch (InterruptedException e) {
            System.out.println("InterruptedException" + e.toString());
        } catch (ExecutionException e) {
            System.out.println("ExecutionException" + e.toString());
        }
        return null;
    }

    public void close() {
        executor.shutdown();
    }


    public AccountAlipayBusiness notifyUrl(HttpServletRequest request)  {


        AliPayNotifyService aliPayPaymentService = new AliPayNotifyService();

        AccountAlipayBusiness param = aliPayPaymentService.verifyingSignature(request,  AccountAlipayBusiness.class);


        if (param!=null) {
            System.out.println("支付宝回调签名认证成功");
            // 按照支付结果异步通知中的描述，对支付结果中的业务内容进行1\2\3\4二次校验，校验成功后在response中返回success，校验失败返回failure
            //   this.check(params);
            // 另起线程处理业务
            //     executorService.execute(new Runnable() {

       /*     addTask(new Runnable() {

                @Override
                public void run() {

                    //  AlipayBusinessSql(param);

                    //     AlipayNotifyParam param = buildAlipayNotifyParam(params);
                    String trade_status = param.getTradeStatus();
                    // 支付成功
                    if (trade_status.equals(AlipayTradeStatus.TRADE_SUCCESS.getStatus())
                            || trade_status.equals(AlipayTradeStatus.TRADE_FINISHED.getStatus())) {
                        // 处理支付成功逻辑
                        try {


                                    // 处理业务逻辑。。。
                                 //   myService.process(param);

                            System.out.println("成功");
                        } catch (Exception e) {
                            //                 System.out.println ("支付宝回调业务处理报错,params:" + paramsJson, e);
                        }
                    } else {
                        //           System.out.println ("没有处理支付宝回调业务，支付宝交易状态：{},params:{}",trade_status,paramsJson);
                    }
                }


            });*/




            executor.shutdown();
            // 如果签名验证正确，立即返回success，后续业务另起线程单独处理
            // 业务处理失败，可查看日志进行补偿，跟支付宝已经没多大关系。
        //    return "success";
            return param;
        } else {
            //    System.out.println ("支付宝回调签名认证失败，signVerified=false, paramsJson:{}", paramsJson);
            //  return "failure";
            return param;
        }



        // System.out.println("================== signVerified ==================" + signVerified);
   /*/     if (signVerified) {
       if ("TRADE_SUCCESS".equals(params.get("trade_status"))) {
                //付款金额
                String amount = params.get("buyer_pay_amount");
                //商户订单号
                String out_trade_no = params.get("out_trade_no");
                //支付宝交易号
                String trade_no = params.get("trade_no");
                //附加数据
                String passback_params = URLDecoder.decode(params.get("passback_params"));
             System.out.println("amount=" + amount + ",out_trade_no=" + out_trade_no + ",trade_no=" + trade_no + "," + passback_params);
                return "success";
            }
            return "success";
        }*/
        //    return "fail";
    }




}
