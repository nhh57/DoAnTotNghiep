package com.example.ecommerce.mvc.controller.admin;



import com.example.ecommerce.common.Utils;
import com.example.ecommerce.model.*;
import com.example.ecommerce.model.helper.AccountHelper;
import com.example.ecommerce.mvc.dao.SessionDAO;
import com.example.ecommerce.mvc.model.AccountResult;
import com.example.ecommerce.mvc.model.RoleCustom;
import com.example.ecommerce.repository.*;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
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
@RequestMapping("mvc/admin/account")
public class AccountAdminController {
    @Autowired
    AccountRepo accountDAO;

    @Autowired
    ShipDetailRepo shipDetailDAO;

    @Autowired
    ReviewRepo reviewDAO;

    @Autowired
    OrderRepo orderDAO;

    @Autowired
    OrderDetailRepo orderDetailDAO;

    @Autowired
    RolesDetailRepo rolesDetailDAO;

    @Autowired
    RolesRepo rolesDAO;

    @Autowired
    private PasswordEncoder passwordEncoder;

    AccountHelper accountHelper = new AccountHelper();

    @GetMapping("")
    public String index(Model model,
                        @RequestParam("txtSearch") Optional<String> txtSearch,
                        @RequestParam Optional<String> message,
                        @RequestParam("soTrang") Optional<String> soTrangString,
                        @RequestParam("soSanPham") Optional<String> soSanPhamString) {
        int soTrang = !soTrangString.isPresent() ? 1 : Integer.parseInt(soTrangString.get());
        int soSanPham = !soSanPhamString.isPresent() ? 6 : Integer.parseInt(soSanPhamString.get());
        int tongSoTrang = txtSearch.isPresent()
                ? accountHelper.getTotalPage(soSanPham, accountDAO.findByUsernameOrFullNameOrEmailOrPhone(txtSearch.get(),txtSearch.get()))
                : accountHelper.getTotalPage(soSanPham, accountDAO.findAll());
        if (soTrang < 1) {
            soTrang = 1;
        } else if (soTrang > tongSoTrang && tongSoTrang > 0) {
            soTrang = tongSoTrang;
        }
        model.addAttribute("soTrangHienTai", soTrang);
        model.addAttribute("soSanPhamHienTai", soSanPham);
        model.addAttribute("tongSoTrang", tongSoTrang);
        if(txtSearch.isPresent() && txtSearch.get()!=null){
            model.addAttribute("timKiemHienTai", txtSearch.get());
        }else{
            model.addAttribute("timKiemHienTai", "");
        }
        Pageable pageable = PageRequest.of(soTrang - 1, soSanPham);
        Page<Account> pageAccount = txtSearch.isPresent()
                ? accountDAO.findByUsernameOrFullNameOrEmailOrPhone(pageable, txtSearch.get(),txtSearch.get())
                : accountDAO.findAll(pageable);
        List<Account> list = pageAccount.getContent();
        if (message.isPresent()) {
            if (message.get().equals("saved")) {
                model.addAttribute("message", "Lưu thành công!");
            }
            if (message.get().equals("saveFail")) {
                model.addAttribute("message", "Lưu thất bại!");
            }
            if (message.get().equals("deleted")) {
                model.addAttribute("message", "Xóa thành công!");
            }
            if (message.get().equals("deleteFail")) {
                model.addAttribute("message", "Xóa thất bại!");
            }
            if (message.get().equals("reverted")) {
                model.addAttribute("message", "Khôi phục thành công!");
            }
            if (message.get().equals("revertFail")) {
                model.addAttribute("message", "Khôi phục thất bại!");
            }
        }
        List<Roles> listRoles=rolesDAO.findAll();
        model.addAttribute("listRole",listRoles);
        model.addAttribute("listAccount", getListAccountResult(list,listRoles));
        return "admin/account";
    }

    private List<AccountResult> getListAccountResult(List<Account> listAccount, List<Roles> listRoles){
        List<AccountResult> listAccountResult=new ArrayList<>();
        for (Account item : listAccount) {
            AccountResult accountResult = new AccountResult();
            accountResult.setAccount(item);
            List<RoleCustom> listRoleCustom=new ArrayList<>();
            List<RolesDetail> listRolesDetail=rolesDetailDAO.findByAccountId(item.getId());
            for(Roles roles : listRoles){
                RoleCustom roleCustom=new RoleCustom();
                roleCustom.setRoles(roles);
                roleCustom.setStatus(false);
                for(RolesDetail rolesDetail:listRolesDetail){
                    if(roles.getId()==rolesDetail.getRoleId()){
                        roleCustom.setStatus(true);
                        break;
                    }
                }
                listRoleCustom.add(roleCustom);
            }
            accountResult.setRoleCustom(listRoleCustom);
            listAccountResult.add(accountResult);
        }
        return listAccountResult;
    }
    @PostMapping("save")
    public String update(@RequestParam("accountId") Optional<Integer> accountId,
                         @RequestParam("username") Optional<String> username,
                         @RequestParam("old-password") Optional<String> oldPassword,
                         @RequestParam("password") Optional<String> password,
                         @RequestParam("email") Optional<String> email,
                         @RequestParam("fullName") Optional<String> fullName,
                         @RequestParam("phone") Optional<String> phone,
                         @RequestParam("dateOfBirth") Optional<String> dateOfBirth,
                         @RequestParam("gender") Optional<String> gender,
                         @RequestParam("cartId") Optional<Integer> cartId,
                         @RequestParam("isDeleted") Optional<Boolean> isDeleted) {
        try{
            Account account = new Account();
            if(accountId.isPresent()){
                account.setId(accountId.get());
            }
            account.setCartId(cartId.get());
            account.setUsername(username.get());
            if(!password.get().equals("passwordDefault")){
                account.setPassword(passwordEncoder.encode(password.get()));
            }else{
                account.setPassword(oldPassword.get());
            }
            account.setEmail(email.get());
            account.setFullName(fullName.get());
            account.setPhone(phone.get());
            account.setGender(gender.get());
            account.setDateOfBirth(Utils.converStringToDate(dateOfBirth.get()));
            if(isDeleted.isPresent()){
                account.setDeleted(isDeleted.get());
            }else{
                account.setDeleted(false);
            }
            accountDAO.save(account);
            return "redirect:/mvc/admin/account?message=saved";
        }catch (Exception e){
            e.printStackTrace();
            return "redirect:/mvc/admin/account?message=saveFail";
        }

    }

