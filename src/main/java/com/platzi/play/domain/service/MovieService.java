package com.platzi.play.domain.service;

import com.platzi.play.domain.dto.MovieDTO;
import com.platzi.play.domain.dto.UpdateMovieDTO;
import com.platzi.play.domain.repository.MovieRepository;
import dev.langchain4j.agent.tool.Tool;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MovieService {
    private final MovieRepository movieRepository;

    public MovieService(MovieRepository movieRepository){
        this.movieRepository = movieRepository;
    }

    @Tool("Busca todas las peliculas que existan dentro de la plataforma")
    public List<MovieDTO> getAll(){
        return this.movieRepository.getAll();
    }

    public MovieDTO getById(long id){
        return this.movieRepository.getById(id);
    }

    public MovieDTO add(MovieDTO movieDTO){
        return this.movieRepository.save(movieDTO);
    }

    public MovieDTO update(long id, UpdateMovieDTO updateMovieDTO){
        return this.movieRepository.update(id, updateMovieDTO);
    }

    public String delete(long id){
        return this.movieRepository.delete(id);
    }
}
