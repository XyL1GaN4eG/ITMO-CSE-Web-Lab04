package endpoints;

import exceptions.UnauthorizedException;
import jakarta.annotation.PostConstruct;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;
import model.Point;
import service.PointsService;

@Stateless // - ejb не хранит состояния между вызовами
@Path("/points")
@Produces(MediaType.APPLICATION_JSON) // - т.е. возвращает жсон
@Consumes(MediaType.APPLICATION_JSON) // - т.е. получает жсоны
@Slf4j
//todo: убрать страшный стактрейс при истечения срока действия токена
public class PointResource {
    @Inject
    private PointsService pointsService;

    @PostConstruct
    void init() {
        log.info("PointResource начал свою работу");
    }

    @GET
    public Response getAllPoints(@HeaderParam("Authorization") String authHeader) {
        log.info("Получен запрос на получение всех точек.");
        try {
            var token = extractToken(authHeader);
            var points = pointsService.getAllPoints(token);
            log.info("Успешно получены {} точек для токена: {}", points.size(), token);
            return Response.ok(points).build();
        } catch (Exception e) {
            log.error("Ошибка при получении всех точек: {}", e.getMessage(), e);
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    @POST
    @Path("/check")
    //fixme: если токен недействительный, то возвращать ошибку
    public Response checkPoint(Point point, @HeaderParam("Authorization") String authHeader) {
        log.info("Получен запрос на проверку точки: {}", point);
        try {
            var token = extractToken(authHeader);
            var checkedPoint = pointsService.checkPoint(point, token);
            log.info("Точка успешно проверена: {}", checkedPoint);
            return Response.ok(checkedPoint).build();
        } catch (UnauthorizedException exception) {
            return Response.status(Response.Status.UNAUTHORIZED).entity(exception.getCause()).build();
        } catch (Exception e) {
            log.error("Ошибка при проверке точки: {}", e.getMessage(), e);
            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
        }
    }

    @DELETE
    @Path("/clear")
    public Response clearPoints(@HeaderParam("Authorization") String authHeader) {
        log.info("Получен запрос на очистку точек.");
        try {
            var token = extractToken(authHeader);
            pointsService.clear(token);
            log.info("Все точки успешно очищены для токена: {}", token);
            return Response.ok().build();
        } catch (Exception e) {
            log.error("Ошибка при очистке точек: {}", e.getMessage(), e);
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        }
    }

    //todo: вынести в утил или еще чето такое
    private String extractToken(String authHeader) {
        log.info("Пытаемся извлечь токен из заголовка авторизации: {}", authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.error("Неверный токен или отсутствует заголовок авторизации.");
            throw new WebApplicationException("Invalid token", Response.Status.UNAUTHORIZED);
        }
        var token = authHeader.substring("Bearer ".length());
        log.info("Токен успешно извлечен: {}", token);
        return token;
    }
}
