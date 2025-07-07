package controller;

import com.google.gson.Gson;

import dao.RotinaDAO;
import model.RotinaModel;
import static spark.Spark.delete;
import static spark.Spark.get;
import static spark.Spark.path;
import static spark.Spark.post;
import static spark.Spark.put;

public class RotinaController {
    private static Gson gson = new Gson();
    private static RotinaDAO dao = new RotinaDAO();

    public static void configurarRotas() {
        path("/atividades", () -> {

            get("", (req, res) -> {
                res.type("application/json");
                return gson.toJson(dao.listar());
            });

            get("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params(":id"));
                RotinaModel rotina = dao.buscarPorId(id);
                if (rotina != null) {
                    return gson.toJson(rotina);
                } else {
                    res.status(404);
                    return gson.toJson(new ResponseMessage("Atividade não encontrada"));
                }
            });

            post("", (req, res) -> {
                RotinaModel nova = new RotinaModel();
                nova.setNome(req.queryParams("nome"));
                nova.setDescricao(req.queryParams("descricao"));
                nova.setHorario(req.queryParams("horario"));
                
                dao.inserir(nova);
                res.redirect("/pages/atividades.html");
                return gson.toJson(new ResponseMessage("Atividade criada com sucesso"));
            });

            put("/:id", (req, res) -> {
                int id = Integer.parseInt(req.params(":id"));
            
                RotinaModel atualizada = new RotinaModel();
                atualizada.setId(id);
                atualizada.setNome(req.queryParams("nome"));
                atualizada.setDescricao(req.queryParams("descricao"));
                atualizada.setHorario(req.queryParams("horario"));
            
                dao.atualizar(atualizada);
                res.redirect("/pages/atividades.html");
                return gson.toJson(new ResponseMessage("Atividade atualizada com sucesso"));
            });

            delete("/:id", (req, res) -> {
                res.type("application/json");
                int id = Integer.parseInt(req.params(":id"));
                dao.deletar(id);
                return gson.toJson(new ResponseMessage("Atividade deletada com sucesso"));
            });

        });
    }

    public static class ResponseMessage {
        private String message;

        public ResponseMessage(String message) {
            this.message = message;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
