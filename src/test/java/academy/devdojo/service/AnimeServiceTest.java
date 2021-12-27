package academy.devdojo.service;

import academy.devdojo.domain.Anime;
import academy.devdojo.repository.AnimeRepository;
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
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
class AnimeServiceTest {

    // @ExtendWith(SpringExtension.class): Através dessa anotação estamos falando que queremos trabalhar com Junit.
    // SpringBootTest: Ele vai tentar Startar a Aplicação para executar os testes.

    // InjectMocks: Utiliza quando você quer testar a Class em Si, a Class que vai ser testada.
    @InjectMocks
    private AnimeService animeService;

    /* Mock: Utiliza para todas as Classes que estão sendo utilizada dentro do 'AnimeController'. Faz um Mock do
       Comportamento ou seja vai definir o comportamento da Class AnimeService. Quando executar Ex: um 'List' nao vai ser
       Executado dentro da Class, vai ser definido um comportamento pra ele etc.
     */
    @Mock
    private AnimeRepository animeRepositoryMock;

    /* BeforeEach:AntesDeCadaUm: Executa esse metodo: setUp:

       Quando retornamos uma Lista significa que o Anime já está salvo.

       BDDMockito.when: Quando alguem executar dentro do Controller uma chamada pro AnimeService.listALL (ou seja não
       importa oque esse listALL receba) quero que retorna o (animePage).
     */
    @BeforeEach
    void setUp() {
        PageImpl<Anime> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnime()));
        BDDMockito.when(animeRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                .thenReturn(animePage);

        // Cria um comportamento para retornar uma lista de anime.
        BDDMockito.when(animeRepositoryMock.findAll())
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.of(AnimeCreator.createValidAnime()));

        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreator.createValidAnime()));

        // Força um comportamento de que apenas esse metodo vai da um Mock se o Objeto que está passando no save é do
        // Tipo AnimePostRequestBody.
        BDDMockito.when(animeRepositoryMock.save(ArgumentMatchers.any(Anime.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        // Força um comportamento para que o BDDMockito não faça nada quando chamar o metodo 'replace'.
        BDDMockito.doNothing().when(animeRepositoryMock).delete(ArgumentMatchers.any(Anime.class));
    }

    @Test
    @DisplayName("listAll returns list of anime inside page object when successful ")
    void listAll_ReturnsListOfAnimesInsidePageObject_WhenSuccessful() {

        // Cira o anime e pega o nome.
        String expectedName = AnimeCreator.createValidAnime().getName();

        // Retorna um ResponseEntity e pega o Body: Ex: Nome que estaria esperando caso esse metodo seja executado com sucesso
        Page<Anime> animePage = animeService.listAll(PageRequest.of(1, 1));

        // Verifica se o animePage não é Nulo.
        Assertions.assertThat(animePage).isNotNull();

        // Verifica se lista capturada está com apenas 1 valor.
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);

        // Verifica o nome da lista captura na posição '0' é o mesmo que foi criado.
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("listAllNoPageable returns list of anime when successful ")
    void listAllNoPageable_ReturnsListOfAnimes_WhenSuccessful() {

        // Cira o anime e pega o nome.
        String expectedName = AnimeCreator.createValidAnime().getName();

        // Retorna um ResponseEntity e pega o Body listando todos.
        List<Anime> animes = animeService.listAllNoPageable();

        // Retorna uma lista com apenas 1 valor.
        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        // Verifica o nome captura na posição '0' é o mesmo que foi criado.
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("findByIdOrdThrowBadRequestException returns anime when successful ")
    void findByIdOrdThrowBadRequestException_ReturnsAnime_WhenSuccessful() {

        // Captura o ID
        Long expectedId = AnimeCreator.createValidAnime().getId();

        Anime anime = animeService.findByIdOrdThrowBadRequestException(1);

        // Verifica se o nome é Nulo.
        Assertions.assertThat(anime).isNotNull();

        // Verifica se o ID é Nulo ou se é o mesmo valor passado.
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);

    }

    @Test
    @DisplayName("findByIdOrdThrowBadRequestException throws BadRequestException when anime is not found")
    void findByIdOrdThrowBadRequestException_BadRequestException_WhenAnimeIsNotFound() {

        // Quando executar um findById no repositorio passando qual quer Long, ele me retorna um Optional.
        BDDMockito.when(animeRepositoryMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(Optional.empty());

        Anime anime = animeService.findByIdOrdThrowBadRequestException(1);

        // Verifica se o nome é Nulo.
        Assertions.assertThat(anime).isNotNull();

        // Verifica se o ID é Nulo ou se é o mesmo valor passado.
        Assertions.assertThat(anime.getId()).isNotNull().isEqualTo(expectedId);

    }

    @Test
    @DisplayName("findByName returns a list of anime when successful ")
    void findByName_ReturnsListOfAnime_WhenSuccessful() {

        // Cria um nome..
        String expectedName = AnimeCreator.createValidAnime().getName();

        // Retorna um ResponseEntity e pega o Body Retornando uma lista com Objetos.
        List<Anime> animes = animeService.findByName("anime");

        // Verifica o Objeto Captura.
        Assertions.assertThat(animes)
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        // Verifica o nome captura na posição '0' é o mesmo que foi criado.
        Assertions.assertThat(animes.get(0).getName()).isEqualTo(expectedName);

    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found ")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound() {

        // Cria um corportamento e quando executar o findByName com qualquer tipo de String, retorna uma lista vazia.
        BDDMockito.when(animeRepositoryMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        // Retorna um ResponseEntity e pega o Body Retornando uma lista com Objetos.
        List<Anime> animes = animeService.findByName("anime");

        // Verifica o Objeto Capturado.
        Assertions.assertThat(animes)
                .isNotNull()
                .isEmpty();

    }

    @Test
    @DisplayName("save returns anime when successful ")
    void save_ReturnsAnime_WhenSuccessful() {

        // Captura e Salva o ID.
        Anime anime = animeService.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

        // Verifica se é Nulo ou se é igual o valor passado.
        Assertions.assertThat(anime).isNotNull().isEqualTo(AnimeCreator.createValidAnime());

    }

    @Test
    @DisplayName("replace updates anime when successful ")
    void replace_UpdatesAnime_WhenSuccessful() {

        // Verifica se esse metodo executou e não lançou nenhuma exceção.
        Assertions.assertThatCode(() -> animeService.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();

    }

    @Test
    @DisplayName("delete removes anime when successful ")
    void delete_RemovesAnime_WhenSuccessful() {

        // Verifica se esse metodo executou e não lançou nenhuma exceção.
        Assertions.assertThatCode(() -> animeService.delete(1))
                .doesNotThrowAnyException();
    }
}