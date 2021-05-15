package nz.assurity.categories.E2Etests;

import io.restassured.http.ContentType;
import nz.assurity.categories.common.BaseTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static io.restassured.RestAssured.given;

@RunWith(SpringRunner.class)
public class CategoriesAPIEnd2EndTest extends BaseTestConfig {

    /**
     * Call the GET/Categories by Id endpoint and check response 200 status
     *
     * @return access by spec
     * Status Code- 200
     * Status message- Ok response
     */
    @Test
    public void test_getCategory_Should_Return_Status_200_OK() throws Exception {

        given().relaxedHTTPSValidation().
                when().
                get(ENDPOINT_CATEGORIES+"v1/Categories/6327/Details.json?catalogue=false").
                then().
                assertThat().
                statusCode(200).
                statusCode(HttpStatus.OK.value()).
                and().
                contentType(ContentType.JSON);
    }
}
