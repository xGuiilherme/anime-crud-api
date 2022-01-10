package academy.devdojo.config;

import academy.devdojo.service.DevDojoUserDetailsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
@Log4j2
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * Alguns Filtros do Spring:
     * BasicAuthenticationFilter: Vai verificar se tem uma autenticação do tipo Basic-64.
     * UsernamePasswordAuthenticationFilter: Vai verificar se na requisição se tem um usuario/senha.
     * FilterSecurityInterceptor: Faz uma validação e vai checar se você está autorizado.
     * Authentication -> Authorization.
     */

    private final DevDojoUserDetailsService devDojoUserDetailsService;

    // Todas as requisição que fizer(das url que está no controller) vão precisar passar por uma autenticação básica.
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        // withHttpOnly:As aplicações de frontend(JS,React,Anguler) não vão conseguir pegar o valor desse Cookie, quando
        // eles fazem uma requisição Post eles precisam passar esse Cookie tambem quando o csrf está habilitado.
        http.csrf().disable()
//                csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()).and()
                .authorizeRequests()
                .antMatchers("/animes/admin/**").hasRole("ADMIN")
                .antMatchers("/animes/**").hasRole("USER")
                .anyRequest()
                .authenticated()
                .and()
                .formLogin()
                .and()
                .httpBasic();
    }

    // Criptografa a senha: vai codifica a palavra 'test'
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        log.info("Password encoder {}", passwordEncoder.encode("academy"));

        // Cria um Usuario e Password em memória e vai ficar ativo durante o ciclo de vida da aplicação até reiniciar
        // Quando reiniciado vai criar novamente com a mesma senha e mesmo usuario.
        auth.inMemoryAuthentication()
                .withUser("guilherme")
                .password(passwordEncoder.encode("academy"))
                .roles("USER", "ADMIN")
                .and()
                .withUser("devcred")
                .password(passwordEncoder.encode("academy"))
                .roles("USER");

        // userDetailsService é um padrao que esta por toda a parte de segurança do Spring e atraves do polimorfismo
        // o Spring vai saber chamar o findByUserName.
        auth.userDetailsService(devDojoUserDetailsService)
                .passwordEncoder(passwordEncoder);
    }
}