    @GetMapping("delete")
    public String delete(@RequestParam("accountId") Optional<String> accountId) {
        try {
            Integer id = Integer.parseInt(accountId.get());
            List<Orders> orderss=orderDAO.findAllByAccountId(id);
            for(Orders o:orderss){
                List<OdersDetail> odersDetails=orderDetailDAO.findAllByOrderId(o.getId());
                for(OdersDetail od:odersDetails){
                    od.setDeleted(true);
                    orderDetailDAO.save(od);
                }
                o.setIsDeleted(true);
                orderDAO.save(o);
            }
            List<ShipDetail> shipDetails=shipDetailDAO.findByAccountId(id);
            for(ShipDetail s:shipDetails){
                s.setDeleted(true);
                shipDetailDAO.save(s);
            }
            List<RolesDetail> rolesDetails=rolesDetailDAO.findByAccountId(id);
            for(RolesDetail r:rolesDetails){
                r.setDeleted(true);
                rolesDetailDAO.save(r);
            }
            List<Reviews> reviewsList=reviewDAO.findByAccountId(id);
            for(Reviews r:reviewsList){
                r.setDeleted(true);
                reviewDAO.save(r);
            }
            Account account=accountDAO.findById(id).get();
            account.setDeleted(true);
            accountDAO.save(account);
            return "redirect:/mvc/admin/account?message=deleted";
        } catch (Exception e) {
            return "redirect:/mvc/admin/account?message=deleteFail";
        }
    }
    @GetMapping("revert")
    public String revert(@RequestParam("accountId") Optional<String> accountId) {
        try {
            Integer id = Integer.parseInt(accountId.get());
            List<Orders> orderss=orderDAO.findAllByAccountId(id);
            for(Orders o:orderss){
                List<OdersDetail> odersDetails=orderDetailDAO.findAllByOrderId(o.getId());
                for(OdersDetail od:odersDetails){
                    od.setDeleted(false);
                    orderDetailDAO.save(od);
                }
                o.setIsDeleted(false);
                orderDAO.save(o);
            }
            List<ShipDetail> shipDetails=shipDetailDAO.findByAccountId(id);
            for(ShipDetail s:shipDetails){
                s.setDeleted(false);
                shipDetailDAO.save(s);
            }
            List<RolesDetail> rolesDetails=rolesDetailDAO.findByAccountId(id);
            for(RolesDetail r:rolesDetails){
                r.setDeleted(false);
                rolesDetailDAO.save(r);
            }
            List<Reviews> reviewsList=reviewDAO.findByAccountId(id);
            for(Reviews r:reviewsList){
                r.setDeleted(false);
                reviewDAO.save(r);
            }
            Account account=accountDAO.findById(id).get();
            account.setDeleted(false);
            accountDAO.save(account);
            return "redirect:/mvc/admin/account?message=reverted";
        } catch (Exception e) {
            return "redirect:/mvc/admin/account?message=revertFail";
        }
    }

    @PostMapping("set-role")
    public ResponseEntity<String> setRole(@RequestParam("accountId") String accountIdString,
                                         @RequestParam("roleId") String roleIdString) throws JSONException {
        Integer accountId=Integer.parseInt(accountIdString);
        Integer roleId=Integer.parseInt(roleIdString);
        RolesDetail rolesDetail=rolesDetailDAO.findByAccountIdAndRoleId(accountId,roleId);
        JSONObject json = new JSONObject();
        if(rolesDetail!=null){
            rolesDetailDAO.delete(rolesDetail);
            json.put("status", "Xóa thành công!");
        }else{
            RolesDetail rd=new RolesDetail();
            rd.setDeleted(false);
            rd.setAccountId(accountId);
            rd.setRoleId(roleId);
            rolesDetailDAO.save(rd);
            json.put("status", "Lưu thành công!");
        }
        return ResponseEntity.ok(String.valueOf(json));
    }
}
