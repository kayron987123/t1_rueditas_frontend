package pe.edu.cibertec.apprueditas.front_end.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import pe.edu.cibertec.apprueditas.front_end.dto.SearchRequestDTO;
import pe.edu.cibertec.apprueditas.front_end.dto.SearchResponseDTO;
import pe.edu.cibertec.apprueditas.front_end.viewmodel.SearchModel;

@Controller
@RequestMapping("/busqueda")
public class SearchController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/inicio")
    public String inicio(Model model) {
        SearchModel searchModel = new SearchModel("00", "");
        model.addAttribute("searchModel", searchModel);
        return "inicioView";
    }

    @PostMapping("/placa")
    public String buscarPlaca(@RequestParam("placa") String placa, Model model) {
        if (placa == null || placa.trim().length() == 0 || placa.trim().length() != 7 ||
                !placa.matches("^[A-Za-z0-9-]+$")) {
            SearchModel searchModel = new SearchModel("01", "Error: Debe ingresar una placa correcta");
            model.addAttribute("searchModel", searchModel);
            return "inicioView";
        }

        try {
            String endpoint = "http://localhost:8090/placas/search";

            SearchRequestDTO searchRequest = new SearchRequestDTO(placa);
            SearchResponseDTO searchResponse = restTemplate.postForObject(endpoint, searchRequest, SearchResponseDTO.class);

            if(searchResponse.codigo().equals("00")) {
                SearchModel searchModel = new SearchModel("00", "");
                model.addAttribute("searchModel", searchModel);
                return "busqueda";
            }else {
                SearchModel searchModel = new SearchModel("02", "Error: Busqueda fallida");
                model.addAttribute("searchModel", searchModel);
                return "inicioView";
            }
        } catch (Exception e) {
            SearchModel searchModel = new SearchModel("99", "Error: Ocurrio un problema en la busqueda");
            model.addAttribute("searchModel", searchModel);
            return "inicioView";
        }
    }
}
