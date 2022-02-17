package academy.devdojo.configurerWeb;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/* Aqui coloca uma configuração global p/ sobrescrever oque o proprio spring tem em relação a paginaçao.
   Ex: Tem 3 pagina,Quando colocamos '1' e quando alguem executar a requisição a Segunda pagina vai ser exibida.
   e o tamanho do Lista/Payload que estamos colocando p/ retornando é '5'.
*/

@Configuration
public class WebMvcConfigurerDev implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver pageHandler = new PageableHandlerMethodArgumentResolver();
        pageHandler.setFallbackPageable(PageRequest.of(0, 5));
        resolvers.add(pageHandler);
    }
}