# 欢迎使用 fastpay 快速接入支付

无聊写出这套东西的主要原因是，我还是学生的时候就在学校写了好多次微信、支付宝的第三方支付接入，出来工作又刚好要写微信支付宝的第三方接入，来去去都那些东西，偶尔还会遇到一些重重复复的小坑，所以这次开发干脆封装了起来！后续有时间还会加入银联和微信公众号、小程序的快速接入！欢迎各位 dalao 多提意见

## [AliPay/Wechat Aay /integration 快速接入整合](https://github.com/vipcchua/payintegration)

## 核心特性

- 使用方法与配置
- 支付宝支付快速接入
- 微信支付快速接入
- 微信与支付宝（当面付）同订单二维码支付生成
- 支付宝对账单获取
- 微信对账单获取

## 如何接入

### 安装

- 要使用 fastpay ，只需在类路径中包含 fastpay-x.x.x.jar 文件即可。

- 如果您正在使用 Maven，只需将以下依赖项添加到您的 pom.xml：

```java
<!-- https://mvnrepository.com/artifact/com.github.vipcchua/fastpay -->
<dependency>
  <groupId>com.github.vipcchua</groupId>
  <artifactId>fastpay</artifactId>
  <version>1.0.6.1</version>
</dependency>
```

### 配置

请预先配置好 **（重要！）**

    fastpay.wechat.configure.WechatConfigure

    fastpay.alipay.configure.AliPayConfigure

这些配置可以在包里面找到，如果使用的是 spring boot 可以进行预配置 ，具体步骤这里不再演示

## 支付宝快速接入

#### 支付宝配置方法使用

接入的方法有很多种 以下举例都可以进行接入 其实主要就是将 AliPayment 里面传入配置文件

```java
FastPayAliPay f1 = new FastPayAliPayBuilder().build();
AliPayment a1 = f1.getAliPayment(new AliPayConfigure());

FastPayAliPay f2 = new FastPayAliPayBuilder().build(new AliPayConfigure());
AliPayment a2 = f2.getAliPayment();

FastPayAliPay f3 = new FastPayAliPayService(new AliPayConfigure());
AliPayment a3 = f3.getAliPayment();

FastPayAliPay f4 = new FastPayAliPayService();
AliPayment a4 = f4.getAliPayment(new AliPayConfigure());

AliPayment a5 = new AliPaymentService(new AliPayConfigure());
```

#### 支付宝支付快速接入

因为支付宝和微信其实每一步操作都是需要配置文件里面的相关信息的， ** 所以每一个操作都是要带上你的 config**，当然你可以让你的配置文件持续放在内存里面这样可以有效减少让 java 不断去读取内存的次数，这里没有做成自动注入的主要原因还是如果项目有多个支付账户的时候，可以让用户自行决定与哪个账户发生相关操作

#### 支付宝快速接入

payment 的配置注入提上方法都可以，最重要的还是配置要传

```java
AliPayment aliPayment = new AliPaymentService(new AliPayConfigure());
```

```java
public interface AliPayment {

  /**
    * 退款
    */

  AlipayTradeRefund tradeRefund(AliPayTradeRefundModel aliPayRefundModel);

  /***
    * 转账
    */

  AliPayFundTransToaccountTransfer transferAccounts(AlipayToaccountTransferModel aliPayRefundModel);

  /**
    * 网页支付
    */

  AliPayTradePage pagePay(AliPayPagePayModel aliPayRefundModel);

  /**
    * 手机支付
    */

  AlipayTradeWapPay wapPay(AliPayWapPayModel wapPayModel);

  /**
    * 统一收单线下交易预创建
    */

  AliPayTradePrecreate tradePrecreatePay(AliPayTradePrecreatePayModel tradePrecreatePayModel);

  /**
    * 回调信息
    */

  AccountAlipayBusiness notify(HttpServletRequest request);

  /**
    * 支付宝三合一支付
    */

  Object unifiedPayment(UnifiedPaymentModel model);
}
```

在所有的 AliPayment 方法 主要是支撑支付宝的快速接入 ，所有的基础方法都已经封装在 AliPayment 里面，更进阶的使用可以自己直接访问底层的 Service 层进行复杂操作，自己定义需要的 Facility 传入需要相关的复杂订单参数

##### 支付宝-电脑网站支付

```java
/**
  * 电脑网站支付
  * https://docs.open.alipay.com/270/alipay.trade.page.pay/
  */
public AliPayTradePage pagePay() {
  AliPayment aliPayment = new AliPaymentService(new AliPayConfigure());
  AliPayPagePayModel model = new AliPayPagePayModel();

  model.setTotal_amount(BigDecimal.valueOf(1.11));
  model.setSubject("订单标题");
  model.setBody("body");//不是必填
  model.setPassback_params("asd");//需要原样返回的数据
  model.setOut_trade_no("123123123");//订单编号

  AliPayTradePage payTradePage = aliPayment.pagePay(model);
  return payTradePage;
}
```

