package com.virtuallab.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    private static final String ADMIN_USER = "admin";
    private static final String ADMIN_PASS = "admin123";

    // ── Show Login Page ───────────────────────────────────────────────────────

    @GetMapping("/")
    public String root() {
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String showLoginPage(HttpSession session) {
        if (session.getAttribute("loggedIn") != null) {
            return "redirect:/dashboard";
        }
        return "login";
    }

    // ── Handle Login Submission ───────────────────────────────────────────────

    @PostMapping("/login")
    public String processLogin(@RequestParam("username") String username,
            @RequestParam("password") String password,
            HttpSession session,
            Model model) {
        if (ADMIN_USER.equals(username) && ADMIN_PASS.equals(password)) {
            session.setAttribute("loggedIn", true);
            session.setAttribute("username", username);
            return "redirect:/dashboard";
        }
        model.addAttribute("error", "Invalid username or password. Please try again.");
        return "login";
    }

    // ── Logout ────────────────────────────────────────────────────────────────

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/login";
    }
}
