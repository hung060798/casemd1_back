package com.codegym.casemd4.controller;

import com.codegym.casemd4.model.Account;
import com.codegym.casemd4.model.AppRole;
import com.codegym.casemd4.service.account.IServiceAccount;
import com.codegym.casemd4.service.approle.IServiceAppRole;
import com.codegym.casemd4.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("/account")
public class SignUpController {
    @Autowired
    IServiceAccount serviceAccount;
    @Autowired
    IServiceAppRole serviceAppRole;
    @Autowired
    JwtService jwtService;


    @PostMapping("/signup")
    public ResponseEntity<String> createAcc(@RequestBody Account account){
        String message ="";
        AppRole role = serviceAppRole.findById(2L).get();
        account.setRole(role);
        if(serviceAccount.add(account)){
            message="Ok";
        }else message ="account exited!";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(HttpServletRequest request, @RequestBody Account account) {
        String result = "";
        HttpStatus httpStatus = null;
        try {
            if (serviceAccount.checkLogin(account)) {
                result = jwtService.generateTokenLogin(account.getEmail());
                httpStatus = HttpStatus.OK;
            } else {
                result = "Wrong userId and password";
                httpStatus = HttpStatus.BAD_REQUEST;
            }
        } catch (Exception ex) {
            result = "Server Error";
            httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return new ResponseEntity<String>(result, httpStatus);
    }


}
