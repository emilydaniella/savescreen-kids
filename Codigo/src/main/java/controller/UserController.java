package controller;

import java.time.LocalDate;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import at.favre.lib.crypto.bcrypt.BCrypt;
import config.LocalDateAdapter;
import model.User;
import service.UserService;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

public class UserController {
    private static final Gson gson = new GsonBuilder()
        .registerTypeAdapter(LocalDate.class, new LocalDateAdapter())
        .create();

    private static final UserService userService = new UserService();

    public static void configurarRotas() {
        path("/usuario", () -> {

            // GET /usuario - lista todos os usuários
            get("", (req, res) -> {
                res.type("application/json");
                try {
                    return gson.toJson(userService.listar());
                } catch (Exception e) {
                    res.status(500);
                    return gson.toJson("Erro ao buscar usuários");
                }
            });

            // GET /usuario/:id - busca usuário por ID
            get("/:id", (req, res) -> {
                res.type("application/json");
                try {
                    int id = Integer.parseInt(req.params(":id"));
                    User user = userService.buscarPorId(id);

                    if (user != null) {
                        return gson.toJson(user);
                    } else {
                        res.status(404);
                        return gson.toJson("Usuário não encontrado");
                    }
                } catch (Exception e) {
                    res.status(500);
                    return gson.toJson("Erro interno do servidor");
                }
            });

            // POST /usuario - cria um novo usuário
            post("", (req, res) -> {
                res.type("application/json");
                User novo = gson.fromJson(req.body(), User.class);

                if (novo.getUsername() == null || novo.getUsername().isEmpty()) {
                    res.status(400);
                    return gson.toJson("Username é obrigatório");
                }
                if (novo.getEmail() == null || novo.getEmail().isEmpty()) {
                    res.status(400);
                    return gson.toJson("Email é obrigatório");
                }
                if (novo.getPassword() == null || novo.getPassword().isEmpty()) {
                    res.status(400);
                    return gson.toJson("Senha é obrigatória");
                }

                String senhaHash = BCrypt.withDefaults()
                    .hashToString(12, novo.getPassword().toCharArray());
                novo.setPassword(senhaHash);

                userService.criar(novo, "default");
                res.status(201);
                return gson.toJson("Usuário criado com sucesso");
            });

            // PUT /usuario/:id - atualiza usuário existente
            put("/:id", (req, res) -> {
                res.type("application/json");
                int id;
                try {
                    id = Integer.parseInt(req.params(":id"));
                } catch (NumberFormatException e) {
                    res.status(400);
                    return gson.toJson("ID inválido");
                }

                User atualizado = gson.fromJson(req.body(), User.class);
                atualizado.setId(id);

                if (atualizado.getUsername() == null || atualizado.getUsername().isEmpty()) {
                    res.status(400);
                    return gson.toJson("Username é obrigatório");
                }
                if (atualizado.getEmail() == null || atualizado.getEmail().isEmpty()) {
                    res.status(400);
                    return gson.toJson("Email é obrigatório");
                }
                if (atualizado.getPassword() == null || atualizado.getPassword().isEmpty()) {
                    res.status(400);
                    return gson.toJson("Senha é obrigatória");
                }

                /******************************************************
                 * A PARTE DE CRIPTOGRAFIA DA SENHA NO BANCO DE DADOS *
                 *****************************************************/

                // transforma em hash passando pela funcao, quanto maior o fator +dificil quebrar
                // aqui o fator do hash eh 12

                String senhaHash = BCrypt.withDefaults()
                    .hashToString(12, atualizado.getPassword().toCharArray());
                atualizado.setPassword(senhaHash);

                userService.atualizar(atualizado);
                return gson.toJson("Usuário atualizado com sucesso");
            });

            // DELETE /usuario/:id - deleta usuário
            delete("/:id", (req, res) -> {
                res.type("application/json");
                int id;
                try {
                    id = Integer.parseInt(req.params(":id"));
                } catch (NumberFormatException e) {
                    res.status(400);
                    return gson.toJson("ID inválido");
                }

                boolean deletado = userService.deletar(id);
                if (deletado) {
                    return gson.toJson("Usuário deletado com sucesso");
                } else {
                    res.status(404);
                    return gson.toJson("Usuário não encontrado");
                }
            });

        });
    }
}
