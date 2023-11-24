package com.lcwd.electronic.store.electronicStore.services;

import com.lcwd.electronic.store.electronicStore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicStore.dtos.UserDto;
import com.lcwd.electronic.store.electronicStore.entities.User;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto,String userId);
    void deleteUser(String userId);
    PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir);
    UserDto getUser(String userId);
    UserDto getUserById(String userId);
    UserDto getUserByEmail(String email);
    List<UserDto> searchUser(String keyword);


}
