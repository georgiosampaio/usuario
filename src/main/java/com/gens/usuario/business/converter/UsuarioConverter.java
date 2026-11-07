package com.gens.usuario.business.converter;

import com.gens.usuario.business.dto.EnderecoDTO;
import com.gens.usuario.business.dto.TelefoneDTO;
import com.gens.usuario.business.dto.UsuarioDTO;
import com.gens.usuario.infrastructure.entity.EnderecoEntity;
import com.gens.usuario.infrastructure.entity.TelefoneEntity;
import com.gens.usuario.infrastructure.entity.UsuarioEntity;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class UsuarioConverter {

    public UsuarioEntity paraUsuario(UsuarioDTO usuarioDTO) {
        return UsuarioEntity.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEnderecos(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefones(usuarioDTO.getTelefones()))
                .build();
    }

    public List<EnderecoEntity> paraListaEnderecos(List<EnderecoDTO> enderecoDTOS) {
        return enderecoDTOS.stream().map(this::paraEndereco).toList();
    }

    public EnderecoEntity paraEndereco(com.gens.usuario.business.dto.EnderecoDTO enderecoDTO) {
        return EnderecoEntity.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .complemento(enderecoDTO.getComplemento())
                .cidade(enderecoDTO.getCidade())
                .estado(enderecoDTO.getEstado())
                .cep(enderecoDTO.getCep())
                .build();
    }

    public List<TelefoneEntity> paraListaTelefones(List<TelefoneDTO> telefoneDTO) {
        return telefoneDTO.stream().map(this::telefoneConverter).toList();
    }

    public TelefoneEntity telefoneConverter(TelefoneDTO telefoneDTO) {
        return TelefoneEntity.builder()
                .ddd(telefoneDTO.getDdd())
                .numero(telefoneDTO.getNumero())
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(UsuarioEntity usuarioEntity) {
        return UsuarioDTO.builder()
                .nome(usuarioEntity.getNome())
                .email(usuarioEntity.getEmail())
                .senha(usuarioEntity.getSenha())
                .enderecos(paraListaEnderecosDTO(usuarioEntity.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuarioEntity.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecosDTO(List<EnderecoEntity> enderecoEntity) {
        return enderecoEntity.stream().map(this::paraEnderecosDTO).toList();
    }

    public EnderecoDTO paraEnderecosDTO(EnderecoEntity enderecoEntity) {
        return EnderecoDTO.builder()
                .rua(enderecoEntity.getRua())
                .numero(enderecoEntity.getNumero())
                .complemento(enderecoEntity.getComplemento())
                .cidade(enderecoEntity.getCidade())
                .estado(enderecoEntity.getEstado())
                .cep(enderecoEntity.getCep())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefonesDTO(List<TelefoneEntity> telefoneEntity) {
        return telefoneEntity.stream().map(this::paraTelefoneDTO).toList();
    }

    public TelefoneDTO paraTelefoneDTO(TelefoneEntity telefoneEntity) {
        return TelefoneDTO.builder()
                .ddd(telefoneEntity.getDdd())
                .numero(telefoneEntity.getNumero())
                .build();
    }

    public UsuarioEntity updateUsuario(UsuarioDTO usuarioDTO, UsuarioEntity usuarioEntity) {
        return UsuarioEntity.builder()
                .nome(usuarioDTO.getNome() != null ? usuarioDTO.getNome() : usuarioEntity.getNome())
                .id(usuarioEntity.getId())
                .email(usuarioDTO.getEmail() != null ? usuarioDTO.getEmail() : usuarioEntity.getEmail())
                .senha(usuarioDTO.getSenha() != null ? usuarioDTO.getSenha() : usuarioEntity.getSenha())
                .enderecos(usuarioEntity.getEnderecos())
                .telefones(usuarioEntity.getTelefones())
                .build();
    }
}
