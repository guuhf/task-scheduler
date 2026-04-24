package com.guuh.taskscheduler.infraestructure.security;


import com.guuh.taskscheduler.business.dtos.UserDTO;
import com.guuh.taskscheduler.infraestructure.security.client.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl{

    @Autowired
    private UserClient client;

    public UserDetails loadUserDataByUsername(String token){
        UserDTO userDTO = client.getLoggedUserData(token);
        return User
                .withUsername(userDTO.getEmail()) // Define o nome de usuário como o e-mail
                .password(userDTO.getPassword()) // Define a senha do usuário
                .build();
    }
}
