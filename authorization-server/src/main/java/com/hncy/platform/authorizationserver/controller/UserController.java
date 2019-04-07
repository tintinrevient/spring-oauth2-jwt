package com.hncy.platform.authorizationserver.controller;

import com.hncy.platform.authorizationserver.repository.UserRepository;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/logout")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        // token can be revoked here if needed
        new SecurityContextLogoutHandler().logout(request, null, null);
        try {
            //sending back to client app
            response.sendRedirect(request.getHeader("referer"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
