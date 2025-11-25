package com.bookly.controller;

import com.bookly.dto.CategoriaGetResponse;
import com.bookly.service.CategoriaService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/categorias")
@RequiredArgsConstructor
public class CategoriaController {

    private final CategoriaService  categoriaService;

    @GetMapping
    public ResponseEntity<Page<CategoriaGetResponse>> listar(@Parameter(hidden = true) Pageable pageable){
        Page<CategoriaGetResponse> categorias = categoriaService.buscarTodasCategorias(pageable);
        return ResponseEntity.ok(categorias);
    }
}
