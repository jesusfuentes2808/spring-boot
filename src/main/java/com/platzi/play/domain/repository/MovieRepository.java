package com.platzi.play.domain.repository;

import com.platzi.play.domain.dto.MovieDTO;
import com.platzi.play.domain.dto.UpdateMovieDTO;

import java.util.List;


public interface MovieRepository {
    List<MovieDTO> getAll();
    MovieDTO getById(long id);
    MovieDTO save(MovieDTO movieDTO);
    MovieDTO update(long id, UpdateMovieDTO updateMovieDTO);
    String delete(long id);
}
