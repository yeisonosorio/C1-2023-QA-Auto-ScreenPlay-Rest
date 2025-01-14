package com.sofkau.stepdefinitons;

import com.sofkau.models.ResponseGame;
import com.sofkau.models.Response;
import com.sofkau.setup.ApiSetUp;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import net.serenitybdd.rest.SerenityRest;
import org.apache.http.HttpStatus;
import org.apache.log4j.Logger;
import org.hamcrest.CoreMatchers;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import static com.sofkau.questions.ReturnQuestionGames.returnQuestionGames;
import static com.sofkau.tasks.DoGetGame.doGetGame;
import static com.sofkau.utils.FreetoGameResources.*;
import static net.serenitybdd.screenplay.GivenWhenThen.seeThat;
import static net.serenitybdd.screenplay.rest.questions.ResponseConsequence.seeThatResponse;
import static org.hamcrest.core.IsNull.notNullValue;

public class GetFreetogameStepDefinition extends ApiSetUp {

    public static Logger LOGGER = Logger.getLogger(GetFreetogameStepDefinition.class);
    JSONParser parser = new JSONParser();
    JSONObject responseBody = null;
    private Response response;

    @Given("El jugador se encuentra dentro de la pagina adecuada para realizar la consulta")
    public void elJugadorSeEncuentraDentroDeLaPaginaAdecuadaParaRealizarLaConsulta() {
        setUp(FREETOGAME_RESOURCES_BASE_URL.getValue());
    }

    @When("El jugador realiza la consulta consulta del  juego por {int}")
    public void elJugadorRealizaLaConsultaConsultaDelJuegoPor(Integer id) {
        actor.attemptsTo(
                doGetGame()
                        .withTheResource(FREETOGAME_GET_RESOURCE.getValue() + id)
        );
        System.out.println(SerenityRest.lastResponse().body().asString());


    }


    @Then("El jugador recibe un estadtus {int} con el juego encontrado")
    public void elJugadorRecibeUnEstadtusConElJuegoEncontrado(Integer code) {
        ResponseGame actualResponse = returnQuestionGames().answeredBy(actor);
        LOGGER.info("respuesta de la api-->" + actualResponse);

        actor.should(
                seeThatResponse("El codigo de respuesta es: " + HttpStatus.SC_OK,
                        response -> response.statusCode(code)),

                seeThat("Retorna información",
                        act -> actualResponse, notNullValue())
        );
    }


}
