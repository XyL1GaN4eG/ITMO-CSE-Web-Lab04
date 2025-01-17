package web.endpoints;

import jakarta.enterprise.context.ApplicationScoped;
import web.exceptions.DatabaseOperationException;
import web.exceptions.UnauthorizedException;
import web.exceptions.UserAlreadyExistsException;
import web.model.UserCredentials;
import web.service.AuthService;

import jakarta.annotation.PostConstruct;
import jakarta.inject.Inject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.extern.slf4j.Slf4j;


@Path("/auth")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
@ApplicationScoped
public class AuthResource {
    @Inject
    AuthService authService;

    @PostConstruct
    void init() {
        log.info("AuthResource начал свою работу");
    }

    @POST
    @Path("/login")
    public Response login(UserCredentials credentials) {
        log.info("Попытка входа для пользователя: {}", credentials.username());
        try {
            var token = authService.login(
                    credentials.username(),
                    credentials.password()
            );
            log.info("Пользователь {} успешно вошел в систему", credentials.username());
            return Response.ok(token).build();
        } catch (UnauthorizedException unauthorizedException) {
            log.error("Попытка несанкционированного доступа для пользователя: {}", credentials.username());
            return Response.status(Response.Status.UNAUTHORIZED).entity(unauthorizedException.getMessage()).build();
        } catch (DatabaseOperationException databaseOperationException) {
            log.error("Ошибка при работе с базой данных при входе пользователя: {}", credentials.username(), databaseOperationException);
            return Response.status(Response.Status.UNAUTHORIZED).entity(databaseOperationException.getMessage()).build();
        }
    }

    @POST
    @Path("/reg")
    //todo: добавить валидацию пароля и юзернейма на длину
    //      можно использовать регулярки
    public Response registration(UserCredentials credentials) {
        log.info("Попытка регистрации для пользователя: {}", credentials.username());
        try {
            authService.registration(
                    credentials.username(),
                    credentials.password()
            );
            log.info("Пользователь {} успешно зарегистрирован", credentials.username());
            return Response.ok().build();
        } catch (UserAlreadyExistsException e) {
            log.error("Регистрация не удалась, пользователь с таким именем уже существует: {}", credentials.username());
            return Response.status(Response.Status.UNAUTHORIZED).entity(e.getMessage()).build();
        } catch (DatabaseOperationException err) {
            log.error("Ошибка при работе с базой данных при регистрации пользователя: {}", credentials.username(), err);
            return Response.status(Response.Status.UNAUTHORIZED).entity(err.getMessage()).build();
        }
    }

    @OPTIONS
    public Response preflight() {
        return Response.ok()
                .header("Access-Control-Allow-Origin", "http://213.171.27.51:8080")
                .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
                .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
                .build();
    }

}
