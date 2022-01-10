package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.requests.AnimePostRequestBody;
import academy.devdojo.requests.AnimePutRequestBody;
import academy.devdojo.service.AnimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    @Operation(summary = "List all animes paginated", description = "The default size is 20, use the parameter size to change the default value",
    tags = {"anime"})
    public ResponseEntity<Page<Anime>> list(@ParameterObject Pageable pageable) {
        return ResponseEntity.ok(animeService.listAll(pageable));
    }

    // Novo end-point onde não é paginavel
    @GetMapping(path = "/all")
    @Operation(summary = "List all animes", description = "Returns a list of all saved anime")
    public ResponseEntity<List<Anime>> listAll() {
        return ResponseEntity.ok(animeService.listAllNoPageable());
    }

    // Os dados são enviados como String anexada a URL 'são visíveis ao usuário'
    @GetMapping(path = "/{id}")
    @Operation(summary = "Search by id", description = "Returns by id of anime")
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
    @Operation(summary = "Search by name", description = "Returns by name of anime")
    public ResponseEntity<List<Anime>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(animeService.findByName(name));
    }

    // Os dados são enviados dentro do bady da requisição 'encapsulada junto ao corpo da requisição HTTP'
    @PostMapping
    public ResponseEntity<Anime> save(@RequestBody @Valid AnimePostRequestBody animePostRequestBody) {
        return new ResponseEntity<>(animeService.save(animePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/admin/{id}")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Successful Operation"),
            @ApiResponse(responseCode = "400", description = "When Anime Does Not Exist in The Database"),
    })
    public ResponseEntity<Void> delete(@PathVariable long id) {
        animeService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Quando executado ele está dando um 'replace' substituindo o estado inteiro do objeto.
    @PutMapping
    @Operation(summary = "Replace an resource", description = "Create a new resource or replaces already existing resource")
    public ResponseEntity<Void> replace(@RequestBody AnimePutRequestBody animePutRequestBody) {
        animeService.replace(animePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
