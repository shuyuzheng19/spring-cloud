package com.zsy.producer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zsy.producer.dto.BookDto;
import com.zsy.producer.entity.Permission;
import com.zsy.producer.entity.Role;
import com.zsy.producer.entity.User;
import com.zsy.producer.repository.PermissionRepository;
import com.zsy.producer.repository.RoleRepository;
import com.zsy.producer.repository.UserRepository;
import com.zsy.producer.service.kafka.KafkaBookService;
import com.zsy.producer.utils.JsonUtils;
import lombok.RequiredArgsConstructor;
import org.hibernate.dialect.MySQL8Dialect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;

import java.util.*;

/**
 * @author 郑书宇
 * @create 2022/10/26 19:02
 * @desc
 */
@SpringBootApplication
@EnableEurekaClient
@RequiredArgsConstructor
public class ProducerApplication {
    public static void main(String[] args) {
        SpringApplication.run(ProducerApplication.class,args);
    }

    private final RoleRepository roleRepository;

    private final PermissionRepository permissionRepository;

    private final UserRepository userRepository;

    private final KafkaBookService kafkaBookService;

    @Bean
    CommandLineRunner commandLineRunner(){
        return args -> {
            String uuid= UUID.randomUUID().toString();
            BookDto bookDto = BookDto.builder().id(uuid).date(new Date()).name("java").build();
            kafkaBookService.sendMessage(JsonUtils.objectToJsonString(bookDto));
//            User user = initUser();
//            userRepository.save(user);
//            List<Role> roleList=initRole();
//            List<Permission> permissionList=initPermission();
//            roleRepository.saveAll(roleList);
//            permissionRepository.saveAll(permissionList);
        };
    }


    public User initUser(){
        List<Role> roles = roleRepository.findAll();
        Role randomRole=roles.get((int)(Math.random()*roles.size()));
        List<Permission> permissions = permissionRepository.findAll();
        Set<Permission> randomPermission=new HashSet<>();
        while(true){
            if(randomPermission.size()>=2){
                break;
            }
            int randomIndex=(int)(Math.random()*permissions.size());
            randomPermission.add(permissions.get(randomIndex));
        }
        return new User(1,"z2528959216","郑","书宇","z2528959216",randomRole,randomPermission);
    }


    public List<Role> initRole(){
        return Arrays.asList(
            new Role(1,"USER","普通用户"),
            new Role(2,"ADMIN","管理员"),
            new Role(3,"SUPER_ADMIN","超级管理员")
        );
    }

    public List<Permission> initPermission(){
        return Arrays.asList(
                new Permission(1,"INSERT","添加权限"),
                new Permission(2,"DELETE","删除权限"),
                new Permission(3,"UPDATE","修改权限"),
                new Permission(4,"SELECT","查询权限")
        );
    }
}
