package com.xll.xmall.admin.web.admin.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xll.xmall.admin.web.admin.dao.UmsAdminRoleRelationDao;
import com.xll.xmall.admin.web.admin.service.UmsAdminCacheService;
import com.xll.xmall.admin.web.admin.service.UmsAdminService;
import com.xll.xmall.admin.web.mbg.entity.UmsAdmin;
import com.xll.xmall.admin.web.mbg.entity.UmsAdminRoleRelation;
import com.xll.xmall.admin.web.mbg.entity.UmsResource;
import com.xll.xmall.admin.web.mbg.mapper.UmsAdminRoleRelationMapper;
import com.xll.xmall.common.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * UmsAdminCacheService实现类
 */
@Service
public class UmsAdminCacheServiceImpl  implements UmsAdminCacheService {
    @Autowired
    private UmsAdminService adminService;
    @Autowired
    private RedisService redisService;
    @Autowired
    private UmsAdminRoleRelationMapper adminRoleRelationMapper;
    @Autowired
    private UmsAdminRoleRelationDao adminRoleRelationDao;
    @Value("${redis.database}")
    private String REDIS_DATABASE;
    @Value("${redis.expire.common}")
    private Long REDIS_EXPIRE;
    @Value("${redis.key.admin}")
    private String REDIS_KEY_ADMIN;
    @Value("${redis.key.resourceList}")
    private String REDIS_KEY_RESOURCE_LIST;
    @Value("${redis.key.token}")
    private String REDIS_KEY_TOKEN;

    @Value("${jwt.expiration}")
    private Long JWT_EXPIRATION;
    @Override
    public void delAdmin(Long adminId) {
        UmsAdmin admin = adminService.getItem(adminId);
        if (admin != null) {
            String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
            redisService.del(key);
        }
    }

    @Override
    public void delResourceList(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.del(key);
    }

    @Override
    public void delResourceListByRole(Long roleId) {
        QueryWrapper<UmsAdminRoleRelation> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",roleId);
        List<UmsAdminRoleRelation> relationList = adminRoleRelationMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(relationList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByRoleIds(List<Long> roleIds) {
        QueryWrapper<UmsAdminRoleRelation> wrapper = new QueryWrapper<>();
        wrapper.in("role_id",roleIds);
        List<UmsAdminRoleRelation> relationList = adminRoleRelationMapper.selectList(wrapper);
        if (!CollectionUtils.isEmpty(relationList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = relationList.stream().map(relation -> keyPrefix + relation.getAdminId()).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public void delResourceListByResource(Long resourceId) {
        List<Long> adminIdList = adminRoleRelationDao.getAdminIdList(resourceId);
        if (!CollectionUtils.isEmpty(adminIdList)) {
            String keyPrefix = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":";
            List<String> keys = adminIdList.stream().map(adminId -> keyPrefix + adminId).collect(Collectors.toList());
            redisService.del(keys);
        }
    }

    @Override
    public UmsAdmin getAdmin(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + username;
        return (UmsAdmin) redisService.get(key);
    }

    @Override
    public void setAdmin(UmsAdmin admin) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_ADMIN + ":" + admin.getUsername();
        redisService.set(key, admin, REDIS_EXPIRE);
    }

    @Override
    public List<UmsResource> getResourceList(Long adminId) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        return (List<UmsResource>) redisService.get(key);
    }

    @Override
    public void setResourceList(Long adminId, List<UmsResource> resourceList) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_RESOURCE_LIST + ":" + adminId;
        redisService.set(key, resourceList, REDIS_EXPIRE);
    }

    /**
     * 删除 token
     *
     * @param username
     */
    @Override
    public void delToken(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_TOKEN + ":" + username;
        redisService.del(key);
    }

    /**
     * 获取 token
     *
     * @param username
     */
    @Override
    public String getToken(String username) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_TOKEN + ":" + username;
        return (String) redisService.get(key);
    }

    /**
     * 设置 token
     *
     * @param username
     * @param token
     */
    @Override
    public void setToken(String username, String token) {
        String key = REDIS_DATABASE + ":" + REDIS_KEY_TOKEN + ":" + username;
        redisService.set(key, token, JWT_EXPIRATION);
    }
}
