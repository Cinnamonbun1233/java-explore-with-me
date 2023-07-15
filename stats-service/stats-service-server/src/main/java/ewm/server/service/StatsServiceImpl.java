package ewm.server.service;

import ewm.dto.StatsRequestDto;
import ewm.dto.StatsResponseDto;
import ewm.server.mapper.StatsMapper;
import ewm.server.repo.StatsRepo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
@Slf4j
public class StatsServiceImpl implements StatsService {
    private static final DateTimeFormatter REQUEST_TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    private final StatsRepo statsRepo;

    @Autowired
    public StatsServiceImpl(StatsRepo statsRepo) {
        this.statsRepo = statsRepo;
    }

    @Override
    @Transactional
    public void saveRecord(StatsRequestDto statsRequestDto) {
        log.info("RECORD SAVED");
        statsRepo.save(StatsMapper.mapRequestToModel(statsRequestDto));
    }

    @Override
    public List<StatsResponseDto> getStats(LocalDateTime statsPeriodStartLdt, LocalDateTime statsPeriodEndLdt, String[] uris, String unique) {
        if (unique == null && uris == null) {
            return statsRepo.getStatsForDates(statsPeriodStartLdt, statsPeriodEndLdt);
        } else if (unique != null && uris == null) {
            if (Boolean.parseBoolean(unique)) {
                return statsRepo.getStatsForDatesWithUniqueIp(statsPeriodStartLdt, statsPeriodEndLdt);
            } else {
                return statsRepo.getStatsForDates(statsPeriodStartLdt, statsPeriodEndLdt);
            }
        } else if (unique == null) {
            return statsRepo.getStatsForDatesAndUris(statsPeriodStartLdt, statsPeriodEndLdt, uris);
        } else {
            if (Boolean.parseBoolean(unique)) {
                return statsRepo.getStatsForDatesAndUrisWithUniqueIp(statsPeriodStartLdt, statsPeriodEndLdt, uris);
            } else {
                return statsRepo.getStatsForDatesAndUris(statsPeriodStartLdt, statsPeriodEndLdt, uris);
            }
        }
    }

    private LocalDateTime parseDateTime(String dateTime) {
        return LocalDateTime.parse(dateTime, REQUEST_TIME_FORMAT);
    }
}