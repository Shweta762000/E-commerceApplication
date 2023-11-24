package com.lcwd.electronic.store.electronicStore.services.serviceImpl;

import com.lcwd.electronic.store.electronicStore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicStore.dtos.UserDto;
import com.lcwd.electronic.store.electronicStore.entities.User;
import com.lcwd.electronic.store.electronicStore.exceptions.ResourceNotFoundException;
import com.lcwd.electronic.store.electronicStore.helper.Helper;
import com.lcwd.electronic.store.electronicStore.repository.UserRepository;
import com.lcwd.electronic.store.electronicStore.services.UserService;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        userDto.setUserId(userId);
        User user = dtoToEntity(userDto);
        user.setUserId(userId); // Set the manually assigned ID
        User savedUser = userRepository.save(user);
        UserDto newDto = entityToDto(savedUser);
        logger.info("User created successfully: {}", newDto);
        return newDto;
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        user.setName(userDto.getName());
        user.setAbout(userDto.getAbout());
        user.setPassword(userDto.getPassword());
        user.setGender(userDto.getGender());
        user.setImageName(userDto.getImageName());

        User updatedUser = userRepository.save(user);
        UserDto updatedDto = entityToDto(updatedUser);
        logger.info("User updated successfully: {}", updatedDto);
        return updatedDto;
    }

    @Override
    public void deleteUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));

        userRepository.delete(user);
        logger.info("User deleted successfully with ID: {}", userId);
    }

    @Override
    public PageableResponse<UserDto> getAllUser(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sortby = sortDir.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber - 1, pageSize, sortby);

        Page<User> page = userRepository.findAll(pageable);
        PageableResponse<UserDto> pageableResponse = Helper.getPageableResponse(page, UserDto.class);
        logger.info("Fetched all users successfully");
        return pageableResponse;
    }

    @Override
    public UserDto getUser(String userId) {
        return null;
    }

    @Override
    public UserDto getUserById(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found"));
        UserDto userDto = entityToDto(user);
        logger.info("Fetched user by ID successfully: {}", userDto);
        return userDto;
    }

    @Override
    public UserDto getUserByEmail(String email) {
        User emailId = userRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException("EmailId not found"));
        UserDto emailDto = entityToDto(emailId);
        logger.info("Fetched user by email successfully: {}", emailDto);
        return emailDto;
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepository.findByNameContaining(keyword);
        List<UserDto> dtolist = users.stream().map(this::entityToDto).collect(Collectors.toList());
        logger.info("Searched users successfully with keyword: {}", keyword);
        return dtolist;
    }

    private UserDto entityToDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
