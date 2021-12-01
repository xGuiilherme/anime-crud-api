package academy.devdojo.clientRequest;

import academy.devdojo.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@Log4j2
public class SpringClient {

    public static void main(String[] args) {

        String url;

        ResponseEntity<Anime> entity = new RestTemplate().getForEntity(url = "http://localhost:8080/animes/", Anime.class);
        log.info(entity);

//        Anime object = new RestTemplate().getForObject(url = "http://localhost:8080/animes/2", Anime.class);
//        log.info(object);
    }
}
