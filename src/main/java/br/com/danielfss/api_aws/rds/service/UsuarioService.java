package br.com.danielfss.api_aws.rds.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.danielfss.api_aws.rds.dto.RequestUsuarioDTO;
import br.com.danielfss.api_aws.rds.entity.UsuarioEntity;
import br.com.danielfss.api_aws.rds.repository.UsuarioRepository;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;

    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    public void save(RequestUsuarioDTO request) {
        UsuarioEntity usuario = new UsuarioEntity();
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuarioRepository.save(usuario);
    }

    public UsuarioEntity findById(Long id) {
        return usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
    }

    public List<UsuarioEntity> findAll() {
        return usuarioRepository.findAll();
    }

    public Page<UsuarioEntity> findAllPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return usuarioRepository.findAll(pageable);
    }

    public void update(Long id, RequestUsuarioDTO request) {
        UsuarioEntity usuario = usuarioRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Usuário não encontrado"));
        usuario.setNome(request.getNome());
        usuario.setEmail(request.getEmail());
        usuarioRepository.save(usuario);
    }

    public void delete(Long id) {
        if (!usuarioRepository.existsById(id)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }
        usuarioRepository.deleteById(id);
    }

}
