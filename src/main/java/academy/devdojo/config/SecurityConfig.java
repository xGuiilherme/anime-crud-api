package academy.devdojo.config;

import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Log4j2
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    // Todas as requisição que fizer(das url que está no controller) vão precisar passar por uma autenticação básica.
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // withHttpOnly:As aplicações de frontend(JS,React,Anguler) não vão conseguir pegar o valor desse Cookie, quando
        // eles fazem uma requisição Post eles precisam passar esse Cookie tambem quando o csrf está habilitado.
        http.csrf().disable()
//                csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .httpBasic();
    }

    // Criptografa a senha: vai codifica a palavra 'test'
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password encoder {}", passwordEncoder.encode("test"));

        // Cria um Usuario e Password em memória e vai ficar ativo durante o ciclo de vida da aplicação até reiniciar
        // Quando reiniciado vai criar novamente com a mesma senha e mesmo usuario.
        auth.inMemoryAuthentication()
                .withUser("Guilherme2")
                .password(passwordEncoder.encode("academy"))
                .roles("USER", "ADMIN")
                .and()
                .withUser("DevCredenc")
                .password(passwordEncoder.encode("12345678"))
                .roles("USER");
    }
}
