package com.interaction.interactionsystemwoman.controller;

import com.interaction.interactionsystemwoman.dto.UsuarioDTO;
import com.interaction.interactionsystemwoman.exceptions.GeneralException;
import com.interaction.interactionsystemwoman.responses.GeneralResponse;
import com.interaction.interactionsystemwoman.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path="/interaction/usuario")
@CrossOrigin
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @ResponseStatus(HttpStatus.OK)
    @PostMapping()
    public GeneralResponse<UsuarioDTO> createUsuario (@RequestBody UsuarioDTO usuarioDTO) throws GeneralException {
        return new GeneralResponse<>("Success",String.valueOf(HttpStatus.OK), "OK",
                usuarioService.createUsuario(usuarioDTO));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    public GeneralResponse<UsuarioDTO> getUsuarioById(@PathVariable Integer id) throws GeneralException{
        return new GeneralResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                usuarioService.getUsuarioById(id));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("login/{correo}/{contrasena}")
    public GeneralResponse<UsuarioDTO> login(@PathVariable String correo, @PathVariable String contrasena) throws GeneralException{
        return new GeneralResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                usuarioService.getUsuarioByCorreoandContrasena(correo, contrasena));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping()
    public GeneralResponse<List<UsuarioDTO>> getUsuarios() throws GeneralException {
        return new GeneralResponse<>("Success",String.valueOf(HttpStatus.OK),"OKAY",
                usuarioService.getUsuarios());
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping()
    public GeneralResponse<UsuarioDTO> updateUsuario(@RequestBody UsuarioDTO usuarioDTO) throws GeneralException{
        return new GeneralResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                usuarioService.updateUsuario(usuarioDTO));
    }

    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping("/{id}")
    public GeneralResponse<String> deleteUsuario(@PathVariable Integer id) throws GeneralException{
        usuarioService.deleteUsuario(id);
        return new GeneralResponse<>("Success", String.valueOf(HttpStatus.OK), "OK",
                "Delete OK");
    }
}
