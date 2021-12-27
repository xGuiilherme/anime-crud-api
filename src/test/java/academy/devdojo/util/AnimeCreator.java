package academy.devdojo.util;

import academy.devdojo.domain.Anime;

public class AnimeCreator {

    // Quando chamar esse Metodo sei que é um Anime que não tem ID.
    public static Anime createAnimeToBeSaved() {
        return Anime.builder()
                .name("Hajime no Ippo")
                .build();
    }

    // Significa que criou um Anime e esse Anime tem um ID valido.
    public static Anime createValidAnime() {
        return Anime.builder()
                .name("Hajime no Ippo")
                .id(1L)
                .build();
    }

    // Significa que o ID é o mesmo, porem o nome é diferente 'Updated'.
    public static Anime createValidUpdatedAnime() {
        return Anime.builder()
                .name("Hajime no Ippo 2")
                .id(1L)
                .build();
    }
}
