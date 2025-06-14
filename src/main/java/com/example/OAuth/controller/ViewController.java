package com.example.OAuth.controller;

import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.Map;

@Controller
public class ViewController {

    @GetMapping("/profile")
    public String profile(OAuth2AuthenticationToken token, Model model) {
        Map<String, Object> attributes = token.getPrincipal().getAttributes();
        String registrationId = token.getAuthorizedClientRegistrationId();

        String name;
        String email;
        String photo;

        switch (registrationId) {
            case "google":
                name = (String) attributes.get("name");
                email = (String) attributes.get("email");
                photo = (String) attributes.get("picture");
                break;
            case "github":
                name = (String) attributes.get("name");
                if (name == null || name.isEmpty()) {
                    name = (String) attributes.get("login");
                }
                email = (String) attributes.getOrDefault("email", "N/A");
                photo = (String) attributes.get("avatar_url");
                break;
            default:
                name = "Unknown User";
                email = "Unknown";
                photo = "/default-avatar.jpg"; // put a default image in static folder
                break;
        }

        model.addAttribute("name", name);
        model.addAttribute("email", email);
        model.addAttribute("photo", photo);

        return "user-profile";
    }



    @GetMapping("/login")
    public String login() {
        return "custom_login";
    }
}
