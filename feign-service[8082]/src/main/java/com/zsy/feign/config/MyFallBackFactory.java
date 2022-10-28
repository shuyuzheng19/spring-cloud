package com.zsy.feign.config;

import com.zsy.common.response.ResultResponse;
import com.zsy.feign.service.FeignClientService;
import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @author 郑书宇
 * @create 2022/10/28 21:19
 * @desc
 */
@Component
public class MyFallBackFactory implements FallbackFactory<FeignClientService> {

    private static final ResultResponse DEFAULT_FAIL=ResultResponse.error();

    @Override
    public FeignClientService create(Throwable throwable) {
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
            public ResultResponse addRole(String token, Map<String, Object> role) {
                return DEFAULT_FAIL;
            }

            @Override
            public ResultResponse addPermission(String token, Map<String, Object> permission) {
                return DEFAULT_FAIL;
            }

            @Override
            public ResultResponse roleToUser(String token, String roleName, String username) {
                return DEFAULT_FAIL;
            }

            @Override
            public ResultResponse permissionToUser(String token, String permissionName, String username) {
                return DEFAULT_FAIL;
            }

            @Override
            public ResultResponse deleteRole(String token, String roleName) {
                return DEFAULT_FAIL;
            }

            @Override
            public ResultResponse deletePermission(String token, String permissionName) {
                return DEFAULT_FAIL;
            }
        };
    }
}
