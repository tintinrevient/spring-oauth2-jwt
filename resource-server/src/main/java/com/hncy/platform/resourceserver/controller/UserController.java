package com.hncy.platform.resourceserver.api;

import com.hncy.platform.resourceserver.domain.User;
import com.hncy.platform.resourceserver.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.security.Principal;
import java.io.IOException;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    private final UserRepository userRepository;

    public UserController(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @RequestMapping("/userinfo")
    public Principal userinfo(Principal principal) {
        return principal;
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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User getUser(@PathVariable("id") int id) {
        return userRepository.findById(id).orElse(null);
    }

    @GetMapping("/search/")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public User findByName(@RequestParam("fullname") String fullname) {
        return userRepository.findByFullnameLike("%" + fullname + "%");
    }

    @GetMapping("/users")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public List<User> findAll() {
        return userRepository.findAll();
    }
}
