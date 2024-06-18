package sk.tuke.gamestudio.server.Controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import sk.tuke.gamestudio.service.UserService;

@Controller
@RequestMapping("/plumber")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/login")
    public String processLogin(@RequestParam String login, @RequestParam String password, RedirectAttributes redirectAttributes, HttpServletRequest request) {
        if (userService.checkUser(login, password)) {
            redirectAttributes.addFlashAttribute("message", "Login successful!");
            request.getSession().setAttribute("user", login);
            return "redirect:/plumber";
        } else {
            redirectAttributes.addFlashAttribute("error", "Invalid credentials");
            return "redirect:/plumber/login";
        }
    }

    @GetMapping("/register")
    public String showRegistrationForm() {
        return "registration";
    }


    @PostMapping("/register")
    public String processRegistration(@RequestParam String login, @RequestParam String password, RedirectAttributes redirectAttributes) {
        try {
            userService.registerUser(login, password);
            redirectAttributes.addFlashAttribute("message", "Registration successful! Please login.");
            return "redirect:/plumber/login";
        } catch (IllegalStateException e) {
            redirectAttributes.addFlashAttribute("error", e.getMessage());
            return "redirect:/plumber/register";
        }
    }
    @GetMapping("/logout")
    public String logout(HttpServletRequest request, RedirectAttributes redirectAttributes) {
        request.getSession().invalidate();
        redirectAttributes.addFlashAttribute("message", "You have been logged out successfully.");
        return "redirect:/plumber/login";
    }

}
