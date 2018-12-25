package pay.integration.utils;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

/**
 * 货币工具类，包含货币计算加、减、乘、除、比较等工具类方法
 * @author wangjc
 * @date 2014-12-10
 */
public class CurrencyUtils {

    public static final Map<String, String> CURRENCY_NAME_CODE_PAIRS = new HashMap<String, String>(22);

    static{
        CURRENCY_NAME_CODE_PAIRS.put("澳大利亚元", "AUD");
        CURRENCY_NAME_CODE_PAIRS.put("巴西里亚尔", "BRL");
        CURRENCY_NAME_CODE_PAIRS.put("加拿大元", "CAD");
        CURRENCY_NAME_CODE_PAIRS.put("瑞士法郎", "CHF");
        CURRENCY_NAME_CODE_PAIRS.put("丹麦克朗", "DKK");
        CURRENCY_NAME_CODE_PAIRS.put("欧元", "EUR");
        CURRENCY_NAME_CODE_PAIRS.put("英镑", "GBP");
        CURRENCY_NAME_CODE_PAIRS.put("港币", "HKD");
        CURRENCY_NAME_CODE_PAIRS.put("印尼卢比", "IDR");
        CURRENCY_NAME_CODE_PAIRS.put("日元", "JPY");
        CURRENCY_NAME_CODE_PAIRS.put("韩国元", "KRW");
        CURRENCY_NAME_CODE_PAIRS.put("澳门元", "MOP");
        CURRENCY_NAME_CODE_PAIRS.put("林吉特", "MYR");
        CURRENCY_NAME_CODE_PAIRS.put("挪威克朗", "NOK");
        CURRENCY_NAME_CODE_PAIRS.put("新西兰元", "NZD");
        CURRENCY_NAME_CODE_PAIRS.put("菲律宾比索", "PHP");
        CURRENCY_NAME_CODE_PAIRS.put("卢布", "RUB");
        CURRENCY_NAME_CODE_PAIRS.put("瑞典克朗", "SEK");
        CURRENCY_NAME_CODE_PAIRS.put("新加坡元", "SGD");
        CURRENCY_NAME_CODE_PAIRS.put("泰国铢", "THB");
        CURRENCY_NAME_CODE_PAIRS.put("新台币", "TWD");
        CURRENCY_NAME_CODE_PAIRS.put("美元", "USD");
    }

    private CurrencyUtils(){}


    public static boolean greaterThan(BigDecimal valA, BigDecimal valB){
        return (valA.compareTo(valB) > 0);
    }


    public static boolean equals(BigDecimal valA, BigDecimal valB){
        return (valA.compareTo(valB) == 0);
    }


    public static BigDecimal add(BigDecimal valA, BigDecimal valB){
        return valA.add(valB).setScale(2, RoundingMode.HALF_UP);
    }


    public static BigDecimal add(BigDecimal valA, BigDecimal valB, int scale){
        return valA.add(valB).setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal minus(BigDecimal valA, BigDecimal valB){
        return valA.add(valB.negate()).setScale(2, RoundingMode.HALF_UP);
    }


    public static BigDecimal minus(BigDecimal valA, BigDecimal valB, int scale){
        return valA.add(valB.negate()).setScale(scale, RoundingMode.HALF_UP);
    }

    public static BigDecimal multiply(BigDecimal valA, BigDecimal valB){
        return valA.multiply(valB).setScale(2, RoundingMode.HALF_UP);
    }


    public static BigDecimal multiply(BigDecimal valA, BigDecimal valB, int scale){
        return valA.multiply(valB).setScale(scale, RoundingMode.HALF_UP);
    }


    public static BigDecimal divide(BigDecimal valA, BigDecimal valB){
        if(BigDecimal.ZERO.compareTo(valB) == 0){
            throw new ArithmeticException("除数不能为0");
        }
        return valA.divide(valB, 2, RoundingMode.HALF_UP);
    }

    public static BigDecimal divide(BigDecimal valA, BigDecimal valB, int scale){
        if(BigDecimal.ZERO.compareTo(valB) == 0){
            throw new ArithmeticException("除数不能为0");
        }
        return valA.divide(valB, scale, RoundingMode.HALF_UP);
    }


    public static BigDecimal toBigDecimal(String val){
        if(val == null || "".equals(val.trim())){
            return BigDecimal.ZERO;
        }else{
            return new BigDecimal(val);
        }
    }

}