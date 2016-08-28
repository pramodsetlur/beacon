package hackathon.slashhack.food;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import hackathon.slashhack.food.model.FoodConfig;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by pramod.setlur on 8/27/16.
 */
public class FoodService {
//        DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(
//                new InstanceProfileCredentialsProvider()));
    private final static String PRIMARY_KEY = "BeaconCategory";
    private final static String VALUE = "BeaconValue";
    private final static String STORE = "store";
    private final static String DEAL = "deal";
    private final static String DETAILS = "details";

    DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(
            new ProfileCredentialsProvider("shantanu")));

    private Table table = dynamoDB.getTable("EstimoteBeacon");

    public String pushToDb(FoodConfig foodConfig) {

        Item item = table.getItem(PRIMARY_KEY, "lunch");
        if (null == item) {
            insertData(foodConfig);
        } else {
            
        }
        
        
        return "Alright, chill out";
    }

    private void insertData(FoodConfig foodConfig) {
        String jsonString = String.format("{%s:%s,%s:%s, %s:%s}",
                STORE, foodConfig.getStore(), DEAL, foodConfig.getDeal(), DETAILS, foodConfig.getDetails());

        Map<String, String> foodMap = new HashMap<>();
        foodMap.put(STORE, foodConfig.getStore());
        foodMap.put(DEAL, foodConfig.getDeal());
        foodMap.put(DETAILS, foodConfig.getDetails());

        List<Map<String, String>> foodList = new ArrayList<>();
        foodList.add(0, foodMap);

        Item item = new Item()
                .withPrimaryKey(PRIMARY_KEY, foodConfig.getCategory())
                .withList(VALUE, foodList);

        PutItemOutcome outcome = table.putItem(item);
    }
}
