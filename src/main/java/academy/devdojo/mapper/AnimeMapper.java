package academy.devdojo.mapper;

import academy.devdojo.domain.Anime;
import academy.devdojo.requests.AnimePostRequestBody;
import academy.devdojo.requests.AnimePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring")
public abstract class AnimeMapper {

    // Chamar o metodo abstrato: É uma instância que está pegando do 'AnimeMapper'
    @Autowired
    public static final AnimeMapper INSTANCE = Mappers.getMapper(AnimeMapper.class);

    // Aqui ele faz automati.. a conversão de todos os atributos que tem nesse valor'Put/Post' convertido p/ Anime.
    @Autowired
    public abstract Anime toAnime(AnimePostRequestBody animePostRequestBody);

    public abstract Anime toAnime(AnimePutRequestBody animePostRequestBody);
}