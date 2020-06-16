package edu.utn.TpCellphone.service;

import edu.utn.TpCellphone.dto.CallAddDto;
import edu.utn.TpCellphone.model.Call;
import edu.utn.TpCellphone.repository.CallRepository;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class CallService {
    
    private final CallRepository CALL_REPOSITORY;
    
    public CallService(CallRepository CALL_REPOSITORY) {
        this.CALL_REPOSITORY = CALL_REPOSITORY;
    }
    
    public Optional<Call> getById(Integer idCall) {
        return CALL_REPOSITORY.findById(idCall);
    }
    
    public List<Call> getAll() {
        return CALL_REPOSITORY.findAll();
    }
    
    public void addCall(CallAddDto callDto) throws SQLException {
        CALL_REPOSITORY.addCall(callDto.getNumberOrigin(), callDto.getNumberDestination(), callDto.getStartTime(), callDto.getEndTime());
    }
}
