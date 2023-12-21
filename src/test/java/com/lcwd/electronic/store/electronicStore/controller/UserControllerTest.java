package com.lcwd.electronic.store.electronicStore.controller;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lcwd.electronic.store.electronicStore.dtos.PageableResponse;
import com.lcwd.electronic.store.electronicStore.dtos.UserDto;
import com.lcwd.electronic.store.electronicStore.entities.User;
import com.lcwd.electronic.store.electronicStore.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import static java.util.Arrays.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ModelMapper mapper;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void init() {
        user = User.builder()
                .userId("userId789")
                .name("shweta")
                .email("shweta@gmail.com")
                .about("This is a testing method")
                .gender("female")
                .password("pass12345")
                .imageName("img.png")
                .build();
    }

    @Test // Add this annotation
    public void createUserTest() throws Exception {
        UserDto userDto = mapper.map(user, UserDto.class);
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(userDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("shweta")) // Adjust this line
                .andDo(print());
    }

    @Test // Add this annotation
    public void updateUserTest() throws Exception {
        String userId="userId123";
        UserDto userDto = mapper.map(user, UserDto.class);
        Mockito.when(userService.updateUser(Mockito.any(),Mockito.anyString())).thenReturn(userDto);
        mockMvc.perform(MockMvcRequestBuilders.put("/users/"+userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(convertObjectToJsonString(user))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists()) // Adjust this line
                .andDo(print());
    }
    @Test
    public void testDeleteUser() throws Exception {
        String userId="UserId123";

        Mockito.doNothing().when(userService).deleteUser(Mockito.anyString());

        mockMvc.perform(MockMvcRequestBuilders.delete("/users/"+userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk()).andDo(print());
    }
    @Test
    public void getAllUsersTest() throws Exception {
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
        List<User> list = asList(user, user1, user2, user3);
        List<UserDto> dto = list.stream().map(user->mapper.map(user, UserDto.class)).collect(Collectors.toList());
        PageableResponse<UserDto> pageableResponse= PageableResponse.<UserDto>builder().pageNumber(1).pageSize(2).totalPages(2).lastPage(true).totalElements(4).content(dto).build();
        Mockito.when(userService.getAllUser(Mockito.anyInt(),Mockito.anyInt(),Mockito.anyString(),Mockito.anyString())).thenReturn(pageableResponse);
        this.mockMvc.perform(MockMvcRequestBuilders.get("/users")
                .param("pageNumber", "1")
                .param("pageSize", "2")
                .param("sortBy", "name")
                .param("sortDir", "asc")
                .content(convertObjectToJsonString(list))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.pageNumber").value(pageableResponse.getPageNumber()))
                .andExpect(jsonPath("$.pageSize").value(pageableResponse.getPageSize()))
                .andExpect(jsonPath("$.totalElements").value(pageableResponse.getTotalElements()))
                .andExpect(jsonPath("$.totalPages").value(pageableResponse.getTotalPages()))
                .andExpect(jsonPath("$.lastPage").value(pageableResponse.isLastPage()))
                .andExpect(jsonPath("$.content").isArray())
                .andDo(print())
                .andExpect(jsonPath("$.content.length()").value(dto.size()));

    }
    @Test
    public void getUserTest() throws Exception {
        String userId="userId123";
        UserDto dto = mapper.map(user, UserDto.class);
        Mockito.when(userService.getUserById(userId)).thenReturn(dto);
        mockMvc.perform(MockMvcRequestBuilders.get("/users/"+userId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(convertObjectToJsonString(user))
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(dto.getUserId()))
                .andReturn();

    }
    @Test
    public void getEmailTest() throws Exception {
        String email = "user@example.com";
        UserDto userDto = mapper.map(user, UserDto.class);
        Mockito.when(userService.getUserByEmail(email)).thenReturn(userDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/users/email/" + email)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userId").value(userDto.getUserId()))
                 .andReturn();
    }
    @Test
    public void searchUserTest() throws Exception {
        String keyword = "shweta";
        UserDto userDto = mapper.map(user,UserDto.class);

        Mockito.when(userService.searchUser(keyword)).thenReturn(Collections.singletonList(userDto));

        mockMvc.perform(MockMvcRequestBuilders.get("/users/search/" + keyword)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(userDto.getUserId()))
                // Add more assertions based on your userDto structure
                .andReturn();
    }


    private String convertObjectToJsonString(Object user) {
        try {
            return objectMapper.writeValueAsString(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
