package com.ecoroute.ecoroute.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Este DTO será usado para receber os dados da requisição POST
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItinerarioDTO {

    private int responsavelId;
    private int caminhaoId;
    private int origemId;
    private int destinoId;
    private String dataExecucao;
    private int residuoId;
}
