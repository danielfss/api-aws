package br.com.danielfss.api_aws.rds.controller;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.com.danielfss.api_aws.rds.dto.RequestUsuarioDTO;
import br.com.danielfss.api_aws.rds.entity.UsuarioEntity;
import br.com.danielfss.api_aws.rds.service.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;

    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @GetMapping("/listar-todos")
    public ResponseEntity<List<UsuarioEntity>> findAll() {
        return ResponseEntity.ok(usuarioService.findAll());
    }

    @GetMapping("/listar-paginado")
    public ResponseEntity<Page<UsuarioEntity>> findAllPaginated(@RequestParam int page, @RequestParam int size) {
        return ResponseEntity.ok(usuarioService.findAllPaginated(page, size));
    }

    @GetMapping("/buscar/{id}")
    public ResponseEntity<UsuarioEntity> findById(@PathVariable Long id) {
        return ResponseEntity.ok(usuarioService.findById(id));
    }

    @PostMapping("/salvar")
    public ResponseEntity<Void> save(@RequestBody RequestUsuarioDTO requestUsuarioDTO) {
        usuarioService.save(requestUsuarioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping("/atualizar/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody RequestUsuarioDTO requestUsuarioDTO) {
        usuarioService.update(id, requestUsuarioDTO);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deletar/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
