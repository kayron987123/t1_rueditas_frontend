package pe.edu.cibertec.apprueditas.front_end.dto;

public record SearchResponseDTO(String codigo,
                                String mensaje,
                                String marca,
                                String modelo,
                                Integer nroAsientos,
                                Integer precio,
                                String color) {
}
