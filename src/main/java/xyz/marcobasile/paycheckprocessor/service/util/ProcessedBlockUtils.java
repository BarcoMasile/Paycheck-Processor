package xyz.marcobasile.paycheckprocessor.service.util;

import com.amazonaws.services.textract.model.Block;
import com.amazonaws.services.textract.model.Relationship;
import lombok.extern.slf4j.Slf4j;
import xyz.marcobasile.paycheckprocessor.model.ProcessedBlock;

import java.util.*;
import java.util.stream.Collectors;


public class ProcessedBlockUtils {


    public static void populateMaps(List<ProcessedBlock> blocks,
                                    HashMap<String, String> textMap,
                                    HashMap<String, ProcessedBlock> blockMap) {

        /* KEY_VALUE_SET, CELL, TABLE, WORD, LINE, PAGE */
        blocks.forEach(block -> {

            if (block.getType().equals("WORD")) {
                textMap.put(block.getId(), block.getText());
            }

            blockMap.put(block.getId(), block);
        });
    }

    public static ProcessedBlock processedBlock(Block block) {

        List<Relationship> relationships = Optional.ofNullable(block.getRelationships())
                .orElse(Collections.emptyList());

        return ProcessedBlock.builder()
                .id(block.getId())
                .type(block.getBlockType())
                .text(block.getText())
                .entityTypes(block.getEntityTypes())
                .relationships(relationships
                        .stream()
                        .map(ProcessedBlockUtils::relationship)
                        .collect(Collectors.toList()))
                .build();
    }

    public static ProcessedBlock.Relationship relationship(Relationship rel) {

        return ProcessedBlock.Relationship.builder()
                .type(rel.getType())
                .ids(rel.getIds())
                .build();
    }

    public static Map<String, String> generateKeyValueMap(List<ProcessedBlock> blocks,
                                                          HashMap<String, ProcessedBlock> blockMap,
                                                          HashMap<String, String> textMap) {

        return blocks.stream()
                .filter(b -> b.getType().equals("KEY_VALUE_SET"))
                .filter(b -> b.getEntityTypes().get(0).equals("KEY"))
                .collect(Collectors.toMap(
                        b -> childrenString(b, textMap),
                        b -> valueChildrenString(b, textMap, blockMap),
                        (uno, due) -> String.join("|")
                    )
                );
    }

    private static String valueChildrenString(ProcessedBlock b,
                                              HashMap<String, String> textMap,
                                              HashMap<String, ProcessedBlock> blockMap) {

        return b.getRelationships().stream()
                .filter(relationship -> relationship.getType().equals("VALUE"))
                .map(ProcessedBlock.Relationship::getIds)
                .map(ids -> ids.get(0))
                .map(id -> childrenString(blockMap.get(id), textMap))
                .findFirst().orElse("");
    }


    private static String childrenString(ProcessedBlock block, HashMap<String, String> textMap) {

        return block.getRelationships().stream()
                .filter(relationship -> relationship.getType().equals("CHILD"))
                .flatMap(relationship -> relationship.getIds().stream())
                .map(id -> textMap.getOrDefault(id, null))
                .filter(Objects::nonNull)
                .collect(Collectors.joining(" "));
    }
}
