package modules.controller;

import modules.entity.CuotaEntity;
import modules.service.CuotaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuota")
public class CuotaController {

    @Autowired
    CuotaService cuotaService;

    // mostrar todas las cuotas
    @GetMapping
    public ResponseEntity<List<CuotaEntity>> getAll() {
        List<CuotaEntity> cuotas = cuotaService.obtenerCuotas();
        if(cuotas.isEmpty())
            return ResponseEntity.noContent().build();
        return ResponseEntity.ok(cuotas);
    }

    // guardar una cuota
    @PostMapping()
    public ResponseEntity<CuotaEntity> save(@RequestBody CuotaEntity cuota) {
        CuotaEntity cuotaNew = cuotaService.guardarCuota(cuota);
        return ResponseEntity.ok(cuotaNew);
    }








}
