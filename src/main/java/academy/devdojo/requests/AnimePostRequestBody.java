package academy.devdojo.requests;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AnimePostRequestBody {

    //Faz a validação dos campos diretamente no Controller sem precisar do If
    @NotEmpty(message = "The anime cannot be empty")
    @Schema(description = "This is the Anime's name", example = "Tensei Shitara Slime Datta Ken", required = true)
    private String name;
}