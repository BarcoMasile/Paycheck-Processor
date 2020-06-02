package xyz.marcobasile.paycheckprocessor.resource.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.pdfbox.pdfparser.PDFStreamParser;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.server.reactive.ServerHttpRequest;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.function.BiFunction;

@Slf4j
public class RxRequestUtils {

    public static Mono<ByteBuffer> collectBuffer(ServerHttpRequest request) {

        return request.getBody()
                .map(DataBuffer::asByteBuffer)
                .map(byteBuffer -> {
                    byte[] bytes = new byte[byteBuffer.capacity()];
                    byteBuffer.get(bytes);
                    return bytes;
                })
                .reduce(new ByteArrayOutputStream(), collectByteArrays())
                .map(ByteArrayOutputStream::toByteArray)
                .map(ByteBuffer::wrap);
    }

    public static Mono<ByteBuffer> convertPDFToImage(ByteBuffer pdfByteBuffer) {

        return Mono.just(pdfByteBuffer.array())
                .publishOn(Schedulers.boundedElastic())
                .map(bytes -> {
                    try {
                        return PDDocument.load(bytes);
                    } catch (IOException e) {
                        log.error("Impossibile leggere il file pdf");
                        return new PDDocument();
                    }
                }).map(PDFRenderer::new)
                .map(pdfRenderer -> {
                    try {
                        return pdfRenderer.renderImage(0);
                    } catch (IOException e) {
                        log.error("Impossibile convertire pdf in immagine");
                        return new BufferedImage(1,1, 0);
                    }
                }).map(bufferedImage -> {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    try {
                        ImageIO.write(bufferedImage, "png", baos);
                        return ByteBuffer.wrap(baos.toByteArray());
                    } catch (IOException e) {
                        log.error("Impossible convertire bufferedImage in ByteBuffer");
                        return (ByteBuffer)null;
                    }
                });
    }

    private static BiFunction<ByteArrayOutputStream, byte[], ByteArrayOutputStream> collectByteArrays() {

        return (baos, byteArray) -> {
            try {
                baos.write(byteArray);
            } catch (IOException e) {
                log.error("Byte persi: {}", byteArray.length);
                return baos;
            }

            return baos;
        };
    }
}
