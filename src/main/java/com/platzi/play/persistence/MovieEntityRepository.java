package com.platzi.play.persistence;

import com.platzi.play.domain.dto.MovieDTO;
import com.platzi.play.domain.dto.UpdateMovieDTO;
import com.platzi.play.domain.exception.MovieAlreadyExistsException;
import com.platzi.play.domain.repository.MovieRepository;
import com.platzi.play.persistence.crud.CrudMovieEntity;
import com.platzi.play.persistence.entity.MovieEntity;
import com.platzi.play.persistence.mapper.MovieMapper;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

@Repository
public class MovieEntityRepository implements MovieRepository {

    private final CrudMovieEntity crudMovieEntity;
    private final MovieMapper movieMapper;

    public MovieEntityRepository(CrudMovieEntity crudMovieEntity, MovieMapper movieMapper){
        this.crudMovieEntity = crudMovieEntity;
        this.movieMapper = movieMapper;
    }

    @Override
    public List<MovieDTO> getAll() {
        return this.movieMapper.toDto(this.crudMovieEntity.findAll());
    }

    @Override
    public MovieDTO getById(long id) {
        MovieEntity movieEntity = this.crudMovieEntity.findById(id).orElse(null);
        return this.movieMapper.toDto(movieEntity);
    }

    @Override
    public MovieDTO save(MovieDTO movieDTO) {
        MovieEntity movieEntityByTitulo = this.crudMovieEntity.findByTitulo(movieDTO.title());
        if(movieEntityByTitulo != null){
            throw new MovieAlreadyExistsException(movieDTO.title());
        }

        MovieEntity movieEntity = this.movieMapper.toEntity(movieDTO);
        movieEntity.setEstado("D");

        return this.movieMapper.toDto(this.crudMovieEntity.save(movieEntity));
    }

    @Override
    public MovieDTO update(long id, UpdateMovieDTO updateMovieDTO) {
        MovieEntity movieEntity = this.crudMovieEntity.findById(id).orElse(null);
        if(movieEntity == null) return null;

        movieEntity.setTitulo(updateMovieDTO.title());
        movieEntity.setFechaEstreno(updateMovieDTO.releaseDate());
        movieEntity.setClasificacion(BigDecimal.valueOf(updateMovieDTO.rating()));
        //System.out.println(updateMovieDTO);
        //this.movieMapper.updateEntityFromDto(updateMovieDTO, movieEntity);
        //System.out.println(updateMovieDTO);

        return this.movieMapper.toDto(this.crudMovieEntity.save(movieEntity));
    }

    @Override
    public String delete(long id) {
        MovieEntity movieEntity = this.crudMovieEntity.findById(id).orElse(null);
        if(movieEntity == null) return "No existe";

        this.crudMovieEntity.delete(movieEntity);

        return "Movie eliminada " + movieEntity.getId();
        //return this.movieMapper.toDto(this.crudMovieEntity.delete(movieEntity));;
    }


}
