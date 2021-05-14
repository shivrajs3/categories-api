package nz.assurity.categories.functionaltests;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import nz.assurity.categories.common.BaseTestConfig;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
//import org.junit.jupiter.api.Test;
import org.junit.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

import static io.restassured.RestAssured.*;

@RunWith(SpringRunner.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CategoriesAPIFunctionalTest  extends BaseTestConfig {

    public Response response;

    @Before
    public void initialize() throws IOException {
        setUp();
        getCategoryDetailsByCategoryID();
    }
    /**
     * Call the GET/Categories by Id endpoint and check response status and content
     *
     * @return access by spec
     * Status Code- 200
     * Name = "Carbon credits"
     * CanRelist = true
     * The Promotions element with Name = "Gallery" has a Description that contains the text "2x larger image"
     */

    private void getCategoryDetailsByCategoryID(){
        response = given().headers("Content-Type", ContentType.JSON, "Accept", ContentType.JSON).
                when().get(ENDPOINT_CATEGORIES+"v1/Categories/6327/Details.json?catalogue=false")
                .then()
                .contentType(ContentType.JSON)
                .statusCode(200)
                .extract().response();
    }

    @Test
    public void getCategory_Details_By_CategoryId_Category_Name_ShouldBe_Correct() throws Exception {
        Assertions.assertEquals(response.body().path("Name"), expectedName);
    }

    @Test
    public void getCategory_Details_By_CategoryId_CanReList_Should_Be_True() throws Exception {
        Assertions.assertEquals(response.body().path("CanRelist"), expectedCanRelist);
    }

    @Test
    public void getCategory_Details_By_CategoryId_Description_Should_Be_Correct() throws Exception {
        validatePromotions(response.body().path("Promotions"));
    }

    /**
     * Call the GET/Categories by Id endpoint and check response 400 status
     *
     * @return access by spec
     * Status Code- 400
     * Status message- Bad Request
     */
    @Test
    public void getCategory_Should_Return_Status_400_BadRequest() throws Exception {

        given().relaxedHTTPSValidation().
                when().
                get(ENDPOINT_CATEGORIES+"v1/Categories/123/Details.json?catalogue=false").
                then().
                assertThat().
                statusCode(400).
                statusCode(HttpStatus.BAD_REQUEST.value()).
                contentType(ContentType.JSON);
    }

    /**
     * Call the GET/Categories by Id endpoint and check response 404 status
     *
     * @return access by spec
     * Status Code- 404
     * Status message- Not Found
     */
    @Test
    public void getCategory_Should_Return_Status_404_Notfound() throws Exception {

        given().relaxedHTTPSValidation().
                when().
                get(ENDPOINT_CATEGORIES+"v2/Categories/6327/Details.json?catalogue=false").
                then().
                assertThat().
                statusCode(404).
                statusCode(HttpStatus.NOT_FOUND.value());
    }

    /**
     * Call the GET/Categories by Id endpoint and check response 403 status
     *
     * @return access by spec
     * Status Code- 403
     * Status message- Forbidden
     */
    @Test
    public void getCategory_Should_Return_Status_403_Forbidden() throws Exception {

        given().relaxedHTTPSValidation().
                when().
                get(ENDPOINT_CATEGORIES).
                then().
                assertThat().
                statusCode(403).
                statusCode(HttpStatus.FORBIDDEN.value());
    }

    private void validatePromotions(List<HashMap> promotions) {
        List<HashMap> promotionsToAssert = promotions.stream()
                .filter((p -> p.get("Name").toString().contains("Gallery"))).collect(Collectors.toList());

        promotionsToAssert.stream().forEach(promotion -> {
            Assertions.assertEquals(promotion.get("Description"), expectedDescription);
        });
    }
}
