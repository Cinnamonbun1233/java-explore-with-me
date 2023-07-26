package ewm.server.mapper.compilation;

import ewm.client.StatsClient;
import ewm.server.dto.compilation.CompilationDto;
import ewm.server.dto.compilation.NewCompilationDto;
import ewm.server.mapper.event.EventMapper;
import ewm.server.model.compilation.Compilation;

import java.util.stream.Collectors;

public class CompilationMapper {
    public static Compilation mapDtoToModel(NewCompilationDto newCompilationDto) {
        Compilation compilation = new Compilation();
        compilation.setPinned(newCompilationDto.getPinned() != null && newCompilationDto.getPinned());
        compilation.setTitle(newCompilationDto.getTitle());
        return compilation;
    }

    public static CompilationDto mapModelToDto(Compilation compilation, StatsClient statsClient) {
        return CompilationDto
                .builder()
                .id(compilation.getCompilationId())
                .title(compilation.getTitle())
                .pinned(compilation.getPinned())
                .events(compilation
                        .getEvents()
                        .stream()
                        .map(e -> EventMapper.mapModelToShortDto(e, statsClient))
                        .collect(Collectors.toList()))
                .build();
    }
}