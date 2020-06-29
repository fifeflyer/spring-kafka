package com.example.kafka.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder(builderMethodName = "timeline")
@NoArgsConstructor
@AllArgsConstructor
public class Timeline {

    private String id;
}
