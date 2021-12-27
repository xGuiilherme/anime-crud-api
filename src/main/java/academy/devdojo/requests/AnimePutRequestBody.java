package academy.devdojo.requests;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AnimePutRequestBody {

    public Long id;
    public String name;
}
