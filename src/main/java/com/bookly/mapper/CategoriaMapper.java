package com.bookly.mapper;

import com.bookly.dto.CategoriaGetResponse;
import com.bookly.model.Categoria;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CategoriaMapper {

    CategoriaGetResponse toResponse(Categoria categoria);
}