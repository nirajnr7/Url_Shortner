package org.coding.urlShortner.Controller;

import org.coding.urlShortner.DTOs.ShortenedUrl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

    @GetMapping("/urlShortener")
    public String showURLShortenerForm(Model model) {
        model.addAttribute("shortenedUrl", new ShortenedUrl());
        return "urlShortner.html";
    }
}