##### 支付宝-手机支付

```java
/**
  * 手机订单支付
  * https://docs.open.alipay.com/203/107090
  */
public AlipayTradeWapPay wapPay() {
  AliPayment aliPayment = new AliPaymentService(new AliPayConfigure());
  AliPayWapPayModel model = new AliPayWapPayModel();

  model.setSubject("订单标题");
  model.setOut_trade_no("123123123");//订单编号
  model.setTotal_amount(BigDecimal.valueOf(1.11));
  model.setBody("body");//不是必填
  model.setPassback_params("asd");//需要原样返回的数据

  AlipayTradeWapPay wapPay = aliPayment.wapPay(model);
  return wapPay;
}
```

##### 支付宝-退款

退款需要支付宝的订单号或者是内部账号 Out_trade_no 和 trade_no 只需要填写其中一个就可以了，如果有别的参数需要一起传可以把 model 里面的其他参数一起填上

```java
/**
  * 退款使用支付宝订单号
  * https://docs.open.alipay.com/api_1/alipay.trade.refund
  */
public AlipayTradeRefund tradeRefundByTradeNo() {
  AliPayment aliPayment = new AliPaymentService(new AliPayConfigure());
  AliPayTradeRefundModel model = new AliPayTradeRefundModel();

  model.setTrade_no("123");
  model.setRefund_amount(BigDecimal.valueOf(1.11));

  AlipayTradeRefund alipayTradeRefund = aliPayment.tradeRefund(model);
  return alipayTradeRefund;
}
```

```java
/**
  * 退款使用内部订单号
  * https://docs.open.alipay.com/api_1/alipay.trade.refund
  */
public AlipayTradeRefund tradeRefundByOutTradeNo() {
  AliPayment aliPayment = new AliPaymentService(new AliPayConfigure());
  AliPayTradeRefundModel model = new AliPayTradeRefundModel();

  model.setOut_trade_no("123");
  model.setRefund_amount(BigDecimal.valueOf(1.11));

  AlipayTradeRefund alipayTradeRefund = aliPayment.tradeRefund(model);
  return alipayTradeRefund;
}
```

##### 支付宝-转账

```java
/**
  * 转账
  * https://docs.open.alipay.com/api_28/alipay.fund.trans.toaccount.transfer
  */
public AliPayFundTransToaccountTransfer transfer() {
  AliPayment aliPayment = new AliPaymentService(new AliPayConfigure());
  AlipayToaccountTransferModel model = new AlipayToaccountTransferModel();

  model.setOut_biz_no("123");
  model.setPayee_type(model.ALIPAY_LOGONID);
  model.setPayee_account("13800138000");
  model.setAmount(BigDecimal.valueOf(12.34));
  model.setPayer_show_name("上海交通卡退款");
  model.setPayee_real_name("张三");
  model.setRemark("转账备注");

  AliPayFundTransToaccountTransfer transfer = aliPayment.transferAccounts(model);
  return transfer;
}
```

##### 统一收单线下交易预创建

```java
/**
  * 统一收单线下交易预创建 tradePrecreatePay
  * https://docs.open.alipay.com/api_1/alipay.trade.precreate
  */
public AliPayTradePrecreate tradePrecreatePay() {
  AliPayment aliPayment = new AliPaymentService(new AliPayConfigure());
  AliPayTradePrecreatePayModel model = new AliPayTradePrecreatePayModel();

  model.setSubject("订单标题");
  model.setOut_trade_no("123123123");//订单编号
  model.setTotal_amount(BigDecimal.valueOf(1.11));
  model.setTimeout_express("90m");//有效时间



  AliPayTradePrecreate wapPay = aliPayment.tradePrecreatePay(model);
  return wapPay;
}
```

##### 统一收单线下交易预创建

```java
/**
  * 统一收单线下交易预创建 tradePrecreatePay
  * https://docs.open.alipay.com/api_1/alipay.trade.precreate
  */
public AliPayTradePrecreate tradePrecreatePay() {
  AliPayment aliPayment = new AliPaymentService(new AliPayConfigure());
  AliPayTradePrecreatePayModel model = new AliPayTradePrecreatePayModel();

  model.setSubject("订单标题");
  model.setOut_trade_no("123123123");//订单编号
  model.setTotal_amount(BigDecimal.valueOf(1.11));
  model.setTimeout_express("90m");//有效时间



  AliPayTradePrecreate wapPay = aliPayment.tradePrecreatePay(model);
  return wapPay;
}
```

