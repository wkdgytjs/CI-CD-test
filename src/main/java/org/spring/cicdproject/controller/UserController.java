package org.spring.cicdproject.controller;

import org.apache.catalina.User;
import org.spring.cicdproject.entity.UserEntity;
import org.spring.cicdproject.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import javax.validation.Valid;

@Controller
public class UserController {

    @Autowired
    public UserRepository userRepository;

    @GetMapping("/signup")
    public String showSignUpForm(UserEntity userEntity) {
        return "add-user";
    }

    @PostMapping("/adduser")
    public String addUser(@Valid UserEntity userEntity, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return "add-user";
        }

        userRepository.save(userEntity);
        return "redirect:/index";
    }

    // additional CRUD methods
    @GetMapping("/index")
    public String showUserList(Model model) {
        model.addAttribute("users", userRepository.findAll());
        return "index";
    }

    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));

        model.addAttribute("user", user);
        return "update-user";
    }

    @PostMapping("/update/{id}")
    public String updateUser(@PathVariable("id") long id, @Valid UserEntity userEntity,
                             BindingResult result,   Model model) {
        if (result.hasErrors()) {
            userEntity.setId(id);
            return "update-user";
        }

        userRepository.save(userEntity);
        return "redirect:/index";
    }

    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable("id") long id, Model model) {
        UserEntity userEntity = userRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid user Id:" + id));
        userRepository.delete(userEntity);
        return "redirect:/index";
    }

}
