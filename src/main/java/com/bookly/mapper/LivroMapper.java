package com.bookly.mapper;

import com.bookly.dto.LivroGetResponse;
import com.bookly.dto.LivroPostRequestBody;
import com.bookly.dto.LivroPutRequestBody;
import com.bookly.model.Livro;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {CategoriaMapper.class})
public interface LivroMapper {

    @Mapping(source = "usuario.id", target = "usuarioId")
    @Mapping(source = "categorias", target = "categorias")
    LivroGetResponse toResponse(Livro livro);

    Livro toLivro(LivroPostRequestBody livroPostRequestBody);

    Livro toLivro(LivroPutRequestBody livroPutRequestBody);

}
