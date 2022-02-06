package com.example.mummoomserver.domain.Report.dto;

import com.example.mummoomserver.domain.Report.Report;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReportSaveRequestDto {
    private String reason;

    @Builder
    public ReportSaveRequestDto(String reason){
        this.reason = reason;
    }

    public Report toEntity(){
        return Report.builder()
                .reason(reason)
                .build();
    }
}
