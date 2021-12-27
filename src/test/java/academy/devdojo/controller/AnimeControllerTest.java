package academy.devdojo.controller;

import academy.devdojo.domain.Anime;
import academy.devdojo.service.AnimeService;
import academy.devdojo.util.AnimeCreator;
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
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
    }

    @Test
    @DisplayName("List returns list of anime inside page object when successful ")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful(){

        String expectedName = AnimeCreator.createValidAnime().getName();

        Page<Anime> animePage = animeController.list(null).getBody();

        // Verifica se o animePage não é Nulo.
        Assertions.assertThat(animePage).isNotNull();

        // Retorna uma lista com apenas 1 valor.
        Assertions.assertThat(animePage.toList())
                .isNotEmpty()
                .hasSize(1);

        // Pega a Lista na posição '0'
        Assertions.assertThat(animePage.toList().get(0).getName()).isEqualTo(expectedName);

    }
}