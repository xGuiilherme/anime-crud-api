package academy.devdojo.clientRequest;

import academy.devdojo.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {

    public static void main(String[] args) {

        String url;

        ResponseEntity<Anime> entity = new RestTemplate().getForEntity(url = "http://localhost:8080/animes/{id}", Anime.class, 2);
        log.info(entity);

        Anime object = new RestTemplate().getForObject(url = "http://localhost:8080/animes/{id}", Anime.class, 2);
        log.info(object);

        // Ultilizando o getForObject você teria que usar Arrays.
        Anime[] animes = new RestTemplate().getForObject(url = "http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));

        // ParameterizedTypeReference - Pode passar diretamente a Lista que você quer.
        // Ultilizando o exchange tem mais flexibilidade doque apenas o getForObject ou getForEntity.
        // Se não tiver nada muito fancy Ex: Passando Autenticação ou um Array, nem sempre precisa usar o exchange.
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange(url = "http://localhost:8080/animes/all",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        log.info(exchange.getBody());
    }
}
