package controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import config.LocalDateAdapter;
import dao.RotinaDAO;
import model.RotinaModel;

import static spark.Spark.*;

import java.time.LocalDate;

public class RotinaController {
    private static Gson gson = new GsonBuilder()
    .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
    .create();
    private static RotinaDAO dao = new RotinaDAO();

    public static void configurarRotas() {
        // GET /rotina/:userId
        get("/rotina/:userId", (req, res) -> {
            res.type("application/json");
            int userId = Integer.parseInt(req.params(":userId"));
            return gson.toJson(dao.buscarPorUsuario(userId));
        });

        path("/atividades", () -> {

            // GET /atividades
            get("", (req, res) -> {
                res.type("application/json");
                return gson.toJson(dao.listar());
            });

            // GET /atividades/:id
            get("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params(":id"));
                RotinaModel rotina = dao.buscarPorId(id);
                if (rotina != null) {
                    return gson.toJson(rotina);
                } else {
                    res.status(404);
                    return gson.toJson("Atividade não encontrada");
                }
            });

            // POST /atividades
            post("", (req, res) -> {
                res.type("application/json");
                RotinaModel nova = gson.fromJson(req.body(), RotinaModel.class);

                if (nova.getNome() == null || nova.getNome().isEmpty()) {
                    res.status(400);
                    return gson.toJson("Nome é obrigatório");
                }

                dao.inserir(nova);
                return gson.toJson("Atividade criada com sucesso");
            });

            // PUT /atividades/:id
            put("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params(":id"));
                RotinaModel atualizada = gson.fromJson(req.body(), RotinaModel.class);
                atualizada.setId(id);

                if (atualizada.getNome() == null || atualizada.getNome().isEmpty()) {
                    res.status(400);
                    return gson.toJson("Nome é obrigatório");
                }

                dao.atualizar(atualizada);
                return gson.toJson("Atividade atualizada com sucesso");
            });

            // DELETE /atividades/:id
            delete("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params(":id"));
                dao.deletar(id);
                return gson.toJson("Atividade deletada com sucesso");
            });

        });
    }
}
