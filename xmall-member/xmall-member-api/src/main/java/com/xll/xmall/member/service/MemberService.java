package com.xll.xmall.member.service;

import com.xll.xmall.member.bo.UmsMemberBO;

public interface MemberService {


    /**
     * 根据用户名获取会员
     */
    UmsMemberBO getByUsername(String username);




}
