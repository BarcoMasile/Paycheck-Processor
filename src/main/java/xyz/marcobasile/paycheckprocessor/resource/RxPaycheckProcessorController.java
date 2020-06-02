package xyz.marcobasile.paycheckprocessor.resource;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;
import xyz.marcobasile.paycheckprocessor.model.Paycheck;
import xyz.marcobasile.paycheckprocessor.resource.util.RxRequestUtils;
import xyz.marcobasile.paycheckprocessor.service.PaycheckExtractionService;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Objects;
import java.util.stream.Stream;

@Slf4j
@RequiredArgsConstructor
@Profile("rx-controllers")
@RestController
@RequestMapping(path = "/paycheck-processor")
public class RxPaycheckProcessorController {

    private final PaycheckExtractionService extractioner;

    @PostMapping(consumes = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    @ResponseStatus(code = HttpStatus.OK, reason = "Paycheck image processed successfully")
    public Mono<Paycheck> processImage(ServerHttpRequest request) {

        log.info("Received bufferedImage");
        return RxRequestUtils.collectBuffer(request)
                .flatMap(extractioner::paycheckFromImage);
    }

    @PostMapping(consumes = { MediaType.APPLICATION_PDF_VALUE })
    @ResponseStatus(code = HttpStatus.OK, reason = "Paycheck image processed successfully")
    public Mono<Paycheck> processPDF(ServerHttpRequest request) {

        log.info("Received pdf file");
        return RxRequestUtils.collectBuffer(request)
                .flatMap(RxRequestUtils::convertPDFToImage)
                .flatMap(extractioner::paycheckFromImage);

    }
}

