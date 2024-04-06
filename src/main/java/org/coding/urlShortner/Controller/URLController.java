package org.coding.urlShortner.Controller;

import org.coding.urlShortner.DTOs.ShortenedUrl;
import org.coding.urlShortner.Service.URLService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/miniUrl")
public class URLController {
    private final URLService urlService;

    public URLController(URLService urlService) {
        this.urlService = urlService;
    }

    @PostMapping()
    public ResponseEntity<Map<String, String>> shortenURL(@RequestBody String originalURL) {
        String shortenedURL = urlService.shortenURL(originalURL);
        Map<String, String> response = new HashMap<>();
        response.put("shortenedUrl", shortenedURL);
        return ResponseEntity.ok().body(response);
    }

    @GetMapping("/{shortURL}")
    public ResponseEntity<Void> redirectToOriginalURL(@PathVariable String shortURL) {
        String originalURL = urlService.getOriginalURL(shortURL);
        if (originalURL != null) {
            return ResponseEntity.status(HttpStatus.FOUND)
                    .location(URI.create(originalURL))
                    .build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

