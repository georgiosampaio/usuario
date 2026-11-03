package com.gens.usuario.business;

import com.gens.usuario.business.converter.UsuarioConverter;
import com.gens.usuario.business.dto.UsuarioDTO;
import com.gens.usuario.infrastructure.entity.UsuarioEntity;
import com.gens.usuario.infrastructure.excpetion.ConflitException;
import com.gens.usuario.infrastructure.excpetion.ResourceNotFoundException;
import com.gens.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO) {
        emailExiste(usuarioDTO.getEmail());
        usuarioDTO.setSenha(passwordEncoder.encode(usuarioDTO.getSenha()));
        UsuarioEntity usuarioEntity = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuarioEntity));
    }

    public void emailExiste(String email) {
        if (verificaEmailExistente(email)) {
            throw new ConflitException("Email já Cadastrado");
        }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }

    public UsuarioEntity buscarUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("email nao encontrado " + email));

    }

    public void deletarUsuarioPorEmail(String email){
        usuarioRepository.deleteByEmail(email);
    }
}
