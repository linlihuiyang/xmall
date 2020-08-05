package com.xll.xmall.admin.web.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xll.xmall.admin.web.admin.service.UmsAdminCacheService;
import com.xll.xmall.admin.web.admin.service.UmsResourceService;
import com.xll.xmall.admin.web.mbg.entity.UmsResource;
import com.xll.xmall.admin.web.mbg.mapper.UmsResourceMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 后台资源管理Service实现类
 * Created by macro on 2020/2/2.
 */
@Service
public class UmsResourceServiceImpl implements UmsResourceService {
    @Autowired
    private UmsResourceMapper resourceMapper;
    @Autowired
    private UmsAdminCacheService adminCacheService;
    @Override
    public int create(UmsResource umsResource) {
        umsResource.setCreateTime(LocalDateTime.now());
        return resourceMapper.insert(umsResource);
    }

    @Override
    public int update(Long id, UmsResource umsResource) {
        umsResource.setId(id);
        int count = resourceMapper.updateById(umsResource);
        adminCacheService.delResourceListByResource(id);
        return count;
    }

    @Override
    public UmsResource getItem(Long id) {
        return resourceMapper.selectById(id);
    }

    @Override
    public int delete(Long id) {
        int count = resourceMapper.deleteById(id);
        adminCacheService.delResourceListByResource(id);
        return count;
    }

    @Override
    public IPage<UmsResource> list(Long categoryId, String nameKeyword, String urlKeyword, Integer pageSize, Integer pageNum) {
        IPage<UmsResource> page = new Page(pageNum,pageSize);
        QueryWrapper<UmsResource> wrapper = new QueryWrapper<>();

        if(categoryId!=null){
            wrapper.eq("categroyId",categoryId);
        }
        if(StringUtils.isNotEmpty(nameKeyword)){
            wrapper.like("name",nameKeyword);
        }
        if(StringUtils.isNotEmpty(urlKeyword)){
            wrapper.like("url",urlKeyword);
        }
        return resourceMapper.selectPage(page,wrapper);
    }

    @Override
    public List<UmsResource> listAll() {
        return resourceMapper.selectList(Wrappers.emptyWrapper());
    }
}
