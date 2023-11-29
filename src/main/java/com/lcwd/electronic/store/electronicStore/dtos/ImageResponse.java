package com.lcwd.electronic.store.electronicStore.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ImageResponse {
    private String img;
    private String massage;
    private boolean status;
    private HttpStatus httpStatus;
}
