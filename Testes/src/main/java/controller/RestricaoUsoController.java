package controller;

import service.RestricaoUsoService;
import model.RestricaoUso;
import com.google.gson.Gson;

import static spark.Spark.*;

public class RestricaoUsoController {
    private static RestricaoUsoService service = new RestricaoUsoService();
    private static Gson gson = new Gson();

    public static void rotas() {
        post("/restricao", (req, res) -> {
            RestricaoUso restricao = gson.fromJson(req.body(), RestricaoUso.class);
            service.registrarRestricao(
                restricao.getIdRestricao(),
                restricao.getHorarioInicio(),
                restricao.getHorarioFim(),
                restricao.getCategoria(),
                restricao.getLimiteDiario()
            );
            res.status(201);
            return "Restrição cadastrada com sucesso!";
        });

        get("/restricoes", (req, res) -> {
            res.type("application/json");
            return gson.toJson(service.listarRestricoes());
        });
    }
}
