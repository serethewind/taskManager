package com.serethewind.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CustomTaskResponse {
    private List<TaskResponse> content;
    private int pageNo;
    private int pageSize;
    private long totalElements;
    private int totalPages;

    private boolean last;

}
