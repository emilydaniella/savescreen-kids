package controller;

import model.Crianca;
import service.CriancaService;
import static spark.Spark.*;

import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import config.LocalDateAdapter;

public class CriancaController {
    private final CriancaService service = new CriancaService();
    private final Gson gson = new GsonBuilder()
    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
    .create();

    public CriancaController() {
        configurarRotas();
    }

    private void configurarRotas() {
        path("/criancas", () -> {
            post("", (req, res) -> {
                res.type("application/json");
                Crianca c = gson.fromJson(req.body(), Crianca.class);
                service.criar(c);
                res.status(201);
                return gson.toJson(c);
            });

            get("", (req, res) -> {
                res.type("application/json");
                return gson.toJson(service.listar());
            });

            get("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params(":id"));
                Crianca c = service.buscarPorId(id);
                if (c == null) {
                    res.status(404);
                    return "{\"erro\":\"Não encontrada\"}";
                }
                return gson.toJson(c);
            });

            get("/responsavel/:id", (req, res) -> {
                res.type("application/json");
                int idResponsavel = Integer.parseInt(req.params(":id"));
                return gson.toJson(service.listarPorResponsavel(idResponsavel));
            });
            
            

            put("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params(":id"));
                Crianca c = gson.fromJson(req.body(), Crianca.class);
                c.setId(id);
                service.atualizar(c);
                return gson.toJson(c);
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
