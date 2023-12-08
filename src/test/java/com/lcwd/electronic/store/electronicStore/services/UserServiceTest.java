package com.lcwd.electronic.store.electronicStore.services;

import com.lcwd.electronic.store.electronicStore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicStore.dtos.UserDto;
import com.lcwd.electronic.store.electronicStore.entities.User;
import com.lcwd.electronic.store.electronicStore.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {
    @MockBean
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private UserService userservice;

    private User user;

    @BeforeEach
    public void init() {
        user = User.builder()
                .userId("userId789")
                .name("shweta")
                .email("shweta@gmail.com")
                .about("This is testing method")
                .gender("female")
                .password("pass")
                .imageName("img.png")
                .build();
    }

    @Test
    public void createUserTest() {
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);

        UserDto user1 = userservice.createUser(mapper.map(user, UserDto.class));

        Assertions.assertNotNull(user1);
        Assertions.assertEquals("shweta", user1.getName());
        System.out.println(user.getName());
    }

    @Test
    public void updateUserTest() {
        System.out.println("update");
        String userId = "UserId456";

        UserDto userDto = UserDto.builder().name("shweta vijay swami")
                .email("shweta@gmail.com")
                .about("This is testing method")
                .gender("female")
                .password("passnew")
                .build();
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        Mockito.when((userRepository.save(Mockito.any()))).thenReturn(user);
        UserDto userDto1 = userservice.updateUser(userDto, userId);
        Assertions.assertNotNull(user);
        Assertions.assertEquals("passnew",user.getPassword());
        System.out.println(user);

    }

    @Test
    public void deleteUserTest(){
        String userId = "userId789";
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(user));
        userservice.deleteUser(userId);
        Mockito.verify(userRepository,Mockito.times(1)).delete(user);
    }
    @Test
    public void getAllUserTest(){
        User user1 = User.builder()
                .userId("userId789")
                .name("shweta")
                .email("shweta@gmail.com")
                .about("This is testing method")
                .gender("female")
                .password("pass")
                .imageName("img.png")
                .build();

    User user2 = User.builder()
            .userId("userId789")
                .name("nitya")
                .email("shweta@gmail.com")
                .about("This is testing method")
                .gender("female")
                .password("pass")
                .imageName("img.png")
                .build();

User user3 = User.builder()
        .userId("userId789")
        .name("karuna")
        .email("shweta@gmail.com")
        .about("This is testing method")
        .gender("female")
        .password("pass")
        .imageName("img.png")
        .build();
        List<User> users= Arrays.asList(user3,user2,user1);
        Page<User> page = new PageImpl<>(users);
        Mockito.when(userRepository.findAll((Pageable) Mockito.any())).thenReturn(page);
        PageableResponse<UserDto> allUser = userservice.getAllUser(1, 2, "userId", "asc");
        Assertions.assertEquals(3,allUser.getContent().size());

}
@Test
public void getUserByIdTest(){
        String userId="userId789";
        Mockito.when(userRepository.findById(userId)).thenReturn(Optional.ofNullable(user));
    UserDto userById = userservice.getUserById(userId);
    Assertions.assertNotNull(userById);
    Assertions.assertEquals(user.getName(),userById.getName());
}
@Test
public void getUserByEmailTest(){
    String EmailId="shweta@gmail.com";
    Mockito.when(userRepository.findById(EmailId)).thenReturn(Optional.ofNullable(user));
    UserDto userByEmailId = userservice.getUserById(EmailId);
    Assertions.assertNotNull(userByEmailId);
    Assertions.assertEquals(user.getName(),userByEmailId.getName());
}
@Test
    public void searchUserTest(){
    User user1 = User.builder()
            .userId("userId789")
            .name("shweta")
            .email("shweta@gmail.com")
            .about("This is testing method")
            .gender("female")
            .password("pass")
            .imageName("img.png")
            .build();

    User user2 = User.builder()
            .userId("userId789")
            .name("nitya")
            .email("shweta@gmail.com")
            .about("This is testing method")
            .gender("female")
            .password("pass")
            .imageName("img.png")
            .build();

    User user3 = User.builder()
            .userId("userId789")
            .name("karuna")
            .email("shweta@gmail.com")
            .about("This is testing method")
            .gender("female")
            .password("pass")
            .imageName("img.png")
            .build();
        String keyword1="karuna";
       // String keyword1="pass";
        Mockito.when(userRepository.findByNameContaining(keyword1)).thenReturn(Arrays.asList(user3));
    List<UserDto> userDtos = userservice.searchUser(keyword1);
    Assertions.assertEquals(1,userDtos.size());
}
}
