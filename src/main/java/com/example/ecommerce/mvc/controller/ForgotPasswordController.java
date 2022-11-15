package com.example.ecommerce.mvc.controller;

import com.example.ecommerce.model.Account;
import com.example.ecommerce.mvc.dao.MailerService;
import com.example.ecommerce.mvc.helper.MailerHelper;
import com.example.ecommerce.mvc.model.MailInfo;
import com.example.ecommerce.repository.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.mail.MessagingException;
import java.io.IOException;
import java.util.Optional;

@Controller
@RequestMapping("mvc/forgot-password")
public class ForgotPasswordController {
	Account account=new Account();
	String codeRandom="";
	@Autowired
	MailerService mailer;
	@Autowired
	AccountRepo accountDAO;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@GetMapping("")
	public String index(Model model,
						@RequestParam Optional<String> password,
						@RequestParam Optional<String> message,
						@RequestParam Optional<String> messageCode,
						@RequestParam Optional<String> messageChangePassword,
						@RequestParam Optional<String> email,
						@RequestParam Optional<String> username,
						@RequestParam Optional<String> changePasswordConfirm) {
		if (message.isPresent()) {
			if(message.get().equals("true")){
				model.addAttribute("success","Mã xác thực đã được gửi đến email của bạn");
				model.addAttribute("showCode",true);
			}else {
				model.addAttribute("error","Tài khoản và địa chỉ email không khớp với bất kì tài khoản nào");
				model.addAttribute("showCode",false);
			}
		}
		if(messageCode.isPresent()){
			if(messageCode.get().equals("true")){
				model.addAttribute("success","Thông tin tài khoản đã được gửi đến email của bạn");
				model.addAttribute("showCode",true);
			}else {
				model.addAttribute("error","Mã xác thực không chính xác, hãy thử lại");
				model.addAttribute("showCode",true);
			}

		}
		if(email.isPresent()){
			model.addAttribute("emailShow",email.get());
		}
		if(username.isPresent()){
			model.addAttribute("usernameShow",username.get());
		}
		if(changePasswordConfirm.isPresent()){
			model.addAttribute("changePasswordConfirm",true);
		}else{
			model.addAttribute("changePasswordConfirm",false);
		}
		if(password.isPresent()){
			model.addAttribute("passwordValue",password.get());
		}
		if(messageChangePassword.isPresent()){
			if(messageChangePassword.get().equals("true")){
				model.addAttribute("changePassSuccess","Đổi mật khẩu thành công");
				model.addAttribute("checkRepassword",false);
			}else if(messageChangePassword.get().equals("-2")){
				model.addAttribute("checkRepassword",true);
			}else if(messageChangePassword.get().equals("-1")){
				model.addAttribute("errorChanePassword","Đổi mật khẩu thất bại");
			}
		}
		return "customer/forgot-password";
	}
	@PostMapping("send-code")
	public String send(@RequestParam Optional<String> email,
					   @RequestParam Optional<String> username) throws IOException, MessagingException {
		if(email.isPresent()){
			account=accountDAO.findByEmailOrPhone(username.get(),email.get());
			if(account==null){
				return "redirect:/mvc/forgot-password?message=false&email="+email.get()+"&username="+username.get();
			}
			MailerHelper helper=new MailerHelper();
			codeRandom=helper.randomAlphaNumeric(6);
			MailInfo mail = new MailInfo();
			mail.setTo(email.get());
			mail.setSubject("Lấy lại mật khẩu");
			String body= helper.htmlCode(codeRandom);
			mail.setBody(body);
			//Gửi mail
			mailer.queue(mail);
			return "redirect:/mvc/forgot-password?message=true&email="+email.get()+"&username="+username.get();
		}else{
			return "redirect:/mvc/forgot-password?message=false&email="+email.get()+"&username="+username.get();
		}
	}
	@PostMapping("verify")
	public String verify(@RequestParam Optional<String> code){
		if(code.isPresent()){
			if(code.get().equals(codeRandom)){
				return "redirect:/mvc/forgot-password?changePasswordConfirm=true";
			}
		}
		return "redirect:/mvc/forgot-password?messageCode=false&email="+account.getEmail()+"&username="+account.getUsername();
	}
	@PostMapping("change-password")
	public String changePassword(@RequestParam Optional<String> newPassword,
								 @RequestParam Optional<String> confirmPassword){
		if(newPassword.isPresent()){
			if(newPassword.get().equals(confirmPassword.get())){
				if(account.getUsername()!=null){
					account.setPassword(passwordEncoder.encode(newPassword.get()));
					accountDAO.save(account);
					return "redirect:/mvc/forgot-password?changePasswordConfirm=true&messageChangePassword=true";
				}else{
					return "redirect:/mvc/forgot-password?changePasswordConfirm=true&messageChangePassword=-1";
				}
			}else{
				return "redirect:/mvc/forgot-password?password="+newPassword.get()+"&changePasswordConfirm=true&messageChangePassword=-2";
			}
		}
		return "redirect:/mvc/forgot-password?messageCode=false&email="+account.getEmail()+"&username="+account.getUsername();
	}

}
