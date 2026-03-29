package com.platzi.play.web.controller;

import com.platzi.play.domain.dto.MovieDTO;
import com.platzi.play.domain.dto.SuggestRequestDto;
import com.platzi.play.domain.dto.UpdateMovieDTO;
import com.platzi.play.domain.service.MovieService;
import com.platzi.play.domain.service.PlatziPlayAIService;
import com.platzi.play.persistence.crud.CrudMovieEntity;
import com.platzi.play.persistence.entity.MovieEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import okhttp3.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import retrofit2.http.DELETE;
import retrofit2.http.PUT;

import java.util.List;

@RestController
@RequestMapping("/movies")
@Tag(name="Movies Pers.", description = "Descripcion generada y personalizada")
public class MovieController {

    private final MovieService movieService;
    private final PlatziPlayAIService aiService;

    /*private final CrudMovieEntity  crudMovieEntity;

    public MovieController(CrudMovieEntity crudMovieEntity){
        this.crudMovieEntity = crudMovieEntity;
    }*/

    public MovieController(MovieService movieService, PlatziPlayAIService aiService){
        this.movieService = movieService;
        this.aiService = aiService;
    }

    /*@Autowired
    private CrudMovieEntity  crudMovieEntity;*/

    //@GetMapping("")
    //@GetMapping("/movies");
    //public List<MovieEntity> getAll(){
        //return (List<MovieEntity>) this.crudMovieEntity.findAll();

    /*public List<MovieDTO> getAll(){
        return this.movieService.getAll();
    }*/

    @GetMapping("")
    public ResponseEntity<List<MovieDTO>> geAll(){
        return ResponseEntity.ok(this.movieService.getAll());
    }

    /*@GetMapping("/{id}")
    public MovieDTO getById(@PathVariable long id){
        return this.movieService.getById(id);
    }*/


    @GetMapping("/{id}")
    @Operation(
            summary = "Get a movie by id (Obtener una peli por id)",
            description =  "Obtiene la información detallada de una película a partir de su identificador único. " +
                    "Si la película existe, retorna un objeto MovieDTO con sus datos. " +
                    "Si no se encuentra ninguna película con el id proporcionado, devuelve un código 404 (Not Found).",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Película encontrada"),
                    @ApiResponse(responseCode = "404", description = "Película No encontrada", content = @Content)
            }
    )
    public ResponseEntity<MovieDTO> getById(
            @Parameter(description = "Identificador de la pelicula")
            @PathVariable long id)
    {
        MovieDTO movieDTO = this.movieService.getById(id);
        if(movieDTO == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(movieDTO);
    }

    @PostMapping()
    public ResponseEntity<MovieDTO> add(@RequestBody MovieDTO movieDTO){
        MovieDTO dtoResponse = this.movieService.add(movieDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(dtoResponse);
    }

    @PostMapping("suggestion")
    public ResponseEntity<String> generateMoviesSuggestion(@RequestBody SuggestRequestDto suggestRequestDto){
        return ResponseEntity.ok(this.aiService.generateMoviesSuggestion(suggestRequestDto.userPreference()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieDTO> update(
            @PathVariable long id,
            @RequestBody @Valid UpdateMovieDTO updateMovieDTO)
    {
        return ResponseEntity.ok(this.movieService.update(id, updateMovieDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable long id){
        return ResponseEntity.ok(this.movieService.delete(id));
    }
}