///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
package com.egg.noticia;

import com.egg.noticia.servicios.UsuarioServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author edgar
 */
//INDICO LA SEGURIDAD EN LA CLASE
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SeguridadWeb extends WebSecurityConfigurerAdapter { //INDICO QUE HERADA DE LA CLASE PADRE

    @Autowired
    public UsuarioServicio usuarioservicio;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception { //AuthenticationManagerBuilder tiene el método userDetailsService, además del encriptador de contraseñas

        auth.userDetailsService(usuarioservicio)
                .passwordEncoder(new BCryptPasswordEncoder()); //PASSWORD ENCONDER ENCRYPTA CONTRASEÑAS
    }

    //TRABAJO SOBRE EL METODO configure DE LA CLASE PADRE Y MEDIANTE EL OVERRIDE INDICO QUE VOY A SOBRESCRIBIRLO A MI GUSTO
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        //INDICO QUE PERMITO EL ACCESO A CUALQUIER ARCHIVO CSS, JS, IMG Y CUALQUIER TIPO A CUALQUIER USUARIO.
        http.csrf().disable()
                .authorizeRequests()
                .antMatchers("/administrador/*").hasAnyRole("ADMIN","PERIODISTA")//Securizo desde aca para toda las clase portal administrador
                .antMatchers("/css/*", "/js/*", "/img/*", "/**")
                .permitAll()
                .and().formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/logincheck")
                .usernameParameter("nombre")
                .passwordParameter("password")
                .defaultSuccessUrl("/inicio")
                .permitAll()
                .and().logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/")
                .permitAll();

    }
    //AGREGAR ESTO PARA TRABAJAR CON LA BASE DE DATOS SIN PROBLEMAS csrf().disable()

}
