package com.bookly.mapper;

import com.bookly.dto.UsuarioGetResponse;
import com.bookly.dto.UsuarioPostRequestBody;
import com.bookly.dto.UsuarioPutRequestBody;
import com.bookly.model.Usuario;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {
    Usuario toUsuario(UsuarioPostRequestBody usuarioPostRequestBody);

    Usuario toUsuario(UsuarioPutRequestBody usuarioPutRequestBody);

    UsuarioGetResponse toResponse(Usuario usuario);
    List<UsuarioGetResponse> toResponseList(List<Usuario> usuarios);
}
