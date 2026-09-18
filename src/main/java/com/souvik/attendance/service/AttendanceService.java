package com.souvik.attendance.service;

import com.souvik.attendance.model.AttendanceRecord;
import com.souvik.attendance.repository.AttendanceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AttendanceService {

    private final AttendanceRepository repository;

    public AttendanceService(AttendanceRepository repository) {
        this.repository = repository;
    }

    public List<AttendanceRecord> getAll() {
        return repository.findAll();
    }

    public List<AttendanceRecord> getByStudentId(String studentId) {
        return repository.findByStudentId(studentId);
    }

    public Optional<AttendanceRecord> getById(Long id) {
        return repository.findById(id);
    }

    public AttendanceRecord create(AttendanceRecord record) {
        return repository.save(record);
    }

    public boolean delete(Long id) {
        if (!repository.existsById(id)) {
            return false;
        }
        repository.deleteById(id);
        return true;
    }

    /**
     * Example of real business logic worth unit-testing:
     * percentage of PRESENT records for a given student.
     */
    public double calculateAttendancePercentage(String studentId) {
        List<AttendanceRecord> records = repository.findByStudentId(studentId);
        if (records.isEmpty()) {
            return 0.0;
        }
        long presentCount = records.stream()
                .filter(r -> "PRESENT".equalsIgnoreCase(r.getStatus()))
                .count();
        return (presentCount * 100.0) / records.size();
    }
}
