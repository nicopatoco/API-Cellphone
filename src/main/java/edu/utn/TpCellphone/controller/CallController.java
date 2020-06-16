package edu.utn.TpCellphone.controller;

import edu.utn.TpCellphone.dto.CallAddDto;
import edu.utn.TpCellphone.dto.CallDto;
import edu.utn.TpCellphone.exceptions.CallNotFoundException;
import edu.utn.TpCellphone.model.Call;
import edu.utn.TpCellphone.service.CallService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
        System.out.println(call.get().getEndTime());
        ResponseEntity<CallDto> responseEntity;
        if (!call.isEmpty()) {
            CallDto callDto = new CallDto(call.get().getNumberOrigin(), call.get().getNumberDestination(), call.get().getDuration());
            responseEntity = ResponseEntity.ok(callDto);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new CallNotFoundException();
        }
        return responseEntity;
    }
    
    @GetMapping("/")
    public ResponseEntity<List<CallDto>> getAllCalls() throws CallNotFoundException {
        List<Call> calls = CALL_SERVICE.getAll();
        ResponseEntity<List<CallDto>> responseEntity;
        if (!calls.isEmpty()) {
            List<CallDto> callsDto = new ArrayList<>();
            for (Call c : calls) {
                callsDto.add(new CallDto(c.getNumberOrigin(), c.getNumberDestination(), c.getDuration()));
            }
            responseEntity = ResponseEntity.ok(callsDto);
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            throw new CallNotFoundException();
        }
        return responseEntity;
    }
    
    @PostMapping("/")
    public ResponseEntity addCall(@RequestBody CallAddDto call) throws SQLException {
        try{
            URI local = new URI("localhost:8080/call/");
            CALL_SERVICE.addCall(call);
            return ResponseEntity.created(local).body(call);
        } catch (SQLException | URISyntaxException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
