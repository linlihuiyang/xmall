package com.xll.xmall.member.dubbo;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xll.xmall.member.bo.UmsMemberBO;
import com.xll.xmall.member.convert.UmsMemberConvert;
import com.xll.xmall.member.convert.UmsMemberLevelConvert;
import com.xll.xmall.member.mbg.entity.UmsMember;
import com.xll.xmall.member.mbg.mapper.UmsMemberMapper;
import com.xll.xmall.member.service.MemberService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Slf4j
@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    private UmsMemberMapper memberMapper;

    @Autowired
    private UmsMemberConvert umsMemberConvert;


    @Override
    public UmsMemberBO getByUsername(String username) {
        UmsMemberBO member;
        QueryWrapper<UmsMember> wrapper = new QueryWrapper<>();
        wrapper.eq("user_name",username);
        List<UmsMember> memberList = memberMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(memberList)) {
            member = umsMemberConvert.from(memberList.get(0));
            return member;
        }
        return null;
    }



}
