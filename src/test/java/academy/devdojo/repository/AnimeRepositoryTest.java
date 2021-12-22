package academy.devdojo.repository;

import academy.devdojo.domain.Anime;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
@DisplayName("Tests for anime Repository ")
@Log4j2
class AnimeRepositoryTest {

    @Autowired
    AnimeRepository animeRepository;

    @Test
    @DisplayName("Save persists anime when Successful")
    void save_PersistAnime_WhenSuccessful() {

        // Cria o anime.
        Anime animeToBeSaved = createAnime();

        // Cria um metodo e Salva o anime em uma variavel local.
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        // Verifica se o OBJ não é Nullo
        Assertions.assertThat(animeSaved).isNotNull();

        // Verifica se o ID do OBJ não é Nullo
        Assertions.assertThat(animeSaved.getId()).isNotNull();

        // Manda um valor pro BD e verifica se ele foi retornado,se tem um ID,e o valor que foi salvo é igual o que pediu.
        Assertions.assertThat(animeSaved.getName()).isEqualTo(animeToBeSaved.getName());
    }

    @Test
    @DisplayName("Save updates anime when Successful")
    void save_UpdatesAnime_WhenSuccessful() {

        // Cria o anime.
        Anime animeToBeSaved = createAnime();

        // Cria um metodo e Salva o anime em uma variavel local.
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        // Faz Update e Atribui um Nome.
        animeSaved.setName("Overlord");

        // Cria um metodo e Salva o anime em uma variavel local.
        Anime animeUpdated = this.animeRepository.save(animeSaved);

        // Verifica se o OBJ não é Nullo
        Assertions.assertThat(animeUpdated).isNotNull();

        // Verifica se o ID do OBJ não é Nullo
        Assertions.assertThat(animeUpdated.getId()).isNotNull();

        // Manda um valor pro BD e verifica se ele foi retornado,se tem um ID,e o valor que foi salvo é igual o que pediu.
        Assertions.assertThat(animeUpdated.getName()).isEqualTo(animeSaved.getName());
    }

    @Test
    @DisplayName("Delete removes anime when Successful")
    void delete_RemovesAnime_WhenSuccessful() {

        // Cria o anime.
        Anime animeToBeSaved = createAnime();

        // Cria um metodo e Salva o anime em uma variavel local.
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        // Metodo p/ Deleta o anime.
        this.animeRepository.delete(animeSaved);

        // Cria uma variavel local e um metodo p/ pegar o ID do anime.
        Optional<Anime> animeOptional = this.animeRepository.findById(animeSaved.getId());

        // Validação p/ verificar se a variavel está vazia.
        Assertions.assertThat(animeOptional).isEmpty();

    }

    @Test
    @DisplayName("Find By Name returns list of anime when Successful")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {

        // Cria o anime.
        Anime animeToBeSaved = createAnime();

        // Cria um metodo e Salva o anime em uma variavel local.
        Anime animeSaved = this.animeRepository.save(animeToBeSaved);

        // Pega o nome do anime.
        String name = animeSaved.getName();

        // Cria um metodo e Retorna uma Lista de animes.
        List<Anime> animes = this.animeRepository.findByName(name);

        // Verifica se a lista que retornou não é vazia e se contem o animeSaved.
        Assertions.assertThat(animes).isNotEmpty();
        Assertions.assertThat(animes).contains(animeSaved);
    }

    @Test
    @DisplayName("Find By Name returns empty list when no anime is found")
    void findByName_ReturnsEmptyList_WhenAnimeNotFound() {

        // Quando fizer uma busca por Nome ele vai retorna uma lista vazia caso nada seja encontrado.
        List<Anime> animes = this.animeRepository.findByName("Death Note");

        // Verifica se a lista está vazia,
        Assertions.assertThat(animes).isNotEmpty();
    }

    private Anime createAnime() {
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }
}