package academy.devdojo.requests;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;

public class AnimePostRequestBody {

    //Faz a validação dos campos diretamente no Controller sem precisar do If
    @NotEmpty(message = "The anime cannot be empty")
    private String name;

    public AnimePostRequestBody() {
    }

    public AnimePostRequestBody(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}