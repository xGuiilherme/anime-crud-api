package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.exception.BadRequestException;
import academy.devdojo.mapper.AnimeMapper;
import academy.devdojo.repository.AnimeRepository;
import academy.devdojo.requests.AnimePostRequestBody;
import academy.devdojo.requests.AnimePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnimeService {

    private final AnimeRepository animeRepository;

    //Paginaçã: Retorna os valores passados dentro de uma pagina. Flexibilidade para quem esta consumindo a API.
    public Page<Anime> listAll(Pageable pageable) {
        return animeRepository.findAll(pageable);
    }

    public List<Anime> findByName(String name) {
        return animeRepository.findByName(name);
    }

    public Anime findByIdOrdThrowBadRequestException(long id) {
        return animeRepository.findById(id)
                .orElseThrow(() -> new BadRequestException("Anime not Found"));
    }

    @Transactional //Spring nao vai comitar essa transacao enquanto o metodo nao for finalizado.
    public Anime save(AnimePostRequestBody animePostRequestBody) {
        return animeRepository.save(AnimeMapper.INSTANCE.toAnime(animePostRequestBody));
    }

    //Ao remover um anime vai verificar se existe e remove,caso ID nao existe vai lançar Exception BAD_REQUEST
    public void delete(long id) {
        animeRepository.delete(findByIdOrdThrowBadRequestException(id));
    }

    public void replace(AnimePutRequestBody animePutRequestBody) {
        Anime savedAnime = findByIdOrdThrowBadRequestException(animePutRequestBody.getId());
        Anime anime = AnimeMapper.INSTANCE.toAnime(animePutRequestBody);
        anime.setId(savedAnime.getId());
        animeRepository.save(anime);
    }
}