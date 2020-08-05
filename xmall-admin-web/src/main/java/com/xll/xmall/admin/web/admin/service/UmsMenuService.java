package com.xll.xmall.admin.web.admin.service;



import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xll.xmall.admin.web.admin.dto.UmsMenuNode;
import com.xll.xmall.admin.web.mbg.entity.UmsMenu;

import java.util.List;

/**
 * 后台菜单管理Service
 */
public interface UmsMenuService {
    /**
     * 创建后台菜单
     */
    int create(UmsMenu umsMenu);

    /**
     * 修改后台菜单
     */
    int update(Long id, UmsMenu umsMenu);

    /**
     * 根据ID获取菜单详情
     */
    UmsMenu getItem(Long id);

    /**
     * 根据ID删除菜单
     */
    int delete(Long id);

    /**
     * 分页查询后台菜单
     */
    IPage<UmsMenu> list(Long parentId, Integer pageSize, Integer pageNum);

    /**
     * 树形结构返回所有菜单列表
     */
    List<UmsMenuNode> treeList();

    /**
     * 修改菜单显示状态
     */
    int updateHidden(Long id, Integer hidden);
}
