package com.xll.xmall.member.service;

import com.xll.xmall.member.bo.UmsMemberLevelBO;
import java.util.List;

/**
 * 会员等级管理Service
 * Created by macro on 2018/4/26.
 */
public interface MemberLevelService {
    /**
     * 获取所有会员登录
     * @param defaultStatus 是否为默认会员
     */
    List<UmsMemberLevelBO> list(Integer defaultStatus);
}
