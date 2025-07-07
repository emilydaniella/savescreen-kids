package controller;

import model.Responsavel;
import service.ResponsavelService;
import static spark.Spark.*;
import com.google.gson.Gson;

public class ResponsavelController {
    private final ResponsavelService service = new ResponsavelService();
    private final Gson gson = new Gson();

    public ResponsavelController() {
        configurarRotas();
    }

    private void configurarRotas() {
        path("/responsaveis", () -> {
            post("", (req, res) -> {
                res.type("application/json");
                Responsavel r = gson.fromJson(req.body(), Responsavel.class);
                service.criar(r);
                res.status(201);
                return gson.toJson(r);
            });

            get("", (req, res) -> {
                res.type("application/json");
                return gson.toJson(service.listar());
            });

            get("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params(":id"));
                Responsavel r = service.buscarPorId(id);
                if (r == null) {
                    res.status(404);
                    return "{\"erro\":\"Não encontrado\"}";
                }
                return gson.toJson(r);
            });

            put("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params(":id"));
                Responsavel r = gson.fromJson(req.body(), Responsavel.class);
                r.setId(id);
                service.atualizar(r);
                return gson.toJson(r);
            });

            delete("/:id", (req, res) -> {
                int id = Integer.parseInt(req.params(":id"));
                service.deletar(id);
                res.status(204);
                return "";
            });
        });
    }
}
