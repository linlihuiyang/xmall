package com.xll.xmall.oss.service;



import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.sts.model.v20150401.AssumeRoleResponse;
import com.xll.xmall.oss.dto.OssPolicyResult;


/**
 * oss上传管理Service
 * Created by macro on 2018/5/17.
 */
public interface OssService {
    /**
     * oss上传策略生成
     */
    OssPolicyResult policy();


    /**
     * 生成sts
     * @return
     * @throws ClientException
     */
    AssumeRoleResponse assumeRole()  throws ClientException;

}
