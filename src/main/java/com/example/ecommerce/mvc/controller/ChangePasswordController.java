package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.model.Account;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Optional;

@Controller
@RequestMapping("mvc/change-password")
public class ChangePasswordController {
	@Autowired
	SessionDAO session;

	@Autowired
	AccountRepo accountDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;

    @GetMapping("")
    public String index(Model model,
						@RequestParam Optional<String> urlReturn,
						@RequestParam Optional<String> password,
						@RequestParam Optional<String> rePassword) {
		if(session.get("user")==null){
			return urlReturn.isPresent()?"redirect:/mvc/" +urlReturn.get():"redirect:/mvc/index";
		}
		if(password.isPresent()){
			if(password.get().equals("true")){
				model.addAttribute("success","Đổi mật khẩu thành công!");
			}else if(password.get().equals("0")){
				model.addAttribute("error","Vui lòng điền mật khẩu!");
			}else{
				model.addAttribute("passwordValue",password.get());
			}
		}
		if(rePassword.isPresent()){
			if(rePassword.get().equals("1")){
				model.addAttribute("error","Nhập lại mật khẩu không đúng!");
			} else if(rePassword.get().equals("0")){
				model.addAttribute("error","Vui lòng điền nhập lại mật khẩu!");
			}
		}
        return "customer/change-password";
    }
	@PostMapping("")
	public String changePassword(@RequestParam Optional<String> newPassword,
								 @RequestParam Optional<String> confirmPassword){
		if(!newPassword.isPresent()){
			//Lỗi 0 là lỗi chưa điền newPassword
			return "redirect:/mvc/change-password?password=0";
		}
		if(!confirmPassword.isPresent()){
			//Lỗi 0 là lỗi chưa điền rePassword
			return "redirect:/mvc/change-password?rePassword=0";
		}
		if(!newPassword.get().equals(confirmPassword.get())){
			//Lỗi 1 là lỗi điền sai rePassword
			return "redirect:/mvc/change-password?password="+newPassword.get()+"&rePassword=1";
		}
		Account account=(Account) session.get("user");
		account.setPassword(passwordEncoder.encode(newPassword.get()));
		accountDAO.save(account);
		return "redirect:/mvc/change-password?password=true";

	}
}
