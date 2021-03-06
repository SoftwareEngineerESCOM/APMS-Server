package com.apms.humanResource;

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

import com.apms.rest.RESTRequest;import java.util.logging.Logger;
import com.apms.rest.RESTResponse;

import com.apms.user.UserService;
import com.apms.user.User;

@RestController
@RequestMapping("/humanResource")
public class HumanResourceRestController {

    @Autowired
    private HumanResourceService humanResourceService;
    @Autowired
    private UserService userService;

    /*
     ** Return a listing of all the resources
     */
    @GetMapping
    public RESTResponse<List<HumanResource>> getAll() {
        List<HumanResource> res;
        try {
            res = humanResourceService.getAll();
        } catch (Exception e) {
            Logger.getLogger(null).log(null,"F: ",e);
            return new RESTResponse<List<HumanResource>>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.",
                    null);
        }
        if (!res.isEmpty()) {
            return new RESTResponse<List<HumanResource>>(RESTResponse.OK, "", res);
        } else {
            return new RESTResponse<List<HumanResource>>(RESTResponse.FAIL,
                    "Servicios no disponibles.", null);
        }
    }

    /*
     ** Return one resource
     */
    @GetMapping("/{id}")
    public RESTResponse<HumanResource> getOne(@PathVariable Integer id) {
        HumanResource res;
        try {
            res = humanResourceService.getOne(id);
        } catch (Exception e) {
            Logger.getLogger(null).log(null,"F: ",e);
            return new RESTResponse<HumanResource>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
        }
        if (res != null) {
            return new RESTResponse<HumanResource>(RESTResponse.OK, "", res);
        } else {
            return new RESTResponse<HumanResource>(RESTResponse.FAIL, "Recurso humano no registrado.", null);
        }
    }

    /*
     ** Store a newly created resource in storage.
     */
    @PostMapping
    public RESTResponse<HumanResource> post(@RequestBody RESTRequest<HumanResource> humanResource) {
        try {
            if (humanResourceService.getOne(humanResource.getPayload().getId()) != null)
                return new RESTResponse<HumanResource>(RESTResponse.FAIL, "El recurso humano ya existe en el sistema.",
                        null);
            humanResourceService.add(humanResource.getPayload());
        } catch (Exception e) {
            Logger.getLogger(null).log(null,"F: ",e);
            return new RESTResponse<HumanResource>(RESTResponse.FAIL,
                    "Por el momento no se puede realizar el registro.", null);
        }
        return new RESTResponse<HumanResource>(RESTResponse.OK, "Registro finalizado exitosamente.", null);
    }

    /*
     ** Update the specified resource in storage partially.
     */
    @PatchMapping
    public RESTResponse<HumanResource> patch(@RequestBody RESTRequest<HumanResource> humanResource) {
        try {
            humanResourceService.update(humanResource.getPayload());
        } catch (Exception e) {
            Logger.getLogger(null).log(null,"F: ",e);
            return new RESTResponse<HumanResource>(RESTResponse.FAIL,
                    "Hubo un error al modificar. Por favor, intentelo mas tarde.", null);
        }
        return new RESTResponse<HumanResource>(RESTResponse.OK, "Los cambios se guardaron exitosamente.", null);
    }

    /*
     ** Update the specified resource in storage.
     */
    @PutMapping
    public RESTResponse<HumanResource> put(@RequestBody RESTRequest<HumanResource> humanResource) {
        try {
            humanResourceService.update(humanResource.getPayload());
        } catch (Exception e) {
            Logger.getLogger(null).log(null,"F: ",e);
            return new RESTResponse<HumanResource>(RESTResponse.FAIL,
                    "Hubo un error al modificar. Por favor, intentelo mas tarde.", null);
        }
        return new RESTResponse<HumanResource>(RESTResponse.OK, "Los cambios se guardaron exitosamente.", null);
    }


    /*
     ** Remove the specified resource from storage.
     */
    @DeleteMapping("/{id}")
    public RESTResponse<HumanResource> delete(@PathVariable Integer id) {
        try {
            User auxUser = userService.getUserByHumanResource(id);
            if (auxUser == null) {
                humanResourceService.delete(id);
            } else {
               return new RESTResponse<HumanResource>(RESTResponse.FAIL,
                       "Este Recurso Humano esta asociado a un Usuario por lo que no puede ser eliminado.", null);
            }

        } catch (Exception e) {
            Logger.getLogger(null).log(null,"F: ",e);
            return new RESTResponse<HumanResource>(RESTResponse.FAIL,
                    "Por el momento no se puede realizar el registro.", null);
        }
        return new RESTResponse<HumanResource>(RESTResponse.OK, "Los cambios se guardaron exitosamente.", null);
    }

    @GetMapping("/humanResourcesByWorkplaceIdAndPositionId/{idW}/{idP}")
    public RESTResponse<List<HumanResource>> getHumanResourcesByWorkplaceIdAndPositionId(@PathVariable Integer idW,
                                                                                         @PathVariable Integer idP) {
        List<HumanResource> res;
        try {
            res = humanResourceService.getHumanResourcesByWorkplaceIdAndPositionId(idW, idP);
        } catch (Exception e) {
            Logger.getLogger(null).log(null,"F: ",e);
            return new RESTResponse<List<HumanResource>>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
        }
        if (!res.isEmpty()) {
            return new RESTResponse<List<HumanResource>>(RESTResponse.OK, "", res);
        } else {
            return new RESTResponse<List<HumanResource>>(RESTResponse.FAIL, "No hay recursos humanos con ese cargo", res);
        }
    }

    @GetMapping("/humanResourcesByWorkplaceId/{id}")
    public RESTResponse<List<HumanResource>> getHumanResourcesByWorkplaceId(@PathVariable Integer id) {
        return new RESTResponse<List<HumanResource>>(RESTResponse.OK, "",
                humanResourceService.getHumanResourcesByWorkplaceId(id));
    }

    @GetMapping("/humanResourceByName/{name}/{first_surname}/{second_surname}")
    public RESTResponse<HumanResource> humanResourceByName(@PathVariable String name,
                                                           @PathVariable String first_surname,
                                                           @PathVariable String second_surname) {
        HumanResource res;
        try {
            res = humanResourceService.getOne(name, first_surname, second_surname);
        } catch (Exception e) {
            Logger.getLogger(null).log(null,"F: ",e);
            return new RESTResponse<HumanResource>(RESTResponse.DBFAIL, "Inconsistencia en la base de datos.", null);
        }
        if (res != null) {
            return new RESTResponse<HumanResource>(RESTResponse.OK, "", res);
        } else {
            return new RESTResponse<HumanResource>(RESTResponse.FAIL, "Recurso Humano no registrado.", null);
        }
    }

}
