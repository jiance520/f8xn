package com.alipay.config;

import java.io.*;

/* *
 *类名：AlipayConfig
 *功能：基础配置类
 *详细：设置帐户有关信息及返回路径。
 */

public class AlipayConfig {

//↓↓↓↓↓↓↓↓↓↓请在这里配置您的基本信息↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓

	// 应用ID,您的APPID，收款账号既是您的APPID对应支付宝账号
//	public static String app_id = "2018100261572277";//我的
	public static String app_id = "2016092000558884";//沙箱测试用

	// 商户私钥，您的PKCS8格式RSA2私钥
	public static String merchant_private_key =
			"MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC1Un5zD56nqkke"+
					"c1p/DIvRB2RqqfP4VH7hjlPnokC86GYnnj1EeFisF77TlkmXw9hf2ADctbfbK0l9"+
					"kZTR5PyDbKdJNYjvs8e4FqplWEcQZeSgV8Frv8roa+coMvs0DJ1Jy41uq4msUdQw"+
					"YJBreuh6oJmD4RKhKN5WDtHSCH0KiABmUp1Nck0s03+ndHuRMuVGchx1PeatQhU5"+
					"jcdDQcKSnSju2Hgtx+T8r4z3es+rgxDcF0XIIbpyjZQCWvaCVulSBwFyypUedURU"+
					"7P80w6eeqC18oG4qVr/w82JMYHQiqWomTrQARSioUxmYNjNXiPFkhsTtp55BOqkQ"+
					"QUkyh3GTAgMBAAECggEADmJX86rGjEh++nJuOGv3X4K1nZyT5RxW4G/ZCiQd/J53"+
					"FCeCdr8RUb2kfsaKWgbIUHojP18PEczyTIrTAn+m5IonDzC3B0T80AbER7vkD1H1"+
					"/B5aEsEpSJzevpqkwPFLPQJv2tqy1j+7lIbkjhI3BRteck7VmoFSegM88+uGd+JF"+
					"w+qh4u2KtJJfTZELPUPdBc+VvfIlu+D8yv3IRsi/4AdGPAGPHJHpIUYOeZzUrOeI"+
					"i9hr35mxX+A58WZJDFB1GHkU5+p5jxtM5+xxTKkpJ/+4XF137TTP0QFDMinhUMd3"+
					"J8rSohTwfvidz9F9HnbCnnEWF2TpHylXx/loKz4zoQKBgQDt4sYmO+JUTIBzBF6A"+
					"Vu12unKyFmciGxZDPPx7BZ1BnQQwUfD3ffIFpXStXirIX6XPczUjkrhPNi54czNY"+
					"LjX2AhGph6xPyw7X2XCcN7oIqBwNDqcDGHqf0BPsrizQ4CCQA41gBW0YxxvoOLhC"+
					"cU9pNVbaoP5C6h9UgxXNAPbSAwKBgQDDIRkOQf5jCFpX1HTtFJjt2VL/itDf8n+x"+
					"Ssgv/4yjlhBqPt2Zf2HHWgcJLIafKpFOC7ju8s+kkpxfFgM1OG5JY0B3IwlSAEoc"+
					"KRb9czEFq5wcruGe741jpVNDOWc3yQU8dLEoA1WYn9GrkN14XI4SySBtc6jjYzJt"+
					"QSI4jgUVMQKBgQCMvO7ILgOwJfaUlZAnU8Z3yMUUsYZgui/IpesmCWNJL1EMGV57"+
					"veqTBplp/F1JYLUjb6CPpD0guAh0QIuu7uAD5KN4bgk2yAbHEyC5e4q3lmXKH9IV"+
					"VcUuiZdQHpfrF76MFV3t4ifyUdIj6HKy9ICr2N/Cxx7GjebtRASA3cIo6wKBgQCl"+
					"GW0UxjvSWS/vnoCsVEAtBzOVqkOfKJRoUwjcq9G4W/FZpAVlqgT2YrMPTRA+vgqh"+
					"yYNZEjMkYTRTs9FVB4ExMPxLkWaCbmJGP3IHUp7I2HHHRyT5kGDGTg0MFRbDeZ+n"+
					"jmiSnXegIqGKE5GAFHfVZz27vh/I/ZaeIH0u8uNUEQKBgCNd1X5aW0kDg0SF2JVQ"+
					"Cv4ChSRUlF06gOG7+pGCzUZ+9fikk0VRmzz/BpnY/m0cQDHjdHthYBv16vlzNY2O"+
					"yZpqdw4hjB4W+giVaz4jbMLs36kiBRleuyKnRkBopVFAI71Uq9gNn/NKjOK8ayWU"+
					"7adopUMv13TQOuz3Wll9f4hr";


	// 支付宝公钥,查看地址：https://openhome.alipay.com/platform/keyManage.htm 对应APPID下的支付宝公钥。
	public static String alipay_public_key = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA1XHf6d0KZmBtl3k9ChNd2Rpvz5H6hGZ0b7KkIT7BGFLu+DAo7bwCsBMy142IA9b1atd4pMvAsn6Qwv4Med9yn0oAhZma04mad8V5khqY"
			+ "/3jWlOVfh2NIgYSycH4F08LDj6SseWXfzLjvohnmc7Ng3k8zBJDR6h2b03xxoKDSHYkT3KkCpZtdGuRXWnH9uvjqRg/s9k7QHWR25C/sttDEf8ccL6/Z96LkW5bcyJmzH2L1KZCXKOkp7IqJ4faBimDJQeLpx8mXhqSLM/YPHs0oLfnCdvP0bYE1ziJjC9Zd3FT/x/iMqtlH4qyQHKEIQG6u60HbBHA7TTVWP06Lldk+eQIDAQAB";

	// 服务器异步通知页面路径  需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	//表示的你项目中的controller，reuestMapper,但是要部署到公网上
	public static String notify_url = "http://1553ve6443.imwork.net:11534/userAndPwd/notify_url.jsp";

	// 页面跳转同步通知页面路径 需http://格式的完整路径，不能加?id=123这类自定义参数，必须外网可以正常访问
	//表示处理了业务以后成功跳转的地址，返回的数据包括签名：charset=utf-8&out_trade_no=201810513535520&method=alipay.trade.page.pay.return&total_amount=0.01&sign=Jix...=2018100522001423400500217962&auth_app_id=2016092000558884&version=1.0&app_id=2016092000558884&sign_type=RSA2&seller_id=2088102176548293&timestamp=2018-10-05+13%3A58%3A42
	public static String return_url = "http://1553ve6443.imwork.net:11534/userAndPwd/return_url.jsp";

	// 签名方式
	public static String sign_type = "RSA2";

	// 字符编码格式
	public static String charset = "utf-8";

	// 支付宝网关
//	public static String gatewayUrl = "https://openapi.alipay.com/gateway.do";//正式网关
	public static String gatewayUrl = "https://openapi.alipaydev.com/gateway.do";//用于沙箱测试

	// 支付宝网关，日记存放目录
	public static String log_path = "C:\\";


//↑↑↑↑↑↑↑↑↑↑请在这里配置您的基本信息↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑

	/**
	 * 写日志，方便测试（看网站需求，也可以改成把记录存入数据库）
	 * @param sWord 要写入日志里的文本内容
	 */
	public static void logResult(String sWord) {
		FileWriter writer = null;
		try {
			writer = new FileWriter(log_path + "alipay_log_" + System.currentTimeMillis()+".txt");
			writer.write(sWord);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null) {
				try {
					writer.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
