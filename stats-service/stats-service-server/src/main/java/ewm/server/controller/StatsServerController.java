package ewm.server.controller;

import ewm.dto.DateTimeFormatterFactory;
import ewm.dto.StatsRequestDto;
import ewm.dto.StatsResponseDto;
import ewm.server.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class StatsServerController {
    private final StatsService statsService;

    @Autowired
    public StatsServerController(StatsService statsService) {
        this.statsService = statsService;
    }

    @PostMapping(value = "/hit")
    public void addHit(@RequestBody StatsRequestDto statsRequestDto) {
        statsService.saveRecord(statsRequestDto);
    }

    @GetMapping("/stats")
    public List<StatsResponseDto> getStats(@RequestParam("start") String statsPeriodStart,
                                           @RequestParam("end") String statsPeriodEnd,
                                           @RequestParam(value = "uris", required = false) String[] uris,
                                           @RequestParam(value = "unique", required = false) String unique) {
        LocalDateTime statsPeriodStartLdt = LocalDateTime.parse(statsPeriodStart, DateTimeFormatterFactory.getDefaultDateTimeFormatter());
        LocalDateTime statsPeriodEndLdt = LocalDateTime.parse(statsPeriodEnd, DateTimeFormatterFactory.getDefaultDateTimeFormatter());
        return statsService.getStats(statsPeriodStartLdt, statsPeriodEndLdt, uris, unique);
    }
}