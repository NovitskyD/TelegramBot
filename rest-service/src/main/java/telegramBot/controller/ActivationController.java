package telegramBot.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import telegramBot.service.UserActivationService;


import java.util.NoSuchElementException;

@RequestMapping("/user")
@RestController
public class ActivationController {
    private final UserActivationService userActivationService;

    public ActivationController(UserActivationService userActivationService) {
        this.userActivationService = userActivationService;
    }

    @GetMapping("/activation")
    public ResponseEntity<String> activation(@RequestParam("id") String id) {
        try {
            boolean success = userActivationService.activation(id);
            if (success) {
                return ResponseEntity.ok().body("Registration successfully completed!");
            } else {
                return ResponseEntity.badRequest().body("Invalid user ID or activation process failed.");
            }
        } catch (NoSuchElementException e) {
            return ResponseEntity.badRequest().body("User not found.");
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Internal server error");
        }
    }
}
