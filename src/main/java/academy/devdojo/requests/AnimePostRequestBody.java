package academy.devdojo.requests;

import lombok.Data;
import lombok.NonNull;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class AnimePostRequestBody {

    //Faz a validação dos campos diretamente no Controller sem precisar do If
    @NotEmpty(message = "The anime cannot be empty")
    private String name;

}