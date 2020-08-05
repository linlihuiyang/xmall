package com.xll.xmall.admin.web.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xll.xmall.admin.web.admin.dto.UmsMenuNode;
import com.xll.xmall.admin.web.admin.service.UmsMenuService;
import com.xll.xmall.admin.web.mbg.entity.UmsMenu;
import com.xll.xmall.admin.web.mbg.mapper.UmsMenuMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 后台菜单管理Service实现类
 * Created by macro on 2020/2/2.
 */
@Service
public class UmsMenuServiceImpl implements UmsMenuService {
    @Autowired
    private UmsMenuMapper menuMapper;

    @Override
    public int create(UmsMenu umsMenu) {
        umsMenu.setCreateTime(LocalDateTime.now());
        updateLevel(umsMenu);
        return menuMapper.insert(umsMenu);
    }

    /**
     * 修改菜单层级
     */
    private void updateLevel(UmsMenu umsMenu) {
        if (umsMenu.getParentId() == 0) {
            //没有父菜单时为一级菜单
            umsMenu.setLevel(0);
        } else {
            //有父菜单时选择根据父菜单level设置
            UmsMenu parentMenu = menuMapper.selectById(umsMenu.getParentId());
            if (parentMenu != null) {
                umsMenu.setLevel(parentMenu.getLevel() + 1);
            } else {
                umsMenu.setLevel(0);
            }
        }
    }

    @Override
    public int update(Long id, UmsMenu umsMenu) {
        umsMenu.setId(id);
        updateLevel(umsMenu);
        return menuMapper.updateById(umsMenu);
    }

    @Override
    public UmsMenu getItem(Long id) {
        return menuMapper.selectById(id);
    }

    @Override
    public int delete(Long id) {
        return menuMapper.deleteById(id);
    }

    @Override
    public IPage<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum) {
        IPage<UmsMenu> page = new Page<>(pageNum, pageSize);
        QueryWrapper<UmsMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",parentId);
        wrapper.orderByDesc("sort");
        return menuMapper.selectPage(page,wrapper);
    }

    @Override
    public List<UmsMenuNode> treeList() {
        List<UmsMenu> menuList = menuMapper.selectList(Wrappers.emptyWrapper());
        List<UmsMenuNode> result = menuList.stream()
                .filter(menu -> menu.getParentId().equals(0L))
                .map(menu -> covertMenuNode(menu, menuList)).collect(Collectors.toList());
        return result;
    }

    @Override
    public int updateHidden(Long id, Integer hidden) {
        UmsMenu umsMenu = new UmsMenu();
        umsMenu.setId(id);
        umsMenu.setHidden(hidden);
        return menuMapper.updateById(umsMenu);
    }

    /**
     * 将UmsMenu转化为UmsMenuNode并设置children属性
     */
    private UmsMenuNode covertMenuNode(UmsMenu menu, List<UmsMenu> menuList) {
        UmsMenuNode node = new UmsMenuNode();
        BeanUtils.copyProperties(menu, node);
        List<UmsMenuNode> children = menuList.stream()
                .filter(subMenu -> subMenu.getParentId().equals(menu.getId()))
                .map(subMenu -> covertMenuNode(subMenu, menuList)).collect(Collectors.toList());
        node.setChildren(children);
        return node;
    }
}
