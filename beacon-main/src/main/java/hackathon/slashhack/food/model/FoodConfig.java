package hackathon.slashhack.food.model;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Created by pramod.setlur on 8/27/16.
 */

@Data
public class FoodConfig {
    @JsonProperty("category")
    private String category;
}
