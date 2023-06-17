package gwkim.storemanage.api.member.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberResponseDto {
    private String id;
    private String name;
    private LocalDateTime localDateTime = LocalDateTime.now();

    public MemberResponseDto(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
