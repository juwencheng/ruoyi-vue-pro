package cn.iocoder.yudao.framework.tenant.core.service;

import cn.iocoder.yudao.framework.common.biz.system.tenant.TenantCommonApi;
import cn.iocoder.yudao.framework.common.exception.ServiceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * {@link TenantFrameworkServiceImpl} 的单元测试
 *
 * @author juwencheng
 */
class TenantFrameworkServiceImplTest {

    @Test
    void refreshTenantDropsStaleValidation() {
        TenantCommonApi tenantApi = mock(TenantCommonApi.class);
        doThrow(new ServiceException(1_002_015_002, "租户已过期")).when(tenantApi).validateTenant(1L);
        TenantFrameworkServiceImpl service = new TenantFrameworkServiceImpl(tenantApi);

        // 第一次校验把“已过期”放进本地缓存
        assertThrows(ServiceException.class, () -> service.validTenant(1L));
        // 数据已经恢复，缓存未失效时仍抛出旧结果，且不会再次访问租户接口
        doNothing().when(tenantApi).validateTenant(1L);
        assertThrows(ServiceException.class, () -> service.validTenant(1L));

        service.refreshTenant(1L);

        assertDoesNotThrow(() -> service.validTenant(1L));
        verify(tenantApi, times(2)).validateTenant(1L);
    }

}
