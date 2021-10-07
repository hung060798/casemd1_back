package com.codegym.casemd4.controller;


import com.codegym.casemd4.model.Account;
import com.codegym.casemd4.service.account.IServiceAccount;
import com.codegym.casemd4.service.jwt.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/admin")
@CrossOrigin(origins = "*")
public class AdminController {
    @Autowired
    IServiceAccount serviceAccount;
    @Autowired
    JwtService jwtService;

    @PostMapping("")
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
