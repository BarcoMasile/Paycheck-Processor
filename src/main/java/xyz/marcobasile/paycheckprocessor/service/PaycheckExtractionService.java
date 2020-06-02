package xyz.marcobasile.paycheckprocessor.service;

import com.amazonaws.services.textract.AmazonTextract;
import com.amazonaws.services.textract.model.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import xyz.marcobasile.paycheckprocessor.model.Paycheck;
import xyz.marcobasile.paycheckprocessor.model.ProcessedBlock;
import xyz.marcobasile.paycheckprocessor.service.util.PaycheckBuilderUtils;
import xyz.marcobasile.paycheckprocessor.service.util.ProcessedBlockUtils;

import java.nio.ByteBuffer;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class PaycheckExtractionService {

    private final AmazonTextract textract;

    public Mono<Paycheck> paycheckFromImage(ByteBuffer image) {

        log.info("image bytes {}", image.array().length);
        return analyzeDocument(image)
                .map(this::generateMapFromDocument)
                .map(PaycheckBuilderUtils::paycheckFromMap);

    }

    private Map<String,String> generateMapFromDocument(AnalyzeDocumentResult result) {

        HashMap<String, String> textMap = new HashMap<>();
        HashMap<String, ProcessedBlock> blockMap = new HashMap<>();

        List<ProcessedBlock> processedBlocks = result.getBlocks()
                .stream()
                .map(ProcessedBlockUtils::processedBlock)
                .collect(Collectors.toList());

        ProcessedBlockUtils.populateMaps(processedBlocks, textMap, blockMap);

        return ProcessedBlockUtils.generateKeyValueMap(processedBlocks, blockMap, textMap);
    }

    private Mono<AnalyzeDocumentResult> analyzeDocument(ByteBuffer image) {

        final AnalyzeDocumentRequest analyzeDocRequest = new AnalyzeDocumentRequest()
                .withFeatureTypes(FeatureType.TABLES, FeatureType.FORMS)
                .withDocument(new Document().withBytes(image));

        return Mono.just(textract.analyzeDocument(analyzeDocRequest));
    }
}
