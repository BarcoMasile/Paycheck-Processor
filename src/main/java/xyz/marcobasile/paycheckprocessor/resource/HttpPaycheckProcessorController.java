package xyz.marcobasile.paycheckprocessor.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import xyz.marcobasile.paycheckprocessor.model.Paycheck;

import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

@Slf4j
@Profile("http-controllers")
@RestController
@RequestMapping(path = "/paycheck-processor")
public class HttpPaycheckProcessorController {

    @PostMapping(consumes = { MediaType.IMAGE_JPEG_VALUE, MediaType.IMAGE_PNG_VALUE })
    @ResponseStatus(code = HttpStatus.OK, reason = "Paycheck image processed successfully")
    public Paycheck processImage(@RequestBody BufferedImage bufferedImage) {

        log.info("/paycheck-processor received bufferedImage of type: {}", bufferedImage.getType());
        return null;
    }

    @PostMapping(consumes = { MediaType.APPLICATION_PDF_VALUE })
    @ResponseStatus(code = HttpStatus.OK, reason = "Paycheck PDF processed successfully")
    public Paycheck processPDF(@RequestBody ByteArrayInputStream pdfBytes) {

        log.info("/paycheck-processor received bytes: {}", pdfBytes.readAllBytes().length);
        return null;
    }
}
