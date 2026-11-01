package com.gens.usuario.business;

import com.gens.usuario.business.converter.UsuarioConverter;
import com.gens.usuario.business.dto.UsuarioDTO;
import com.gens.usuario.infrastructure.entity.UsuarioEntity;
import com.gens.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;

    public UsuarioDTO salvarUsuario(UsuarioDTO usuarioDTO){
        UsuarioEntity usuarioEntity = usuarioConverter.paraUsuario(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuarioEntity));
    }
}
