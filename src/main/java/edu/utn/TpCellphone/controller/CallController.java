package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.dto.CallDto;
import edu.utn.TpCellphone.exceptions.CallNotFoundException;
import edu.utn.TpCellphone.model.Call;
import edu.utn.TpCellphone.service.CallService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/call")
public class CallController {
    private final CallService CALL_SERVICE;
    
    public CallController(CallService CALL_SERVICE) {
        this.CALL_SERVICE = CALL_SERVICE;
    }
    
    @GetMapping("/{idCall}")
    public ResponseEntity<CallDto> getCallById(@PathVariable Integer idCall) throws CallNotFoundException {
        Optional<Call> call = CALL_SERVICE.getById(idCall);
        System.out.println(call);
        ResponseEntity<CallDto> responseEntity;
        if (!call.isEmpty()) {
            CallDto callDto = new CallDto(call.get().getStartTime(), call.get().getEndTime(), call.get().getNumber_origin(), call.get().getNumber_destination());
            responseEntity = ResponseEntity.ok(callDto);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new CallNotFoundException();
        }
        return responseEntity;
    }
}
