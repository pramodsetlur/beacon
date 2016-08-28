package hackathon.slashhack.food;

import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.document.Item;
import com.amazonaws.services.dynamodbv2.document.PutItemOutcome;
import com.amazonaws.services.dynamodbv2.document.Table;
import hackathon.slashhack.food.model.FoodConfig;

/**
 * Created by pramod.setlur on 8/27/16.
 */
public class FoodService {
//        DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(
//                new InstanceProfileCredentialsProvider()));

    DynamoDB dynamoDB = new DynamoDB(new AmazonDynamoDBClient(
            new ProfileCredentialsProvider("shantanu")));

    private Table table = dynamoDB.getTable("EstimoteBeacon");

    public String updateDb(FoodConfig foodConfig) {



//        Item item = new Item()
//                .withPrimaryKey("BeaconCategory", "LunchBruh")
//                .withString("Store", "subway");
//        PutItemOutcome outcome = table.putItem(item);
        return "Alright, chill out";
    }
}
