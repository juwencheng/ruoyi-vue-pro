package cn.iocoder.yudao.framework.tenant.core.service;

import java.util.List;

/**
 * Tenant 框架 Service 接口，定义获取租户信息
 *
 * @author 芋道源码
 */
public interface TenantFrameworkService {

    /**
     * 获得所有租户
     *
     * @return 租户编号数组
     */
    List<Long> getTenantIds();

    /**
     * 校验租户是否合法
     *
     * @param id 租户编号
     */
    void validTenant(Long id);

    /**
     * 使指定租户的校验缓存失效，下一次校验重新加载
     *
     * 本地缓存按时间异步刷新，更新过期时间、状态后，请求仍可能读到旧的校验结果。
     *
     * @param id 租户编号
     */
    void refreshTenant(Long id);

}
