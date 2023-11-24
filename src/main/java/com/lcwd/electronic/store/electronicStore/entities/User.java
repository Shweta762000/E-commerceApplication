package com.lcwd.electronic.store.electronicStore.entities;

import lombok.*;
import net.bytebuddy.dynamic.loading.InjectionClassLoader;

import javax.persistence.*;
import java.lang.reflect.Type;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User {


 @Id

 private String userId;

 @Column(name="user_name")
    private String name;
    @Column(name = "user_email",unique = true)
    private String email;
    @Column(name = "user_password",length = 255)
    private  String password;
    @Column(name="user_gender")
    private String gender;
    @Column(name="user_about",length = 1000)
    private String about;
    @Column(name = "user_image_name")
    private String imageName;


}
