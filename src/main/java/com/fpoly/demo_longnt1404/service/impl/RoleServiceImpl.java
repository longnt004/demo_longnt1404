package com.fpoly.demo_longnt1404.service.impl;


import com.fpoly.demo_longnt1404.model.Role;
import com.fpoly.demo_longnt1404.repository.RoleRepository;
import com.fpoly.demo_longnt1404.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImpl(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<Role> FindAll() {
        return roleRepository.findAll();
    }
}
