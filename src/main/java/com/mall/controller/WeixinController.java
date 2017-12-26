package com.mall.controller;

import com.mall.vo.JSON;
import com.mall.utils.Utils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

@Controller
@RequestMapping("weixin")
public class WeixinController {

    private RestTemplate restTemplate = new RestTemplate();


    /**
     * 账号相关
     */
    @Value("${weixin.appid}")
    private String appid;
    @Value("${weixin.secret}")
    private String secret;


    /**
     * 凭证
     * 凭证获取时间，单位：Linux时间
     * 凭证有效时间，单位：秒
     */
    private String accessToken;
    private long accessTokenExpiresTime;
    private int accessTokenExpiresIn;
    @Value("${weixin.access_token_url}")
    private String accessTokenUrl;

    /**
     * 临时票据
     * 临时票据获取时间，单位：Linux时间
     * 临时票据有效时间，单位：秒
     */
    private String ticket;
    private long ticketExpiresTime;
    private int ticketExpiresIn;
    @Value("${weixin.jsapi_ticket_url}")
    private String ticketUrl;


    /**
     * 签名
     * 生成签名的随机串
     * 生成签名的时间戳
     */
    private String signature;
    @Value("${weixin.noncestr}")
    private String noncestr;
    private long signatureTimestamp;

    /**
     * 微信扫一扫功能
     * <p>
     * 扫描商品条形码
     *
     * @param model
     * @return
     */
    @RequestMapping("/scan")
    public String scan(Model model) {
        model.addAttribute("signature", getSignature("http://k.teamtor.com/weixin/scan"));
        model.addAttribute("appId", appid);
        model.addAttribute("signatureTimestamp", signatureTimestamp);
        model.addAttribute("nonceStr", noncestr);
        return "weixin/scan";
    }


    private String getSignature(String url) {
        signatureTimestamp = System.currentTimeMillis();
        signature = "jsapi_ticket={jsapi_ticket}&noncestr={noncestr}&timestamp={timestamp}&url={url}";
        signature = signature.replace("{jsapi_ticket}", getJsapiTicket()).replace("{noncestr}", noncestr).replace("{timestamp}", signatureTimestamp + "").replace("{url}", url);
        signature = Utils.getSha1(signature);
        return signature;
    }

    private String getAccessToken() {
        // 如果凭证不存在或者过时，则重新获取
        if (Utils.isEmpty(accessToken) || System.currentTimeMillis() - accessTokenExpiresTime > accessTokenExpiresIn) {
            String access_token_url = accessTokenUrl;
            JSON result = new JSON(restTemplate.getForObject(access_token_url, String.class));
            accessToken = result.getStr("access_token");
            accessTokenExpiresIn = Integer.parseInt(result.getStr("expires_in")) * 1000;
            accessTokenExpiresTime = System.currentTimeMillis();
        }
        return accessToken;
    }

    private String getJsapiTicket() {
        // 如果凭证不存在或者过时，则重新获取
        if (Utils.isEmpty(ticket) || System.currentTimeMillis() - ticketExpiresTime > ticketExpiresIn) {
            JSON result = new JSON(restTemplate.getForObject(ticketUrl.replace("ACCESS_TOKEN", getAccessToken()), String.class));
            ticket = result.getStr("ticket");
            ticketExpiresIn = Integer.parseInt(result.getStr("expires_in")) * 1000;
            ticketExpiresTime = System.currentTimeMillis();
        }
        return ticket;
    }
}
