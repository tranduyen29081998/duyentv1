package com.fpt.ppmtool.web;

import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fpt.ppmtool.domain.User;
import com.fpt.ppmtool.payload.JWTLoginSucessReponse;
import com.fpt.ppmtool.payload.LoginRequest;
import com.fpt.ppmtool.security.JwtTokenProvider;
import com.fpt.ppmtool.security.SecurityConstants;
import com.fpt.ppmtool.services.MapValidationErrorService;
import com.fpt.ppmtool.services.UserService;
import com.fpt.ppmtool.validator.UserValidator;

@RestController
@RequestMapping("/api/users")
public class UserController {
    @Autowired
    private MapValidationErrorService mapValidationErrorService;

    @Autowired
    private UserService userService;
    @Autowired
    private UserValidator userValidator;
    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result){
    	
    	// validate form
        ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
        if(errorMap != null) return errorMap;
        
        // sử dụng AuthenticationManager để xác thực người dùng( hàm:
        //protected void configure(AuthenticationManagerBuilder authenticationManagerBuilder) throws Exception {
        //    authenticationManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder);
       // } để xác thực người dùng
        // 
        
        //UsernamePasswordAuthenticationToken là 1 object authenticate
        // authenticationManager.authenticate(authentication--(new UsernamePasswordAuthentication)): tức là 
        // sử dụng authenticationManager để xác thực authentication này
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword()
                )
        );
        
        
        // Tạo token và trả về cho user
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = SecurityConstants.TOKEN_PREFIX +  tokenProvider.generateToken(authentication);

        return ResponseEntity.ok(new JWTLoginSucessReponse(true, jwt));
    }
    
    @PostMapping("/register")
    public ResponseEntity<?> registerUser (@Valid @RequestBody User user, BindingResult result){
    	
    	//Validate passwords match
    	// kiểm tra trường password và nhập lại password, nhờ lớp Validator để thêm
    	// trường lỗi vào trong BindingResult, thông qua errors.rejectValue
    	userValidator.validate(user,result);
    	
    	// Bên trong hàm MapValidation là nhận vào tham số BindingResult(bao gồm tên lỗi và thông báo lỗi , dùng collection Map
    	// để map tất cả vào collection map, và trả về return new ResponseEntity<Map<String, String>>(errorMap, HttpStatus.BAD_REQUEST);)
    	ResponseEntity<?> errorMap = mapValidationErrorService.MapValidationService(result);
    	if(errorMap != null) return errorMap;
    	
    	User newUser = userService.saveUser(user);
    	return new ResponseEntity<User>(newUser, HttpStatus.CREATED);
    }
}