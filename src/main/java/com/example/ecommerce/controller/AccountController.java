package com.example.ecommerce.controller;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.ecommerce.common.Utils;
import com.example.ecommerce.jwt.CustomUser;
import com.example.ecommerce.model.Account;
import com.example.ecommerce.model.data.AccountOauthDataModel;
import com.example.ecommerce.request.AccountLoginRequest;
import com.example.ecommerce.request.AccountRequest;
import com.example.ecommerce.response.AccountResponse;
import com.example.ecommerce.response.BaseResponse;
import com.example.ecommerce.service.AccountOauthService;
import com.example.ecommerce.service.AccountService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountOauthService oauthService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Authentication authentication;

    @Autowired
    private PasswordEncoder passwordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);


    @RequestMapping(value = "/get-all-account", method = RequestMethod.GET, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> getAllAccount(
            @RequestParam(name = "key_search", required = false, defaultValue = "") String keySearch,
            @RequestParam(name = "is_deleted", required = false, defaultValue = "-1") int isDeleted) throws Exception {
        BaseResponse response = new BaseResponse();
        List<Account> listAccounts = accountService.getAllAccount(keySearch, isDeleted);
        List<AccountResponse> responseList = new AccountResponse().mapToListResponse(listAccounts);
        response.setData(responseList);
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/create-account", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> createAccount(@Valid @RequestBody AccountRequest request) throws Exception {
        BaseResponse response = new BaseResponse();
        List<Account> listAccount = null;
        listAccount = accountService.getOneAccount(request.getUsername());
        if (listAccount.size() > 0) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessageError("Tên tài khoản đã tồn tại");
            return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
        }
        listAccount = accountService.getOneAccount(request.getPhone());
        if (listAccount.size() > 0) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessageError("Số điện thoại đã tồn tại");
            return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
        }
        listAccount = accountService.getOneAccount(request.getEmail());
        if (listAccount.size() > 0) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessageError("Email đã tồn tại");
            return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
        }
        Account account = new Account();
        account.setFullName(request.getFullName());
        account.setEmail(request.getEmail());
        account.setPassword(request.getPassword());
        account.setPhone(request.getPhone());
        account.setUsername(request.getUsername());
        account.setDateOfBirth(Utils.convertStringToDate(request.getDateOfBirth()));
        accountService.createAccount(account);
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/edit-profile/{id}", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> editProfile(@PathVariable("id") int id,
                                                    @Valid @RequestBody AccountRequest request) throws Exception {
        BaseResponse response = new BaseResponse();
        Account account = null;
        account = accountService.findOne(id);
        if (account == null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessageError("Tài khoản không tồn tại");
            return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
        }
        List<Account> listAccount = null;
        listAccount = accountService.getOneAccount(request.getPhone());
        if (listAccount.size() > 0) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessageError("Số điện thoại đã tồn tại");
            return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
        }
        listAccount = accountService.getOneAccount(request.getEmail());
        if (listAccount.size() > 0) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessageError("Email đã tồn tại");
            return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
        }
        account = new Account();
        account.setId(id);
        account.setFullName(request.getFullName());
        account.setEmail(request.getEmail());
        account.setPhone(request.getPhone());
        account.setDateOfBirth(Utils.convertStringToDate(request.getDateOfBirth()));
        accountService.editProfile(account);
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/delete-account/{id}", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> deleteAccount(@PathVariable("id") int id) throws Exception {
        BaseResponse response = new BaseResponse();
        Account account = accountService.findOne(id);
        if (account == null) {
            response.setStatus(HttpStatus.BAD_REQUEST);
            response.setMessageError("Tài khoản không tồn tại");
            return new ResponseEntity<BaseResponse>(response, HttpStatus.BAD_REQUEST);
        }
        accountService.deleteAccount(id);
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }

    @RequestMapping(value = "/refresh-token", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public void refershToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String authorizationHeader = request.getHeader(AUTHORIZATION);
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            try {
                String refresh_token = authorizationHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
                JWTVerifier verifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = verifier.verify(refresh_token);
                String username = decodedJWT.getSubject();
                AccountOauthDataModel accountOauthDataModel = oauthService.getAccountOauth(username); // thêm store
                String access_token = JWT.create()
                        .withSubject(accountOauthDataModel.getUserName())
                        .withExpiresAt(new Date(System.currentTimeMillis() + 10 * 60 * 1000))
                        .withClaim("roles", accountOauthDataModel.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()))
                        .sign(algorithm);
                String roles = accountOauthDataModel.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()).toString();
                System.out.println("roles: " + roles);
                Map<String, String> tokens = new HashMap<>();
                tokens.put("access_token", access_token);
                tokens.put("refresh_token", refresh_token);
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), tokens);
            } catch (Exception e) {
                response.setHeader("error", e.getMessage());
                response.setStatus(FORBIDDEN.value());
                Map<String, String> errors = new HashMap<>();
                errors.put("error_message", e.getMessage());
                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(), errors);
            }
        } else {
            throw new RuntimeException("Refresh token is missing");
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {
            MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<BaseResponse> accountLogin(@RequestBody AccountLoginRequest request) throws Exception {
        BaseResponse response = new BaseResponse();
        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
        AccountOauthDataModel accountOauthDataModel = oauthService.getAccountOauth(request.getUsername()); // thêm store
        String access_token = JWT.create()
                .withSubject(accountOauthDataModel.getUserName())
                .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
                .withClaim("roles", accountOauthDataModel.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()))
                .sign(algorithm);
        response.setData(access_token);
        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
    }


//    @RequestMapping(value = "/login", method = RequestMethod.POST, produces = {
//            MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<BaseResponse> accountLogin(@RequestParam(name = "username", required = false, defaultValue = "") String username,
//                                                     @RequestParam(name = "password", required = false, defaultValue = "") String password) throws Exception {
//        BaseResponse response = new BaseResponse();
//        Algorithm algorithm = Algorithm.HMAC256("secret".getBytes());
//        AccountOauthDataModel accountOauthDataModel = oauthService.getAccountOauth(username); // thêm store
//        String access_token = JWT.create()
//                .withSubject(accountOauthDataModel.getUserName())
//                .withExpiresAt(new Date(System.currentTimeMillis() + 24 * 60 * 60 * 1000))
//                .withClaim("roles", accountOauthDataModel.getRoles().stream().map(role -> role.getName()).collect(Collectors.toList()))
//                .sign(algorithm);
//        System.out.println("Nguyễn Hoàng Hải");
//        response.setData(access_token);
//        return new ResponseEntity<BaseResponse>(response, HttpStatus.OK);
//    }

}
