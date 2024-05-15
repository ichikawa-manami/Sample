package com.example.demo.controller;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.demo.form.LoginForm;
import com.example.demo.repository.UserInfoRepository;
import com.example.demo.service.LoginService;

import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class LoginController {
	
	private final LoginService service;
	
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
	
	@GetMapping("/login")
	public String view(Model model,LoginForm form) {
		
		return "login";
	}
	
 @PostMapping("/login")
 public String login(Model model,LoginForm form) {
	 var userInfo = service.searchUserById(form.getLoginId());
	 var isCorrectUserAuth = userInfo.isPresent()
			 && passwordEncoder.matches(form.getPassword(),userInfo.get().getPassword());
	 if(isCorrectUserAuth) {
		 return "redirect:/menu";
	 }else {
		 model.addAttribute("errorMsg","ログインIDとパスワードの組み合わせが間違っています。");
		 return "login";
 }
 }

}