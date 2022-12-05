package com.example.ecommerce.mvc.controller.admin;

import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.RolesDetail;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.validate.AccountValidate;
import com.example.ecommerce.repository.AccountRepo;
import com.example.ecommerce.repository.RolesDetailRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("mvc/admin")
public class LoginAdminController {
    String path="";
    AccountValidate accountValidate=new AccountValidate();
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AccountRepo accountDAO;
    @Autowired
    SessionDAO session;

    @Autowired
    RolesDetailRepo rolesDetailDAO;

    @GetMapping("/login")
    public String login(Model model, @RequestParam Optional<String> urlReturn,
                        @RequestParam Optional<String> error) {
        if(session.get("admin")!=null){
            return "redirect:/mvc/admin/category";
        }
        path=urlReturn.isPresent()?urlReturn.get():"";
        if(error.isPresent()) {
            if(error.get().equals("errorNoLogin")){
                model.addAttribute("message", "Không đủ quyền truy cập!");
            }else if(error.get().equals("loginFail")){
                model.addAttribute("message", "Sai tên đăng nhập hoặc mật khẩu");
            }
        }
        return "admin/login";
    }

    @GetMapping("/logout")
    public String logout(@RequestParam Optional<String> urlReturn) {
        session.clear();
        return urlReturn.isPresent()?"redirect:/mvc/admin/login?urlReturn=" +urlReturn.get():"redirect:/mvc/admin/login";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password) {
        boolean loginSuccess = false;
        boolean failUserOrPassword = true;
        List<String> listCheck=new ArrayList<>();
        listCheck.add(username);
        listCheck.add(password);
        if (!accountValidate.listIsNullOrEmpty(listCheck)) {
            List<Account> list = accountDAO.findAll();
            for (Account account : list) {
                List<RolesDetail> listRolesDetail=rolesDetailDAO.findByAccountId(account.getId());
                for(RolesDetail rolesDetail:listRolesDetail){
                    if (username.equalsIgnoreCase(account.getUsername())
                            && passwordEncoder.matches(password,account.getPassword())) {
                        failUserOrPassword=false;
                        if(rolesDetail.getRolesByRoleId().getRoleName().equals("OWNER")){
                            session.set("admin", account);
                            loginSuccess = true;
                            break;
                        }
                    }
                }
            }
        }
        if(failUserOrPassword){
            return !path.isEmpty() ? "redirect:/mvc/admin/login?error=loginFail&urlReturn="+path :"redirect:/mvc/admin/login?error=loginFail";
        }
        if(loginSuccess) {
            return !path.isEmpty() ? "redirect:/mvc/admin/"+path : "redirect:/mvc/admin/category";
        }else {
            return !path.isEmpty() ? "redirect:/mvc/admin/login?error=errorNoLogin&urlReturn="+path :"redirect:/mvc/admin/login?error=errorNoLogin";
        }

    }

}