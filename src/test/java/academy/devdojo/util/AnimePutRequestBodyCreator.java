package academy.devdojo.util;

import academy.devdojo.requests.AnimePutRequestBody;

public class AnimePutRequestBodyCreator {

    /* Quando criarmos esse valor, oque estamos esperando como retorno é o Anime que foi atualizado.
       Aqui ele está mocando uma resposta do Service e está sendo criado esse valor que será passado pro Controller.
    */
    public static AnimePutRequestBody createAnimePutRequestBody() {
        return AnimePutRequestBody.builder()
                .id(AnimeCreator.createValidUpdatedAnime().getId())
                .name(AnimeCreator.createValidUpdatedAnime().getName())
                .build();
    }
}
