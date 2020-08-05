package com.xll.xmall.member.convert;


import com.xll.xmall.member.bo.UmsMemberLevelBO;
import com.xll.xmall.member.mbg.entity.UmsMemberLevel;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UmsMemberLevelConvert {


//    @Mappings({
//            @Mapping(target = "uname", source = "sname")
//            // 多个属性不对应可以用 "，" 隔开编写多个@Mapping
//            // ,@Mapping(target = "uname", source = "sname")
//    })
    UmsMemberLevelBO toUmsMemberLevelBO(UmsMemberLevel umsMemberLevel);


    List<UmsMemberLevelBO> toUmsMemberLevelBOs(List<UmsMemberLevel> umsMemberLevels);


}
