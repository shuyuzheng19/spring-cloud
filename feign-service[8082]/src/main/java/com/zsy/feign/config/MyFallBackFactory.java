package com.zsy.feign.config;

import com.zsy.common.response.ResultResponse;
import com.zsy.feign.service.FeignClientService;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 郑书宇
 * @create 2022/10/28 21:19
 * @desc
 */
@Component
@Slf4j
public class MyFallBackFactory implements FallbackFactory<FeignClientService> {

    private static final ResultResponse DEFAULT_FAIL=ResultResponse.error();

    @Override
    public FeignClientService create(Throwable throwable) {
        log.error("Feign熔断异常",throwable);
        return new FeignClientService() {
            @Override
            public ResultResponse login(Map<String, Object> map) {
                return DEFAULT_FAIL;
            }

            @Override
            public ResultResponse registeredUser(Map<String, Object> map) {
                return DEFAULT_FAIL;
            }

            @Override
            public ResultResponse addRole(Map<String, Object> role) {
                return DEFAULT_FAIL;
            }

            @Override
            public ResultResponse addPermission(Map<String, Object> permission) {
                return DEFAULT_FAIL;
            }

            @Override
            public ResultResponse roleToUser(String roleName, String username) {
                return DEFAULT_FAIL;
            }

            @Override
            public ResultResponse permissionToUser(String permissionName, String username) {
                return DEFAULT_FAIL;
            }

            @Override
            public ResultResponse deleteRole(String roleName) {
                return DEFAULT_FAIL;
            }

            @Override
            public ResultResponse deletePermission(String permissionName) {
                return DEFAULT_FAIL;
            }
        };
    }
}
