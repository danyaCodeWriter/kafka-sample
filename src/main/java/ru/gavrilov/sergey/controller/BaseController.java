package ru.gavrilov.sergey.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.gavrilov.sergey.exception.ServiceException;
import ru.gavrilov.sergey.service.KafkaSender;

/**
 * @author Danil_Slesarenko
 */
@RestController
@Slf4j
@RequestMapping(path = "/base")
@Api(value = "BASE", description = "Operations")
public class BaseController {

    @Autowired
    KafkaSender kafkaSender;

    @Value(value = "${secret.constant}")
    String secret;

    @GetMapping(path = "/string")
    @ApiOperation(value = "get it")
    @ApiResponses({
            @ApiResponse(code = 200, message = "Positive response"),
            @ApiResponse(code = 400, message = "Negative response")
    })
    public void getSmthng(@RequestParam("message") String message,
                          @RequestParam("password") String password) {
        log.debug("send message {}", message);
        validate(password);
        kafkaSender.send(message);
    }

    private void validate(@RequestParam("password") String password) {
        if (!secret.equals(password)) {
            throw new ServiceException("password is broker");
        }
    }
}
