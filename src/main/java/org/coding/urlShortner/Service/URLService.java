package org.coding.urlShortner.Service;

import jakarta.servlet.http.HttpServletRequest;
import org.apache.commons.collections4.CollectionUtils;
import org.coding.urlShortner.Entity.URLMapping;
import org.coding.urlShortner.Repository.URLMappingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.List;

@Service
public class URLService {

    @Autowired
    private URLMappingRepository urlMappingRepository;

    @Autowired
    private CacheManager cacheManager;

    @Autowired
    private HttpServletRequest request;


    public String shortenURL(String originalURL) {
        // Check if URL already exists in the database
        List<URLMapping> existingMapping = urlMappingRepository.findByOriginalURL(originalURL);
        if (CollectionUtils.isNotEmpty(existingMapping)) {
            return request.getRequestURL().toString() + "/" + existingMapping.get(0).getShortURL();
        }

        // Generate short URL (You can use any algorithm here)
        String shortURL = generateShortURL(originalURL);

        // Save mapping to the database
        URLMapping newMapping = new URLMapping();
        newMapping.setOriginalURL(originalURL);
        newMapping.setShortURL(shortURL);
        urlMappingRepository.save(newMapping);

        return request.getRequestURL().toString() + "/" + shortURL;
    }

    public String getOriginalURL(String shortURL) {
        // Check cache first
        Cache cache = cacheManager.getCache("shortURLCache");
        if (cache != null) {
            Cache.ValueWrapper wrapper = cache.get(shortURL);
            if (wrapper != null) {
                return (String) wrapper.get();
            }
        }

        List<URLMapping> mapping = urlMappingRepository.findByShortURL(shortURL);
        if (CollectionUtils.isNotEmpty(mapping)) {
            cache.put(shortURL, mapping.get(0).getOriginalURL());
            return mapping.get(0).getOriginalURL();
        }

        return null;
    }


    private String generateShortURL(String originalURL) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(originalURL.getBytes());
            // Encode the hash to Base64 to get a shorter representation
            String encodedHash = Base64.getUrlEncoder().encodeToString(hash);
            // Take the first 8 characters of the encoded hash as the short URL
            return encodedHash.substring(0, 8);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}

