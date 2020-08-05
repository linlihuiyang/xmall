package com.xll.xmall.member.dubbo;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xll.xmall.member.bo.UmsMemberLevelBO;
import com.xll.xmall.member.convert.UmsMemberLevelConvert;
import com.xll.xmall.member.mbg.entity.UmsMemberLevel;
import com.xll.xmall.member.mbg.mapper.UmsMemberLevelMapper;
import com.xll.xmall.member.service.MemberLevelService;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * 会员等级管理Service实现类
 */
@Service
public class MemberLevelServiceImpl implements MemberLevelService {

    @Autowired
    private UmsMemberLevelMapper memberLevelMapper;

    @Autowired
    private UmsMemberLevelConvert umsMemberLevelConvert;

    @Override
    public List<UmsMemberLevelBO> list(Integer defaultStatus) {
        QueryWrapper<UmsMemberLevel> wrapper = new QueryWrapper();
        wrapper.eq("default_status",defaultStatus);

        List<UmsMemberLevel> umsMemberLevels = memberLevelMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(umsMemberLevels)) {
            return umsMemberLevelConvert.toUmsMemberLevelBOs(umsMemberLevels);
        }
        return null;
    }
}
