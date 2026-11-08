package com.gens.usuario.business;

import com.gens.usuario.business.converter.UsuarioConverter;
import com.gens.usuario.business.dto.EnderecoDTO;
import com.gens.usuario.business.dto.TelefoneDTO;
import com.gens.usuario.business.dto.UsuarioDTO;
import com.gens.usuario.infrastructure.entity.EnderecoEntity;
import com.gens.usuario.infrastructure.entity.TelefoneEntity;
import com.gens.usuario.infrastructure.entity.UsuarioEntity;
import com.gens.usuario.infrastructure.excpetion.ConflitException;
import com.gens.usuario.infrastructure.excpetion.ResourceNotFoundException;
import com.gens.usuario.infrastructure.repository.EnderecoRepository;
import com.gens.usuario.infrastructure.repository.TelefoneRepository;
import com.gens.usuario.infrastructure.repository.UsuarioRepository;
import com.gens.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final  EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;

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

    public UsuarioDTO buscarUsuarioPorEmail(String email) {

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email nao encontrado " + email)));

    }

    public void deletarUsuarioPorEmail(String email) {
        usuarioRepository.deleteByEmail(email);
    }

    public UsuarioDTO atualizarDadosUsuario(String token, UsuarioDTO usuarioDTO) {
        String email = jwtUtil.extrairEmailDoToken(token.substring(7));

        usuarioDTO.setSenha(usuarioDTO.getSenha() != null ? passwordEncoder.encode(usuarioDTO.getSenha()) : null);

        UsuarioEntity usuarioEntity = usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email não encontado"));

        UsuarioEntity usuario = usuarioConverter.updateUsuario(usuarioDTO, usuarioEntity);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuarioEntity));
    }

    public EnderecoDTO atualizaEndereco(Long idEndereco, EnderecoDTO enderecoDTO) {

        EnderecoEntity enderecoEnt = enderecoRepository.findById(idEndereco).orElseThrow(
                () -> new ResourceNotFoundException("Id não Encontrado" + idEndereco));

        EnderecoEntity enderecoEntity = usuarioConverter.updateEndereco(enderecoDTO, enderecoEnt);

        return usuarioConverter.paraEnderecosDTO(enderecoRepository.save(enderecoEntity));
    }

    public TelefoneDTO atualizaTelefone(Long idTelefone, TelefoneDTO telefoneDTO) {

        TelefoneEntity telefoneEnt = telefoneRepository.findById(idTelefone).orElseThrow(
                () -> new ResourceNotFoundException("Id não encontrado" + idTelefone));

        TelefoneEntity telEntity = usuarioConverter.updateTelefone(telefoneDTO, telefoneEnt);

        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telEntity));

    }
}
