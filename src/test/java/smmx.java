import org.junit.Test;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import static org.junit.Assert.*;
import static io.restassured.RestAssured.*;
import java.net.URLEncoder;
import java.util.Base64;




public class smmx {
    @Test
    public void get_token_status_fail_test(){
        given().queryParam("Lang","es").when()
                .log().all()
                .post("https://webapi.segundamano.mx/nga/api/v1.1/private/accounts")
                .then().statusCode(400);

    }

    @Test
    public void Coronavirus() {

        RestAssured.baseURI = String.format("https://api.quarantine.country/api/v1/summary/latest");
        Response response = given()
                .log().all()
                .header("Accept", "application/json")
                .get();

        //validations
        String body = response.getBody().asString();
        System.out.println("Status expected: 200");
        System.out.println("Response: " + body);
        System.out.println("Response Status: " + response.getStatusCode());
        System.out.println("Response Headers: " + response.getHeaders());
        System.out.println("Response ContentType: " + response.contentType());
        assertEquals(200, response.getStatusCode());
        assertTrue(body.contains("summary"));
    }

    @Test
    public void Test_token_fail(){
        //Request an account token without authorization header
        baseURI = String.format("https://webapi.segundamano.mx/nga/api/v1.1/private/accounts");
        Response response = given().log().all().post();
        //Validation
        System.out.println("Status Expected: 400");
        System.out.println("Result: " + response.getStatusCode());
        assertEquals(400,response.getStatusCode());
        String errorCode = response.jsonPath().getString("error.code");
        System.out.println("Error Code Expected: VALIDATION_FAILED \n Result: " + errorCode);
        assertEquals("VALIDATION_FAILED", errorCode);
    }

    @Test
    public void Get_Token(){
        String email = "apitest@mailinator.com";
        String pass = "12345";

        String ToEncode = email + ":" + pass;

        // Postman: forma de concatenar dentro del encode (el anterior se concateno anteriormente por separado en Crear Usuario)
        // let encodeKeys = CryptoJS.enc.Base64.stringify(CryptoJS.enc.Utf8.parse(emailValid + ":" + passValid));
        String Basic_encoded = Base64.getEncoder().encodeToString(ToEncode.getBytes());

        baseURI = String.format("https://webapi.segundamano.mx/nga/api/v1.1/private/accounts");
        Response response = given().queryParam("lang","es").log().all()
                .header("Authorization","Basic " + Basic_encoded)
                .post();

        String body = response.getBody().asString();
        System.out.println(response.getBody().asString());

        assertEquals(200,response.getStatusCode());
        assertNotNull(body);
        assertTrue(body.contains("access_token"));
    }


    @Test
    public void Create_account() {
        RestAssured.baseURI = String.format("https://webapi.segundamano.mx/nga/api/v1.1/private/accounts?lang=es");
        Response response = given().log().all()
                .post();
        System.out.println("Result: " + response.getStatusCode());


    }


    @Test
    public void Datos() {
        RestAssured.baseURI = String.format("https://api.quarantine.country/api/v1/summary/latest");
        /*RequestSpecification httpRequest = RestAssured.given();*/

        Response response = given().log().all().header("Accept", "application/json").get();

        String body = response.getBody().asString();
        System.out.println("Body: " + body);
    }


}
