package com.lcwd.electronic.store.electronicStore.dtos;

import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponce {
    private String message;
    private boolean sucess;
    private HttpStatus status;
}
