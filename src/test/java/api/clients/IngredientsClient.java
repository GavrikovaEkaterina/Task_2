package api.clients;

import api.config.ApiConfig;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;

public class IngredientsClient {

    @Step("Get all ingredients")
    public Response getIngredients() {
        return given()
                .spec(ApiConfig.getRequestSpec())
                .when()
                .get(ApiConfig.INGREDIENTS_ENDPOINT);
    }

    @Step("Get all ingredient IDs")
    public List<String> getAllIngredientIds() {
        Response response = getIngredients();
        if (response.getStatusCode() != 200) {
            System.err.println("Failed to get ingredients. Status code: " + response.getStatusCode());
            return new ArrayList<>();
        }
        return response.jsonPath().getList("data._id");
    }

    @Step("Get {count} random valid ingredients")
    public List<String> getRandomValidIngredients(int count) {
        List<String> allIds = getAllIngredientIds();
        if (allIds.isEmpty()) {
            return new ArrayList<>();
        }
        if (allIds.size() < count) {
            return allIds;
        }
        return allIds.subList(0, Math.min(count, allIds.size()));
    }

    @Step("Get one valid ingredient ID")
    public String getValidIngredientId() {
        List<String> allIds = getAllIngredientIds();
        if (allIds.isEmpty()) {
            return null;
        }
        return allIds.get(0);
    }
}