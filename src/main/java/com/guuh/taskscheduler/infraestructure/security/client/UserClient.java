    package com.guuh.taskscheduler.infraestructure.security.client;

    import com.guuh.taskscheduler.business.dtos.UserDTO;
    import org.springframework.cloud.openfeign.FeignClient;
    import org.springframework.web.bind.annotation.GetMapping;
    import org.springframework.web.bind.annotation.RequestHeader;

    @FeignClient(name = "user", url = "${user.url}")
    public interface UserClient {

        @GetMapping("/user/me")
        UserDTO getLoggedUserData(@RequestHeader("Authorization") String token);
    }
