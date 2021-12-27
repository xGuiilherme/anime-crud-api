package academy.devdojo.requests;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class AnimePostRequestBody {

    //Faz a validação dos campos diretamente no Controller sem precisar do If
    @NotEmpty(message = "The anime cannot be empty")
    private String name;
}