package com.fpoly.demo_longnt1404.mapper;


import com.fpoly.demo_longnt1404.dto.RoleDTO;
import com.fpoly.demo_longnt1404.dto.UserDTO;
import com.fpoly.demo_longnt1404.model.Role;
import com.fpoly.demo_longnt1404.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    // Use MapStruct to map data between User and UserDTO
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    // Map data between User and UserDTO
    UserDTO userToUserDTO(User user);

    // Map data between UserDTO and User
    User userDTOToUser(UserDTO userDTO);

    // Map data between Role and RoleDTO
    RoleDTO roleToRoleDTO(Role role);

    // Map data between RoleDTO and Role
    Role roleDTOToRole(RoleDTO roleDTO);
}
