package academy.devdojo.clientRequest;

import academy.devdojo.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

@Log4j2
public class SpringClient {

    public static void main(String[] args) {

        // O getForEntity vai retornar o object dentro de um Wrapper que é o ResponseEntity.
        ResponseEntity<Anime> entity = new RestTemplate().getForEntity("http://localhost:8080/animes/{id}", Anime.class, 2);
        log.info(entity);

        // O getForObject vai retornar o object diretamente
        Anime object = new RestTemplate().getForObject("http://localhost:8080/animes/{id}", Anime.class, 2);
        log.info(object);

        // Ultilizando o getForObject você teria que usar Arrays, aqui foi passado um Array de Animes.
        Anime[] animes = new RestTemplate().getForObject("http://localhost:8080/animes/all", Anime[].class);
        log.info(Arrays.toString(animes));

        // Passando o Exchange para pegar o tipo ParameterizedTypeReference e converter automaticamente para uma lista.
        // Ultilizando o exchange tem mais flexibilidade doque apenas o getForObject ou getForEntity.
        // Se não tiver nada muito fancy Ex: Passando Autenticação ou um Array, 'nem sempre precisa usar o exchange'.
        ResponseEntity<List<Anime>> exchange = new RestTemplate().exchange("http://localhost:8080/animes/all",
                HttpMethod.GET, null, new ParameterizedTypeReference<>() {});
        log.info(exchange.getBody());

        Anime bleach = Anime.builder().name("bleach").build();
        Anime bleachSaved = new RestTemplate().postForObject("http://localhost:8080/animes/", bleach, Anime.class);
        log.info("saved anime {}", bleachSaved);

        // Exchange: Primeiro passa a URL, Metodo:POST Envia um OBJ,
        Anime kindom = Anime.builder().name("kindom").build();
        ResponseEntity<Anime> kindomSaved = new RestTemplate().exchange("http://localhost:8080/animes/",
                HttpMethod.POST,
                new HttpEntity<>(kindom, createJsonHeader()),
                Anime.class);
        log.info("saved anime {}", kindomSaved);

//        Anime animeToBeUpdated = kindomSaved.getBody();
//        animeToBeUpdated.setName("kindom 2");
//
//        ResponseEntity<Void> kindomUpdated = new RestTemplate().exchange("http://localhost:8080/animes/",
//                    HttpMethod.PUT,
//                new HttpEntity<>(animeToBeUpdated, createJsonHeader()),
//                Void.class);
//
//        log.info("kindomUpdated");

//        ResponseEntity<Void> kindomDelete = new RestTemplate().exchange("http://localhost:8080/animes/{id}",
//                HttpMethod.DELETE,
//                null,
//                Void.class,
//                animeToBeUpdated.getId());
//
//        log.info("kindomDelete");
    }

    private static HttpHeaders createJsonHeader() {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);
        return httpHeaders;
    }
}
