package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.requests.AnimePostRequestBody;
import academy.devdojo.requests.AnimePutRequestBody;
import academy.devdojo.service.AnimeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "animes")
@Log4j2
@RequiredArgsConstructor
public class AnimeController {

    private final AnimeService animeService;

    @GetMapping
    public ResponseEntity<Page<Anime>> list(Pageable pageable) {
        return ResponseEntity.ok(animeService.listAll(pageable));
    }

    // Novo end-point onde não é paginavel
    @GetMapping(path = "/all")
    public ResponseEntity<List<Anime>> listAll() {
        return ResponseEntity.ok(animeService.listAllNoPageable());
    }

    // Os dados são enviados como String anexada a URL 'são visíveis ao usuário'
    @GetMapping(path = "/{id}")
    public ResponseEntity<Anime> findById(@PathVariable long id) {
        return ResponseEntity.ok(animeService.findByIdOrdThrowBadRequestException(id));
    }

    @GetMapping(path = "by-id/{id}")
    public ResponseEntity<Anime> findByIdAutenticationPrincipal(@PathVariable long id,
                                                                @AuthenticationPrincipal UserDetails userDetails) {
        log.info(userDetails);
        return ResponseEntity.ok(animeService.findByIdOrdThrowBadRequestException(id));
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(animeService.findByName(name));
    }

    // Os dados são enviados dentro do bady da requisição 'encapsulada junto ao corpo da requisição HTTP'
    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody) {
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/admin/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Quando executado ele está dando um 'replace' substituindo o estado inteiro do objeto.
    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody) {
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
