package org.coding.urlShortner.DTOs;

import lombok.Data;

@Data
public class URLMappingDTO {
    private Long id;
    private String originalURL;
    private String shortURL;

    // Constructors, getters, setters
}