##### 下载对账单

因为支付宝的对账单是以下载文件的形式，所以想要读取对账单需要读取支付宝下载下来的 CSV 文件 ，默认的 bill 方法中 filePath 是会将 CSV 文件读取完成后将下载下来的 CSV 文件删除的 在方法 **AliPayAccountStatementService** 中提供了更多的方法让你读取、下载支付支付宝的对账单文件

```java
String day = "2018-12-25";
String filePath = "D://dowm/";
String zipfilePath = "D://dowm/";

AliPayment aliPayment = new AliPaymentService(new AliPayConfigure());


//AliPayAccountStatementService statementService = new AliPayAccountStatementService(aliPayConfigure);

//     AliPayAccountStatementAllModel model = statementService.getAccountStatement(billDate, filePath, zipFilePath);

AliPayAccountStatementAllModel model = aliPayment.bill(day, filePath, zipfilePath);
return model;

```

#### 支付宝三合一支付

```java
Object unifiedPayment(UnifiedPaymentModel model);
```

##### 自己封装

如果你觉得已经封装好的是一个 object 不好处理，当然你也可以自己进行封装

```java
public class IntegratedPayService implements IntegratedPay {
  public final String WeChat = "WeChat";
  public final String AliPay = "AliPay";

  AliPayConfigure aliPayConfigure = new AliPayConfigureTodo().getConfig();
  WechatConfigure wechatConfigure = new WechatConfigureTodo().getconfig();

  public PaymentInformationModel qrCodePay(IntegratedPayModel integratedPay, String mode) {
    try {
      PaymentInformation paymentInformationService = null;
      PayOrderGenerate payOrderGenerate = new PayOrderGenerate();
      payOrderGenerate.setTotalAmount(integratedPay.getTotalAmount());
      payOrderGenerate.setOutTradeNo(integratedPay.getOutTradeNo());
      payOrderGenerate.setBody(integratedPay.getBody());
      payOrderGenerate.setSubject(integratedPay.getSubject());
      payOrderGenerate.setPaymentMethod(mode);
      payOrderGenerate.setIp(integratedPay.getIp());
      if (mode.equals(AliPay)) {
        AliPayConfigure config = new AliPayConfigureTodo().getConfig();
        paymentInformationService = new PaymentInformationService(config);
      }
      if (mode.equals(WeChat)) {
        WechatConfigure config = new WechatConfigureTodo().getconfig();
        paymentInformationService = new PaymentInformationService(config);
      }
      PaymentInformationModel paymentInformation = paymentInformationService.qrCodePay(payOrderGenerate, mode);
      return paymentInformation;
    } catch (Exception e) {
      return null;
    }
  }
}
```

#### 微信配置方法使用

```java
FastPayWeChat w1 = new FastPayWeChatBuilder().build(new WechatConfigure());
WeChatPayment b1 = w1.getWeChatPayment();

FastPayWeChat w2 = new FastPayWeChatBuilder().build();
WeChatPayment b2 = w2.getWeChatPayment(new WechatConfigure());

FastPayWeChat w3 = new FastPayWeChatService(new WechatConfigure());
WeChatPayment b3 = w3.getWeChatPayment();

FastPayWeChat w4 = new FastPayWeChatService();
WeChatPayment b4 = w4.getWeChatPayment(new WechatConfigure());

WeChatPayment b5 = new WeChatPaymentService(new WechatConfigure());
```

#### 微信快速接入

微信的快速接入其实和支付宝差不多最主要的是往 Payment 里面放入了配置文件， **因为每一步操作都是需要相关信息的** ，更进阶的使用可以自己直接访问底层的 Service 层进行复杂操作，自己定义需要的 Facility 传入需要相关的复杂订单参数

```java
WeChatPayment weChatPaymen = new WeChatPaymentService(new WechatConfigure());
```

```java

public interface WeChatPayment {
  /**
    * 统一下单
    */
  Map<String, String> webPayQrCode(QrCodeRequest qrCodeRequest);

  /**
    * 查询订单
    */

  WxOrderQueryResponse orderQuery(OrderQueryRequest orderQueryRequest);


  /**
    * 关闭订单
    */

  WxPayCloseOrderResponse closeOrder(CloseOrderRequest request);

  /**
    * 申请退款
    */

  WxRefundResponse refundOrder(RefundRequest refundRequest);

  /**
    * 查询退款
    */

  Map<String, String> refundQuery(RefundQueryRequest refundRequest);


  /**
    * 下载对账单
    */

  String downloadBillRequest(DownloadBillRequest request);

  /**
    * 下载资金账单
    */

  String downloadFundFlowRequest(DownloadFundFlowRequest request);

  /**
    * 微信回调处理
    */

  WechatPayNotifyResult notifyPay(String request);
}

```

