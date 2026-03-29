package com.platzi.play.persistence.mapper;

import com.platzi.play.domain.dto.MovieDTO;
import com.platzi.play.domain.dto.UpdateMovieDTO;
import com.platzi.play.persistence.MovieEntityRepository;
import com.platzi.play.persistence.entity.MovieEntity;
import org.hibernate.type.ListType;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", uses = {GenreMapper.class})
public interface MovieMapper {

    @Mapping(source = "titulo", target = "title")
    @Mapping(source = "duracion", target = "duration")
    @Mapping(source = "genero", target = "genre", qualifiedByName = "stringToGenre")
    @Mapping(source = "fechaEstreno", target = "releaseDate")
    @Mapping(source = "clasificacion", target = "rating")
    MovieDTO toDto(MovieEntity entity);
    List<MovieDTO> toDto(Iterable<MovieEntity> entities);

    @InheritInverseConfiguration
    @Mapping(source = "genre", target = "genero", qualifiedByName = "genreToString")
    MovieEntity toEntity(MovieDTO movieDTO);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    @Mapping(target = "titulo", source="title")
    @Mapping(target = "fechaEstreno", source="releaseDate")
    @Mapping(target = "clasificacion", source="rating")
    void updateEntityFromDto(UpdateMovieDTO updateMovieDTO, @MappingTarget() MovieEntity movieEntity);
}
