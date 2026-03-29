package com.platzi.play.domain.dto;

import com.platzi.play.domain.Genre;

import java.time.LocalDate;

public record MovieDTO(
        Long id,
        String title,
        Integer duration,
        //String genre,
        Genre genre,
        LocalDate releaseDate,
        Double rating
) {
}
