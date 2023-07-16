package ru.itmentor.spring.boot_security.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import ru.itmentor.spring.boot_security.demo.model.Role;
import ru.itmentor.spring.boot_security.demo.model.User;
import ru.itmentor.spring.boot_security.demo.service.RoleService;
import ru.itmentor.spring.boot_security.demo.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;

    private RoleService roleService;

    @Autowired
    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping
    public String showAllUsers(ModelMap model, HttpSession session) {
        model.addAttribute("users", userService.getAllUsers());
        if (session.getAttribute("message") != null) {
            model.addAttribute("message", session.getAttribute("message"));
            session.removeAttribute("message");
        }

        return "users";
    }

    @GetMapping("/create")
    public String showUserCreationPage(ModelMap map) {
        final User user = new User();
        user.setRoles(new HashSet<>());
        final List<Role> roles = roleService.getAllRoles();
        map.addAttribute("user", user);
        map.addAttribute("roles", roles);
        return "create";
    }

    @PostMapping("/create")
    public String createUser(@ModelAttribute("user") User user,
                             @RequestParam("roleIds") List<Long> roleIds,
                             ModelMap map) {
        userService.addUser(user, roleIds);
        final String message = "User was added";
        map.addAttribute("user", new User());
        map.addAttribute("message", message);
        return "create";
    }

    @PostMapping("/delete")
    public String deleteUser(@RequestParam("id") Long id, HttpSession session) {
        userService.deleteUserById(id);
        session.setAttribute("message", "User was deleted");
        return "redirect:/admin";
    }

    @GetMapping("/update")
    public String showUpdatePage(@RequestParam("id") Long id,
                                 ModelMap map) {
        map.addAttribute("user", userService.getUser(id));
        map.addAttribute("roles", roleService.getAllRoles());
        return "update";
    }


    @PostMapping("/update")
    public String updateUser(@ModelAttribute("user") User user,
                             @RequestParam("roleIds") List<Long> roleIds,
                             HttpSession session) {
        userService.updateUser(user, roleIds);
        session.setAttribute("message", "User was updated");
        return "redirect:/admin";
    }
}