#### 微信 NATIVE 统一下单

```java
/*
  *  NATIVE 统一下单
  * https://pay.weixin.qq.com/wiki/doc/api/native.php?chapter=9_1
  */
public Map<String, String> pagePay() {
  WeChatPayment weChatPayment = new WeChatPaymentService(new WechatConfigure());
  QrCodeRequest request = new QrCodeRequest();
  request.setBody("商品简单描述，该字段请按照规范传递，具体请见参数规定");
  request.setOutTradeNo("123");
  request.setTotalFee(1);//注意！ 这里没进行自动转换分->元  订单总金额，单位为分
  request.setSpBillCreateIp("8.8.8.8");//ip
  request.setAttach("{}");//附加数据，在查询API和支付通知中原样返回，可作为自定义参数使用。
  Map<String, String> data = weChatPayment.webPayQrCode(request);
  return data;
}
```

##### 微信-账单 Csv 解析

```java
package test;
import com.alibaba.fastjson.JSONObject;
import fastpay.wechat.model.bill.BillDetailsModel;
import fastpay.wechat.model.bill.BillModel;
import fastpay.wechat.model.bill.BillTotalModel;
import fastpay.wechat.service.pay.service.WeChatBillAnalysis;
import fastpay.wechat.service.pay.service.impl.WeChatBillAnalysisService;
import java.util.List;

/**
 * Copyright@ vipcchua.github.io
 * Author:Cchua
 * Date:2019/2/14
 */
public class WeChatBillTest {
  public static void main(String[] args) throws Exception {
    String responseResult = "交易时间,公众账号ID,商户号,特约商户号,设备号,微信订单号,商户订单号,用户标识,交易类型,交易状态,付款银行,货币种类,应结订单金额,代金券金额,微信退款单号,商户退款单号,退款金额,充值券退款金额,退款类型,退款状态,商品名称,商户数据包,手续费,费率,订单金额,申请退款金额,费率备注\n" +
            "`2018-12-29 13:44:33,`wx1231231231231231,`1519498421,`0,`WEB,`4200000000000000000000000000,`494,`oiIdIdIdIdIdIdIdIdIdIdIdIdId,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`0,`0,`0.00,`0.00,`,`,`充值,`,`0.00000,`1.00%,`0.01,`0.00,`\n" +
            "`2018-12-29 15:33:24,`wx1231231231231231,`1519498421,`0,`WEB,`4200000000000000000000000000,`501,`oiIdIdIdIdIdIdIdIdIdIdIdIdId,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`0,`0,`0.00,`0.00,`,`,`充值,`,`0.00000,`1.00%,`0.01,`0.00,`\n" +
            "`2018-12-29 15:02:14,`wx1231231231231231,`1519498421,`0,`WEB,`4200000000000000000000000000,`498,`oiIdIdIdIdIdIdIdIdIdIdIdIdId,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`0,`0,`0.00,`0.00,`,`,`充值,`,`0.00000,`1.00%,`0.01,`0.00,`\n" +
            "`2018-12-29 15:26:04,`wx1231231231231231,`1519498421,`0,`WEB,`4200000000000000000000000000,`499,`oiIdIdIdIdIdIdIdIdIdIdIdIdId,`NATIVE,`SUCCESS,`CFT,`CNY,`0.01,`0.00,`0,`0,`0.00,`0.00,`,`,`充值,`,`0.00000,`1.00%,`0.01,`0.00,`\n" +
            "总交易单数,应结订单总金额,退款总金额,充值券退款总金额,手续费总金额,订单总金额,申请退款总金额" +
            "`4,`0.04,`0.00,`0.00,`0.00000,`0.04,`0.00";
    WeChatBillAnalysis weChatBillAnalysisService = new WeChatBillAnalysisService();
    BillTotalModel a = weChatBillAnalysisService.getBillTotal(responseResult);
    BillModel b = weChatBillAnalysisService.getBill(responseResult);
    List<BillDetailsModel> c = weChatBillAnalysisService.getBillDetails(responseResult);
    System.out.println(JSONObject.toJSONString(a) + "\n\n\n\n\n\n\n");
    System.out.println(JSONObject.toJSONString(b) + "\n\n\n\n\n\n\n");
    System.out.println(JSONObject.toJSONString(c) + "\n\n\n\n\n\n\n");
  }
}

```

##### 微信与支付宝（当面付）同订单二维码支付生成

```java
AliPayment aliPayment = new AliPaymentService(new AliPayConfigure());
aliPayment.tradePrecreatePay(AliPayTradePrecreatePayModel tradePrecreatePayModel);
```
