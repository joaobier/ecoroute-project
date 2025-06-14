package com.ecoroute.ecoroute.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

// Este DTO será usado para receber os dados da requisição POST
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItinerarioDTO {

    private int responsavelId; // ID do Usuario responsável
    private int caminhaoId;    // ID do Caminhao
    private int rotaId;        // ID da Rota
    private String dataExecucao;
    private String tiposResiduo;
}
