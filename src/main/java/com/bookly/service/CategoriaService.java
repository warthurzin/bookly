package com.bookly.service;

import com.bookly.dto.CategoriaGetResponse;
import com.bookly.exception.BadRequestException;
import com.bookly.mapper.CategoriaMapper;
import com.bookly.model.Categoria;
import com.bookly.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CategoriaService {

    private final CategoriaRepository categoriaRepository;
    private final CategoriaMapper categoriaMapper;

    public Page<CategoriaGetResponse> buscarTodasCategorias(Pageable pageable) {
        return categoriaRepository.findAll(pageable)
                .map(categoriaMapper::toResponse);
    }

    public Set<Categoria> buscarCategoriasPorIds(Set<Long> categoriasIds) {
        if (categoriasIds == null || categoriasIds.isEmpty()) {
            return Set.of();
        }

        List<Categoria> categorias = categoriaRepository.findAllById(categoriasIds);

        if (categorias.size() != categoriasIds.size()) {

            Set<Long> foundsIds = categorias.stream().map(Categoria::getId).collect(Collectors.toSet());

            Set<Long> missingIds = categoriasIds.stream()
                    .filter(id -> !foundsIds.contains(id))
                    .collect(Collectors.toSet());

            throw new BadRequestException("Categorias com IDs: " + missingIds + " não encontradas. Verifique se os IDs são válidos.");
        }
        return Set.copyOf(categorias);
    }
}