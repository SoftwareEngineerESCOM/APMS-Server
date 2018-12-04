package com.apms.practiceRelation;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.apms.rest.RESTRequest;
import com.apms.rest.RESTResponse;

@RestController
@RequestMapping("/practiceRelation")
public class PracticeRelationRestController {
	
    @Autowired
    private PracticeRelationService practiceRelationService;

    /*
     ** Return a listing of all the resources
     */
    @GetMapping
    public RESTResponse<List<PracticeRelation>> getAll() {
        List<PracticeRelation> res;
        try {
            res = practiceRelationService.getAll();
        } catch (Exception e) {
            e.printStackTrace();
            return new RESTResponse<List<PracticeRelation>>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.",
                    null);
        }
        if (!res.isEmpty()) {
            return new RESTResponse<List<PracticeRelation>>(RESTResponse.OK, "", res);
        } else {
            return new RESTResponse<List<PracticeRelation>>(RESTResponse.FAIL,
                    "Servicios no disponibles.", null);
        }
    }

    /*
     ** Return one resource
     */
    @GetMapping("/{id}")
    public RESTResponse<PracticeRelation> getOne(@PathVariable Integer id) {
        PracticeRelation res;
        try {
            res = practiceRelationService.getOne(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new RESTResponse<PracticeRelation>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.",
                    null);
        }
        if (res != null) {
            return new RESTResponse<PracticeRelation>(RESTResponse.OK, "", res);
        } else {
            return new RESTResponse<PracticeRelation>(RESTResponse.FAIL, "Relación de Prácticas no registrada.", null);
        }
    }

    /*
     ** Store a newly created resource in storage.
     */
    @PostMapping
    public RESTResponse<PracticeRelation> post(@RequestBody RESTRequest<PracticeRelation> req) {
        try {
            if (practiceRelationService.getOne(req.getPayload().getId()) != null)
                return new RESTResponse<PracticeRelation>(RESTResponse.FAIL, "La Relación de Prácticas ya existe en el sistema.", null);

            if (req.getPayload().getPractices() != null) {
                for (int i = 0; i < req.getPayload().getTopics().size(); i++) {
                }
            }
            practiceRelationService.add(req.getPayload());
        } catch (Exception e) {
            e.printStackTrace();
            return new RESTResponse<PracticeRelation>(RESTResponse.FAIL,
                    "Por el momento no se puede realizar el registro.", null);
        }
        return new RESTResponse<PracticeRelation>(RESTResponse.OK, "Registro finalizado exitosamente.", null);
    }

    /*
     ** Update the specified resource in storage partially.
     */
    @PatchMapping
    public RESTResponse<PracticeRelation> patch(@RequestBody RESTRequest<PracticeRelation> req) {
        try {
            practiceRelationService.update(req.getPayload());
        } catch (Exception e) {
            e.printStackTrace();
            return new RESTResponse<PracticeRelation>(RESTResponse.FAIL,
                    "Hubo un error al modificar. Por favor, intentelo mas tarde.", null);
        }
        return new RESTResponse<PracticeRelation>(RESTResponse.OK, "Los cambios se guardaron exitosamente.", null);
    }

    /*
     ** Update the specified resource in storage.
     */
    @PutMapping
    public RESTResponse<PracticeRelation> put(@RequestBody RESTRequest<PracticeRelation> req) {
        try {
            practiceRelationService.update(req.getPayload());
        } catch (Exception e) {
            e.printStackTrace();
            return new RESTResponse<PracticeRelation>(RESTResponse.FAIL,
                    "Hubo un error al modificar. Por favor, intentelo mas tarde.", null);
        }
        return new RESTResponse<PracticeRelation>(RESTResponse.OK, "Los cambios se guardaron exitosamente.", null);
    }

    /*
     ** Remove the specified resource from storage.
     */
    @DeleteMapping("/{id}")
    public RESTResponse<PracticeRelation> delete(@PathVariable Integer id) {
        try {
            practiceRelationService.delete(id);
        } catch (Exception e) {
            e.printStackTrace();
            return new RESTResponse<PracticeRelation>(RESTResponse.FAIL,
                    "Por el momento no se puede realizar el registro.", null);
        }
        return new RESTResponse<PracticeRelation>(RESTResponse.OK, "Los cambios se guardaron exitosamente.", null);
    }
}
