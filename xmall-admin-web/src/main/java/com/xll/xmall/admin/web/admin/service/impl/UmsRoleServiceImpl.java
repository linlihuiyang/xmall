package com.xll.xmall.admin.web.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xll.xmall.admin.web.admin.dao.UmsRoleDao;
import com.xll.xmall.admin.web.admin.dao.UmsRolePermissionRelationDao;
import com.xll.xmall.admin.web.admin.service.UmsAdminCacheService;
import com.xll.xmall.admin.web.admin.service.UmsRoleService;
import com.xll.xmall.admin.web.mbg.entity.UmsMenu;
import com.xll.xmall.admin.web.mbg.entity.UmsPermission;
import com.xll.xmall.admin.web.mbg.entity.UmsResource;
import com.xll.xmall.admin.web.mbg.entity.UmsRole;
import com.xll.xmall.admin.web.mbg.entity.UmsRoleMenuRelation;
import com.xll.xmall.admin.web.mbg.entity.UmsRolePermissionRelation;
import com.xll.xmall.admin.web.mbg.entity.UmsRoleResourceRelation;
import com.xll.xmall.admin.web.mbg.mapper.UmsRoleMapper;
import com.xll.xmall.admin.web.mbg.mapper.UmsRoleMenuRelationMapper;
import com.xll.xmall.admin.web.mbg.mapper.UmsRolePermissionRelationMapper;
import com.xll.xmall.admin.web.mbg.mapper.UmsRoleResourceRelationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 后台角色管理Service实现类
 */
@Service
public class UmsRoleServiceImpl implements UmsRoleService {
    @Autowired
    private UmsRoleMapper roleMapper;
    @Autowired
    private UmsRolePermissionRelationMapper rolePermissionRelationMapper;
    @Autowired
    private UmsRoleMenuRelationMapper roleMenuRelationMapper;
    @Autowired
    private UmsRoleResourceRelationMapper roleResourceRelationMapper;
    @Autowired
    private UmsRolePermissionRelationDao rolePermissionRelationDao;
    @Autowired
    private UmsRoleDao roleDao;
    @Autowired
    private UmsAdminCacheService adminCacheService;
    @Override
    public int create(UmsRole role) {
        role.setCreateTime(LocalDateTime.now());
        role.setAdminCount(0);
        role.setSort(0);
        return roleMapper.insert(role);
    }

    @Override
    public int update(Long id, UmsRole role) {
        role.setId(id);
        return roleMapper.updateById(role);
    }

    @Override
    public int delete(List<Long> ids) {
        int count = roleMapper.deleteBatchIds(ids);
        adminCacheService.delResourceListByRoleIds(ids);
        return count;
    }

    @Override
    public List<UmsPermission> getPermissionList(Long roleId) {
        return rolePermissionRelationDao.getPermissionList(roleId);
    }

    @Override
    public int updatePermission(Long roleId, List<Long> permissionIds) {
        //先删除原有关系
        UpdateWrapper<UmsRolePermissionRelation> wrapper = new UpdateWrapper<>();
        wrapper.eq("role_id",roleId);
        rolePermissionRelationMapper.delete(wrapper);
        //批量插入新关系
        List<UmsRolePermissionRelation> relationList = new ArrayList<>();
        for (Long permissionId : permissionIds) {
            UmsRolePermissionRelation relation = new UmsRolePermissionRelation();
            relation.setRoleId(roleId);
            relation.setPermissionId(permissionId);
            relationList.add(relation);
        }
        return rolePermissionRelationDao.insertList(relationList);
    }

    @Override
    public List<UmsRole> list() {
        return roleMapper.selectList(Wrappers.emptyWrapper());
    }

    @Override
    public IPage<UmsRole> list(String keyword, Integer pageSize, Integer pageNum) {
        IPage<UmsRole> page = new Page<>(pageNum, pageSize);

        QueryWrapper<UmsRole> wrapper = new QueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            wrapper.like("name",keyword);
        }
        return roleMapper.selectPage(page,wrapper);
    }

    @Override
    public List<UmsMenu> getMenuList(Long adminId) {
        return roleDao.getMenuList(adminId);
    }

    @Override
    public List<UmsMenu> listMenu(Long roleId) {
        return roleDao.getMenuListByRoleId(roleId);
    }

    @Override
    public List<UmsResource> listResource(Long roleId) {
        return roleDao.getResourceListByRoleId(roleId);
    }

    @Override
    public int allocMenu(Long roleId, List<Long> menuIds) {
        //先删除原有关系
        UpdateWrapper<UmsRoleMenuRelation> wrapper = new UpdateWrapper<>();
        wrapper.eq("role_id",roleId);
        roleMenuRelationMapper.delete(wrapper);
        //批量插入新关系
        for (Long menuId : menuIds) {
            UmsRoleMenuRelation relation = new UmsRoleMenuRelation();
            relation.setRoleId(roleId);
            relation.setMenuId(menuId);
            roleMenuRelationMapper.insert(relation);
        }
        return menuIds.size();
    }

    @Override
    public int allocResource(Long roleId, List<Long> resourceIds) {
        //先删除原有关系
        //先删除原有关系
        UpdateWrapper<UmsRoleResourceRelation> wrapper = new UpdateWrapper<>();
        wrapper.eq("role_id",roleId);
        roleResourceRelationMapper.delete(wrapper);
        //批量插入新关系
        for (Long resourceId : resourceIds) {
            UmsRoleResourceRelation relation = new UmsRoleResourceRelation();
            relation.setRoleId(roleId);
            relation.setResourceId(resourceId);
            roleResourceRelationMapper.insert(relation);
        }
        adminCacheService.delResourceListByRole(roleId);
        return resourceIds.size();
    }
}
