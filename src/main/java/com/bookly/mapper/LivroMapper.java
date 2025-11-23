package com.bookly.mapper;

import com.bookly.dto.LivroGetResponse;
import com.bookly.dto.LivroPostRequestBody;
import com.bookly.dto.LivroPutRequestBody;
import com.bookly.model.Livro;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LivroMapper {
    LivroGetResponse toResponse(Livro livro);

    Livro toLivro(LivroPostRequestBody livroPostRequestBody);

    Livro toLivro(LivroPutRequestBody livroPutRequestBody);

}
