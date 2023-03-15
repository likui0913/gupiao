package com.gupiao.util;

/**
 * 通用方法类
 */
public class Util {

    //定义交易所
    public static String BJ = "bj";
    public static String SZ = "sz";
    public static String SH = "SH";
    public static String KCB = "kcb";

    /**
     * 通过code，获取股票对应交易所
     * @param code
     * @return
     */
    public static String codeToExchange(String code) {

        String res = "null";

        if( code.startsWith("000") || code.startsWith("001") || code.startsWith("002") || code.startsWith("003") ||
           code.startsWith("300") || code.startsWith("301") ){
            res = SZ;
        } else if( code.startsWith("600") || code.startsWith("601") || code.startsWith("603") || code.startsWith("605") ){
            res = SH;
        } else if(code.startsWith("430") || code.startsWith("830") || code.startsWith("831") || code.startsWith("832") ||
                code.startsWith("833") || code.startsWith("834") || code.startsWith("835") || code.startsWith("836") ||
                code.startsWith("837") || code.startsWith("838") || code.startsWith("839") || code.startsWith("870") ||
                code.startsWith("871") || code.startsWith("872") || code.startsWith("873") ){
            res = BJ;
        } else if(code.startsWith("688") || code.startsWith("689") ){
            res = KCB;
        }

        return res;

    }

    /**
     * 基于Code获取东方财富对应股票详细信息
     * @param code
     * @return
     */
    public static String getUrlByCode(String code){

        String exchange = codeToExchange(code);
        if(exchange.equals(SZ)){
            return "http://quote.eastmoney.com/sz" + code + ".html";
        }else if(exchange.equals(SH)){
            return "http://quote.eastmoney.com/sh" + code + ".html";
        }else if(exchange.equals(BJ)){
            return "http://quote.eastmoney.com/bj/" + code + ".html";
        }else if(exchange.equals(KCB)){
            return "http://quote.eastmoney.com/kcb/" + code + ".html";
        }else{
            return "未知";
        }

    }

}
