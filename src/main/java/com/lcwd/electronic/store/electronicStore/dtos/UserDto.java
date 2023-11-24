package com.lcwd.electronic.store.electronicStore.dtos;

import com.lcwd.electronic.store.electronicStore.validate.ImageNameValid;
import com.lcwd.electronic.store.electronicStore.validate.ValidGender;
import lombok.*;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserDto {
   // @NotBlank(message = "User ID cannot be blank")
    private String userId;

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Email cannot be blank")
    @Email(regexp = "[a-z0-9._% +-]+@[a-z0-9.-]+\\.[a-z]{2,3}",message = "Invalid email format")
    @Column(unique=true)
    private String email;


    @NotBlank(message = "Password cannot be blank")
    @Size(min = 8, max = 255, message = "Password must be at least 8 characters")
    private String password;

    @ValidGender(message ="value must be male or female")
    private String gender;

    @Size(max = 255, message = "About must not exceed 255 characters")
    private String about;

   @ImageNameValid(message = "Enter valid img name")
    private String imageName;
}
