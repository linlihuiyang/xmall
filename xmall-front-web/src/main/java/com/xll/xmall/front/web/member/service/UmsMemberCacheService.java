package com.xll.xmall.front.web.member.service;


import com.xll.xmall.member.bo.UmsMemberBO;

/**
 * 会员信息缓存业务类
 * Created by macro on 2020/3/14.
 */
public interface UmsMemberCacheService {
    /**
     * 删除会员用户缓存
     */
//    void delMember(Long memberId);

    /**
     * 获取会员用户缓存
     */
    UmsMemberBO getMember(String username);

    /**
     * 设置会员用户缓存
     */
    void setMember(UmsMemberBO member);

    /**
     * 设置验证码
     */
    void setAuthCode(String telephone, String authCode);

    /**
     * 获取验证码
     */
    String getAuthCode(String telephone);
}
