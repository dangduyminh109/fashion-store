package com.fashion_store.seeder;

import com.fashion_store.entity.Permission;
import com.fashion_store.entity.Role;
import com.fashion_store.repository.PermissionRepository;
import com.fashion_store.repository.RoleRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleSeeder implements ApplicationRunner {
    RoleRepository roleRepository;
    PermissionRepository permissionRepository;

    @Override
    public void run(ApplicationArguments args) {
        if (permissionRepository.count() == 0) {
            List<Permission> defaultPermissions = generateDefaultPermissions();
            permissionRepository.saveAll(defaultPermissions);
        }

        if (roleRepository.count() == 0) {
            List<Permission> allPermissions = permissionRepository.findAll();

            Role adminRole = Role.builder()
                    .name("ADMIN")
                    .permissions(new HashSet<>(allPermissions))
                    .build();

            roleRepository.save(adminRole);
        }
    }

    List<Permission> generateDefaultPermissions() {
        return List.of(
                Permission.builder().code("PRODUCT_VIEW").name("Xem").module("Sản phẩm").build(),
                Permission.builder().code("PRODUCT_CREATE").name("Thêm mới").module("Sản phẩm").build(),
                Permission.builder().code("PRODUCT_UPDATE").name("Cập nhật").module("Sản phẩm").build(),
                Permission.builder().code("PRODUCT_DELETE").name("Xóa").module("Sản phẩm").build(),
                Permission.builder().code("CATEGORY_VIEW").name("Xem").module("Danh mục").build(),
                Permission.builder().code("CATEGORY_CREATE").name("Thêm mới").module("Danh mục").build(),
                Permission.builder().code("CATEGORY_UPDATE").name("Cập nhật").module("Danh mục").build(),
                Permission.builder().code("CATEGORY_DELETE").name("Xóa").module("Danh mục").build()
        );
    }
}
