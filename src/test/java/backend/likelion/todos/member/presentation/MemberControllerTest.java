package backend.likelion.todos.member.presentation;

import static org.assertj.core.api.Assertions.assertThat;

import backend.likelion.todos.ApiTest;
import backend.likelion.todos.member.presentation.request.MemberSignupRequest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.ExtractableResponse;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator.ReplaceUnderscores;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

@SuppressWarnings("NonAsciiCharacters")
@DisplayNameGeneration(ReplaceUnderscores.class)
@DisplayName("회원 API 테스트")
class MemberControllerTest extends ApiTest {

    @Nested
    class 회원가입_시 {

        @Test
        void 성공하면_201_상태코드와_함께_응답_헤더의_Location_값으로_생성된_회원을_조회할_수_있는_URL이_반환된다() {
            // given
            MemberSignupRequest request = new MemberSignupRequest(
                    "likelion",
                    "likelion1234",
                    "멋사",
                    "이미지 URL"
            );

            // when
            ExtractableResponse<Response> response = RestAssured.given()
                    .log().all()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .post("/members")
                    .then()
                    .log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value());
            assertThat(response.header("Location")).contains("/members/");
        }

        @Test
        void 아이디가_중복되면_409_상태코드와_예외_메세지를_반환한다() {
            // given
            MemberSignupRequest request = new MemberSignupRequest(
                    "likelion",
                    "likelion1234",
                    "멋사",
                    "이미지 URL"
            );
            RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .post("/members")
                    .then()
                    .extract();

            // when
            ExtractableResponse<Response> response = RestAssured.given()
                    .contentType(ContentType.JSON)
                    .body(request)
                    .post("/members")
                    .then()
                    .log().all()
                    .extract();

            // then
            assertThat(response.statusCode()).isEqualTo(HttpStatus.CONFLICT.value());
        }
    }
}
