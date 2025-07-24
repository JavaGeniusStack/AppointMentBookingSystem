package com.truemed.Abs.Exception;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
private LocalDateTime timeStamp;
private String message;
private String Details;
}
