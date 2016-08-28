package hackathon.slashhack.food;

import hackathon.slashhack.food.model.FoodConfig;

/**
 * Created by pramod.setlur on 8/27/16.
 */
public class FoodService {
    public String updateDb(FoodConfig foodConfig) {
        System.out.println(foodConfig.getCategory());
        return "Alright, chill out";
    }
}
