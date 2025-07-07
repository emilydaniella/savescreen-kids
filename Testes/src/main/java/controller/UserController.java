package controller;

import com.google.gson.Gson;

import model.User;
import service.UserService;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

public class UserController {
    private final UserService service = new UserService();
    private final Gson gson = new Gson();

    public UserController() {
        // garante que a tabela exista
        service.dao.createTable();
        configurarRotas();
    }

    private void configurarRotas() {
        path("/users", () -> {
            post("", (req, res) -> {
                res.type("application/json");
                // o JSON deve conter também um campo "type": "CRIANCA" ou "RESPONSAVEL"
                var payload = gson.fromJson(req.body(), com.google.gson.JsonObject.class);

                User u = new User(
                  payload.get("username").getAsString(),
                  payload.get("password").getAsString(),
                  payload.get("email").getAsString()
                );
                String type = payload.get("type").getAsString();

                User criado = service.criar(u, type);
                res.status(201);
                return gson.toJson(criado);
            });

            get("", (req, res) -> {
                res.type("application/json");
                return gson.toJson(service.listar());
            });

            get("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params(":id"));
                User u = service.buscarPorId(id);
                if (u == null) {
                    res.status(404);
                    return "{\"erro\":\"Usuário não encontrado\"}";
                }
                return gson.toJson(u);
            });

            put("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params(":id"));
                User u = gson.fromJson(req.body(), User.class);
                u.setId(id);
                User atualizado = service.atualizar(u);
                if (atualizado == null) {
                    res.status(404);
                    return "{\"erro\":\"Falha ao atualizar\"}";
                }
                return gson.toJson(atualizado);
            });

            delete("/:id", (req, res) -> {
                int id = Integer.parseInt(req.params(":id"));
                boolean ok = service.deletar(id);
                res.status(ok ? 204 : 404);
                return "";
            });
        });
    }
}