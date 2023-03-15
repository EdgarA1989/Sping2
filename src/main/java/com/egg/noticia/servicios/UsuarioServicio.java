/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.egg.noticia.servicios;

import com.egg.noticia.entidades.Imagen;
import com.egg.noticia.entidades.Usuario;
import com.egg.noticia.enumeraciones.Rol;
import com.egg.noticia.excepciones.MiException;
import com.egg.noticia.repositorios.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author edgar
 */
@Service
public class UsuarioServicio implements UserDetailsService{ //Implementamos la clase user sarasa para traerno el metodo para autentificar los Usuarios 

    @Autowired
    private UsuarioRepositorio usuariorepositorio;

    @Autowired
    private ImagenServicio imagenservicio;
    
    @Transactional
    public void crearUsuario(MultipartFile archivo,String nombre, String password, String password1) throws MiException {

        validar(nombre, password, password1);
                
        Usuario usuario = new Usuario();
        Date fechaactual = new Date();

        usuario.setNombre(nombre);
        usuario.setPassword(new BCryptPasswordEncoder().encode(password));
        usuario.setRol(Rol.USER);
        usuario.setFechaalta(fechaactual);
        usuario.setActivo(true);
        
        Imagen imagen = imagenservicio.guardar(archivo);
        
        usuario.setImagen(imagen);
        
        usuariorepositorio.save(usuario);

    }

    public void validar(String nombre, String password, String password1) throws MiException {

        if (nombre.isEmpty() || nombre == null) {
            throw new MiException("El nombre no puede ser nulo");
        }
        if (password.isEmpty() || password == null) {
            throw new MiException("La contraseña no puede ser nula");
        }
        if (password.length() < 8) {      
        throw new MiException("La contraseña debe tener al menos 8 caracteres");
        }
        if (!password.equals(password1)){
            throw new MiException("Las contraseñas ingresadas no coinciden");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String nombre) throws UsernameNotFoundException { //CONVERTIMOS NUESTRO USUARIO A DOMINIO DE SPRING SECURITY
        Usuario usuario = usuariorepositorio.buscarPorNombre(nombre);
    
        if (usuario!=null) {
        
           List<GrantedAuthority> permisos = new ArrayList(); //CREO ARRAYLIST DE PERMISOS
           
           GrantedAuthority p = new SimpleGrantedAuthority("ROLE_" + usuario.getRol().toString()); //Creamos un objeto GrantedAuthority 'p' y concatenamos la palabra ROLE_ + el rol del USUARIO
           
           permisos.add(p); //Ingresamos el objeto p creado al Arraylist de permisos
           
           ServletRequestAttributes attr = (ServletRequestAttributes) RequestContextHolder.currentRequestAttributes(); //RECUOERA LOS ATRIBURTOS DE LA SOLICITUD HTTP
           
           HttpSession session = attr.getRequest().getSession(true); //Almacenamos los datos de sesion en el objeto creado.
           
           session.setAttribute("usuariosession", usuario); //usariosession lo usamos con llave tipo tymeleaft
           
           return new User(usuario.getNombre(), usuario.getPassword(), permisos);
        }else{
            return null;
        }
        
    }
   
    public Usuario traerUno(Integer id){
        
        Optional<Usuario> respuestausuario = usuariorepositorio.findById(id);
        
        if (respuestausuario != null){
            Usuario usuario = respuestausuario.get();
            
            return usuario;
            
        }
        
        return null;
    }
}
