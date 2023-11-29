package com.lcwd.electronic.store.electronicStore.controller;

import com.lcwd.electronic.store.electronicStore.AppConstanstant.UrlConstants;
import com.lcwd.electronic.store.electronicStore.dtos.ApiResponce;
import com.lcwd.electronic.store.electronicStore.dtos.ImageResponse;
import com.lcwd.electronic.store.electronicStore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicStore.dtos.UserDto;
import com.lcwd.electronic.store.electronicStore.entities.User;
import com.lcwd.electronic.store.electronicStore.services.FileService;
import com.lcwd.electronic.store.electronicStore.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static com.lcwd.electronic.store.electronicStore.AppConstanstant.Constantants.*;

@RestController
@RequestMapping(UrlConstants.Base_URL)
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String path;


    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        logger.info("Request received to create a new user");
        UserDto user = userService.createUser(userDto);
        logger.info("User created successfully");
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(
            @PathVariable String userId,
            @Valid @RequestBody UserDto userDto
    ) {
        logger.info("Request received to update user with ID: {}", userId);
        UserDto updatedUserDto = userService.updateUser(userDto, userId);
        logger.info("User with ID {} updated successfully", userId);
        return new ResponseEntity<>(updatedUserDto, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponce> deleteUser(@PathVariable String userId) {
        logger.info("Request received to delete user with ID: {}", userId);
        userService.deleteUser(userId);
        logger.info("User with ID {} deleted successfully", userId);
        ApiResponce userDeleted = ApiResponce.builder().message("User deleted successfully").sucess(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(userDeleted, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<PageableResponse<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = PAGE_NUMBER, required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = SORT_DIR, required = false) String sortDir
    ) {
        logger.info("Request received to get all users");
        return new ResponseEntity<>(userService.getAllUser(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable String userId) {
        logger.info("Request received to get user with ID: {}", userId);
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getEmail(@PathVariable String email) {
        logger.info("Request received to get user by email: {}", email);
        return new ResponseEntity<>(userService.getUserByEmail(email), HttpStatus.OK);
    }

    @GetMapping("/search/{keyword}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keyword) {
        logger.info("Request received to search users with keyword: {}", keyword);
        return new ResponseEntity<>(userService.searchUser(keyword), HttpStatus.OK);
    }
    @GetMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadImg(@RequestParam ("image")MultipartFile img, @PathVariable String userId) throws IOException {

        String s = fileService.uploadFile(img, path, userId);
        UserDto userById = userService.getUserById(userId);
        userById.setImageName(s);
        userService.updateUser(userById, userId);
        ImageResponse imageResponse = ImageResponse.builder().img(s).massage("sucess").httpStatus(HttpStatus.CREATED).status(true).build();
        return new ResponseEntity<>(imageResponse,HttpStatus.CREATED);
    }
}
