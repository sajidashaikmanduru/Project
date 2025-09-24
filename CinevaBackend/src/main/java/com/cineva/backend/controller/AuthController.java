package com.cineva.backend.controller;

import com.cineva.backend.dto.SignInRequest;
import com.cineva.backend.entity.User;
import com.cineva.backend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:5173", methods = {RequestMethod.POST, RequestMethod.GET}, allowedHeaders = "*")
public class AuthController {

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
        logger.info("AuthController initialized");
    }

    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@Validated @RequestBody SignInRequest signInRequest, BindingResult bindingResult) {
        logger.debug("Received sign-in request for emailOrPhone: {}", signInRequest.getEmailOrPhone());

        // Check for validation errors
        if (bindingResult.hasErrors()) {
            String errorMessage = bindingResult.getAllErrors().get(0).getDefaultMessage();
            logger.error("Validation failed: {}", errorMessage);
            return ResponseEntity.badRequest().body(errorMessage);
        }

        try {
            User user = userService.signIn(signInRequest.getEmailOrPhone(), signInRequest.getPassword());
            logger.info("Sign-in successful for user: {}", user.getEmailOrPhone());
            return ResponseEntity.ok("Sign-in successful for user: " + user.getEmailOrPhone());
        } catch (Exception e) {
            logger.error("Sign-in failed: {}", e.getMessage(), e);
            return ResponseEntity.badRequest().body("Sign-in failed: " + e.getMessage());
        }
    }
}