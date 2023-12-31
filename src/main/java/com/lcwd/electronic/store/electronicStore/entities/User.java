package com.lcwd.electronic.store.electronicStore.entities;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name="users")
public class User implements UserDetails {


 @Id

 private String userId;

 @Column(name="user_name")
    private String name;
    @Column(name = "user_email",unique = true)
    private String email;
    @Column(name = "user_password",length = 500)
    private  String password;
    @Column(name="user_gender")
    private String gender;
    @Column(name="user_about",length = 1000)
    private String about;
    @Column(name = "user_image_name")
    private String imageName;
    @OneToMany(mappedBy ="user" ,fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private List<Order> orders= new ArrayList<>();


   @Override
   public Collection<? extends GrantedAuthority> getAuthorities() {
      return null;
   }

   @Override
   public String getUsername() {
      return this.email;
   }
   @Override
   public String getPassword() {
      return this.password;
   }

   @Override
   public boolean isAccountNonExpired() {
      return true;
   }

   @Override
   public boolean isAccountNonLocked() {
      return true;
   }

   @Override
   public boolean isCredentialsNonExpired() {
      return true;
   }

   @Override
   public boolean isEnabled() {
      return true;
   }
}
