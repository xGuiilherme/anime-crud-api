package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.requests.AnimePostRequestBody;
import academy.devdojo.requests.AnimePutRequestBody;
import academy.devdojo.service.AnimeService;
import academy.devdojo.util.AnimeCreator;
import academy.devdojo.util.AnimePostRequestBodyCreator;
import academy.devdojo.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    // @ExtendWith(SpringExtension.class): Através dessa anotação estamos falando que queremos trabalhar com Junit.
    // SpringBootTest: Ele vai tentar Startar a Aplicação para executar os testes.

    // InjectMocks: Utiliza quando você quer testar a Class em Si, a Class que vai ser testada.
    @InjectMocks
    private AnimeController animeController;

    /* Mock: Utiliza para todas as Classes que estão sendo utilizada dentro do 'AnimeController'. Faz um Mock do
       Comportamento ou seja vai definir o comportamento da Class AnimeService. Quando executar Ex: um 'List' nao vai ser
       Executado dentro da Class, vai ser definido um comportamento pra ele etc.
     */
    @Mock
    private AnimeService animeServiceMock;

    /* BeforeEach:AntesDeCadaUm: Executa esse metodo: setUp:

       Quando retornamos uma Lista significa que o Anime já está salvo.

       BDDMockito.when: Quando alguem executar dentro do Controller uma chamada pro AnimeService.listALL (ou seja não
       importa oque esse listALL receba) quero que retorna o (animePage).
     */
    @BeforeEach
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        BDDMockito.when(animeServiceMock.listAllNoPageable())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeServiceMock.findByIdOrdThrowBadRequestException(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        // Força um comportamento de que apenas esse metodo vai da um Mock se o Objeto que está passando no save é do
        // Tipo AnimePostRequestBody.
        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        // Força um comportamento para que o BDDMockito não faça nada quando chamar o metodo 'replace'.
        BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("list returns list of anime inside page object when successful ")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {

        // Cira o anime e pega o nome.
        String expectedName = AnimeCreator.createValidAnime().getName();

        // Retorna um ResponseEntity e pega o Body: Ex: Nome que estaria esperando caso esse metodo seja executado com sucesso
        Page<Anime> animePage = animeController.list(null).getBody();

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("listALL returns list of anime when successful ")
    void listALL_ReturnsListOfAnimes_WhenSuccessful() {

        String expectedName = AnimeCreator.createValidAnime().getName();

        // Retorna um ResponseEntity e pega o Body listando todos.
        List<Anime> animes = animeController.listAll().getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("findById returns anime when successful ")
    void findById_ReturnsAnime_WhenSuccessful() {

        Long expectedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeController.findById(0).getBody();

        Assertions.assertThat(anime).isNotNull();

        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);

    }

    @Test
    @DisplayName("findByName returns a list of anime when successful ")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {

        String expectedName = AnimeCreator.createValidAnime().getName();

        // Retorna um ResponseEntity e pega o Body Retornando uma lista com Objetos.
        List<Anime> animes = animeController.findByName("anime").getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found ")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {

        // Cria um corportamento e quando executar o findByName com qualquer tipo de String, retorna uma lista vazia.
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        // Retorna um ResponseEntity e pega o Body Retornando uma lista com Objetos.
        List<Anime> animes = animeController.findByName("anime").getBody();

        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("save returns anime when successful ")
    void save_ReturnsAnime_WhenSuccessful() {

        Anime anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody()).getBody();

        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());

    }

    @Test
    @DisplayName("replace updates anime when successful ")
    void replace_UpdatesAnime_WhenSuccessful() {

        // Verifica se esse metodo executou e não lançou nenhuma exceção.
        Assertions.assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions.assertThat(entity).isNotNull();

        // Retorna Status NO_CONTENT.
        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }

    @Test
    @DisplayName("delete removes anime when successful ")
    void delete_RemovesAnime_WhenSuccessful() {

        // Verifica se esse metodo executou e não lançou nenhuma exceção.
        Assertions.assertThatCode(() -> animeController.delete(1))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.delete(1);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

    }
}