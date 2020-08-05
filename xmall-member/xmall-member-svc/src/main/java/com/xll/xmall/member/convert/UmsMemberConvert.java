package com.xll.xmall.member.convert;


import com.xll.xmall.member.bo.UmsMemberBO;
import com.xll.xmall.member.mbg.entity.UmsMember;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UmsMemberConvert {

    UmsMemberBO from(UmsMember member);


}
