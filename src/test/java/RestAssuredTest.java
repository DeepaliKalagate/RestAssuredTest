import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.apache.http.HttpStatus;
import org.hamcrest.MatcherAssert;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import java.io.File;

public class RestAssuredTest
{
    private String tokenValue;

    @Before
    public void setUp()
    {
        RestAssured.baseURI="https://fundoopush-backend-dev.bridgelabz.com/";
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"email\":\"dipakalagate1991@gmail.com\",\"password\":\"123456\"}")
                .when()
                .post("/login");

        String string = response.asString();
        JsonPath jsonPath = new JsonPath(string);
         tokenValue = jsonPath.get("token");
        System.out.println("Token value is: "+tokenValue);
    }

    @Test
    public void givenUser_EmailIdForRegistration_ShouldReturnSuccessfulMessage()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"email\":\"prajktaRaut@gmail.com\",\"password\":\"123456\"}")
                .when()
                .post("/registration");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_CREATED));
    }

    @Test
    public void givenUser_RegistrationWithBlankEmailAndPassword_ShouldReturnEmailIsRequired()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"email\":\"\",\"password\":\"123456\"}")
                .when()
                .post("/registration");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void givenUser_RegistrationWithEmailAndBlankPassword_ShouldReturnPasswordIsRequired()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"email\":\"dipakalagate1991@gmail.com\",\"password\":\"\"}")
                .when()
                .post("/registration");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void givenUser_RegisterWithAlreadyPresentEmailAndPassword_ShouldReturnalreadyExists()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"email\":\"dipalkalagate1991@gmail.com\",\"password\":\"12345\"}")
                .when()
                .post("/registration");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_CONFLICT));
    }

    @Test
    public void givenUser_EmailIdForLogin_ShouldReturnMessageSuccessful()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"email\":\"dipakalagate1991@gmail.com\",\"password\":\"123456\"}")
                .when()
                .post("/login");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenUser_LoginWithBlankEmailAndPassword_ShouldReturnEmailIsRequired()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"email\":\"\",\"password\":\"123456\"}")
                .when()
                .post("/login");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void givenUser_LoginWithEmailAndBlankPassword_ShouldReturnPasswordIsRequired()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"email\":\"dipakalagate1991@gmail.com\",\"password\":\"\"}")
                .when()
                .post("/login");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void givenUser_EmailIdForLogin_ShouldReturnErrorMessage()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"email\":\"dipalilokeshpatil1991@gmail.com\",\"password\":\"123456\"}")
                .when()
                .post("/login");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_NOT_FOUND));
    }

    @Test
    public void givenUserId_ShouldReturnLogoutSuccessfulMessage()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"user_id\":\"5e0985fb4d22670032530f14\"}")
                .when()
                .post("/logout");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenWrongUserId_ShouldReturnWrongUserId()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"user_id\":\"5e0985fb4d22670032530f\"}")
                .when()
                .post("/logout");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void givenUserIdEmailIdPassword_ChangePassword_ShouldReturnMessage()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokenValue)
                .body("{\"user_id\":\"5e0985fb4d22670032530f14\",\"email\":\"dipakalagate1991@gmail.com\",\"password\":\"123456\",\"new_password\":\"abcd1234\"}")
                .when()
                .post("/account/change-password");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void givenUserId_ForgotPassword_ShouldReturnMessage()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"user_id\":\"5e0985fb4d22670032530f14\",\"email\":\"dipakalagate1991@gmail.com\"}")
                .when()
                .post("/account/forgot-password");
        int status = response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_UNAUTHORIZED) );
    }

    @Test
    public void givenUserIdEmailIdPassword_ResetPassword_ShouldReturnMessage()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokenValue)
                .body("{\"user_id\":\"5e0985fb4d22670032530f14\",\"email\":\"dipakalagate1991@gmail.com\",\"new_password\":\"abcd1234\"}")
                .when()
                .post("/account/reset-password");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void givenUserId_Redirect_ShouldReturnMessageSuccessful()
    {
        File testFile = new File("/home/user/Pictures/example.jpeg");
        Response response=RestAssured.given()
                .accept(ContentType.JSON)
                .header("token",tokenValue)
                .multiPart("image",testFile)
                .formParam("title","Global Warming")
                .formParam("description","Project on global warming with images")
                .formParam("redirect_link","www.google.com")
                .formParam("is_published","false")
                .formParam("archive","false")
                .formParam("youtube_flag","true")
                .formParam("youtube_url","www.google.com")
                .formParam("video_link","www.google.com")
                .when()
                .post("/redirects");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_CREATED));
    }

    @Test
    public void givenUserId_Redirect_ShouldReturnErrorMessage()
    {
        Response response=RestAssured.given()
                .accept(ContentType.JSON)
                .header("token",tokenValue)
                .formParam("description","Project on global warming with images")
                .formParam("redirect_link","www.google.com")
                .formParam("is_published","false")
                .formParam("archive","false")
                .formParam("youtube_flag","true")
                .formParam("youtube_url","www.google.com")
                .formParam("video_link","www.google.com")
                .when()
                .post("/redirects");
        int status=response.getStatusCode();
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenUserId_UpdateRedirect_ShouldReturnMessageSuccessful()
    {
        File testFile = new File("/home/user/Pictures/image.jpeg");
        Response response=RestAssured.given()
                .accept(ContentType.JSON)
                .header("token",tokenValue)
                .formParam("_id","5e0acfbf4d22670032531003")
                .multiPart("image",testFile)
                .formParam("title","image Changed")
                .formParam("description","set different image")
                .formParam("redirect_link","www.google.com")
                .formParam("isDeleted","false")
                .formParam("is_published","false")
                .formParam("archive","false")
                .formParam("youtube_flag","true")
                .formParam("youtube_url","www.google.com")
                .formParam("video_link","www.google.com")
                .when()
                .put("/redirects");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenToken_ShouldReturnAllRedirectData()
    {
        Response response=RestAssured.given()
                .accept(ContentType.JSON)
                .header("token",tokenValue)
                .when()
                .get("/redirects");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenToken_ShouldReturnDeleteRedirectData()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokenValue)
                .body("{\"_id\":\"5e0ad3034d2267003253100c\"}")
                .when()
                .post("/redirects/delete");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void getAllRedirectData()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .get("/bl-redirects");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenToken_EditHashtag_ShouldReturnSuccessfullyMessage()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokenValue)
                .body("{\"redirect_id\":\"5e0ad3034d2267003253100c\",\"hashtag\":\"#testing\"}")
                .when()
                .post("/hashtag/edit");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenOnlyRedirectId_ForEditHashtag_ShouldReturnErrorMessage()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"redirect_id\":\"5e0ad3034d2267003253100c\",\"hashtag\":\"#testing\"}")
                .when()
                .post("/hashtag/edit");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void givenToken_GetHashtagData_ShouldReturnSuccessfullyMessage()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokenValue)
                .pathParam("hashtagname","#testing")
                .when()
                .get("/redirects/hashtag/{hashtagname}");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenTokenAndUrl_GetScrapData_ShouldReturnSuccessfullyMessage()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokenValue)
                .body("{\"url\":\"https://www.deccanchronicle.com/technology/in-other-news/270319/companies-that-are-changing-the-way-education-is-being-delivered-to-st.html\"}")
                .when()
                .post("/web-scraping");
        int status=response.getStatusCode();
        String string = response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenTokenAndHashtag_SearchAPI_ShoudReturnSuccessfullyMessage()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokenValue)
                .body("{\"hashtag\":\"#bridgelabz\"}")
                .when()
                .post("/search/hashtag");
        int status=response.getStatusCode();
        String string=response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
    }


    @Test
    public void givenTokenAndRedirectId_PostJob_ShouldReturnSuccessfullyMessage()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokenValue)
                .body("{\"redirect_id\":\"5e0af5a14d2267003253108d\",\"years_of_experience\":\"1\",\"salary\":\"3.6\",\"location\":\"Mumbai\",\"company_profile\":\"Ideation\"}")
                .when()
                .post("/jobs");
        int status=response.getStatusCode();
        String string=response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenRedirectId_PostJob_ShouldReturnErrorMessage()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{\"redirect_id\":\"5e0af5a14d2267003253108d\",\"years_of_experience\":\"1\",\"salary\":\"3.6\",\"location\":\"Mumbai\",\"company_profile\":\"Ideation\"}")
                .when()
                .post("/jobs");
        int status=response.getStatusCode();
        String string=response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void givenTokenAndRedirectId_AddJob_ShouldReturnSuccessfullyMessage()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokenValue)
                .body("{\"job_id\":\"5e0afa9493f6d3003793d239\",\"hashtag\":\"#Bridgelabz\"}")
                .when()
                .post("/jobs/hashtag/add");
        int status=response.getStatusCode();
        String string=response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void givenTokenAndRedirectId_AddJob_ShouldReturnErrorMessage()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokenValue)
                .body("{\"job_id\":\"5e0afa9493f6d3003793d\",\"hashtag\":\"#Bridgelabz\"}")
                .when()
                .post("/jobs/hashtag/add");
        int status=response.getStatusCode();
        String string=response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_UNAUTHORIZED));
    }

    @Test
    public void givenTokenAndRedirectId_RemoveJob_ShouldReturnSuccessfullyMessage()
    {
        Response response=RestAssured.given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .header("token",tokenValue)
                .body("{\"job_id\":\"5e0afa9493f6d3003793d239\",\"hashtag_id\":\"5d662f00f149c627ab5d3efd\"}")
                .when()
                .post("/jobs/hashtag/remove");
        int status=response.getStatusCode();
        String string=response.asString();
        System.out.println(string);
        MatcherAssert.assertThat(status, Matchers.equalTo(HttpStatus.SC_OK));
    }
}
