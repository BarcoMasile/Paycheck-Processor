package xyz.marcobasile.paycheckprocessor.resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;
import xyz.marcobasile.paycheckprocessor.model.Paycheck;

import java.awt.image.BufferedImage;

@Slf4j
@Profile("rsocket-controllers")
@Controller
public class RSocketPaycheckProcessorController {

    @MessageMapping("paycheck-processor-image")
    public Paycheck processImage(BufferedImage bufferedImage) {

        return null;
    }

    @MessageMapping("paycheck-processor-pdf")
    public Paycheck processPDF(BufferedImage bufferedImage) {

        return null;
    }
}
