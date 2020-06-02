package xyz.marcobasile.paycheckprocessor.model;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ProcessedBlock {

    private final String id;
    private final String type;
    private final String text;
    private final List<String> entityTypes;
    private final List<Relationship> relationships;

    @Getter
    @Builder
    public static class Relationship {
        private final String type;
        private final List<String> ids;
    }
}
